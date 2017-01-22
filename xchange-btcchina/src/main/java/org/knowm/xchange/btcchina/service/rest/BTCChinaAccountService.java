package org.knowm.xchange.btcchina.service.rest;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcchina.BTCChinaAdapters;
import org.knowm.xchange.btcchina.dto.BTCChinaID;
import org.knowm.xchange.btcchina.dto.BTCChinaResponse;
import org.knowm.xchange.btcchina.dto.account.BTCChinaAccountInfo;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.service.account.AccountService;

/**
 * Implementation of the account data service for BTCChina.
 * <ul>
 * <li>Provides access to account data</li>
 * </ul>
 *
 * @author ObsessiveOrange
 */
public class BTCChinaAccountService extends BTCChinaAccountServiceRaw implements AccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BTCChinaAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    BTCChinaResponse<BTCChinaAccountInfo> response = getBTCChinaAccountInfo();
    return BTCChinaAdapters.adaptAccountInfo(response);
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {

    BTCChinaResponse<BTCChinaID> response = withdrawBTCChinaFunds(currency.toString(), amount, address);
    return response.getResult().getId();
  }

  @Override
  public String requestDepositAddress(Currency currency, String... arguments) throws IOException {

    return requestBTCChinaDepositAddress(currency.toString());
  }
}
