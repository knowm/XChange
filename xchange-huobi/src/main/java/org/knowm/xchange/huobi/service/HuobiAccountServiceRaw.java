package org.knowm.xchange.huobi.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.huobi.dto.account.HuobiAccountInfo;

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

    if (accountInfo.getMsg() != null) {
      throw new ExchangeException(accountInfo.getMsg());
    } else {
      return accountInfo;
    }
  }
}
