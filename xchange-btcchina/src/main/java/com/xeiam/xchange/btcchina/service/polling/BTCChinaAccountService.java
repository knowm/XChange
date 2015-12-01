package com.xeiam.xchange.btcchina.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.btcchina.BTCChinaAdapters;
import com.xeiam.xchange.btcchina.dto.BTCChinaID;
import com.xeiam.xchange.btcchina.dto.BTCChinaResponse;
import com.xeiam.xchange.btcchina.dto.account.BTCChinaAccountInfo;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

/**
 * Implementation of the account data service for BTCChina.
 * <ul>
 * <li>Provides access to account data</li>
 * </ul>
 *
 * @author ObsessiveOrange
 */
public class BTCChinaAccountService extends BTCChinaAccountServiceRaw implements PollingAccountService {

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
