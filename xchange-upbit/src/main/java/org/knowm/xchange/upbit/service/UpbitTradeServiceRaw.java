package org.knowm.xchange.upbit.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.instrument.Instrument;
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
    final Instrument instrument = limitOrder.getInstrument();
    final String marketId = instrument.getCounter() + "-" + instrument.getBase();

    UpbitOrderRequest upbitOrderRequest = new UpbitOrderRequest();
    upbitOrderRequest.setMarketId(marketId);
    upbitOrderRequest.setVolume(limitOrder.getOriginalAmount().toString());
    upbitOrderRequest.setPrice(limitOrder.getLimitPrice().toString());
    upbitOrderRequest.setSide(UpbitUtils.toSide(limitOrder.getType()));
    upbitOrderRequest.setOrderType("limit");
    return upbit.placeOrder(this.signatureCreator, upbitOrderRequest);
  }

  public UpbitOrderResponse marketOrder(MarketOrder marketOrder) throws IOException {
    final Instrument instrument = marketOrder.getInstrument();
    final String marketId = instrument.getCounter() + "-" + instrument.getBase();

    UpbitOrderRequest upbitOrderRequest = new UpbitOrderRequest();
    upbitOrderRequest.setMarketId(marketId);
    upbitOrderRequest.setSide(UpbitUtils.toSide(marketOrder.getType()));

    if (marketOrder.getType() == OrderType.BID) {
      upbitOrderRequest.setPrice(marketOrder.getOriginalAmount().toString());
      upbitOrderRequest.setOrderType("price");
    } else if (marketOrder.getType() == OrderType.ASK) {
      upbitOrderRequest.setVolume(marketOrder.getOriginalAmount().toString());
      upbitOrderRequest.setOrderType("market");
    }
    return upbit.placeOrder(this.signatureCreator, upbitOrderRequest);
  }

  public UpbitOrderResponse cancelOrderRaw(String cancelId) throws IOException {
    return upbit.cancelOrder(this.signatureCreator, cancelId);
  }

  public UpbitOrderResponse getOrderRaw(String orderId) throws IOException {
    return upbit.getOrder(this.signatureCreator, orderId);
  }
}
