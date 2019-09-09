package org.knowm.xchange.binance;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.dto.meta.exchangeinfo.BinanceExchangeInfo;
import org.knowm.xchange.binance.dto.meta.exchangeinfo.Filter;
import org.knowm.xchange.binance.dto.meta.exchangeinfo.Symbol;
import org.knowm.xchange.binance.service.BinanceAccountService;
import org.knowm.xchange.binance.service.BinanceMarketDataService;
import org.knowm.xchange.binance.service.BinanceTradeService;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.utils.AuthUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class BinanceExchange extends BaseExchange {

    private static final Logger LOG = LoggerFactory.getLogger(BinanceExchange.class);

    private static final int DEFAULT_PRECISION = 8;

    private BinanceExchangeInfo exchangeInfo;
    private Long deltaServerTimeExpire;
    private Long deltaServerTime;

    @Override
    protected void initServices() {

        this.marketDataService = new BinanceMarketDataService(this);
        this.tradeService = new BinanceTradeService(this);
        this.accountService = new BinanceAccountService(this);
    }

    @Override
    public SynchronizedValueFactory<Long> getNonceFactory() {
        throw new UnsupportedOperationException(
                "Binance uses timestamp/recvwindow rather than a nonce");
    }

    @Override
    public ExchangeSpecification getDefaultExchangeSpecification() {

        ExchangeSpecification spec = new ExchangeSpecification(this.getClass().getCanonicalName());
        spec.setSslUri("https://api.binance.com");
        spec.setHost("www.binance.com");
        spec.setPort(80);
        spec.setExchangeName("Binance");
        spec.setExchangeDescription("Binance Exchange.");
        AuthUtils.setApiAndSecretKey(spec, "binance");
        return spec;
    }

    public BinanceExchangeInfo getExchangeInfo() {

        return exchangeInfo;
    }

    @Override
    public void remoteInit() {

        try {
            // populate currency pair keys only, exchange does not provide any other metadata for download
            Map<CurrencyPair, CurrencyPairMetaData> currencyPairs = exchangeMetaData.getCurrencyPairs();
            Map<Currency, CurrencyMetaData> currencies = exchangeMetaData.getCurrencies();

            BinanceMarketDataService marketDataService =
                    (BinanceMarketDataService) this.marketDataService;
            exchangeInfo = marketDataService.getExchangeInfo();
            Symbol[] symbols = exchangeInfo.getSymbols();

            for (Symbol symbol : symbols) {
                if (!symbol.getStatus().equals("BREAK")) { // Symbols with status "BREAK" are delisted
                    int basePrecision = Integer.parseInt(symbol.getBaseAssetPrecision());
                    int counterPrecision = Integer.parseInt(symbol.getQuotePrecision());
                    int pairPrecision = 8;
                    int amountPrecision = 8;

                    BigDecimal minQty = null;
                    BigDecimal maxQty = null;
                    BigDecimal stepSize = null;

                    Filter[] filters = symbol.getFilters();

                    CurrencyPair currentCurrencyPair =
                            new CurrencyPair(symbol.getBaseAsset(), symbol.getQuoteAsset());

                    for (Filter filter : filters) {
                        if (filter.getFilterType().equals("PRICE_FILTER")) {
                            pairPrecision = Math.min(pairPrecision, numberOfDecimals(filter.getTickSize()));
                        } else if (filter.getFilterType().equals("LOT_SIZE")) {
                            amountPrecision = Math.min(amountPrecision, numberOfDecimals(filter.getMinQty()));
                            minQty = new BigDecimal(filter.getMinQty()).stripTrailingZeros();
                            maxQty = new BigDecimal(filter.getMaxQty()).stripTrailingZeros();
                            stepSize = new BigDecimal(filter.getStepSize()).stripTrailingZeros();
                        }
                    }

                    currencyPairs.put(
                            currentCurrencyPair,
                            new CurrencyPairMetaData(
                                    new BigDecimal("0.1"), // Trading fee at Binance is 0.1 %
                                    minQty, // Min amount
                                    maxQty, // Max amount
                                    pairPrecision, // precision
                                    null, /* TODO get fee tiers, although this is not necessary now
                        because their API returns current fee directly */
                                    stepSize));
                    currencies.put(
                            new Currency(symbol.getBaseAsset()),
                            new CurrencyMetaData(
                                    basePrecision,
                                    currencies.containsKey(currentCurrencyPair.base)
                                            ? currencies.get(currentCurrencyPair.base).getWithdrawalFee()
                                            : null));
                    currencies.put(
                            new Currency(symbol.getQuoteAsset()),
                            new CurrencyMetaData(
                                    counterPrecision,
                                    currencies.containsKey(currentCurrencyPair.counter)
                                            ? currencies.get(currentCurrencyPair.counter).getWithdrawalFee()
                                            : null));
                }
            }
        } catch (Exception e) {
            throw new ExchangeException("Failed to initialize: " + e.getMessage(), e);
        }
    }

    private int numberOfDecimals(String value) {

        return new BigDecimal(value).stripTrailingZeros().scale();
    }

    public void clearDeltaServerTime() {

        deltaServerTime = null;
    }

    public long deltaServerTime() throws IOException {

        if (deltaServerTime == null || deltaServerTimeExpire <= System.currentTimeMillis()) {

            // Do a little warm up
            // we need to create one RestProxyFactory Adaptor to central control the configuration
            Binance binance = RestProxyFactory.createProxy(Binance.class,
                    getExchangeSpecification().getSslUri(),
                    getClientConfig());

            Date serverTime = new Date(binance.time().getServerTime().getTime());

            // Assume that we are closer to the server time when we get the repose
            Date systemTime = new Date(System.currentTimeMillis());

            // Expire every 10min
            deltaServerTimeExpire = systemTime.getTime() + TimeUnit.MINUTES.toMillis(10);
            deltaServerTime = serverTime.getTime() - systemTime.getTime();

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            LOG.trace(
                    "deltaServerTime: {} - {} => {}",
                    df.format(serverTime),
                    df.format(systemTime),
                    deltaServerTime);
        }

        return deltaServerTime;
    }

    /**
     * Get a ClientConfig object which contains exchange-specific timeout values
     * (<i>httpConnTimeout</i> and <i>httpReadTimeout</i>) if they were present in the
     * ExchangeSpecification of this instance. Subclasses are encouraged to use this config object
     * when creating a RestCU proxy.
     *
     * @return a rescu client config object
     */
    public ClientConfig getClientConfig() {

        ClientConfig rescuConfig = new ClientConfig(); // create default rescu config

        // set per exchange connection- and read-timeout (if they have been set in the
        // ExchangeSpecification)
        int customHttpConnTimeout = getExchangeSpecification().getHttpConnTimeout();
        if (customHttpConnTimeout > 0) {
            rescuConfig.setHttpConnTimeout(customHttpConnTimeout);
        }
        int customHttpReadTimeout = getExchangeSpecification().getHttpReadTimeout();
        if (customHttpReadTimeout > 0) {
            rescuConfig.setHttpReadTimeout(customHttpReadTimeout);
        }
        if (getExchangeSpecification().getProxyHost() != null) {
            rescuConfig.setProxyHost(getExchangeSpecification().getProxyHost());
        }
        if (getExchangeSpecification().getProxyPort() != null) {
            rescuConfig.setProxyPort(getExchangeSpecification().getProxyPort());
        }
        return rescuConfig;
    }
}
