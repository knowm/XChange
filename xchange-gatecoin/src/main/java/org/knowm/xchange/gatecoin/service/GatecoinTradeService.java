package org.knowm.xchange.gatecoin.service;

import static org.knowm.xchange.dto.Order.OrderType.BID;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.gatecoin.GatecoinAdapters;
import org.knowm.xchange.gatecoin.dto.trade.GatecoinOrder;
import org.knowm.xchange.gatecoin.dto.trade.Results.GatecoinCancelOrderResult;
import org.knowm.xchange.gatecoin.dto.trade.Results.GatecoinOrderResult;
import org.knowm.xchange.gatecoin.dto.trade.Results.GatecoinPlaceOrderResult;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParamTransactionId;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.utils.DateUtils;

/**
 * @author sumedha
 */
public class GatecoinTradeService extends GatecoinTradeServiceRaw implements TradeService {
  /**
   * Constructor
   *
   * @param exchange
   */
  public GatecoinTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(
      OpenOrdersParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    GatecoinOrderResult openOrdersResult = getGatecoinOpenOrders();

    List<LimitOrder> limitOrders = new ArrayList<>();
    for (GatecoinOrder gatecoinOrder : openOrdersResult.getOrders()) {
      /* get side is order side (ask or bid) get type is order type, (limit or market) */
      OrderType orderType = gatecoinOrder.getSide() == 0 ? OrderType.BID : OrderType.ASK;
      String id = gatecoinOrder.getClOrderId();
      BigDecimal price = gatecoinOrder.getPrice();
      CurrencyPair ccyPair = new CurrencyPair(gatecoinOrder.getCode().substring(0, 3), gatecoinOrder.getCode().substring(3, 6));
      limitOrders.add(new LimitOrder(orderType, gatecoinOrder.getRemainingQuantity(), ccyPair, id,
          DateUtils.fromMillisUtc(Long.valueOf(gatecoinOrder.getDate()) * 1000L), price));
    }
    return new OpenOrders(limitOrders);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    String ccyPair = marketOrder.getCurrencyPair().toString().replace("/", "");
    GatecoinPlaceOrderResult gatecoinPlaceOrderResult;
    if (marketOrder.getType() == BID) {
      gatecoinPlaceOrderResult = placeGatecoinOrder(marketOrder.getTradableAmount(), BigDecimal.ZERO, "BID", ccyPair);
    } else {
      gatecoinPlaceOrderResult = placeGatecoinOrder(marketOrder.getTradableAmount(), BigDecimal.ZERO, "ASK", ccyPair);
    }

    return gatecoinPlaceOrderResult.getOrderId();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    String ccyPair = limitOrder.getCurrencyPair().toString().replace("/", "");
    GatecoinPlaceOrderResult gatecoinOrderResult;
    if (limitOrder.getType() == BID) {
      gatecoinOrderResult = placeGatecoinOrder(limitOrder.getTradableAmount(), limitOrder.getLimitPrice(), "BID", ccyPair);
    } else {
      gatecoinOrderResult = placeGatecoinOrder(limitOrder.getTradableAmount(), limitOrder.getLimitPrice(), "ASK", ccyPair);
    }
    return gatecoinOrderResult.getOrderId();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    GatecoinCancelOrderResult response = null;
    if (orderId != null && !orderId.isEmpty()) {
      response = cancelGatecoinOrder(orderId);
    } else {
      response = cancelAllGatecoinOrders();
    }
    if (response != null && response.getResponseStatus() != null && response.getResponseStatus().getMessage() != null) {
      return response.getResponseStatus().getMessage().equalsIgnoreCase("OK");
    } else {
      return false;
    }

  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      cancelOrder(((CancelOrderByIdParams) orderParams).orderId);
    }
    return false;
  }

  /**
   * @param params Supported optional parameters: {@link TradeHistoryParamPaging#getPageLength()}, {@link TradeHistoryParamTransactionId}
   */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    Integer limit = null;
    Long txId = null;

    if (params instanceof TradeHistoryParamPaging) {
      limit = ((TradeHistoryParamPaging) params).getPageLength();
    }

    if (params instanceof TradeHistoryParamTransactionId) {
      String txIdStr = ((TradeHistoryParamTransactionId) params).getTransactionId();
      if (txIdStr != null)
        txId = Long.valueOf(txIdStr);
    }

    return GatecoinAdapters.adaptTradeHistory(getGatecoinUserTrades(limit, txId));
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new GatecoinTradeHistoryParams(1000);
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return null;
  }

  @Override
  public Collection<Order> getOrder(
      String... orderIds) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }

  public static class GatecoinTradeHistoryParams implements TradeHistoryParamPaging, TradeHistoryParamTransactionId {
    Integer pageLength;
    String transactionId;

    public GatecoinTradeHistoryParams(Integer pageLength) {
      this.pageLength = pageLength;
    }

    public GatecoinTradeHistoryParams(Integer pageLength, String transactionId) {
      this.pageLength = pageLength;
      this.transactionId = transactionId;
    }

    @Override
    public void setPageLength(Integer pageLength) {
      this.pageLength = pageLength;
    }

    @Override
    public Integer getPageLength() {
      return pageLength;
    }

    @Override
    public void setPageNumber(Integer pageNumber) {
    }

    @Override
    public Integer getPageNumber() {
      return null;
    }

    @Override
    public void setTransactionId(String txId) {
      transactionId = txId;
    }

    @Override
    public String getTransactionId() {
      return transactionId;
    }

  }
}
