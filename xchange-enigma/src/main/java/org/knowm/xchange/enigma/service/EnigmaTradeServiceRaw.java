package org.knowm.xchange.enigma.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.enigma.dto.BaseResponse;
import org.knowm.xchange.enigma.dto.trade.*;

import java.io.IOException;

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
}
