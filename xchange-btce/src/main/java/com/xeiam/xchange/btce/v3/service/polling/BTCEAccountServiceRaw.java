package com.xeiam.xchange.btce.v3.service.polling;

import java.io.IOException;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btce.v3.BTCEAuthenticated;
import com.xeiam.xchange.btce.v3.dto.account.BTCEAccountInfo;
import com.xeiam.xchange.btce.v3.dto.account.BTCEAccountInfoReturn;

/**
 * Author: brox
 */
public class BTCEAccountServiceRaw extends BTCEBasePollingService<BTCEAuthenticated> {

  /**
   * Initialize common properties from the exchange specification
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  public BTCEAccountServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(BTCEAuthenticated.class, exchangeSpecification);
  }

  /**
   * @param from The ID of the transaction to start displaying with; default 0
   * @param count The number of transactions for displaying default 1000
   * @param fromId The ID of the transaction to start displaying with default 0
   * @param endId The ID of the transaction to finish displaying with default +inf
   * @param descOrder sorting ASC or DESC default DESC
   * @param since When to start displaying? UNIX time default 0
   * @param end When to finish displaying? UNIX time default +inf
   * @return BTCEAccountInfo object: {funds={usd=0, rur=0, eur=0, btc=0.1, ltc=0, nmc=0}, rights={info=1, trade=1, withdraw=1}, transaction_count=1, open_orders=0, server_time=1357678428}
   */
  public BTCEAccountInfo getBTCEAccountInfo(Long from, Long count, Long fromId, Long endId, Boolean descOrder, Long since, Long end) throws IOException {

    BTCEAccountInfoReturn info = btce.getInfo(apiKey, signatureCreator, nextNonce(), from, count, fromId, endId, BTCEAuthenticated.SortOrder.DESC, null, null);
    checkResult(info);
    return info.getReturnValue();
  }

}
