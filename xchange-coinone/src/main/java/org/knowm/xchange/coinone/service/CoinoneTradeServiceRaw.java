package org.knowm.xchange.coinone.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinone.dto.CoinoneException;
import org.knowm.xchange.coinone.dto.trade.CoinoneOrderInfoRequest;
import org.knowm.xchange.coinone.dto.trade.CoinoneOrderInfoResponse;
import org.knowm.xchange.coinone.dto.trade.CoinoneTradeCancelRequest;
import org.knowm.xchange.coinone.dto.trade.CoinoneTradeRequest;
import org.knowm.xchange.coinone.dto.trade.CoinoneTradeResponse;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;

public class CoinoneTradeServiceRaw extends CoinoneBaseService {

  public CoinoneTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public CoinoneTradeResponse placeLimitOrderRaw(LimitOrder limitOrder)
      throws CoinoneException, IOException {
    CoinoneTradeRequest request =
        new CoinoneTradeRequest(
            apiKey,
            exchange.getNonceFactory().createValue(),
            limitOrder.getLimitPrice(),
            limitOrder.getOriginalAmount(),
            limitOrder.getCurrencyPair().base);
    if (limitOrder.getType().equals(OrderType.ASK)) {
      return coinone.limitSell(payloadCreator, signatureCreator, request);
    } else {
      return coinone.limitBuy(payloadCreator, signatureCreator, request);
    }
  }

  public CoinoneTradeResponse cancerOrder(CoinoneTradeCancelRequest orderParams)
      throws CoinoneException, IOException {
    if (orderParams.getAccessTocken() == null || orderParams.getNonce() == null) {
      orderParams.setAccessTocken(apiKey);
      orderParams.setNonce(exchange.getNonceFactory().createValue());
    }
    return coinone.cancelOrder(payloadCreator, signatureCreator, orderParams);
  }

  public CoinoneOrderInfoResponse getOrderInfo(String orderId, CurrencyPair currencyPair)
      throws CoinoneException, IOException {
    CoinoneOrderInfoRequest request =
        new CoinoneOrderInfoRequest(
            apiKey,
            exchange.getNonceFactory().createValue(),
            orderId,
            currencyPair.base.getSymbol().toLowerCase());
    return coinone.getOrder(payloadCreator, signatureCreator, request);
  }
}
