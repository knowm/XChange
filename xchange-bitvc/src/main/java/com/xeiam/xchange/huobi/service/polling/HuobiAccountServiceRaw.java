package com.xeiam.xchange.huobi.service.polling;

import java.io.IOException;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitvc.dto.account.HuobiAccountInfo;

public class HuobiAccountServiceRaw extends HuobiBaseTradeService {

  public HuobiAccountServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  public HuobiAccountInfo getHuobiAccountInfo() throws IOException {
    HuobiAccountInfo accountInfo = huobi.getAccountInfo(accessKey, nextCreated(), "get_account_info", digest);

    if (accountInfo.getResult().equals("fail")) {
      throw new ExchangeException(accountInfo.getMsg());
    }
    else {
      return accountInfo;
    }
  }
}
