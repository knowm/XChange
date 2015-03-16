package com.xeiam.xchange.examples.lakebtc.account;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.examples.lakebtc.LakeBTCExamplesUtils;
import com.xeiam.xchange.lakebtc.dto.account.LakeBTCAccountInfoResponse;
import com.xeiam.xchange.lakebtc.service.polling.LakeBTCAccountServiceRaw;

public class LakeBTCAccountRawSpecificDemo {

  public static void main(String[] args) throws IOException {

    Exchange lakebtcExchange = LakeBTCExamplesUtils.createTestExchange();

    LakeBTCAccountServiceRaw rawLakeBtcAcctService = (LakeBTCAccountServiceRaw) lakebtcExchange.getPollingAccountService();

    LakeBTCAccountInfoResponse balanceInfo = rawLakeBtcAcctService.getLakeBTCAccountInfo();
    System.out.println(balanceInfo);

    //TODO: add more demos
  }
}
