package org.knowm.xchange.examples.lakebtc.account;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.examples.lakebtc.LakeBTCExamplesUtils;
import org.knowm.xchange.lakebtc.dto.account.LakeBTCAccountInfoResponse;
import org.knowm.xchange.lakebtc.service.LakeBTCAccountServiceRaw;

public class LakeBTCAccountRawSpecificDemo {

  public static void main(String[] args) throws IOException {

    Exchange lakebtcExchange = LakeBTCExamplesUtils.createTestExchange();

    LakeBTCAccountServiceRaw rawLakeBtcAcctService =
        (LakeBTCAccountServiceRaw) lakebtcExchange.getAccountService();

    LakeBTCAccountInfoResponse balanceInfo = rawLakeBtcAcctService.getLakeBTCAccountInfo();
    System.out.println(balanceInfo);

    // TODO: add more demos
  }
}
