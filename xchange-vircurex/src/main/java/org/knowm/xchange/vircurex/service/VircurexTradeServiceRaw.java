package org.knowm.xchange.vircurex.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.vircurex.VircurexAdapters;
import org.knowm.xchange.vircurex.VircurexUtils;
import org.knowm.xchange.vircurex.dto.trade.VircurexOpenOrdersReturn;
import org.knowm.xchange.vircurex.dto.trade.VircurexPlaceOrderReturn;

public class VircurexTradeServiceRaw extends VircurexBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public VircurexTradeServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public String placeVircurexLimitOrder(LimitOrder limitOrder) throws IOException {

    String type = limitOrder.getType() == Order.OrderType.BID ? "buy" : "sell";
    String timestamp = VircurexUtils.getUtcTimestamp();
    long nonce = exchange.getNonceFactory().createValue();
    VircurexSha2Digest digest =
        new VircurexSha2Digest(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            timestamp,
            nonce,
            "create_order",
            type,
            limitOrder.getOriginalAmount().floatValue() + "",
            limitOrder.getCurrencyPair().counter.getCurrencyCode().toLowerCase(),
            limitOrder.getLimitPrice().floatValue() + "",
            limitOrder.getCurrencyPair().base.getCurrencyCode().toLowerCase());

    VircurexPlaceOrderReturn ret =
        vircurexAuthenticated.trade(
            exchange.getExchangeSpecification().getApiKey(),
            nonce,
            digest.toString(),
            timestamp,
            type,
            limitOrder.getOriginalAmount().floatValue() + "",
            limitOrder.getCurrencyPair().counter.getCurrencyCode().toLowerCase(),
            limitOrder.getLimitPrice().floatValue() + "",
            limitOrder.getCurrencyPair().base.getCurrencyCode().toLowerCase());

    timestamp = VircurexUtils.getUtcTimestamp();
    nonce = exchange.getNonceFactory().createValue();

    digest =
        new VircurexSha2Digest(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            timestamp,
            nonce,
            "release_order",
            ret.getOrderId());

    ret =
        vircurexAuthenticated.release(
            exchange.getExchangeSpecification().getApiKey(),
            nonce,
            digest.toString(),
            timestamp,
            ret.getOrderId());
    return ret.getOrderId();
  }

  public OpenOrders getVircurexOpenOrders() throws IOException {

    String timestamp = VircurexUtils.getUtcTimestamp();
    long nonce = exchange.getNonceFactory().createValue();
    VircurexSha2Digest digest =
        new VircurexSha2Digest(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            timestamp,
            nonce,
            "read_orders");
    VircurexOpenOrdersReturn openOrdersReturn =
        vircurexAuthenticated.getOpenOrders(
            exchange.getExchangeSpecification().getUserName(),
            nonce,
            digest.toString(),
            timestamp,
            VircurexUtils.RELEASED_ORDER);

    return new OpenOrders(VircurexAdapters.adaptOpenOrders(openOrdersReturn.getOpenOrders()));
  }
}
