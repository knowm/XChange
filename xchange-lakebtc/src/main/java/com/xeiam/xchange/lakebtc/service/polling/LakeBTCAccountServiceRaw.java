package com.xeiam.xchange.lakebtc.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.lakebtc.dto.account.LakeBTCAccountInfoResponse;
import com.xeiam.xchange.lakebtc.dto.account.LakeBTCAccountRequest;

/**
 * User: cristian.lucaci Date: 10/3/2014 Time: 5:02 PM
 */
public class LakeBTCAccountServiceRaw extends LakeBTCBasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public LakeBTCAccountServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public LakeBTCAccountInfoResponse getLakeBTCAccountInfo() throws IOException {
    return checkResult(lakeBTCAuthenticated.getAccountInfo(signatureCreator, exchange.getNonceFactory(), new LakeBTCAccountRequest()));
  }

}
