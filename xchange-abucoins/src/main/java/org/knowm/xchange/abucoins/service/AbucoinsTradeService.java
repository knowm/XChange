package org.knowm.xchange.abucoins.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.abucoins.AbucoinsAdapters;
import org.knowm.xchange.abucoins.dto.AbucoinsCreateLimitOrderRequest;
import org.knowm.xchange.abucoins.dto.AbucoinsCreateMarketOrderRequest;
import org.knowm.xchange.abucoins.dto.AbucoinsOrderRequest;
import org.knowm.xchange.abucoins.dto.account.AbucoinsFills;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsCreateOrderResponse;
import org.knowm.xchange.abucoins.dto.trade.AbucoinsOrder;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByCurrencyPair;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamNextPageCursor;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

/** Author: bryant_harris */
public class AbucoinsTradeService extends AbucoinsTradeServiceRaw implements TradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public AbucoinsTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    if (params instanceof OpenOrdersParamCurrencyPair) {
      OpenOrdersParamCurrencyPair cpParams = (OpenOrdersParamCurrencyPair) params;
      AbucoinsOrderRequest orderRequest =
          new AbucoinsOrderRequest(
              AbucoinsOrder.Status.open,
              AbucoinsAdapters.adaptCurrencyPairToProductID(cpParams.getCurrencyPair()));
      AbucoinsOrder[] openOrders = getAbucoinsOrders(orderRequest);
      return AbucoinsAdapters.adaptOpenOrders(openOrders);
    }

    throw new NotYetImplementedForExchangeException("Only OpenOrdersParamCurrencyPair supported");
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    AbucoinsCreateMarketOrderRequest req =
        AbucoinsAdapters.adaptAbucoinsCreateMarketOrderRequest(marketOrder);
    AbucoinsCreateOrderResponse resp = createAbucoinsOrder(req);
    if (resp.getMessage() != null) throw new ExchangeException(resp.getMessage());
    return resp.getId();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    AbucoinsCreateLimitOrderRequest req =
        AbucoinsAdapters.adaptAbucoinsCreateLimitOrderRequest(limitOrder);
    AbucoinsCreateOrderResponse resp = createAbucoinsOrder(req);
    if (resp.getMessage() != null) throw new ExchangeException(resp.getMessage());
    return resp.getId();
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    String id = deleteAbucoinsOrder(orderId);
    return id.equals(orderId);
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByCurrencyPair) {
      CancelOrderByCurrencyPair cob = (CancelOrderByCurrencyPair) orderParams;
      deleteAllAbucoinsOrders(AbucoinsAdapters.adaptCurrencyPairToProductID(cob.getCurrencyPair()));
      return true;
    } else {
      throw new NotYetImplementedForExchangeException("Only CancelOrderByCurrencyPair supported");
    }
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    Integer limit = null;
    if (params instanceof TradeHistoryParamLimit) {
      limit = ((TradeHistoryParamLimit) params).getLimit();
    }
    String afterCursor = null;
    if (params instanceof TradeHistoryParamNextPageCursor) {
      afterCursor = ((TradeHistoryParamNextPageCursor) params).getNextPageCursor();
    }
    AbucoinsFills fills = getAbucoinsFills(afterCursor, limit);
    return AbucoinsAdapters.adaptUserTrades(fills);
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new AbucoinsTradeHistoryParams();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new DefaultOpenOrdersParamCurrencyPair();
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {

    List<Order> orders = new ArrayList<>();
    for (String orderId : orderIds) {
      AbucoinsOrder AbucoinsOrder = getAbucoinsOrder(orderId);
      orders.add(AbucoinsAdapters.adaptOrder(AbucoinsOrder));
    }
    return orders;
  }
}
