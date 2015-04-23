package com.xeiam.xchange.quoine.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.quoine.QuoineAdapters;
import com.xeiam.xchange.quoine.dto.account.QuoineAccountInfo;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

/**
 * <p>
 * XChange service to provide the following to {@link com.xeiam.xchange.Exchange}:
 * </p>
 * <ul>
 * <li>ANX specific methods to handle account-related operations</li>
 * </ul>
 */
public class QuoineAccountService extends QuoineAccountServiceRaw implements PollingAccountService {

  /**
   * Constructor
   */
  public QuoineAccountService(BaseExchange baseExchange) {

    super(baseExchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    QuoineAccountInfo quoineAccountInfo = getQuoineAccountInfo();

    return QuoineAdapters.adaptAccountinfo(quoineAccountInfo);
  }

  @Override
  public String withdrawFunds(String currency, BigDecimal amount, String address) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String requestDepositAddress(String currency, String... args) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

}
