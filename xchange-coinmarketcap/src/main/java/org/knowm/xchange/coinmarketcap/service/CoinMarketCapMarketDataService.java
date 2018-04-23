package org.knowm.xchange.coinmarketcap.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinmarketcap.dto.marketdata.CoinMarketCapCurrency;
import org.knowm.xchange.coinmarketcap.dto.marketdata.CoinMarketCapTicker;
import org.knowm.xchange.currency.Currency;
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
  public Ticker getTicker(
      org.knowm.xchange.currency.CurrencyPair currencyPair, final Object... args)
      throws IOException {
    Currency b = currencyPair.getBase();
    Currency c = currencyPair.getCounter();

    if (!tickers.containsKey(b.getCurrencyCode()) && b.getCurrencyCode().compareTo("USD") != 0)
      throw new IOException("unsupported ISO 4217 Currency: " + b.getCurrencyCode());
    if (!tickers.containsKey(c.getCurrencyCode()) && c.getCurrencyCode().compareTo("USD") != 0)
      throw new IOException("unsupported ISO 4217 Currency: " + c.getCurrencyCode());
    if (b.getCurrencyCode().compareTo(c.getCurrencyCode()) == 0)
      throw new IOException("base and counter currency must not be identical");

    CoinMarketCapTicker cmcB = tickers.get(b.getCurrencyCode());

    org.knowm.xchange.currency.CurrencyPair pair;
    BigDecimal price;
    BigDecimal volume;

    if (c.getCurrencyCode().compareTo("USD") == 0) {
      pair = org.knowm.xchange.currency.CurrencyPair.build(cmcB.getIsoCode(), "USD");
      price = cmcB.getPriceUSD();
      volume = cmcB.getVolume24hUSD();
    } else if (c.getCurrencyCode().compareTo("BTC") == 0) {
      pair = org.knowm.xchange.currency.CurrencyPair.build(cmcB.getIsoCode(), "BTC");
      price = cmcB.getPriceBTC();

      // TODO move to conversion function
      // volume = new BigDecimal(cmcB.getVolume24hUSD().doubleValue() /
      // BTC.getPriceUSD().doubleValue());
      volume = null;
    } else {
      CoinMarketCapTicker cmcC = tickers.get(c.getCurrencyCode());
      pair = org.knowm.xchange.currency.CurrencyPair.build(cmcB.getIsoCode(), cmcC.getIsoCode());

      // TODO move to conversion function
      // price = new BigDecimal(cmcB.getPriceBTC().doubleValue() /
      // cmcC.getPriceBTC().doubleValue());
      // volume = new BigDecimal((cmcB.getVolume24hUSD().doubleValue() /
      // BTC.getPriceUSD().doubleValue()) / cmcC.getPriceBTC().doubleValue());
      price = null;
      volume = null;
    }

    return new Ticker.Builder()
        .currencyPair(pair)
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

  public Ticker getTickerFresh(
      org.knowm.xchange.currency.CurrencyPair currencyPair, final Object... args)
      throws IOException {
    tickers = getNewTickers();
    return getTicker(currencyPair, args);
  }

  private Map<String, CoinMarketCapTicker> getNewTickers() throws IOException {
    Map<String, CoinMarketCapTicker> freshTickers = new HashMap<>();
    List<CoinMarketCapTicker> tt = getCoinMarketCapTickers();
    for (CoinMarketCapTicker t : tt) {
      freshTickers.put(t.getIsoCode(), t);
    }
    return freshTickers;
  }

  @Override
  public OrderBook getOrderBook(
      org.knowm.xchange.currency.CurrencyPair currencyPair, Object... objects) {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public Trades getTrades(org.knowm.xchange.currency.CurrencyPair currencyPair, Object... objects) {
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
