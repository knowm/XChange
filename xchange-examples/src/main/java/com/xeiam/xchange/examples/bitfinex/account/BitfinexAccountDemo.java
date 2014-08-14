package com.xeiam.xchange.examples.bitfinex.account;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitfinex.v1.dto.account.BitfinexMarginInfosResponse;
import com.xeiam.xchange.bitfinex.v1.service.polling.BitfinexAccountServiceRaw;
import com.xeiam.xchange.examples.bitfinex.BitfinexDemoUtils;

public class BitfinexAccountDemo {

  public static void main(String[] args) throws IOException {

    Exchange bfx = BitfinexDemoUtils.createExchange();
    BitfinexAccountServiceRaw accountService = (BitfinexAccountServiceRaw) bfx.getPollingAccountService();
    BitfinexMarginInfosResponse[] marginInfos = accountService.getBitfinexMarginInfos();
    System.out.println("Margin infos response: " + marginInfos[0]);

  }

}
