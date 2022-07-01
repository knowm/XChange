package org.knowm.xchange.enigma.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.enigma.dto.BaseResponse;
import org.knowm.xchange.enigma.dto.marketdata.EnigmaOpenOrders;
import org.knowm.xchange.enigma.dto.marketdata.EnigmaProduct;
import org.knowm.xchange.enigma.dto.trade.EnigmaExecuteQuoteRequest;
import org.knowm.xchange.enigma.dto.trade.EnigmaExecutedQuote;
import org.knowm.xchange.enigma.dto.trade.EnigmaLimitOrderRequest;
import org.knowm.xchange.enigma.dto.trade.EnigmaNewOrderRequest;
import org.knowm.xchange.enigma.dto.trade.EnigmaOrderSubmission;
import org.knowm.xchange.enigma.dto.trade.EnigmaQuote;
import org.knowm.xchange.enigma.dto.trade.EnigmaQuoteRequest;
import org.knowm.xchange.enigma.model.EnigmaException;

public class EnigmaTradeServiceRaw extends EnigmaBaseService {

  public EnigmaTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public EnigmaOrderSubmission submitOrder(EnigmaNewOrderRequest orderRequest) throws IOException {
    return this.enigmaAuthenticated.submitOrder(accessToken(), orderRequest);
  }

  public EnigmaQuote askForQuote(EnigmaQuoteRequest quoteRequest) throws IOException {
    return this.enigmaAuthenticated.askForQuote(accessToken(), quoteRequest);
  }

  public EnigmaExecutedQuote executeQuoteRequest(EnigmaExecuteQuoteRequest quoteRequest)
      throws IOException {
    return this.enigmaAuthenticated.executeQuoteRequest(accessToken(), quoteRequest);
  }

  public BaseResponse cancelOrder(Integer orderId) throws IOException {
    return new BaseResponse(200, "order cannot be canceled, only market order accepted", false);
  }

  public EnigmaOpenOrders openOrders() throws IOException {
    return this.enigmaAuthenticated.openOrders(accessToken());
  }

  public EnigmaOrderSubmission placeMarketOrderRequest(MarketOrder marketOrder) throws IOException {

    Integer productId = getProductId(marketOrder.getCurrencyPair());

    EnigmaNewOrderRequest enigmaNewOrderRequest = new EnigmaNewOrderRequest();
    enigmaNewOrderRequest.setQuantity(marketOrder.getOriginalAmount());
    enigmaNewOrderRequest.setSideId(marketOrder.getType().equals(Order.OrderType.ASK) ? 2 : 1);
    enigmaNewOrderRequest.setProductId(productId);

    return this.enigmaAuthenticated.submitOrder(accessToken(), enigmaNewOrderRequest);
  }

  public EnigmaOrderSubmission placeLimitOrderRequest(LimitOrder limitOrder) throws IOException {
    EnigmaLimitOrderRequest enigmaNewOrderRequest = new EnigmaLimitOrderRequest();
    enigmaNewOrderRequest.setQuantity(limitOrder.getOriginalAmount());
    enigmaNewOrderRequest.setSideId(limitOrder.getType().equals(Order.OrderType.ASK) ? 2 : 1);
    enigmaNewOrderRequest.setProductId(getProductId(limitOrder.getCurrencyPair()));
    enigmaNewOrderRequest.setLimitPrice(limitOrder.getLimitPrice());
    return this.enigmaAuthenticated.submitLimitOrder(accessToken(), enigmaNewOrderRequest);
  }

  private Integer getProductId(CurrencyPair currencyPair) throws IOException {
    List<EnigmaProduct> products = getProducts();
    return products.stream()
        .filter(
            product -> product.getProductName().equals(currencyPair.toString().replace("/", "-")))
        .map(EnigmaProduct::getProductId)
        .findFirst()
        .orElseThrow(() -> new EnigmaException("Currency pair not found"));
  }

  public List<EnigmaProduct> getProducts() throws IOException {
    return this.enigmaAuthenticated.getProducts(accessToken());
  }
}
