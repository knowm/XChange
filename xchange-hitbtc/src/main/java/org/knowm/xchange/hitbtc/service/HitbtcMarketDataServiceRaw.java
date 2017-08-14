package org.knowm.xchange.hitbtc.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.hitbtc.HitbtcAdapters;
import org.knowm.xchange.hitbtc.dto.HitbtcException;
import org.knowm.xchange.hitbtc.dto.marketdata.*;

/**
 * @author kpysniak
 */
public class HitbtcMarketDataServiceRaw extends HitbtcBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  protected HitbtcMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

//  public HitbtcTime getHitbtcTime() throws IOException {
//
//    try {
//      return hitbtc.getHitbtcTime();
//    } catch (HitbtcException e) {
//      throw handleException(e);
//    }
//  }

  public List<HitbtcSymbol> getHitbtcSymbols() throws IOException {

    try {
      return hitbtc.getSymbols();
    } catch (HitbtcException e) {
      throw handleException(e);
    }
  }

  public HitbtcTicker getHitbtcTicker(CurrencyPair currencyPair) throws IOException {

    try {
      return hitbtc.getHitbtcTicker(HitbtcAdapters.adaptCurrencyPair(currencyPair));
    } catch (HitbtcException e) {
      throw handleException(e);
    }
  }

  public List<HitbtcTicker> getHitbtcTickers() throws IOException {

    try {
      return  hitbtc.getHitbtcTickers();
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

  public HitbtcTrades getHitbtcTrades(CurrencyPair currencyPair, long from, HitbtcTrades.HitbtcTradesSortField sortBy,
      HitbtcTrades.HitbtcTradesSortDirection sortDirection, long startIndex, long maxResults) throws IOException {

    try {
      return hitbtc.getTrades(HitbtcAdapters.adaptCurrencyPair(currencyPair));

    } catch (HitbtcException e) {
      throw handleException(e);
    }
  }

//  public HitbtcTrades getHitbtcTradesRecent(CurrencyPair currencyPair, long maxResults) throws IOException {
//
//    try {
//      return hitbtc.getTradesRecent(HitbtcAdapters.adaptCurrencyPair(currencyPair), String.valueOf(maxResults), "object", "true");
//    } catch (HitbtcException e) {
//      throw handleException(e);
//    }
//  }

}
