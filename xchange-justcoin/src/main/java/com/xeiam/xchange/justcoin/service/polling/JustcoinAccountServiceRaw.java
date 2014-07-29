package com.xeiam.xchange.justcoin.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.justcoin.JustcoinAuthenticated;
import com.xeiam.xchange.justcoin.dto.account.JustcoinBalance;
import com.xeiam.xchange.utils.AuthUtils;

/**
 * @author jamespedwards42
 */
public class JustcoinAccountServiceRaw extends JustcoinBasePollingService<JustcoinAuthenticated> {

  public JustcoinAccountServiceRaw(final ExchangeSpecification exchangeSpecification) {

    super(JustcoinAuthenticated.class, exchangeSpecification);
  }

  public JustcoinBalance[] getBalances() throws IOException {

    final String username = exchangeSpecification.getUserName();
    final String basicAuth = AuthUtils.getBasicAuth(username, exchangeSpecification.getPassword());
    final JustcoinBalance[] justcoinBalances = justcoin.getBalances(basicAuth, exchangeSpecification.getApiKey());

    return justcoinBalances;
  }

  public String requestDepositAddress(final String currency) throws IOException {

    return justcoin.getDepositAddress(currency, getBasicAuthentication(), exchangeSpecification.getApiKey()).getAddress();
  }

  public String withdrawFunds(final String currency, final BigDecimal amount, final String address) throws IOException {

    return justcoin.withdraw(currency, address, amount, getBasicAuthentication(), exchangeSpecification.getApiKey()).getId();
  }
}
