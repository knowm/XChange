package org.knowm.xchange.bitmex;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitmex.dto.account.BitmexTicker;
import org.knowm.xchange.bitmex.dto.account.BitmexTickerList;
import org.knowm.xchange.bitmex.service.BitmexAccountService;
import org.knowm.xchange.bitmex.service.BitmexMarketDataService;
import org.knowm.xchange.bitmex.service.BitmexMarketDataServiceRaw;
import org.knowm.xchange.bitmex.service.BitmexTradeService;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.utils.nonce.CurrentTimeIncrementalNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class BitmexExchange extends BaseExchange implements Exchange {

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
      if (exchangeSpecification.getExchangeSpecificParametersItem("Use_Sandbox").equals(true)) {
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

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://www.bitmex.com");
    exchangeSpecification.setHost("bitmex.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Bitmex");
    exchangeSpecification.setExchangeDescription("Bitmex is a bitcoin exchange");
    exchangeSpecification.setExchangeSpecificParametersItem("Use_Sandbox", false);
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }

  @Override
  public void remoteInit() throws IOException {
    updateExchangeMetaData();
  }

  public RateLimitUpdateListener getRateLimitUpdateListener() {
    return rateLimitUpdateListener;
  }

  public void setRateLimitUpdateListener(RateLimitUpdateListener rateLimitUpdateListener) {
    this.rateLimitUpdateListener = rateLimitUpdateListener;
  }

  public void updateExchangeMetaData() {

    List<BitmexTicker> tickers =
        ((BitmexMarketDataServiceRaw) marketDataService).getActiveTickers();
    List<CurrencyPair> activeCurrencyPairs = new ArrayList<>();
    Set<Currency> activeCurrencies = new HashSet<>();

    tickers.forEach(
        ticker -> collectCurrenciesAndPairs(ticker, activeCurrencyPairs, activeCurrencies));

    Map<CurrencyPair, CurrencyPairMetaData> pairsMap = exchangeMetaData.getCurrencyPairs();
    Map<Currency, CurrencyMetaData> currenciesMap = exchangeMetaData.getCurrencies();

    // Remove pairs that are no-longer in use
    pairsMap.keySet().retainAll(activeCurrencyPairs);

    // Remove currencies that are no-longer in use
    currenciesMap.keySet().retainAll(activeCurrencies);

    // Add missing pairs and currencies
    activeCurrencyPairs.forEach(
        cp -> {
          if (!pairsMap.containsKey(cp)) {
            pairsMap.put(
                cp,
                new CurrencyPairMetaData(
                    null, BigDecimal.ONE, null, getPriceScale(tickers, cp), null));
          }
          if (!currenciesMap.containsKey(cp.base)) {
            currenciesMap.put(cp.base, null);
          }
          if (!currenciesMap.containsKey(cp.counter)) {
            currenciesMap.put(cp.counter, null);
          }
        });
  }

  private void collectCurrenciesAndPairs(
      BitmexTicker ticker, List<CurrencyPair> activeCurrencyPairs, Set<Currency> activeCurrencies) {

    String bitmexSymbol = ticker.getSymbol();
    String baseSymbol =
        ("XBK".equals(ticker.getRootSymbol()) || "XBJ".equals(ticker.getRootSymbol()))
            ? "XBT"
            : ticker.getRootSymbol();
    String counterSymbol;

    if (bitmexSymbol.contains(baseSymbol)) {
      counterSymbol = bitmexSymbol.substring(baseSymbol.length());
    } else {
      logger.warn("Not clear how to create currency pair for symbol: {}", bitmexSymbol);
      return;
    }

    activeCurrencyPairs.add(new CurrencyPair(baseSymbol, counterSymbol));
    activeCurrencies.add(new Currency(baseSymbol));
    activeCurrencies.add(new Currency(counterSymbol));
  }

  private Integer getPriceScale(List<BitmexTicker> tickers, CurrencyPair cp) {

    return tickers.stream()
        .filter(ticker -> ticker.getSymbol().equals(BitmexAdapters.adaptCurrencyPairToSymbol(cp)))
        .findFirst()
        .map(BitmexTicker::getLastPrice)
        .filter(Objects::nonNull)
        .map(BigDecimal::scale)
        .orElse(null);
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
