package org.knowm.xchange.examples.bitfinex.account;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexMarginInfosResponse;
import org.knowm.xchange.bitfinex.v1.service.BitfinexAccountServiceRaw;
import org.knowm.xchange.examples.bitfinex.BitfinexDemoUtils;

public class BitfinexAccountDemo {

  public static void main(String[] args) throws IOException {

    Exchange bfx = BitfinexDemoUtils.createExchange();
    BitfinexAccountServiceRaw accountService = (BitfinexAccountServiceRaw) bfx.getAccountService();
    BitfinexMarginInfosResponse[] marginInfos = accountService.getBitfinexMarginInfos();
    System.out.println("Margin infos response: " + marginInfos[0]);

  }

}
