package com.xeiam.xchange.btce.v2.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.btce.v2.BTCEAdapters;
import com.xeiam.xchange.btce.v2.BTCEAuthenticated;
import com.xeiam.xchange.btce.v2.dto.account.BTCEAccountInfoReturn;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.service.polling.PollingAccountService;

/**
 * @author Matija Mazi
 */
@Deprecated
public class BTCEAccountService extends BTCEBasePollingService implements PollingAccountService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BTCEAccountService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    BTCEAccountInfoReturn info = btce.getInfo(apiKey, signatureCreator, nextNonce(), null, null, null, null, BTCEAuthenticated.SortOrder.DESC, null, null);
    checkResult(info);
    return BTCEAdapters.adaptAccountInfo(info.getReturnValue());
  }

  @Override
  public String withdrawFunds(String currency, BigDecimal amount, String address) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String requestDepositAddress(String currency, String... arguments) throws IOException {

    throw new NotAvailableFromExchangeException();
  }
}
