package com.xeiam.xchange.vircurex.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.vircurex.VircurexAdapters;
import com.xeiam.xchange.vircurex.VircurexUtils;
import com.xeiam.xchange.vircurex.dto.trade.VircurexOpenOrdersReturn;
import com.xeiam.xchange.vircurex.dto.trade.VircurexPlaceOrderReturn;
import com.xeiam.xchange.vircurex.service.VircurexSha2Digest;

public class VircurexTradeServiceRaw extends VircurexBasePollingService {

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
    VircurexSha2Digest digest = new VircurexSha2Digest(exchange.getExchangeSpecification().getApiKey(),
        exchange.getExchangeSpecification().getUserName(), timestamp, nonce, "create_order", type.toString(),
        limitOrder.getTradableAmount().floatValue() + "", limitOrder.getCurrencyPair().counter.getCurrencyCode().toLowerCase(),
        limitOrder.getLimitPrice().floatValue() + "", limitOrder.getCurrencyPair().base.getCurrencyCode().toLowerCase());

    VircurexPlaceOrderReturn ret = vircurexAuthenticated.trade(exchange.getExchangeSpecification().getApiKey(), nonce, digest.toString(), timestamp,
        type.toString(), limitOrder.getTradableAmount().floatValue() + "", limitOrder.getCurrencyPair().counter.getCurrencyCode().toLowerCase(),
        limitOrder.getLimitPrice().floatValue() + "", limitOrder.getCurrencyPair().base.getCurrencyCode().toLowerCase());

    timestamp = VircurexUtils.getUtcTimestamp();
    nonce = exchange.getNonceFactory().createValue();

    digest = new VircurexSha2Digest(exchange.getExchangeSpecification().getApiKey(), exchange.getExchangeSpecification().getUserName(), timestamp,
        nonce, "release_order", ret.getOrderId());

    ret = vircurexAuthenticated.release(exchange.getExchangeSpecification().getApiKey(), nonce, digest.toString(), timestamp, ret.getOrderId());
    return ret.getOrderId();
  }

  public OpenOrders getVircurexOpenOrders() throws IOException {

    String timestamp = VircurexUtils.getUtcTimestamp();
    long nonce = exchange.getNonceFactory().createValue();
    VircurexSha2Digest digest = new VircurexSha2Digest(exchange.getExchangeSpecification().getApiKey(),
        exchange.getExchangeSpecification().getUserName(), timestamp, nonce, "read_orders");
    VircurexOpenOrdersReturn openOrdersReturn = vircurexAuthenticated.getOpenOrders(exchange.getExchangeSpecification().getUserName(), nonce,
        digest.toString(), timestamp, VircurexUtils.RELEASED_ORDER);

    return new OpenOrders(VircurexAdapters.adaptOpenOrders(openOrdersReturn.getOpenOrders()));
  }

}
