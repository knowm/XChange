package org.knowm.xchange.btce.v3.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.btce.v3.BTCEAuthenticated;
import org.knowm.xchange.btce.v3.dto.account.BTCEAccountInfo;
import org.knowm.xchange.btce.v3.dto.account.BTCEAccountInfoReturn;
import org.knowm.xchange.btce.v3.dto.account.BTCEWithDrawInfoReturn;

/**
 * Author: brox
 */
public class BTCEAccountServiceRaw extends BTCEBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BTCEAccountServiceRaw(Exchange exchange) {

    super(exchange);
  }

  /**
   * @return BTCEAccountInfo object: {funds={usd=0, rur=0, eur=0, btc=0.1, ltc=0, nmc=0}, rights={info=1, trade=1, withdraw=1}, transaction_count=1,
   * open_orders=0, server_time=1357678428}
   */
  public BTCEAccountInfo getBTCEAccountInfo() throws IOException {

    BTCEAccountInfoReturn info = btce.getInfo(apiKey, signatureCreator, exchange.getNonceFactory());
    checkResult(info);
    return info.getReturnValue();
  }

  /**
   * Author: Ondřej Novtný
   *
   * @param currency Currency to withdraw
   * @param amount Amount of withdrawal
   * @param address Withdrawall address
   * @return Transactoin ID
   */
  public String withdraw(String currency, BigDecimal amount, String address) {
    BTCEWithDrawInfoReturn info = btce.WithdrawCoin(apiKey, signatureCreator, exchange.getNonceFactory(), currency, amount, address);
    checkResult(info);
    return String.valueOf(info.getReturnValue().gettId());
  }

}
