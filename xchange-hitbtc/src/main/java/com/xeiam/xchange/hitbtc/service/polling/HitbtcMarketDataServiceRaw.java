package com.xeiam.xchange.hitbtc.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.hitbtc.HitbtcAdapters;
import com.xeiam.xchange.hitbtc.dto.HitbtcException;
import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcOrderBook;
import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcSymbols;
import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcTicker;
import com.xeiam.xchange.hitbtc.dto.marketdata.HitbtcTrades;

/**
 * @author kpysniak
 */
public class HitbtcMarketDataServiceRaw extends HitbtcBasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  protected HitbtcMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public HitbtcTicker getHitbtcTicker(CurrencyPair currencyPair) throws IOException {

    try {
      return hitbtc.getHitbtcTicker(HitbtcAdapters.adaptCurrencyPair(currencyPair));
    } catch (HitbtcException e) {
      throw handleException(e);
    }
  }

  public HitbtcOrderBook getHitbtcOrderBook(CurrencyPair currencyPair) throws IOException {

    try {
      return hitbtc.getOrderBook(HitbtcAdapters.adaptCurrencyPair(currencyPair));
    } catch (HitbtcException e) {
      throw handleException(e);
    }
  }

  public HitbtcTrades getHitbtcTrades(CurrencyPair currencyPair, long from, HitbtcTrades.HitbtcTradesSortOrder sortBy, long startIndex,
      long maxResults) throws IOException {

    try {
      return hitbtc.getTrades(HitbtcAdapters.adaptCurrencyPair(currencyPair), String.valueOf(from), sortBy.toString(), "desc",
          String.valueOf(startIndex), String.valueOf(maxResults), "object");
    } catch (HitbtcException e) {
      throw handleException(e);
    }
  }

  public HitbtcSymbols getHitbtcSymbols() throws IOException {

    return hitbtc.getSymbols();
  }

}
