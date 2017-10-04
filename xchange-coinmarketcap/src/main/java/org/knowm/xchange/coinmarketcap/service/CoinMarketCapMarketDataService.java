package org.knowm.xchange.coinmarketcap.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinmarketcap.dto.marketdata.CoinMarketCapCurrency;
import org.knowm.xchange.coinmarketcap.dto.marketdata.CoinMarketCapTicker;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * @author allenday
 */
public class CoinMarketCapMarketDataService extends CoinMarketCapMarketDataServiceRaw implements MarketDataService {

  private Map<String,CoinMarketCapTicker> tickers;

  /**
   * Constructor
   *
   * @param exchange
   */
  public CoinMarketCapMarketDataService(Exchange exchange) {
    super(exchange);
    tickers = new HashMap<>();
    try {
      List<CoinMarketCapTicker> tt = getCoinMarketCapTickers();
      for (CoinMarketCapTicker t : tt)
        tickers.put(t.getIsoCode(), t);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, final Object... args) throws IOException {
    Currency b = currencyPair.base;
    Currency c = currencyPair.counter;

    if (!tickers.containsKey(b.getCurrencyCode()) && b.getCurrencyCode().compareTo("USD")!=0)
      throw new IOException("unsupported ISO 4217 Currency: " + b.getCurrencyCode());
    if (!tickers.containsKey(c.getCurrencyCode()) && c.getCurrencyCode().compareTo("USD")!=0)
      throw new IOException("unsupported ISO 4217 Currency: " + c.getCurrencyCode());
    if (b.getCurrencyCode().compareTo(c.getCurrencyCode()) == 0)
      throw new IOException("base and counter currency must not be identical");

    CoinMarketCapTicker cmcB = tickers.get(b.getCurrencyCode());

    CoinMarketCapTicker BTC = tickers.get("BTC");

    CurrencyPair pair;
    BigDecimal price;
    BigDecimal volume;

    if (c.getCurrencyCode().compareTo("USD") == 0) {
      pair = new CurrencyPair(cmcB.getIsoCode(), "USD");
      price = cmcB.getPriceUSD();
      volume = cmcB.getVolume24hUSD();
    } else if (c.getCurrencyCode().compareTo("BTC") == 0) {
      pair = new CurrencyPair(cmcB.getIsoCode(), "BTC");
      price = cmcB.getPriceBTC();

      //TODO move to conversion function
      //volume = new BigDecimal(cmcB.getVolume24hUSD().doubleValue() / BTC.getPriceUSD().doubleValue());
      volume = null;
    } else {
      CoinMarketCapTicker cmcC = tickers.get(c.getCurrencyCode());
      pair = new CurrencyPair(cmcB.getIsoCode(), cmcC.getIsoCode());

      //TODO move to conversion function
      //price = new BigDecimal(cmcB.getPriceBTC().doubleValue() / cmcC.getPriceBTC().doubleValue());
      //volume = new BigDecimal((cmcB.getVolume24hUSD().doubleValue() / BTC.getPriceUSD().doubleValue()) / cmcC.getPriceBTC().doubleValue());
      price = null;
      volume = null;
    }

    Ticker.Builder builder = new Ticker.Builder();
    builder
        .currencyPair(pair)
        .timestamp(cmcB.getLastUpdated())
        .last(price)
        .bid(price)
        .ask(price)
        .high(price)
        .low(price)
        .vwap(price)
        .volume(volume);

    return builder.build();
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... objects) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... objects) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotAvailableFromExchangeException();
  }

  public List<Currency> getCurrencies() {
    List<Currency> currencies = new ArrayList<Currency>();
    try {
      List<CoinMarketCapCurrency> cmcCurrencies = getCoinMarketCapCurrencies();
      for (CoinMarketCapCurrency cmcCurrency : cmcCurrencies) {
        currencies.add(cmcCurrency.getCurrency());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return currencies;
  }
}