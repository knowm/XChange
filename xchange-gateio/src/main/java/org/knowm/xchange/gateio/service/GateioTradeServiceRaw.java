package org.knowm.xchange.gateio.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.gateio.GateioUtils;
import org.knowm.xchange.gateio.dto.GateioBaseResponse;
import org.knowm.xchange.gateio.dto.GateioOrderType;
import org.knowm.xchange.gateio.dto.trade.GateioOpenOrders;
import org.knowm.xchange.gateio.dto.trade.GateioOrderStatus;
import org.knowm.xchange.gateio.dto.trade.GateioPlaceOrderReturn;
import org.knowm.xchange.gateio.dto.trade.GateioTradeHistoryReturn;

public class GateioTradeServiceRaw extends GateioBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public GateioTradeServiceRaw(Exchange exchange) {

    super(exchange);
  }

  /**
   * Submits a Limit Order to be executed on the Gateio Exchange for the desired market defined by {@code CurrencyPair}. WARNING - Gateio will return
   * true regardless of whether or not an order actually gets created. The reason for this is that orders are simply submitted to a queue in their
   * back-end. One example for why an order might not get created is because there are insufficient funds. The best attempt you can make to confirm
   * that the order was created is to poll {@link #getGateioOpenOrders}. However if the order is created and executed before it is caught in its open
   * state from calling {@link #getGateioOpenOrders} then the only way to confirm would be confirm the expected difference in funds available for your
   * account.
   *
   * @param limitOrder
   * @return String order id of submitted request.
   * @throws IOException
   */
  public String placeGateioLimitOrder(LimitOrder limitOrder) throws IOException {

    GateioOrderType type = (limitOrder.getType() == Order.OrderType.BID) ? GateioOrderType.BUY : GateioOrderType.SELL;

    return placeGateioLimitOrder(limitOrder.getCurrencyPair(), type, limitOrder.getLimitPrice(), limitOrder.getOriginalAmount());
  }

  /**
   * Submits a Limit Order to be executed on the Gateio Exchange for the desired market defined by {@code currencyPair}. WARNING - Gateio will return
   * true regardless of whether or not an order actually gets created. The reason for this is that orders are simply submitted to a queue in their
   * back-end. One example for why an order might not get created is because there are insufficient funds. The best attempt you can make to confirm
   * that the order was created is to poll {@link #getGateioOpenOrders}. However if the order is created and executed before it is caught in its open
   * state from calling {@link #getGateioOpenOrders} then the only way to confirm would be confirm the expected difference in funds available for your
   * account.
   *
   * @param currencyPair
   * @param orderType
   * @param rate
   * @param amount
   * @return String order id of submitted request.
   * @throws IOException
   */
  public String placeGateioLimitOrder(CurrencyPair currencyPair, GateioOrderType orderType, BigDecimal rate, BigDecimal amount) throws IOException {

    String pair = String.format("%s_%s", currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode()).toLowerCase();

    GateioPlaceOrderReturn orderId;
    if (orderType.equals(GateioOrderType.BUY)) {
      orderId = bter.buy(pair, rate, amount, apiKey, signatureCreator, exchange.getNonceFactory());
    } else {
      orderId = bter.sell(pair, rate, amount, apiKey, signatureCreator, exchange.getNonceFactory());
    }

    return handleResponse(orderId).getOrderId();
  }

  public boolean cancelOrder(String orderId) throws IOException {

    GateioBaseResponse cancelOrderResult = bter.cancelOrder(orderId, apiKey, signatureCreator, exchange.getNonceFactory());

    return handleResponse(cancelOrderResult).isResult();
  }

  public GateioOpenOrders getGateioOpenOrders() throws IOException {

    GateioOpenOrders gateioOpenOrdersReturn = bter.getOpenOrders(apiKey, signatureCreator, exchange.getNonceFactory());

    return handleResponse(gateioOpenOrdersReturn);
  }

  public GateioOrderStatus getGateioOrderStatus(String orderId) throws IOException {

    GateioOrderStatus orderStatus = bter.getOrderStatus(orderId, apiKey, signatureCreator, exchange.getNonceFactory());

    return handleResponse(orderStatus);
  }

  public GateioTradeHistoryReturn getGateioTradeHistory(CurrencyPair currencyPair) throws IOException {

    GateioTradeHistoryReturn gateioTradeHistoryReturn = bter.getUserTradeHistory(apiKey, signatureCreator, exchange.getNonceFactory(),
        GateioUtils.toPairString(currencyPair));

    return handleResponse(gateioTradeHistoryReturn);
  }
}
