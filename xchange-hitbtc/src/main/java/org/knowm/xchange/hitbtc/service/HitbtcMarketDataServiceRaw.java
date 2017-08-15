package org.knowm.xchange.hitbtc.service;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.hitbtc.HitbtcAdapters;
import org.knowm.xchange.hitbtc.dto.HitbtcException;
import org.knowm.xchange.hitbtc.dto.marketdata.HitbtcOrderBook;
import org.knowm.xchange.hitbtc.dto.marketdata.HitbtcSymbol;
import org.knowm.xchange.hitbtc.dto.marketdata.HitbtcTicker;
import org.knowm.xchange.hitbtc.dto.marketdata.HitbtcTrade;

public class HitbtcMarketDataServiceRaw extends HitbtcBaseService {


  protected HitbtcMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

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

  public List<HitbtcTrade> getHitbtcTrades(CurrencyPair currencyPair, Integer maxResults, long from, HitbtcTrade.HitbtcTradesSortField sortBy,
      HitbtcTrade.HitbtcTradesSortDirection sortDirection, long startIndex) throws IOException {

    try {
      return hitbtc.getTrades(HitbtcAdapters.adaptCurrencyPair(currencyPair), maxResults);

    } catch (HitbtcException e) {
      throw handleException(e);
    }
  }

  public List<HitbtcTrade> getHitbtcTradesRecent(CurrencyPair currencyPair, Integer maxResults) throws IOException {

    try {
      return hitbtc.getTrades(HitbtcAdapters.adaptCurrencyPair(currencyPair), maxResults);
    } catch (HitbtcException e) {
      throw handleException(e);
    }
  }

}
