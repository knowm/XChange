package org.knowm.xchange.btc38.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.btc38.Btc38;
import org.knowm.xchange.btc38.dto.marketdata.Btc38Ticker;
import org.knowm.xchange.btc38.dto.marketdata.Btc38TickerReturn;
import org.knowm.xchange.btc38.dto.marketdata.Btc38Trade;
import org.knowm.xchange.currency.CurrencyPair;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yingzhe on 12/19/2014.
 */
public class Btc38MarketDataServiceRaw extends Btc38BaseService<Btc38> {

  /**
   * Constructor for Btc38MarketDataServiceRaw
   *
   * @param exchange The {@link org.knowm.xchange.Exchange}
   */
  public Btc38MarketDataServiceRaw(Exchange exchange) {

    super(Btc38.class, exchange);
  }

  /**
   * Gets ticker from Btc38
   *
   * @param baseCurrency Base currency
   * @param targetCurrency Target currency
   * @return Btc38Ticker object
   * @throws IOException
   */
  public Btc38Ticker getBtc38Ticker(String baseCurrency, String targetCurrency) throws IOException {

    if (!this.getCurrencyPairMap().containsKey(baseCurrency.toUpperCase() + "_" + targetCurrency.toUpperCase())) {
      return null;
    }

    Map<String, Btc38TickerReturn> allTickers = this.btc38.getMarketTicker(targetCurrency);
    Btc38Ticker ticker = allTickers != null && allTickers.containsKey(baseCurrency.toLowerCase())
        ? allTickers.get(baseCurrency.toLowerCase()).getTicker() : null;
    return ticker.getBuy() != null || ticker.getHigh() != null || ticker.getLast() != null || ticker.getLow() != null || ticker.getSell() != null
        || ticker.getVol() != null ? ticker : null;
  }

  public HashMap<String, CurrencyPair> getCurrencyPairMap() throws IOException {

    HashMap<String, CurrencyPair> currencyPairMap = new HashMap<>();

    Map<String, Btc38TickerReturn> btcTickers = this.btc38.getMarketTicker("BTC");
    Map<String, Btc38TickerReturn> cnyTickers = this.btc38.getMarketTicker("CNY");

    if (btcTickers != null) {
      for (String key : btcTickers.keySet()) {
        String base = key.toUpperCase();
        String target = "BTC";
        currencyPairMap.put(base + "_" + target, new CurrencyPair(base, target));
      }
    }

    if (cnyTickers != null) {
      for (String key : cnyTickers.keySet()) {
        String base = key.toUpperCase();
        String target = "CNY";
        currencyPairMap.put(base + "_" + target, new CurrencyPair(base, target));
      }
    }

    return currencyPairMap;
  }

  public Btc38Trade[] getBtc38Trades(CurrencyPair currencyPair, Object[] args) throws IOException {
    return (args != null && args.length > 0 && args[0] != null && args[0] instanceof Long)
            ? btc38.getTradesFrom(currencyPair.base.getCurrencyCode().toUpperCase(), currencyPair.counter.getCurrencyCode().toUpperCase(), (Long) args[0])
            : btc38.getTrades(currencyPair.base.getCurrencyCode().toUpperCase(), currencyPair.counter.getCurrencyCode().toUpperCase());
  }

}
