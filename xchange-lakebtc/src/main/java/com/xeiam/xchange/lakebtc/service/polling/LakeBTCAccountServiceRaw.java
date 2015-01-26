package com.xeiam.xchange.lakebtc.service.polling;

import java.io.IOException;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.lakebtc.LakeBTCAuthenticated;
import com.xeiam.xchange.lakebtc.LakeBTCUtil;
import com.xeiam.xchange.lakebtc.dto.account.LakeBTCAccountInfoResponse;
import com.xeiam.xchange.lakebtc.dto.account.LakeBTCAccountRequest;

/**
 * User: cristian.lucaci Date: 10/3/2014 Time: 5:02 PM
 */
public class LakeBTCAccountServiceRaw extends LakeBTCBasePollingService<LakeBTCAuthenticated> {

  /**
   * Constructor
   *
   * @param exchangeSpecification
   * @param tonceFactory
   */
  public LakeBTCAccountServiceRaw(Exchange exchange, SynchronizedValueFactory<Long> tonceFactory) {
    super(LakeBTCAuthenticated.class, exchange, tonceFactory);
  }

  public LakeBTCAccountInfoResponse getLakeBTCAccountInfo() throws IOException {
    return checkResult(btcLakeBTC.getAccountInfo(signatureCreator, LakeBTCUtil.getNonce(), new LakeBTCAccountRequest()));
  }

}
