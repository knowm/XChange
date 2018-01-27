package org.knowm.xchange.kucoin.service;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.kucoin.dto.KucoinAdapters;
import org.knowm.xchange.kucoin.dto.KucoinResponse;
import org.knowm.xchange.kucoin.dto.marketdata.KucoinCoin;
import org.knowm.xchange.kucoin.dto.marketdata.KucoinDealOrder;
import org.knowm.xchange.kucoin.dto.marketdata.KucoinOrderBook;
import org.knowm.xchange.kucoin.dto.marketdata.KucoinTicker;

public class KucoinMarketDataServiceRaw extends KucoinBaseService {

  protected KucoinMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }
  
  KucoinResponse<KucoinTicker> tick(CurrencyPair currencyPair) throws IOException {
    return kucoin.tick(KucoinAdapters.adaptCurrencyPair(currencyPair));
  }
  
  KucoinResponse<KucoinOrderBook> orders(CurrencyPair currencyPair, Integer limit) throws IOException {
    // "group" param is set to null for now since I have no idea what it does
    return kucoin.orders(KucoinAdapters.adaptCurrencyPair(currencyPair), null, limit);
  }
  
  KucoinResponse<List<KucoinDealOrder>> dealOrders(CurrencyPair currencyPair, Integer limit,
      Long since) throws IOException {
    return kucoin.dealOrders(KucoinAdapters.adaptCurrencyPair(currencyPair), limit, since);
  }
  
  KucoinResponse<List<KucoinCoin>> coins() throws IOException {
    return kucoin.coins();
  }
}
