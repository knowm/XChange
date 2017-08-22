package org.knowm.xchange.hitbtc.v2.service;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcOrderBook;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcSort;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcSymbol;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcTicker;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcTrade;
import org.knowm.xchange.hitbtc.v2.internal.HitbtcAdapters;
import org.knowm.xchange.hitbtc.v2.internal.api.HitbtcRestClient;
import org.knowm.xchange.service.BaseService;

public class HitbtcMarketDataServiceRaw implements BaseService {

  public List<HitbtcSymbol> getHitbtcSymbols() throws IOException {

    return HitbtcRestClient.INSTANCE.call().getSymbols();
  }

  public HitbtcTicker getHitbtcTicker(CurrencyPair currencyPair) throws IOException {

    return HitbtcRestClient.INSTANCE.call().getTicker(HitbtcAdapters.adaptCurrencyPair(currencyPair));
  }

  public HitbtcOrderBook getHitbtcOrderBook(CurrencyPair currencyPair) throws IOException {

    return HitbtcRestClient.INSTANCE.call().getOrderBook(HitbtcAdapters.adaptCurrencyPair(currencyPair));

  }

  public List<HitbtcTrade> getHitbtcTrades(CurrencyPair currencyPair) throws IOException {

    return getHitbtcTrades(currencyPair, 100, HitbtcTrade.HitbtcTradesSortField.SORT_BY_TRADE_ID, HitbtcSort.SORT_ASCENDING, 0, 100);
  }

  //TODO add extra params in API
  public List<HitbtcTrade> getHitbtcTrades(CurrencyPair currencyPair, long from, HitbtcTrade.HitbtcTradesSortField sortBy,
      HitbtcSort sortDirection, long startIndex, long maxResults) throws IOException {

    return HitbtcRestClient.INSTANCE.call().getTrades(HitbtcAdapters.adaptCurrencyPair(currencyPair));
  }



}
