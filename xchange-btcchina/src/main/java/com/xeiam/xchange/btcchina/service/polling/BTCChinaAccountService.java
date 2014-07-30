package com.xeiam.xchange.btcchina.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btcchina.BTCChinaAdapters;
import com.xeiam.xchange.btcchina.dto.BTCChinaID;
import com.xeiam.xchange.btcchina.dto.BTCChinaResponse;
import com.xeiam.xchange.btcchina.dto.account.BTCChinaAccountInfo;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.service.polling.PollingAccountService;

/**
 * @author ObsessiveOrange
 *         <p>
 *         Implementation of the account data service for BTCChina
 *         </p>
 *         <ul>
 *         <li>Provides access to account data</li>
 *         </ul>
 */
public class BTCChinaAccountService extends BTCChinaAccountServiceRaw implements PollingAccountService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BTCChinaAccountService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);

  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    BTCChinaResponse<BTCChinaAccountInfo> response = getBTCChinaAccountInfo();
    return BTCChinaAdapters.adaptAccountInfo(response);
  }

  @Override
  public String withdrawFunds(String currency, BigDecimal amount, String address) throws IOException {

    BTCChinaResponse<BTCChinaID> response = withdrawBTCChinaFunds(currency, amount, address);
    return response.getResult().getId();
  }

  @Override
  public String requestDepositAddress(String currency, String... arguments) throws IOException {

    return requestBTCChinaBitcoinDepositAddress();
  }
}
