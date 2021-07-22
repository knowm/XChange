package org.knowm.xchange.ascendex.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ascendex.AscendexException;

import org.knowm.xchange.ascendex.dto.trade.AscendexCancelOrderRequestPayload;
import org.knowm.xchange.ascendex.dto.trade.AscendexOpenOrdersResponse;
import org.knowm.xchange.ascendex.dto.trade.AscendexPlaceOrderRequestPayload;
import org.knowm.xchange.ascendex.dto.trade.AscendexOrderResponse;

import java.io.IOException;
import java.util.List;

public class AscendexTradeServiceRaw extends AscendexBaseService {

  public AscendexTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public AscendexOrderResponse placeBitmaxOrder(AscendexPlaceOrderRequestPayload payload)
      throws AscendexException, IOException {
    return checkResult(
        bitmaxAuthenticated.placeOrder(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getNonceFactory().createValue(),
            signatureCreator,
            "cash",
            payload));
  }

  public AscendexOrderResponse cancelBitmaxOrder(AscendexCancelOrderRequestPayload payload)
      throws AscendexException, IOException {
    return checkResult(
        bitmaxAuthenticated.cancelOrder(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getNonceFactory().createValue(),
            signatureCreator,
            "cash",
            payload.getOrderId(),
            payload.getSymbol(),
            payload.getTime()));
  }

  public AscendexOrderResponse cancelAllBitmaxOrdersBySymbol(String symbol)
      throws AscendexException, IOException {
    return checkResult(
        bitmaxAuthenticated.cancelAllOrders(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getNonceFactory().createValue(),
            signatureCreator,
            "cash",
            symbol));
  }

  public List<AscendexOpenOrdersResponse> getBitmaxOpenOrders(String symbol)
      throws AscendexException, IOException {

    return checkResult(
        bitmaxAuthenticated.getOpenOrders(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getNonceFactory().createValue(),
            signatureCreator,
            "cash",
            symbol));
  }

  public AscendexOpenOrdersResponse getBitmaxOrderById(String orderId)
      throws AscendexException, IOException {
    return checkResult(
        bitmaxAuthenticated.getOrderById(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getNonceFactory().createValue(),
            signatureCreator,
            "cash",
            orderId));
  }

  public List<AscendexOpenOrdersResponse> getBitmaxUserTrades(String symbol)
      throws AscendexException, IOException {
    return checkResult(
        bitmaxAuthenticated.getOrdersHistory(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getNonceFactory().createValue(),
            signatureCreator,
            "cash",
            50,
            symbol,
            true));
  }
}
