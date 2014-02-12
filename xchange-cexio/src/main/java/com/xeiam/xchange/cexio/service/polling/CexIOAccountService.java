package com.xeiam.xchange.cexio.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.cexio.CexIOAdapters;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.service.polling.PollingAccountService;

/**
 * Author: brox
 * Since: 2/6/14
 */

public class CexIOAccountService extends CexIOAccountServiceRaw implements PollingAccountService {

  /**
   * Initialize common properties from the exchange specification
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  public CexIOAccountService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return CexIOAdapters.adaptAccountInfo(getCexIOAccountInfo(), exchangeSpecification.getUserName());
  }

  @Override
  public String withdrawFunds(BigDecimal amount, String address) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String requestBitcoinDepositAddress(String... arguments) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

}
