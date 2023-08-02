package org.knowm.xchange.gateio.service;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.Validate;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.gateio.GateioAdapters;
import org.knowm.xchange.gateio.GateioExchange;
import org.knowm.xchange.gateio.dto.account.GateioOrder;
import org.knowm.xchange.instrument.Instrument;

public class GateioTradeServiceRaw extends GateioBaseService {

  public GateioTradeServiceRaw(GateioExchange exchange) {
    super(exchange);
  }


  public List<GateioOrder> listOrders(Instrument instrument, OrderStatus orderStatus) throws IOException {
    // validate arguments
    Validate.notNull(orderStatus);
    var allowedOrderStatuses = Set.of(OrderStatus.OPEN, OrderStatus.CLOSED);
    Validate.validState(allowedOrderStatuses.contains(orderStatus), "Allowed order statuses are: {}", allowedOrderStatuses);
    Validate.notNull(instrument);

    return gateioV4Authenticated.listOrders(apiKey, exchange.getNonceFactory(),
        gateioV4ParamsDigest, GateioAdapters.toString(instrument), GateioAdapters.toString(orderStatus)
    );

  }


  public GateioOrder createOrder(GateioOrder gateioOrder) throws IOException {
    return gateioV4Authenticated.createOrder(apiKey, exchange.getNonceFactory(), gateioV4ParamsDigest, gateioOrder);
  }


  public GateioOrder getOrder(String orderId, Instrument instrument) throws IOException {
    return gateioV4Authenticated.getOrder(apiKey, exchange.getNonceFactory(), gateioV4ParamsDigest,
        orderId, GateioAdapters.toString(instrument));
  }


  public GateioOrder cancelOrderRaw(String orderId, Instrument instrument) throws IOException {
    return gateioV4Authenticated.cancelOrder(apiKey, exchange.getNonceFactory(), gateioV4ParamsDigest,
        orderId, GateioAdapters.toString(instrument));
  }


}
