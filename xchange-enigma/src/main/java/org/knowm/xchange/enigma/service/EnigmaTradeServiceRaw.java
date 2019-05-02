package org.knowm.xchange.enigma.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.enigma.dto.trade.EnigmaExecuteQuoteRequest;
import org.knowm.xchange.enigma.dto.trade.EnigmaExecutedQuote;
import org.knowm.xchange.enigma.dto.trade.EnigmaNewOrder;
import org.knowm.xchange.enigma.dto.trade.EnigmaNewOrderRequest;
import si.mazi.rescu.HttpStatusIOException;

public class EnigmaTradeServiceRaw extends EnigmaBaseService {

  public EnigmaTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public EnigmaNewOrder submitOrder(String accessToken, EnigmaNewOrderRequest orderRequest)
      throws IOException {
    try {
      return this.enigmaAuthenticated.submitOrder(accessToken, orderRequest);
    } catch (HttpStatusIOException httpStatusIOException) {
      throw handleError(httpStatusIOException);
    }
  }

  public EnigmaExecutedQuote askForQuote(String accessToken, EnigmaExecuteQuoteRequest quoteRequest)
      throws IOException {
    try {
      return this.enigmaAuthenticated.askForQuote(accessToken, quoteRequest);
    } catch (HttpStatusIOException httpStatusIOException) {
      throw handleError(httpStatusIOException);
    }
  }

  public EnigmaExecutedQuote executeQuoteRequest(
      String accessToken, EnigmaExecuteQuoteRequest quoteRequest) throws IOException {
    try {
      return this.enigmaAuthenticated.executeQuoteRequest(accessToken, quoteRequest);
    } catch (HttpStatusIOException httpStatusIOException) {
      throw handleError(httpStatusIOException);
    }
  }
}
