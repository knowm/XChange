package com.xeiam.xchange.justcoin.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.justcoin.JustcoinAuthenticated;
import com.xeiam.xchange.justcoin.JustcoinUtils;
import com.xeiam.xchange.justcoin.dto.account.JustcoinBalance;
import com.xeiam.xchange.justcoin.dto.account.WithdrawResponse;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;

/**
 * @author jamespedwards42
 */
public class JustcoinAccountServiceRaw extends BasePollingExchangeService {

  private JustcoinAuthenticated justcoinAuthenticated;

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public JustcoinAccountServiceRaw(final ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.justcoinAuthenticated = RestProxyFactory.createProxy(JustcoinAuthenticated.class, exchangeSpecification.getSslUri());
  }

  public JustcoinBalance[] getBalances() throws IOException {

    final String username = exchangeSpecification.getUserName();
    final String basicAuth = JustcoinUtils.getBasicAuth(username, exchangeSpecification.getPassword());
    final JustcoinBalance[] justcoinBalances = justcoinAuthenticated.getBalances(basicAuth, exchangeSpecification.getApiKey());

    return justcoinBalances;
  }

  public String requestDepositAddress(final String currency) throws IOException {

    return justcoinAuthenticated.getDepositAddress(currency, getBasicAuthentication(), exchangeSpecification.getApiKey()).getAddress();
  }

  public WithdrawResponse withdrawFunds(final String currency, final BigDecimal amount, final String address) throws IOException {

    return justcoinAuthenticated.withdraw(currency, address, amount, getBasicAuthentication(), exchangeSpecification.getApiKey());
  }

  private String getBasicAuthentication() {

    return JustcoinUtils.getBasicAuth(exchangeSpecification.getUserName(), exchangeSpecification.getPassword());
  }
}
