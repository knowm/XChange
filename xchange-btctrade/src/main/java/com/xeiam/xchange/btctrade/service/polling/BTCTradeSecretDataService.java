package com.xeiam.xchange.btctrade.service.polling;

import java.io.IOException;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btctrade.dto.BTCTradeSecretData;
import com.xeiam.xchange.btctrade.dto.BTCTradeSecretResponse;

public class BTCTradeSecretDataService extends BTCTradeBasePollingService {

  /**
   * @param exchangeSpecification
   */
  public BTCTradeSecretDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  public BTCTradeSecretData getSecretData() throws IOException {

    BTCTradeSecretResponse response = btcTrade.getSecret(exchangeSpecification.getSecretKey(), exchangeSpecification.getApiKey());
    if (response.getResult()) {
      return response.getData();
    }
    else {
      throw new ExchangeException(response.getMessage());
    }
  }

}
