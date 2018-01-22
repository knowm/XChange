package org.knowm.xchange.kucoin.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.kucoin.dto.KucoinAdapters;
import org.knowm.xchange.kucoin.dto.KucoinOrderType;
import org.knowm.xchange.kucoin.dto.KucoinResponse;
import org.knowm.xchange.kucoin.dto.trading.KucoinOrder;

public class KucoinTradeServiceRaw extends KucoinBaseService {

  protected KucoinTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }
  
  /**
   * Places a limit order.
   */
  KucoinResponse<KucoinOrder> order(LimitOrder order) throws IOException {
    return kucoin.order(apiKey, exchange.getNonceFactory(), signatureCreator,
        KucoinAdapters.adaptCurrencyPair(order.getCurrencyPair()),
        KucoinOrderType.fromOrderType(order.getType()),
        order.getLimitPrice(),
        order.getOriginalAmount());
  }
}
