package org.knowm.xchange.upbit.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.upbit.dto.account.UpbitBalances;
import org.knowm.xchange.upbit.dto.trade.UpbitOrderRequest;
import org.knowm.xchange.upbit.dto.trade.UpbitOrderResponse;

public class UpbitTradeServiceRaw extends UpbitBaseService {

  /** Constructor */
  public UpbitTradeServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public UpbitBalances getWallet() throws IOException {
    UpbitBalances upbitBalances = upbit.getWallet(this.signatureCreator);
    return upbitBalances;
  }

  public UpbitOrderResponse limitOrder(LimitOrder limitOrder) throws IOException {
    UpbitOrderRequest upbitOrderRequest = new UpbitOrderRequest();
    String marketId =
        limitOrder.getCurrencyPair().counter + "-" + limitOrder.getCurrencyPair().base;
    upbitOrderRequest.setMarketId(marketId);
    upbitOrderRequest.setOrderType(limitOrder.getType().name().toLowerCase());
    upbitOrderRequest.setVolume(limitOrder.getOriginalAmount().toString());
    upbitOrderRequest.setPrice(limitOrder.getLimitPrice().toString());
    upbitOrderRequest.setSide(limitOrder.getType().equals(Order.OrderType.ASK) ? "ask" : "bid");
    upbitOrderRequest.setOrderType("limit");
    return upbit.limitOrder(this.signatureCreator, upbitOrderRequest);
  }

  public UpbitOrderResponse cancelOrderRaw(String cancelId) throws IOException {
    UpbitOrderResponse upbitOrderResponse = upbit.cancelOrder(this.signatureCreator, cancelId);
    return upbitOrderResponse;
  }

  public UpbitOrderResponse getOrderRaw(String orderId) throws IOException {
    UpbitOrderResponse upbitOrderResponse = upbit.getOrder(this.signatureCreator, orderId);
    return upbitOrderResponse;
  }
}
