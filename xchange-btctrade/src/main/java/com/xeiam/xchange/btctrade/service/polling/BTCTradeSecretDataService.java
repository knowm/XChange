package com.xeiam.xchange.btctrade.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.btctrade.dto.BTCTradeSecretData;
import com.xeiam.xchange.btctrade.dto.BTCTradeSecretResponse;
import com.xeiam.xchange.exceptions.ExchangeException;

public class BTCTradeSecretDataService extends BTCTradeBasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BTCTradeSecretDataService(Exchange exchange) {

    super(exchange);
  }

  public BTCTradeSecretData getSecretData() throws IOException {

    BTCTradeSecretResponse response = btcTrade.getSecret(exchange.getExchangeSpecification().getSecretKey(),
        exchange.getExchangeSpecification().getApiKey());
    if (response.getResult()) {
      return response.getData();
    } else {
      throw new ExchangeException(response.getMessage());
    }
  }

}
