package org.knowm.xchange.coinsuper.service;

import java.io.IOException;
import java.util.*;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinsuper.CoinsuperAdapters;
import org.knowm.xchange.coinsuper.dto.CoinsuperResponse;
import org.knowm.xchange.coinsuper.dto.trade.CoinsuperOrder;
import org.knowm.xchange.coinsuper.dto.trade.OrderDetail;
import org.knowm.xchange.coinsuper.dto.trade.OrderList;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByCurrencyPair;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;

public class CoinsuperTradeService extends CoinsuperTradeServiceRaw implements TradeService {
  public CoinsuperTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws ExchangeException, IOException {
    List<LimitOrder> limitOrders = new ArrayList<>();
    Map<String, String> parameters = new HashMap<String, String>();

    CoinsuperResponse<List<String>> openOrders = orderOpenList(parameters);

    for (String orderNo : openOrders.getData().getResult()) {
      limitOrders.add(new LimitOrder(null, null, null, orderNo, new Date(), null));
    }

    return new OpenOrders(limitOrders);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    Map<String, String> parameters = new HashMap<String, String>();
    parameters.put("side", marketOrder.getType().toString());
    parameters.put("orderType", "MKT");
    parameters.put("symbol", marketOrder.getCurrencyPair().toString());
    parameters.put("priceLimit", "0");
    parameters.put("amount", marketOrder.getOriginalAmount().toString());
    parameters.put("quantity", "0");

    CoinsuperResponse<CoinsuperOrder> coinsuperCreateOrder = createOrder(parameters);
    if (coinsuperCreateOrder.getCode() == 1000) {
      return coinsuperCreateOrder.getData().getResult().getOrderNo();
    } else {
      return "false";
    }
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    Map<String, String> parameters = new HashMap<String, String>();
    parameters.put("side", limitOrder.getType().toString());
    parameters.put("orderType", "LMT");
    parameters.put("symbol", limitOrder.getCurrencyPair().toString());
    parameters.put("priceLimit", limitOrder.getLimitPrice().toString());
    parameters.put("amount", "0");
    parameters.put("quantity", limitOrder.getOriginalAmount().toString());

    CoinsuperResponse<CoinsuperOrder> coinsuperCreateOrder = createOrder(parameters);
    if (coinsuperCreateOrder.getCode() == 1000) {
      return coinsuperCreateOrder.getData().getResult().getOrderNo();
    } else {
      return "false";
    }
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    Map<String, String> parameters = new HashMap<String, String>();
    parameters.put("orderNo", orderId);

    return cancelCoinsuperOrder(parameters);
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (!(orderParams instanceof CancelOrderByCurrencyPair)
        && !(orderParams instanceof CancelOrderByIdParams)) {
      throw new ExchangeException(
          "You need to provide the currency pair and the order id to cancel an order.");
    }
    // CancelOrderByCurrencyPair currencyPair = (CancelOrderByCurrencyPair) orderParams;
    CancelOrderByIdParams orderIdParam = (CancelOrderByIdParams) orderParams;

    //	    String orderId =
    //	        super.cancelOrder(
    //	            CoinsuperAdapters.toMarket(currencyPair.getCurrencyPair()),
    // orderIdParam.getOrderId());

    Map<String, String> parameters = new HashMap<String, String>();
    parameters.put("orderNo", orderIdParam.getOrderId());

    return cancelCoinsuperOrder(parameters);
  }

  private int getLimit(Object... args) {
    int limitDepth = 0;
    if (args != null && args.length == 1) {
      Object arg0 = args[0];
      if (!(arg0 instanceof Integer)) {
        throw new ExchangeException("Argument 0 must be an Integer!");
      } else {
        limitDepth = (Integer) arg0;
      }
    }
    return limitDepth;
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {
    return new DefaultOpenOrdersParamCurrencyPair();
  }

  public Collection<Order> getOrderImpl(String... orderIds) throws IOException {
    // This method possibly not working
    Collection<Order> orders = new ArrayList<>(orderIds.length);

    Map<String, String> parameters = new HashMap<>();
    StringBuilder builder = new StringBuilder();
    for (int index = 0; index < orderIds.length; index++) {
      builder.append(orderIds[index]);
      if (index < orderIds.length - 1) {
        builder.append(",");
      }
    }
    parameters.put("orderNoList", builder.toString());
    // without translation
    CoinsuperResponse<List<OrderList>> ordersList = orderList(parameters);
    for (OrderList orderList : ordersList.getData().getResult()) {
      orders.add(CoinsuperAdapters.adaptOrder(Long.toString(orderList.getOrderNo()), orderList));
    }

    return orders;
  }

  @Override
  public Collection<Order> getOrder(OrderQueryParams... orderQueryParams) throws IOException {
    return getOrderImpl(TradeService.toOrderIds(orderQueryParams));
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    CoinsuperTradeHistoryParams coinsuperTradeHistoryParams = (CoinsuperTradeHistoryParams) params;
    Map<String, String> parameters = new HashMap<String, String>();
    parameters.put("orderNoList", coinsuperTradeHistoryParams.getOrderNoList());

    CoinsuperResponse<List<OrderDetail>> orderDetails = orderDetails(parameters);
    // System.out.println(data);

    // CoinsuperUserTransaction[] coinsuperUserTransaction = null;

    return CoinsuperAdapters.adaptTradeHistory(orderDetails);
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    return new CoinsuperTradeHistoryParams(null, null);
  }
}
