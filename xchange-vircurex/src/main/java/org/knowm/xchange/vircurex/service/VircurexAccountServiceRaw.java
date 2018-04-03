package org.knowm.xchange.vircurex.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.vircurex.VircurexUtils;
import org.knowm.xchange.vircurex.dto.account.VircurexAccountInfoReturn;

public class VircurexAccountServiceRaw extends VircurexBaseService {

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
    VircurexSha2Digest digest =
        new VircurexSha2Digest(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getUserName(),
            timestamp,
            nonce,
            "get_balances");
    VircurexAccountInfoReturn info =
        vircurexAuthenticated.getInfo(
            exchange.getExchangeSpecification().getUserName(), nonce, digest.toString(), timestamp);
    return info;
  }
}
