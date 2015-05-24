package com.xeiam.xchange.bitmarket.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitmarket.dto.account.BitMarketAccountInfo;
import com.xeiam.xchange.bitmarket.dto.account.BitMarketAccountInfoResponse;
import com.xeiam.xchange.exceptions.ExchangeException;

import java.io.IOException;

/**
 * @author kfonal
 */
public class BitMarketAccountServiceRaw extends BitMarketBasePollingService {

  public BitMarketAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public BitMarketAccountInfo getBitMarketAccountInfo() throws IOException, ExchangeException {

    BitMarketAccountInfoResponse response = bitMarketAuthenticated.info(apiKey, sign, exchange.getNonceFactory());

    if (!response.getSuccess()) {
      throw new ExchangeException(String.format("%d: %s", response.getError(), response.getErrorMsg()));
    }

    return response.getData();
  }
}
