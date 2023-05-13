package org.knowm.xchange.gateio.service;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.gateio.GateioExchange;
import org.knowm.xchange.gateio.GateioUtils;
import org.knowm.xchange.gateio.dto.GateioBaseResponse;
import org.knowm.xchange.gateio.dto.GateioOrderType;
import org.knowm.xchange.gateio.dto.trade.GateioOpenOrders;
import org.knowm.xchange.gateio.dto.trade.GateioOrderStatus;
import org.knowm.xchange.gateio.dto.trade.GateioPlaceOrderReturn;
import org.knowm.xchange.gateio.dto.trade.GateioTradeHistoryReturn;
import org.knowm.xchange.service.trade.params.CancelOrderByCurrencyPair;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;

public class GateioTradeServiceRaw extends GateioBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public GateioTradeServiceRaw(GateioExchange exchange) {

    super(exchange);
  }

  /**
   * Submits a Limit Order to be executed on the Gateio Exchange for the desired market defined by
   * {@code CurrencyPair}. WARNING - Gateio will return true regardless of whether or not an order
   * actually gets created. The reason for this is that orders are simply submitted to a queue in
   * their back-end. One example for why an order might not get created is because there are
   * insufficient funds. The best attempt you can make to confirm that the order was created is to
   * poll {@link #getGateioOpenOrders}. However if the order is created and executed before it is
   * caught in its open state from calling {@link #getGateioOpenOrders} then the only way to confirm
   * would be confirm the expected difference in funds available for your account.
   *
   * @param limitOrder
   * @return String order id of submitted request.
   * @throws IOException
   */
  public String placeGateioLimitOrder(LimitOrder limitOrder) throws IOException {

    GateioOrderType type =
        (limitOrder.getType() == Order.OrderType.BID) ? GateioOrderType.BUY : GateioOrderType.SELL;

    return placeGateioLimitOrder(
        limitOrder.getCurrencyPair(),
        type,
        limitOrder.getLimitPrice(),
        limitOrder.getOriginalAmount());
  }

  /**
   * Submits a Limit Order to be executed on the Gateio Exchange for the desired market defined by
   * {@code currencyPair}. WARNING - Gateio will return true regardless of whether or not an order
   * actually gets created. The reason for this is that orders are simply submitted to a queue in
   * their back-end. One example for why an order might not get created is because there are
   * insufficient funds. The best attempt you can make to confirm that the order was created is to
   * poll {@link #getGateioOpenOrders}. However if the order is created and executed before it is
   * caught in its open state from calling {@link #getGateioOpenOrders} then the only way to confirm
   * would be confirm the expected difference in funds available for your account.
   *
   * @param currencyPair
   * @param orderType
   * @param rate
   * @param amount
   * @return String order id of submitted request.
   * @throws IOException
   */
  public String placeGateioLimitOrder(
      CurrencyPair currencyPair, GateioOrderType orderType, BigDecimal rate, BigDecimal amount)
      throws IOException {

    String pair = formatCurrencyPair(currencyPair);

    GateioPlaceOrderReturn orderId;
    if (orderType.equals(GateioOrderType.BUY)) {
      orderId = gateioAuthenticated.buy(pair, rate, amount, apiKey, signatureCreator);
    } else {
      orderId = gateioAuthenticated.sell(pair, rate, amount, apiKey, signatureCreator);
    }

    return handleResponse(orderId).getOrderId();
  }

  public boolean cancelOrder(String orderId, CurrencyPair currencyPair) throws IOException {

    GateioBaseResponse cancelOrderResult =
        gateioAuthenticated.cancelOrder(orderId, GateioUtils.toPairString(currencyPair), apiKey, signatureCreator);

    return handleResponse(cancelOrderResult).isResult();
  }

  /**
   * Cancels all orders. See https://gate.io/api2.
   *
   * @param type order type(0:sell,1:buy,-1:all)
   * @param currencyPair currency pair
   * @return
   * @throws IOException
   */
  public boolean cancelAllOrders(String type, CurrencyPair currencyPair) throws IOException {

    GateioBaseResponse cancelAllOrdersResult =
        gateioAuthenticated.cancelAllOrders(type, formatCurrencyPair(currencyPair), apiKey, signatureCreator);

    return handleResponse(cancelAllOrdersResult).isResult();
  }

  public GateioOpenOrders getGateioOpenOrders() throws IOException {

    GateioOpenOrders gateioOpenOrdersReturn = gateioAuthenticated.getOpenOrders(apiKey, signatureCreator);

    return handleResponse(gateioOpenOrdersReturn);
  }

  public GateioOrderStatus getGateioOrderStatus(String orderId, CurrencyPair currencyPair)
      throws IOException {

    GateioOrderStatus orderStatus =
        gateioAuthenticated.getOrderStatus(
            orderId, GateioUtils.toPairString(currencyPair), apiKey, signatureCreator);

    return handleResponse(orderStatus);
  }

  public GateioTradeHistoryReturn getGateioTradeHistory(CurrencyPair currencyPair)
      throws IOException {

    GateioTradeHistoryReturn gateioTradeHistoryReturn =
        gateioAuthenticated.getUserTradeHistory(apiKey, signatureCreator, GateioUtils.toPairString(currencyPair));

    return handleResponse(gateioTradeHistoryReturn);
  }

  private String formatCurrencyPair(CurrencyPair currencyPair) {
    return String.format(
            "%s_%s", currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode())
        .toLowerCase();
  }

  public static class GateioCancelOrderParams
      implements CancelOrderByIdParams, CancelOrderByCurrencyPair {
    public final CurrencyPair currencyPair;
    public final String orderId;

    public GateioCancelOrderParams(CurrencyPair currencyPair, String orderId) {
      this.currencyPair = currencyPair;
      this.orderId = orderId;
    }

    @Override
    public String getOrderId() {
      return orderId;
    }

    @Override
    public CurrencyPair getCurrencyPair() {
      return currencyPair;
    }
  }
}
