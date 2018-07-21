package org.knowm.xchange.binance;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.dto.marketdata.BinancePrice;
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
import org.knowm.xchange.utils.AuthUtils;
import org.knowm.xchange.utils.nonce.AtomicLongCurrentTimeIncrementalNonceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class BinanceExchange extends BaseExchange {

  private static final Logger LOG = LoggerFactory.getLogger(BinanceExchange.class);

  private static final int DEFAULT_PRECISION = 8;

  private SynchronizedValueFactory<Long> nonceFactory =
      new AtomicLongCurrentTimeIncrementalNonceFactory();
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

    return nonceFactory;
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

      BinanceMarketDataService marketDataService = (BinanceMarketDataService) this.marketDataService;
      exchangeInfo = marketDataService.getExchangeInfo();
      Symbol[] symbols = exchangeInfo.getSymbols();

      for (BinancePrice price : marketDataService.tickerAllPrices()) {
        CurrencyPair pair = price.getCurrencyPair();

        for (Symbol symbol : symbols) {
          if (symbol.getSymbol().equals(pair.base.getCurrencyCode() + pair.counter.getCurrencyCode())) {

            int basePrecision = Integer.parseInt(symbol.getBaseAssetPrecision());
            int counterPrecision = Integer.parseInt(symbol.getQuotePrecision());
            int pairPrecision = 8;
            int amountPrecision = 8;
            
            BigDecimal minQty = null;
            BigDecimal maxQty = null;

            Filter[] filters = symbol.getFilters(); 

            for (Filter filter : filters) {
        		  if (filter.getFilterType().equals("PRICE_FILTER")) {
        			  
        			  pairPrecision = Math.min(pairPrecision, numberOfDecimals(filter.getMinPrice()));
        			  
        		  } else if (filter.getFilterType().equals("LOT_SIZE")) {
        			  amountPrecision = Math.min(amountPrecision, numberOfDecimals(filter.getMinQty()));
        			  minQty = new BigDecimal(filter.getMinQty()).stripTrailingZeros();
        			  maxQty = new BigDecimal(filter.getMaxQty()).stripTrailingZeros();
              }
            }
            
            currencyPairs.put(price.getCurrencyPair(), new CurrencyPairMetaData(
					  new BigDecimal("0.1"), // Trading fee at Binance is 0.1 %
					  minQty, // Min amount
					  maxQty, // Max amount
					  pairPrecision // precision
				  ));            
            currencies.put(pair.base, new CurrencyMetaData(basePrecision, currencies.containsKey(pair.base) ? 
            		currencies.get(pair.base).getWithdrawalFee() : null));
            currencies.put(pair.counter, new CurrencyMetaData(counterPrecision, currencies.containsKey(pair.counter) ? 
            		currencies.get(pair.counter).getWithdrawalFee() : null));
          }
        }
      }
    } catch (Exception e) {
      logger.warn("An exception occurred while loading the metadata", e);
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
      Binance binance =
          RestProxyFactory.createProxy(Binance.class, getExchangeSpecification().getSslUri());
      Date serverTime = new Date(binance.time().getServerTime().getTime());
      serverTime = new Date(binance.time().getServerTime().getTime());

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
}
