package org.knowm.xchange.kucoin.service;

import static org.knowm.xchange.kucoin.KucoinUtils.checkSuccess;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.kucoin.KucoinException;
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
    try {
      return checkSuccess(kucoin.tick(KucoinAdapters.adaptCurrencyPair(currencyPair)));
    } catch (KucoinException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public KucoinResponse<List<KucoinTicker>> getKucoinTickers() throws IOException {
    try {
      return checkSuccess(kucoin.tick());
    } catch (KucoinException e) {
      throw new ExchangeException(e.getMessage());
    }
  }
  
  public KucoinResponse<KucoinOrderBook> getKucoinOrderBook(CurrencyPair currencyPair, Integer limit) throws IOException {
    // "group" param is set to null for now since I have no idea what it does
    try {
      return checkSuccess(kucoin.orders(KucoinAdapters.adaptCurrencyPair(currencyPair), null, limit));
    } catch (KucoinException e) {
      throw new ExchangeException(e.getMessage());
    }
  }
  
  public KucoinResponse<List<KucoinDealOrder>> getKucoinTrades(CurrencyPair currencyPair, Integer limit,
      Long since) throws IOException {
    try {
      return checkSuccess(kucoin.dealOrders(KucoinAdapters.adaptCurrencyPair(currencyPair), limit, since));
    } catch (KucoinException e) {
      throw new ExchangeException(e.getMessage());
    }
  }
  
  public KucoinResponse<List<KucoinCoin>> getKucoinCurrencies() throws IOException {
    try {
      return checkSuccess(kucoin.coins());
    } catch (KucoinException e) {
      throw new ExchangeException(e.getMessage());
    }
  }
}
