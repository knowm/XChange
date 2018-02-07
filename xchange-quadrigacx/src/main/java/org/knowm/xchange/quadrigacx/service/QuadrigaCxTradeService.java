package org.knowm.xchange.quadrigacx.service;

import static org.knowm.xchange.dto.Order.OrderType.BID;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.quadrigacx.QuadrigaCxAdapters;
import org.knowm.xchange.quadrigacx.dto.QuadrigaCxException;
import org.knowm.xchange.quadrigacx.dto.trade.QuadrigaCxOrder;
import org.knowm.xchange.quadrigacx.dto.trade.QuadrigaCxUserTransaction;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOffset;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsSorted;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamMultiCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

public class QuadrigaCxTradeService extends QuadrigaCxTradeServiceRaw implements TradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public QuadrigaCxTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException, QuadrigaCxException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    Collection<CurrencyPair> currencyPairs;
    if (params instanceof OpenOrdersParamMultiCurrencyPair) {
      currencyPairs = ((OpenOrdersParamMultiCurrencyPair) params).getCurrencyPairs();
    } else if (params instanceof OpenOrdersParamCurrencyPair) {
      currencyPairs = Collections.singletonList(((OpenOrdersParamCurrencyPair) params).getCurrencyPair());
    } else {
      currencyPairs = exchange.getExchangeSymbols();
    }

    List<LimitOrder> limitOrders = new ArrayList<>();

    for (CurrencyPair pair : currencyPairs) {
      QuadrigaCxOrder[] openOrders = getQuadrigaCxOpenOrders(pair);

      for (QuadrigaCxOrder quadrigaCxOrder : openOrders) {
        OrderType orderType = quadrigaCxOrder.getType() == 0 ? OrderType.BID : OrderType.ASK;
        String id = quadrigaCxOrder.getId();
        BigDecimal price = quadrigaCxOrder.getPrice();

        limitOrders.add(new LimitOrder(orderType, quadrigaCxOrder.getAmount(), pair, id, quadrigaCxOrder.getTime(), price));
      }
    }

    return new OpenOrders(limitOrders);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException, QuadrigaCxException {

    QuadrigaCxOrder quadrigacxOrder;
    if (marketOrder.getType() == BID) {
      quadrigacxOrder = buyQuadrigaCxOrder(marketOrder.getCurrencyPair(), marketOrder.getOriginalAmount());
    } else {
      quadrigacxOrder = sellQuadrigaCxOrder(marketOrder.getCurrencyPair(), marketOrder.getOriginalAmount());
    }
    if (quadrigacxOrder.getErrorMessage() != null) {
      throw new ExchangeException(quadrigacxOrder.getErrorMessage());
    }

    return quadrigacxOrder.getId();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException, QuadrigaCxException {

    QuadrigaCxOrder quadrigacxOrder;
    if (limitOrder.getType() == BID) {
      quadrigacxOrder = buyQuadrigaCxOrder(limitOrder.getCurrencyPair(), limitOrder.getOriginalAmount(), limitOrder.getLimitPrice());
    } else {
      quadrigacxOrder = sellQuadrigaCxOrder(limitOrder.getCurrencyPair(), limitOrder.getOriginalAmount(), limitOrder.getLimitPrice());
    }
    if (quadrigacxOrder.getErrorMessage() != null) {
      throw new ExchangeException(quadrigacxOrder.getErrorMessage());
    }

    return quadrigacxOrder.getId();
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException, QuadrigaCxException {

    return cancelQuadrigaCxOrder(orderId);
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      return cancelOrder(((CancelOrderByIdParams) orderParams).getOrderId());
    } else {
      return false;
    }
  }

  /**
   * Required parameter types: {@link TradeHistoryParamPaging#getPageLength()}
   * <p/>
   * Warning: using a limit here can be misleading. The underlying call retrieves trades, withdrawals, and deposits. So the example here will limit
   * the result to 17 of those types and from those 17 only trades are returned. It is recommended to use the raw service demonstrated below if you
   * want to use this feature.
   */

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    Long limit = null;
    CurrencyPair currencyPair = null;
    Long offset = null;
    String sort = null;

    if (params instanceof TradeHistoryParamPaging) {
      limit = Long.valueOf(((TradeHistoryParamPaging) params).getPageLength());
    }

    if (params instanceof TradeHistoryParamOffset) {
      offset = ((TradeHistoryParamOffset) params).getOffset();
    }

    if (params instanceof TradeHistoryParamsSorted) {
      TradeHistoryParamsSorted.Order order = ((TradeHistoryParamsSorted) params).getOrder();
      if (order != null) {
        if (order.equals(TradeHistoryParamsSorted.Order.asc))
          sort = "asc";
        else
          sort = "desc";
      }
    }

    if (params instanceof TradeHistoryParamCurrencyPair) {
      currencyPair = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair();
    }

    QuadrigaCxUserTransaction[] txs = getQuadrigaCxUserTransactions(currencyPair, limit, offset, sort);

    return QuadrigaCxAdapters.adaptTradeHistory(txs, currencyPair);
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    return new QuadrigaCxTradeHistoryParams(CurrencyPair.BTC_CAD, 1000);
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return null;
  }

  @Override
  public Collection<Order> getOrder(
      String... orderIds) throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

}
