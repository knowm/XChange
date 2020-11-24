package org.knowm.xchange.upbit.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.upbit.UpbitUtils;
import org.knowm.xchange.upbit.dto.account.UpbitBalances;
import org.knowm.xchange.upbit.dto.trade.UpbitOrderRequest;
import org.knowm.xchange.upbit.dto.trade.UpbitOrderResponse;

public class UpbitTradeServiceRaw extends UpbitBaseService {

  /** Constructor */
  public UpbitTradeServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public UpbitBalances getWallet() throws IOException {
    return upbit.getWallet(this.signatureCreator);
  }

  public UpbitOrderResponse limitOrder(LimitOrder limitOrder) throws IOException {
    UpbitOrderRequest upbitOrderRequest = new UpbitOrderRequest();
    String marketId =
        limitOrder.getCurrencyPair().counter + "-" + limitOrder.getCurrencyPair().base;
    upbitOrderRequest.setMarketId(marketId);
    upbitOrderRequest.setVolume(limitOrder.getOriginalAmount().toString());
    upbitOrderRequest.setPrice(limitOrder.getLimitPrice().toString());
    upbitOrderRequest.setSide(UpbitUtils.toSide(limitOrder.getType()));
    upbitOrderRequest.setOrderType("limit");
    return upbit.limitOrder(this.signatureCreator, upbitOrderRequest);
  }

  public UpbitOrderResponse cancelOrderRaw(String cancelId) throws IOException {
    return upbit.cancelOrder(this.signatureCreator, cancelId);
  }

  public UpbitOrderResponse getOrderRaw(String orderId) throws IOException {
    return upbit.getOrder(this.signatureCreator, orderId);
  }
}
