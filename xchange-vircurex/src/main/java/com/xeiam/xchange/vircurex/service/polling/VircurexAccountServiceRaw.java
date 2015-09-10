package com.xeiam.xchange.vircurex.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.vircurex.VircurexUtils;
import com.xeiam.xchange.vircurex.dto.account.VircurexAccountInfoReturn;
import com.xeiam.xchange.vircurex.service.VircurexSha2Digest;

public class VircurexAccountServiceRaw extends VircurexBasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public VircurexAccountServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public VircurexAccountInfoReturn getVircurexAccountInfo() throws IOException {

    String timestamp = VircurexUtils.getUtcTimestamp();
    long nonce = exchange.getNonceFactory().createValue();
    VircurexSha2Digest digest = new VircurexSha2Digest(exchange.getExchangeSpecification().getApiKey(),
        exchange.getExchangeSpecification().getUserName(), timestamp, nonce, "get_balances");
    VircurexAccountInfoReturn info = vircurexAuthenticated.getInfo(exchange.getExchangeSpecification().getUserName(), nonce, digest.toString(),
        timestamp);
    return info;
  }
}
