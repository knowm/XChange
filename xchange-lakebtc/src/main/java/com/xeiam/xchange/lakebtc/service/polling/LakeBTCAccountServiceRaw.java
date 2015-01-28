package com.xeiam.xchange.lakebtc.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.lakebtc.LakeBTCAuthenticated;
import com.xeiam.xchange.lakebtc.dto.account.LakeBTCAccountInfoResponse;
import com.xeiam.xchange.lakebtc.dto.account.LakeBTCAccountRequest;

/**
 * User: cristian.lucaci Date: 10/3/2014 Time: 5:02 PM
 */
public class LakeBTCAccountServiceRaw extends LakeBTCBasePollingService<LakeBTCAuthenticated> {

  /**
   * Constructor
   *
   * @param exchange
   */
  public LakeBTCAccountServiceRaw(Exchange exchange) {

    super(LakeBTCAuthenticated.class, exchange);
  }

  public LakeBTCAccountInfoResponse getLakeBTCAccountInfo() throws IOException {
    return checkResult(btcLakeBTC.getAccountInfo(signatureCreator, exchange.getNonceFactory(), new LakeBTCAccountRequest()));
  }

}
