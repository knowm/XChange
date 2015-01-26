package com.xeiam.xchange.huobi.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitvc.dto.account.HuobiAccountInfo;
import com.xeiam.xchange.exceptions.ExchangeException;

public class HuobiAccountServiceRaw extends HuobiBaseTradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public HuobiAccountServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public HuobiAccountInfo getHuobiAccountInfo() throws IOException {
    HuobiAccountInfo accountInfo = huobi.getAccountInfo(accessKey, nextCreated(), "get_account_info", digest);

    if (accountInfo.getResult().equals("fail")) {
      throw new ExchangeException(accountInfo.getMsg());
    } else {
      return accountInfo;
    }
  }
}
