package org.knowm.xchange.bl3p.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bl3p.Bl3pUtils;
import org.knowm.xchange.bl3p.dto.Bl3pTrade;
import org.knowm.xchange.bl3p.dto.marketdata.Bl3pOrderBook;
import org.knowm.xchange.bl3p.dto.marketdata.Bl3pTicker;
import org.knowm.xchange.bl3p.dto.marketdata.Bl3pTrades;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class Bl3pMarketDataService extends Bl3pBaseService implements MarketDataService {

  public Bl3pMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    Bl3pTicker ticker = this.bl3p.getTicker(Bl3pUtils.toPairString(currencyPair));

    return new Ticker.Builder()
        .currencyPair(currencyPair)
        .ask(ticker.getAsk())
        .bid(ticker.getBid())
        .high(ticker.getHigh())
        .last(ticker.getLast())
        .low(ticker.getLow())
        .timestamp(ticker.getTimestamp())
        .volume(ticker.getVolume().getDay())
        .build();
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    Bl3pOrderBook orderBook = this.bl3p.getOrderBook(Bl3pUtils.toPairString(currencyPair));
    Date now = new Date();

    return new OrderBook(
        now,
        toXChangeLimitOrders(orderBook.getData().getAsks(), Order.OrderType.ASK, currencyPair, now),
        toXChangeLimitOrders(
            orderBook.getData().getBids(), Order.OrderType.BID, currencyPair, now));
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    Bl3pTrades trades = this.bl3p.getTrades(Bl3pUtils.toPairString(currencyPair));

    List<Trade> tradesList = new ArrayList<>(trades.getData().getTrades().length);
    for (Bl3pTrade bl3pTrade : trades.getData().getTrades()) {
      Trade trade =
          new Trade.Builder()
              .type(null) // TODO @BL3P Trade Type is not returned by API
              .originalAmount(Bl3pUtils.fromSatoshi(new BigDecimal(bl3pTrade.getAmountInt())))
              .currencyPair(currencyPair)
              .price(Bl3pUtils.fromEuroshi(new BigDecimal(bl3pTrade.getPriceInt())))
              .timestamp(bl3pTrade.getDate())
              .id("" + bl3pTrade.getTradeId())
              .build();

      tradesList.add(trade);
    }

    return new Trades(tradesList);
  }

  private List<LimitOrder> toXChangeLimitOrders(
      Bl3pOrderBook.Bl3pOrderBookOrder[] bl3pOrders,
      Order.OrderType type,
      CurrencyPair currencyPair,
      Date timestamp) {
    List<LimitOrder> orders = new ArrayList<>(bl3pOrders.length);

    for (Bl3pOrderBook.Bl3pOrderBookOrder bl3pOrder : bl3pOrders) {
      LimitOrder order =
          new LimitOrder(
              type,
              new BigDecimal(bl3pOrder.getAmountInt()).multiply(new BigDecimal(1e8)),
              currencyPair,
              "",
              timestamp,
              new BigDecimal(bl3pOrder.getPriceInt()).multiply(new BigDecimal(1e5)));

      orders.add(order);
    }

    return orders;
  }
}
