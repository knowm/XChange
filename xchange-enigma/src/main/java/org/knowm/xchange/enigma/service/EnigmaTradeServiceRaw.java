package org.knowm.xchange.enigma.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.enigma.dto.trade.EnigmaExecuteQuoteRequest;
import org.knowm.xchange.enigma.dto.trade.EnigmaExecutedQuote;
import org.knowm.xchange.enigma.dto.trade.EnigmaNewOrder;
import org.knowm.xchange.enigma.dto.trade.EnigmaNewOrderRequest;

public class EnigmaTradeServiceRaw extends EnigmaBaseService {

  public EnigmaTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public EnigmaNewOrder submitOrder(EnigmaNewOrderRequest orderRequest) throws IOException {
    return this.enigmaAuthenticated.submitOrder(accessToken(), orderRequest);
  }

  public EnigmaExecutedQuote askForQuote(EnigmaExecuteQuoteRequest quoteRequest)
      throws IOException {
    return this.enigmaAuthenticated.askForQuote(accessToken(), quoteRequest);
  }

  public EnigmaExecutedQuote executeQuoteRequest(EnigmaExecuteQuoteRequest quoteRequest)
      throws IOException {
    return this.enigmaAuthenticated.executeQuoteRequest(accessToken(), quoteRequest);
  }
}
