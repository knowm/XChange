package org.known.xchange.dsx.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.known.xchange.dsx.dto.trade.DSXActiveOrdersReturn;
import org.known.xchange.dsx.dto.trade.DSXOrder;
import org.known.xchange.dsx.dto.trade.DSXTradeResult;
import org.known.xchange.dsx.dto.trade.DSXTradeReturn;

/**
 * @author Mikhail Wall
 */

public class DSXTradeServiceRaw extends DSXBaseService {

  private static final String MSG_NO_TRADES = "no trades";
  private static final String MSG_BAD_STATUS = "bad status";

  /**
   * Constructor
   *
   * @param exchange
   */
  protected DSXTradeServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public Map<Long, DSXOrder> getDSXActiveOrders(String pair) throws IOException {
    DSXActiveOrdersReturn orders = dsx.ActiveOrders(apiKey, signatureCreator, exchange.getNonceFactory(), pair);
    if ("no orders".equals(orders.getError())) {
      return new HashMap<>();
    }
    checkResult(orders);
    return orders.getReturnValue();
  }

  public DSXTradeResult tradeDSX(DSXOrder order) throws IOException {

    String pair = order.getPair().toLowerCase();
    DSXTradeReturn ret = dsx.Trade(apiKey, signatureCreator, exchange.getNonceFactory(), order.getType(), order.getRate(),
        order.getAmount(), pair);
    checkResult(ret);
    return ret.getReturnValue();
  }
}
