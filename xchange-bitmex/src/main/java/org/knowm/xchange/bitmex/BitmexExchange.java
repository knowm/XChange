package org.knowm.xchange.bitmex;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitmex.dto.account.BitmexTicker;
import org.knowm.xchange.bitmex.dto.account.BitmexTicker.SymbolType;
import org.knowm.xchange.bitmex.dto.account.BitmexTickerList;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexAsset;
import org.knowm.xchange.bitmex.service.BitmexAccountService;
import org.knowm.xchange.bitmex.service.BitmexMarketDataService;
import org.knowm.xchange.bitmex.service.BitmexMarketDataServiceRaw;
import org.knowm.xchange.bitmex.service.BitmexTradeService;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.utils.nonce.CurrentTimeIncrementalNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class BitmexExchange extends BaseExchange {

  protected RateLimitUpdateListener rateLimitUpdateListener;

  private final SynchronizedValueFactory<Long> nonceFactory =
      new SynchronizedValueFactory<Long>() {

        private final SynchronizedValueFactory<Long> secondsNonce =
            new CurrentTimeIncrementalNonceFactory(TimeUnit.SECONDS);

        @Override
        public Long createValue() {
          return secondsNonce.createValue() + 30;
        }
      };

  /** Adjust host parameters depending on exchange specific parameters */
  private static void concludeHostParams(ExchangeSpecification exchangeSpecification) {

    if (exchangeSpecification.getExchangeSpecificParameters() != null) {
      if (exchangeSpecification
          .getExchangeSpecificParametersItem(Exchange.USE_SANDBOX)
          .equals(true)) {
        exchangeSpecification.setSslUri("https://testnet.bitmex.com");
        exchangeSpecification.setHost("testnet.bitmex.com");
      }
    }
  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);

    concludeHostParams(exchangeSpecification);
  }

  @Override
  protected void initServices() {

    concludeHostParams(exchangeSpecification);

    this.marketDataService = new BitmexMarketDataService(this);
    this.accountService = new BitmexAccountService(this);
    this.tradeService = new BitmexTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification specification = new ExchangeSpecification(getClass());
    specification.setSslUri("https://www.bitmex.com");
    specification.setHost("bitmex.com");
    specification.setExchangeName("Bitmex");
    specification.setExchangeSpecificParametersItem(USE_SANDBOX, false);
    return specification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }

  @Override
  public void remoteInit() {
    BitmexMarketDataService bitmexMarketDataService = (BitmexMarketDataService) getMarketDataService();
    List<BitmexTicker> tickers = bitmexMarketDataService.getActiveTickers();

    Map<Instrument, InstrumentMetaData> instruments = tickers.stream()
        .filter(bitmexTicker -> bitmexTicker.getSymbolType() != SymbolType.UNKNOWN)
        .collect(Collectors.toMap(
            BitmexAdapters::toInstrument,
            BitmexAdapters::toInstrumentMetaData
        ));

    Map<Currency, CurrencyMetaData> currencyMetadata = bitmexMarketDataService.getAssets().stream()
        .collect(Collectors.toMap(
            BitmexAsset::getAsset,
            bitmexAsset -> {
              return new CurrencyMetaData(bitmexAsset.getScale(), null);
            }
        ));

    exchangeMetaData = new ExchangeMetaData(instruments, currencyMetadata, exchangeMetaData.getPublicRateLimits(), exchangeMetaData.getPrivateRateLimits(), exchangeMetaData.isShareRateLimits());
  }

  public RateLimitUpdateListener getRateLimitUpdateListener() {
    return rateLimitUpdateListener;
  }

  public void setRateLimitUpdateListener(RateLimitUpdateListener rateLimitUpdateListener) {
    this.rateLimitUpdateListener = rateLimitUpdateListener;
  }

  public CurrencyPair determineActiveContract(
      String baseSymbol, String counterSymbol, BitmexPrompt contractTimeframe) {

    if ("BTC".equals(baseSymbol)) {
      baseSymbol = "XBT";
    }
    if ("BTC".equals(counterSymbol)) {
      counterSymbol = "XBT";
    }

    final String symbols = baseSymbol + "/" + counterSymbol;

    BitmexTickerList tickerList =
        ((BitmexMarketDataServiceRaw) marketDataService)
            .getTicker(baseSymbol + ":" + contractTimeframe);

    String bitmexSymbol =
        tickerList.stream()
            .map(BitmexTicker::getSymbol)
            .findFirst()
            .orElseThrow(
                () ->
                    new ExchangeException(
                        String.format(
                            "Instrument for %s %s is not active or does not exist",
                            symbols, contractTimeframe)));

    String contractTypeSymbol = bitmexSymbol.substring(3);
    return new CurrencyPair(baseSymbol, contractTypeSymbol);
  }
}
