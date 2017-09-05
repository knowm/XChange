package org.knowm.xchange.yobit.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.yobit.YoBit;
import org.knowm.xchange.yobit.YoBitAdapters;
import org.knowm.xchange.yobit.YoBitExchange;
import org.knowm.xchange.yobit.dto.BaseYoBitResponse;

public abstract class YoBitTradeServiceRaw extends YoBitBaseService<YoBit> implements TradeService {

  public YoBitTradeServiceRaw(YoBitExchange exchange) {
    super(YoBit.class, exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException("Need to specify OpenOrdersParams");
  }

  public BaseYoBitResponse activeOrders(OpenOrdersParamCurrencyPair params) throws IOException {
    CurrencyPair currencyPair = params.getCurrencyPair();
    String market = YoBitAdapters.adaptCcyPairToUrlFormat(currencyPair);

    BaseYoBitResponse response = service.activeOrders(
        exchange.getExchangeSpecification().getApiKey(),
        signatureCreator,
        "ActiveOrders",
        exchange.getNonceFactory(),
        market
    );

    if (!response.success)
      throw new ExchangeException("failed to get open orders");

    return response;
  }

  public BaseYoBitResponse trade(LimitOrder limitOrder) throws IOException {
    String market = YoBitAdapters.adaptCcyPairToUrlFormat(limitOrder.getCurrencyPair());
    String direction = limitOrder.getType().equals(Order.OrderType.BID) ? "buy" : "sell";

    BaseYoBitResponse response = service.trade(
        exchange.getExchangeSpecification().getApiKey(),
        signatureCreator,
        "Trade",
        exchange.getNonceFactory(),
        market,
        direction,
        limitOrder.getLimitPrice(),
        limitOrder.getTradableAmount()
    );

    if (!response.success)
      throw new ExchangeException("failed to get place order");

    return response;
  }

  public BaseYoBitResponse cancelOrder(CancelOrderByIdParams params) throws IOException {
    return service.cancelOrder(
        exchange.getExchangeSpecification().getApiKey(),
        signatureCreator,
        "CancelOrder",
        exchange.getNonceFactory(),
        Long.valueOf(params.getOrderId())
    );
  }

  public BaseYoBitResponse tradeHistory(Integer count, Long offset, String market, Long fromTransactionId, Long endTransactionId, String order, Long fromTimestamp, Long toTimestamp) throws IOException {
    return service.tradeHistory(
        exchange.getExchangeSpecification().getApiKey(),
        signatureCreator,
        "TradeHistory",
        exchange.getNonceFactory(),
        offset,
        count,
        fromTransactionId,
        endTransactionId,
        order,
        fromTimestamp,
        toTimestamp,
        market
    );
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public void verifyOrder(LimitOrder limitOrder) {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public void verifyOrder(MarketOrder marketOrder) {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    List<Order> orders = new ArrayList<>();

    for (String orderId : orderIds) {
      Long id = Long.valueOf(orderId);

      BaseYoBitResponse response = service.orderInfo(
          exchange.getExchangeSpecification().getApiKey(),
          signatureCreator,
          "OrderInfo",
          exchange.getNonceFactory(),
          id
      );

      if (response.returnData != null) {
        Map map = (Map) response.returnData.get(orderId);
        Order order = YoBitAdapters.adaptOrder(orderId, map);

        orders.add(order);
      }
    }

    return orders;
  }

}
