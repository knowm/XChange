package org.knowm.xchange.btcmarkets.service;

import static org.knowm.xchange.dto.Order.OrderType.BID;

import com.google.common.collect.Sets;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcmarkets.BTCMarketsAdapters;
import org.knowm.xchange.btcmarkets.dto.BTCMarketsException;
import org.knowm.xchange.btcmarkets.dto.BTCMarketsOrderFlags;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsOrder;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsOrders;
import org.knowm.xchange.btcmarkets.dto.v3.trade.BTCMarketsPlaceOrderResponse;
import org.knowm.xchange.btcmarkets.dto.v3.trade.BTCMarketsTradeHistoryResponse;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsIdSpan;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;

/** @author Matija Mazi */
public class BTCMarketsTradeService extends BTCMarketsTradeServiceRaw implements TradeService {

  public BTCMarketsTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public String placeMarketOrder(MarketOrder order) throws IOException, BTCMarketsException {
    return placeOrder(
        order.getCurrencyPair(),
        order.getType(),
        order.getOriginalAmount(),
        BigDecimal.ZERO,
        BTCMarketsOrder.Type.Market,
        order.getOrderFlags(),
        order.getUserReference());
  }

  @Override
  public String placeLimitOrder(LimitOrder order) throws IOException, BTCMarketsException {
    return placeOrder(
        order.getCurrencyPair(),
        order.getType(),
        order.getOriginalAmount(),
        order.getLimitPrice(),
        BTCMarketsOrder.Type.Limit,
        order.getOrderFlags(),
        order.getUserReference());
  }

  private String placeOrder(
      CurrencyPair currencyPair,
      Order.OrderType orderSide,
      BigDecimal amount,
      BigDecimal price,
      BTCMarketsOrder.Type orderType,
      Set<Order.IOrderFlags> flags,
      String clientOrderId)
      throws IOException {
    boolean postOnly = false;
    if (flags.contains(BTCMarketsOrderFlags.POST_ONLY)) {
      postOnly = true;
      flags = Sets.filter(flags, flag -> flag != BTCMarketsOrderFlags.POST_ONLY);
    }

    BTCMarketsOrder.Side side =
        orderSide == BID ? BTCMarketsOrder.Side.Bid : BTCMarketsOrder.Side.Ask;
    final String marketId = currencyPair.base.toString() + "-" + currencyPair.counter.toString();
    String timeInForce;
    if (flags.contains(BTCMarketsOrderFlags.FOK)) {
      timeInForce = "FOK";
    } else if (flags.contains(BTCMarketsOrderFlags.IOC)) {
      timeInForce = "IOC";
    } else {
      timeInForce = "GTC";
    }
    final BTCMarketsPlaceOrderResponse orderResponse =
        placeBTCMarketsOrder(
            marketId, amount, price, side, orderType, timeInForce, postOnly, clientOrderId);
    return orderResponse.orderId;
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException, BTCMarketsException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    BTCMarketsOrders openOrders =
        getBTCMarketsOpenOrders(((OpenOrdersParamCurrencyPair) params).getCurrencyPair(), 50, null);

    return BTCMarketsAdapters.adaptOpenOrders(openOrders);
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException, BTCMarketsException {
    return cancelBTCMarketsOrder(Long.parseLong(orderId)).getSuccess();
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      return cancelOrder(((CancelOrderByIdParams) orderParams).getOrderId());
    } else {
      return false;
    }
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    Integer limit = 200;
    if (params instanceof TradeHistoryParamPaging) {
      limit = ((TradeHistoryParamPaging) params).getPageLength();
    }

    String after = null;
    if (params instanceof TradeHistoryParamsIdSpan) {
      TradeHistoryParamsIdSpan tradeHistoryParamsIdSpan = (TradeHistoryParamsIdSpan) params;
      after = tradeHistoryParamsIdSpan.getStartId();
    }

    String before = null;
    if (params instanceof TradeHistoryParamsIdSpan) {
      TradeHistoryParamsIdSpan tradeHistoryParamsIdSpan = (TradeHistoryParamsIdSpan) params;
      before = tradeHistoryParamsIdSpan.getEndId();
    }

    CurrencyPair cp = null;
    if (params instanceof TradeHistoryParamCurrencyPair) {
      CurrencyPair paramsCp = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair();
      if (paramsCp != null) {
        cp = paramsCp;
      }
    }

    List<BTCMarketsTradeHistoryResponse> response =
        getBTCMarketsUserTransactions(cp, before, after, limit);
    return BTCMarketsAdapters.adaptTradeHistory(response);
  }

  @Override
  public Collection<Order> getOrder(OrderQueryParams... orderQueryParams) throws IOException {
    List<Long> orderIds =
        Arrays.stream(orderQueryParams)
            .map(orderQueryParams1 -> Long.valueOf(orderQueryParams1.getOrderId()))
            .collect(Collectors.toList());
    return getOrderDetails(orderIds).getOrders().stream()
        .map(BTCMarketsAdapters::adaptOrder)
        .collect(Collectors.toList());
  }

  @Override
  public HistoryParams createTradeHistoryParams() {
    return new HistoryParams();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new DefaultOpenOrdersParamCurrencyPair();
  }

  public static class HistoryParams
      implements TradeHistoryParamPaging, TradeHistoryParamCurrencyPair, TradeHistoryParamsIdSpan {
    private Integer limit = 200;
    private CurrencyPair currencyPair;
    private String startId;
    private String endId;

    @Override
    public String getStartId() {
      return startId;
    }

    @Override
    public void setStartId(String startId) {
      this.startId = startId;
    }

    @Override
    public String getEndId() {
      return this.endId;
    }

    @Override
    public void setEndId(String endId) {
      this.endId = endId;
    }

    @Override
    public Integer getPageLength() {
      return limit;
    }

    @Override
    public void setPageLength(Integer pageLength) {
      this.limit = pageLength;
    }

    @Override
    public Integer getPageNumber() {
      throw new UnsupportedOperationException();
    }

    @Override
    public void setPageNumber(Integer pageNumber) {
      throw new UnsupportedOperationException();
    }

    @Override
    public CurrencyPair getCurrencyPair() {
      return currencyPair;
    }

    @Override
    public void setCurrencyPair(CurrencyPair currencyPair) {
      this.currencyPair = currencyPair;
    }
  }
}
