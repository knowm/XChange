package org.knowm.xchange.coinmarketcap.deprecated.v2.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinmarketcap.deprecated.v2.dto.marketdata.CoinMarketCapCurrency;
import org.knowm.xchange.coinmarketcap.deprecated.v2.dto.marketdata.CoinMarketCapTicker;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** @author allenday */
public class CoinMarketCapMarketDataService extends CoinMarketCapMarketDataServiceRaw
    implements MarketDataService {

  private Map<String, CoinMarketCapTicker> tickers;

  /**
   * Constructor
   *
   * @param exchange
   */
  public CoinMarketCapMarketDataService(Exchange exchange) {
    super(exchange);
    try {
      tickers = getNewTickers();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, final Object... args) throws IOException {
    Currency b = currencyPair.base;
    Currency c = currencyPair.counter;

    if (!tickers.containsKey(b.getCurrencyCode()) && b.getCurrencyCode().compareTo("USD") != 0)
      throw new IOException("unsupported ISO 4217 Currency: " + b.getCurrencyCode());
    if (!tickers.containsKey(c.getCurrencyCode()) && c.getCurrencyCode().compareTo("USD") != 0)
      throw new IOException("unsupported ISO 4217 Currency: " + c.getCurrencyCode());
    if (b.getCurrencyCode().compareTo(c.getCurrencyCode()) == 0)
      throw new IOException("base and counter currency must not be identical");

    CoinMarketCapTicker cmcB = tickers.get(b.getCurrencyCode());
    BigDecimal price;
    BigDecimal volume;
    try {
      price = cmcB.getQuotes().get(c.toString()).getPrice();
      volume = cmcB.getQuotes().get(c.toString()).getVolume24h();
    } catch (NullPointerException npe) {
      throw new NotAvailableFromExchangeException();
    }

    return new Ticker.Builder()
        .currencyPair(currencyPair)
        .timestamp(cmcB.getLastUpdated())
        .last(price)
        .bid(price)
        .ask(price)
        .high(price)
        .low(price)
        .vwap(price)
        .volume(volume)
        .build();
  }

  public Ticker getTickerFresh(CurrencyPair currencyPair) throws IOException {
    tickers = getNewTickers();
    return getTicker(currencyPair);
  }

  private Map<String, CoinMarketCapTicker> getNewTickers() throws IOException {
    Map<String, CoinMarketCapTicker> freshTickers = new HashMap<>();
    List<CoinMarketCapTicker> tt = getCoinMarketCapTickers();
    for (CoinMarketCapTicker t : tt) {
      freshTickers.put(t.getSymbol(), t);
    }
    return freshTickers;
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... objects) {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... objects) {
    throw new NotAvailableFromExchangeException();
  }

  public List<Currency> getCurrencies() {
    List<Currency> currencies = new ArrayList<>();
    List<CoinMarketCapCurrency> cmcCurrencies = getCoinMarketCapCurrencies();

    for (CoinMarketCapCurrency cmcCurrency : cmcCurrencies) {
      currencies.add(cmcCurrency.getCurrency());
    }

    return currencies;
  }

  @Override
  public List<CoinMarketCapCurrency> getCoinMarketCapCurrencies() {
    Collection<CoinMarketCapTicker> tickers = this.tickers.values();
    List<CoinMarketCapCurrency> currencies = new ArrayList<>();
    for (CoinMarketCapTicker ticker : tickers) currencies.add(ticker.getBaseCurrency());
    return currencies;
  }
}
