package org.knowm.xchange.lakebtc.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.lakebtc.dto.account.LakeBTCAccountInfoResponse;
import org.knowm.xchange.lakebtc.dto.account.LakeBTCAccountRequest;

/** User: cristian.lucaci Date: 10/3/2014 Time: 5:02 PM */
public class LakeBTCAccountServiceRaw extends LakeBTCBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public LakeBTCAccountServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public LakeBTCAccountInfoResponse getLakeBTCAccountInfo() throws IOException {
    return checkResult(
        lakeBTCAuthenticated.getAccountInfo(
            signatureCreator, exchange.getNonceFactory(), new LakeBTCAccountRequest()));
  }
}
