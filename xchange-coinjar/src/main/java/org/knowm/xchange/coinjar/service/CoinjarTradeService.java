package org.knowm.xchange.coinjar.service;

import static org.knowm.xchange.coinjar.CoinjarAdapters.currencyPairToProduct;
import static org.knowm.xchange.coinjar.CoinjarAdapters.orderTypeToBuySell;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.coinjar.CoinjarAdapters;
import org.knowm.xchange.coinjar.CoinjarErrorAdapter;
import org.knowm.xchange.coinjar.CoinjarException;
import org.knowm.xchange.coinjar.CoinjarExchange;
import org.knowm.xchange.coinjar.CoinjarOrderFlags;
import org.knowm.xchange.coinjar.dto.CoinjarOrder;
import org.knowm.xchange.coinjar.dto.trading.CoinjarFills;
import org.knowm.xchange.coinjar.dto.trading.CoinjarOrderRequest;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.DefaultCancelOrderParamId;
import org.knowm.xchange.service.trade.params.TradeHistoryParamNextPageCursor;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamOffset;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;

public class CoinjarTradeService extends CoinjarTradeServiceRaw implements TradeService {

  public CoinjarTradeService(CoinjarExchange exchange) {
    super(exchange);
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    Order.IOrderFlags orderFlag = CoinjarOrderFlags.GTC;
    if (limitOrder.getOrderFlags().size() > 0) {
      orderFlag = limitOrder.getOrderFlags().iterator().next();
    }
    try {
      CoinjarOrderRequest request =
          new CoinjarOrderRequest(
              currencyPairToProduct(limitOrder.getCurrencyPair()),
              "LMT",
              orderTypeToBuySell(limitOrder.getType()),
              limitOrder.getLimitPrice().stripTrailingZeros().toPlainString(),
              limitOrder.getOriginalAmount().stripTrailingZeros().toPlainString(),
              orderFlag.toString(),
              limitOrder.getUserReference());
      CoinjarOrder coinjarOrder = placeOrder(request);
      return coinjarOrder.oid.toString();
    } catch (CoinjarException e) {
      throw CoinjarErrorAdapter.adaptCoinjarException(e);
    }
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    Integer cursor = null;
    if (params instanceof TradeHistoryParamPaging) {
      cursor = ((TradeHistoryParamPaging) params).getPageNumber();
    }
    List<LimitOrder> limitOrders =
        super.getOpenOrders(cursor).stream()
            .map(CoinjarAdapters::adaptOrderToLimitOrder)
            .collect(Collectors.toList());
    return new OpenOrders(limitOrders);
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new CoinjarOpenOrdersParams();
  }

  @Override
  public Collection<Order> getOrder(OrderQueryParams... orderQueryParams) throws IOException {
    List<Order> res = new ArrayList<>();
    for (OrderQueryParams orderQueryParam : orderQueryParams) {
      res.addAll(this.getOrder(new String[] {orderQueryParam.getOrderId()}));
    }
    return res;
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    String cursor = null;
    if (params instanceof TradeHistoryParamNextPageCursor) {
      cursor = ((TradeHistoryParamNextPageCursor) params).getNextPageCursor();
    }
    try {

      CoinjarFills coinjarFills = getFills(cursor, null, null);
      List<UserTrade> trades =
          coinjarFills.stream()
              .map(CoinjarAdapters::adaptFillToUserTrade)
              .collect(Collectors.toList());
      Long lastTradeId =
          coinjarFills.stream().max(Comparator.comparing(t -> t.tid)).map(t -> t.tid).orElse(0L);
      return new UserTrades(
          trades, lastTradeId, UserTrades.TradeSortType.SortByID, coinjarFills.getNextPageCursor());
    } catch (CoinjarException e) {
      throw CoinjarErrorAdapter.adaptCoinjarException(e);
    }
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new CoinjarTradeHistoryParams();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    return cancelOrder(new DefaultCancelOrderParamId(orderId));
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    try {
      if (orderParams instanceof CancelOrderByIdParams) {
        CoinjarOrder cancelledOrder =
            cancelOrderById(((CancelOrderByIdParams) orderParams).getOrderId());
        return "cancelled".equals(cancelledOrder.status);
      } else {
        throw new IllegalArgumentException(
            "Unable to extract id from CancelOrderParams" + orderParams);
      }
    } catch (CoinjarException e) {
      throw CoinjarErrorAdapter.adaptCoinjarException(e);
    }
  }

  private static class CoinjarTradeHistoryParams
      implements TradeHistoryParams, TradeHistoryParamNextPageCursor {

    private String nextPageCursor;

    @Override
    public String getNextPageCursor() {
      return nextPageCursor;
    }

    @Override
    public void setNextPageCursor(String cursor) {
      this.nextPageCursor = cursor;
    }
  }

  private static class CoinjarOpenOrdersParams implements OpenOrdersParams, OpenOrdersParamOffset {
    private Integer offset;

    @Override
    public Integer getOffset() {
      return this.offset;
    }

    @Override
    public void setOffset(Integer offset) {
      this.offset = offset;
    }
  }
}
