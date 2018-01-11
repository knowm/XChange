package org.knowm.xchange.bitflyer.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitflyer.BitflyerAdapters;
import org.knowm.xchange.bitflyer.dto.marketdata.BitflyerOrderbook;
import org.knowm.xchange.bitflyer.dto.marketdata.BitflyerTicker;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class BitflyerMarketDataService extends BitflyerMarketDataServiceRaw implements MarketDataService {
  /**
   * Constructor
   *
   * @param exchange baseExchange
   */
  public BitflyerMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    BitflyerTicker ticker = getTicker(currencyPair.base + "_" + currencyPair.counter);
    return BitflyerAdapters.adaptTicker(ticker, currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    BitflyerOrderbook orderbook = getOrderbook(currencyPair.base + "_" + currencyPair.counter);
    List<LimitOrder> bids = orderbook.bids.entrySet().stream().map(e -> new LimitOrder(Order.OrderType.BID, e.getValue(), currencyPair, null, null, e.getKey())).collect(Collectors.toList());
    List<LimitOrder> asks = orderbook.asks.entrySet().stream().map(e -> new LimitOrder(Order.OrderType.ASK, e.getValue(), currencyPair, null, null, e.getKey())).collect(Collectors.toList());
    return new OrderBook(null, asks, bids);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }
}
