package org.knowm.xchange.bitmex;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
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
import org.knowm.xchange.utils.nonce.ExpirationTimeFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class BitmexExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new ExpirationTimeFactory(30);

  protected RateLimitUpdateListener rateLimitUpdateListener;

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

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
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
        (ticker.getRootSymbol().equals("XBK") || ticker.getRootSymbol().equals("XBJ"))
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
        .map(ticker -> ticker.getLastPrice().scale())
        .get();
  }

  public CurrencyPair determineActiveContract(
      String baseSymbol, String counterSymbol, BitmexPrompt contractTimeframe) {

    if (baseSymbol.equals("BTC")) {
      baseSymbol = "XBT";
    }
    if (counterSymbol.equals("BTC")) {
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
