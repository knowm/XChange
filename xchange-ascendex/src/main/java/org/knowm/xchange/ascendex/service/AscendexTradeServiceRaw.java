package org.knowm.xchange.ascendex.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ascendex.AscendexException;
import org.knowm.xchange.ascendex.dto.trade.AscendexCancelOrderRequestPayload;
import org.knowm.xchange.ascendex.dto.trade.AscendexOpenOrdersResponse;
import org.knowm.xchange.ascendex.dto.trade.AscendexOrderResponse;
import org.knowm.xchange.ascendex.dto.trade.AscendexPlaceOrderRequestPayload;

public class AscendexTradeServiceRaw extends AscendexBaseService {

  private static final String ACCOUNT_CASH_CATEGORY = "cash";

  public AscendexTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public AscendexOrderResponse placeAscendexOrder(AscendexPlaceOrderRequestPayload payload)
      throws AscendexException, IOException {
    return checkResult(
        ascendexAuthenticated.placeOrder(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getNonceFactory().createValue(),
            signatureCreator,
            ACCOUNT_CASH_CATEGORY,
            payload));
  }

  public AscendexOrderResponse cancelAscendexOrder(AscendexCancelOrderRequestPayload payload)
      throws AscendexException, IOException {
    return checkResult(
        ascendexAuthenticated.cancelOrder(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getNonceFactory().createValue(),
            signatureCreator,
            ACCOUNT_CASH_CATEGORY,
            payload.getOrderId(),
            payload.getSymbol(),
            payload.getTime()));
  }

  public AscendexOrderResponse cancelAllAscendexOrdersBySymbol(String symbol)
      throws AscendexException, IOException {
    return checkResult(
        ascendexAuthenticated.cancelAllOrders(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getNonceFactory().createValue(),
            signatureCreator,
            ACCOUNT_CASH_CATEGORY,
            symbol));
  }

  public List<AscendexOpenOrdersResponse> getAscendexOpenOrders(String symbol)
      throws AscendexException, IOException {

    return checkResult(
        ascendexAuthenticated.getOpenOrders(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getNonceFactory().createValue(),
            signatureCreator,
            ACCOUNT_CASH_CATEGORY,
            symbol));
  }

  public AscendexOpenOrdersResponse getAscendexOrderById(String orderId)
      throws AscendexException, IOException {
    return checkResult(
        ascendexAuthenticated.getOrderById(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getNonceFactory().createValue(),
            signatureCreator,
            ACCOUNT_CASH_CATEGORY,
            orderId));
  }

  public List<AscendexOpenOrdersResponse> getAscendexUserTrades(String symbol)
      throws AscendexException, IOException {
    return checkResult(
        ascendexAuthenticated.getOrdersHistory(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getNonceFactory().createValue(),
            signatureCreator,
            ACCOUNT_CASH_CATEGORY,
            50,
            symbol,
            true));
  }
}
