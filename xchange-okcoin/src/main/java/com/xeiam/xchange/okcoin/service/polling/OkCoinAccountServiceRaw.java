package com.xeiam.xchange.okcoin.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.okcoin.dto.account.OkCoinUserInfo;

public class OkCoinAccountServiceRaw extends OKCoinBaseTradePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  protected OkCoinAccountServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public OkCoinUserInfo getUserInfo() throws IOException {

    OkCoinUserInfo userInfo = okCoin.getUserInfo(apikey, signatureCreator);

    return returnOrThrow(userInfo);
  }

  /** Unfinished **/
  public OkCoinUserInfo getFutureUserInfo() throws IOException {

    OkCoinUserInfo userInfo = okCoin.getFuturesUserInfo(apikey, signatureCreator);

    return returnOrThrow(userInfo);
  }

}
