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
   * @param from The ID of the transaction to start displaying with; default 0
   * @param count The number of transactions for displaying default 1000
   * @param fromId The ID of the transaction to start displaying with default 0
   * @param endId The ID of the transaction to finish displaying with default +inf
   * @param descOrder sorting ASC or DESC default DESC
   * @param since When to start displaying? UNIX time default 0
   * @param end When to finish displaying? UNIX time default +inf
   * @return BTCEAccountInfo object: {funds={usd=0, rur=0, eur=0, btc=0.1, ltc=0, nmc=0}, rights={info=1, trade=1, withdraw=1}, transaction_count=1,
   *         open_orders=0, server_time=1357678428}
   */
  public BTCEAccountInfo getBTCEAccountInfo(Long from, Long count, Long fromId, Long endId, Boolean descOrder, Long since, Long end)
      throws IOException {

    BTCEAccountInfoReturn info = btce.getInfo(apiKey, signatureCreator, exchange.getNonceFactory(), from, count, fromId, endId,
        BTCEAuthenticated.SortOrder.DESC, null, null);
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
