package org.knowm.xchange.btctrade.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.btctrade.dto.BTCTradeSecretData;
import org.knowm.xchange.btctrade.dto.BTCTradeSecretResponse;
import org.knowm.xchange.exceptions.ExchangeException;

public class BTCTradeSecretDataService extends BTCTradeBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BTCTradeSecretDataService(Exchange exchange) {

    super(exchange);
  }

  public BTCTradeSecretData getSecretData() throws IOException {

    BTCTradeSecretResponse response =
        btcTrade.getSecret(
            exchange.getExchangeSpecification().getSecretKey(),
            exchange.getExchangeSpecification().getApiKey());
    if (response.getResult()) {
      return response.getData();
    } else {
      throw new ExchangeException(response.getMessage());
    }
  }
}
