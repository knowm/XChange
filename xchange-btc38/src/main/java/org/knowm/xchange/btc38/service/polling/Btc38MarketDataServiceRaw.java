package org.knowm.xchange.btc38.service.polling;

import java.io.IOException;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.btc38.Btc38;
import org.knowm.xchange.btc38.dto.marketdata.Btc38Ticker;
import org.knowm.xchange.btc38.dto.marketdata.Btc38TickerReturn;

/**
 * Created by Yingzhe on 12/19/2014.
 */
public class Btc38MarketDataServiceRaw extends Btc38BasePollingService<Btc38> {

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
}
