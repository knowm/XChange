package org.knowm.xchange.btcturk.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcturk.BTCTurkAdapters;
import org.knowm.xchange.btcturk.dto.BTCTurkOperations;
import org.knowm.xchange.btcturk.dto.account.BTCTurkUserTransactions;
import org.knowm.xchange.btcturk.dto.trade.BTCTurkOpenOrders;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

/** @author mertguner */
public class BTCTurkTradeService extends BTCTurkTradeServiceRaw implements TradeService {

  public BTCTurkTradeService(Exchange exchange) {
    super(exchange);
    // TODO Auto-generated constructor stub
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return BTCTurkAdapters.adaptOpenOrders(super.getBTCTurkOpenOrders());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    List<BTCTurkOpenOrders> openOrdersRaw =
        super.getBTCTurkOpenOrders(((DefaultOpenOrdersParamCurrencyPair) params).getCurrencyPair());
    return BTCTurkAdapters.adaptOpenOrders(openOrdersRaw);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    return super.placeMarketOrder(
            marketOrder.getOriginalAmount(),
            marketOrder.getCurrencyPair(),
            BTCTurkAdapters.adaptOrderType(marketOrder.getType()))
        .getId();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    return super.placeLimitOrder(
            limitOrder.getOriginalAmount(),
            limitOrder.getLimitPrice(),
            limitOrder.getCurrencyPair(),
            BTCTurkAdapters.adaptOrderType(limitOrder.getType()))
        .getId();
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws IOException {
    return super.placeStopLimitOrder(
            stopOrder.getOriginalAmount(),
            stopOrder.getLimitPrice(),
            stopOrder.getStopPrice(),
            stopOrder.getCurrencyPair(),
            BTCTurkAdapters.adaptOrderType(stopOrder.getType()))
        .getId();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    return super.cancelOrder(orderId);
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    CancelOrderByIdParams paramId = (CancelOrderByIdParams) orderParams;
    return super.cancelOrder(paramId.getOrderId());
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    List<UserTrade> trades = new ArrayList<UserTrade>();

    List<BTCTurkUserTransactions> transactions = super.getBTCTurkUserTransactions();
    for (BTCTurkUserTransactions transaction : transactions) {
      if (transaction.getOperation().equals(BTCTurkOperations.trade))
        trades.add(
            new UserTrade.Builder()
                .type(
                    ((transaction.getAmount().compareTo(BigDecimal.ZERO) > 0)
                        ? OrderType.ASK
                        : OrderType.BID))
                .originalAmount(transaction.getAmount())
                .price(transaction.getPrice())
                .timestamp(transaction.getDate())
                .id(transaction.getId())
                .feeAmount(transaction.getFee())
                .feeCurrency(transaction.getCurrency())
                .build());
    }

    long lastId =
        transactions.stream().map(t -> Long.parseLong(t.getId())).max(Long::compareTo).orElse(0L);

    return new UserTrades(trades, lastId, TradeSortType.SortByTimestamp);
  }
}
