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
  
  public KucoinResponse<KucoinTicker> getKucoinTicker(CurrencyPair currencyPair) throws IOException {
    return kucoin.tick(KucoinAdapters.adaptCurrencyPair(currencyPair));
  }
  
  public KucoinResponse<List<KucoinTicker>> getKucoinTickers() throws IOException {
    return kucoin.tick();
  }
  
  public KucoinResponse<KucoinOrderBook> getKucoinOrderBook(CurrencyPair currencyPair, Integer limit) throws IOException {
    // "group" param is set to null for now since I have no idea what it does
    return kucoin.orders(KucoinAdapters.adaptCurrencyPair(currencyPair), null, limit);
  }
  
  public KucoinResponse<List<KucoinDealOrder>> getKucoinTrades(CurrencyPair currencyPair, Integer limit,
      Long since) throws IOException {
    return kucoin.dealOrders(KucoinAdapters.adaptCurrencyPair(currencyPair), limit, since);
  }
  
  public KucoinResponse<List<KucoinCoin>> getKucoinCurrencies() throws IOException {
    return kucoin.coins();
  }
}
