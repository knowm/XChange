package org.knowm.xchange.bitso.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitso.BitsoAdapters;
import org.knowm.xchange.bitso.dto.marketdata.BitsoOrderBook;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** @author Piotr Ładyżyński */
public class BitsoMarketDataService extends BitsoMarketDataServiceRaw implements MarketDataService {

  public BitsoMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    return BitsoAdapters.adaptTicker(getBitsoTicker(currencyPair), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return BitsoAdapters.adaptOrderBook(getBitsoOrderBook(currencyPair), currencyPair, 1000);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    return BitsoAdapters.adaptTrades(getBitsoTrades(currencyPair), currencyPair);
  }

  public static OrderBook convertOrderBook(BitsoOrderBook ob, CurrencyPair pair) {
    List<LimitOrder> bids = (List)ob.payload.bids.stream().map((e) -> {
      return new LimitOrder(Order.OrderType.BID, new BigDecimal(e.getAmount()), pair, (String)null, (Date)null, new BigDecimal(e.getPrice()));
    }).collect(Collectors.toList());
    List<LimitOrder> asks = (List)ob.payload.asks.stream().map((e) -> {
      return new LimitOrder(Order.OrderType.ASK,  new BigDecimal(e.getAmount()), pair, (String)null, (Date)null, new BigDecimal(e.getPrice()));
    }).collect(Collectors.toList());
    return new OrderBook((Date)null, asks, bids);
  }
}
