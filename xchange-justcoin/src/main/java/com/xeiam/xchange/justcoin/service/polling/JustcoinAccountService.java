package com.xeiam.xchange.justcoin.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.justcoin.JustcoinAdapters;
import com.xeiam.xchange.service.polling.PollingAccountService;

/**
 * @author jamespedwards42
 */
public class JustcoinAccountService extends JustcoinAccountServiceRaw implements PollingAccountService {

  public JustcoinAccountService(final ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return JustcoinAdapters.adaptAccountInfo(exchangeSpecification.getUserName(), super.getBalances());
  }

  @Override
  public String withdrawFunds(final String currency, final BigDecimal amount, final String address) throws ExchangeException, IOException {

    return super.withdrawFunds(currency, amount, address);
  }

  @Override
  public String requestDepositAddress(final String currency, final String... arguments) throws IOException {

    return super.requestDepositAddress(currency);
  }
}
