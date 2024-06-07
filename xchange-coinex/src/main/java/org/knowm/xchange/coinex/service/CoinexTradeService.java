package org.knowm.xchange.coinex.service;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import org.apache.commons.lang3.Validate;
import org.knowm.xchange.coinex.CoinexAdapters;
import org.knowm.xchange.coinex.CoinexErrorAdapter;
import org.knowm.xchange.coinex.CoinexExchange;
import org.knowm.xchange.coinex.dto.CoinexException;
import org.knowm.xchange.coinex.dto.account.CoinexOrder;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParamInstrument;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;

public class CoinexTradeService extends CoinexTradeServiceRaw implements TradeService {

  public CoinexTradeService(CoinexExchange exchange) {
    super(exchange);
  }


  @Override
  public Collection<Order> getOrder(OrderQueryParams... orderQueryParams) throws IOException {
    Validate.validState(orderQueryParams.length == 1);
    Validate.isInstanceOf(OrderQueryParamInstrument.class, orderQueryParams[0]);

    OrderQueryParamInstrument params = (OrderQueryParamInstrument) orderQueryParams[0];

    try {
      CoinexOrder gateioOrder = orderStatus(params.getInstrument(), params.getOrderId());
      return Collections.singletonList(CoinexAdapters.toOrder(gateioOrder));
    }
    catch (CoinexException e) {
      throw CoinexErrorAdapter.adapt(e);
    }
  }


}
