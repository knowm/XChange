package com.xeiam.xchange.therock.service.polling;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.therock.TheRockAuthenticated;
import com.xeiam.xchange.therock.dto.TheRockException;
import com.xeiam.xchange.therock.dto.account.TheRockBalance;
import com.xeiam.xchange.therock.dto.account.TheRockWithdrawal;
import com.xeiam.xchange.therock.dto.account.TheRockWithdrawalResponse;
import com.xeiam.xchange.therock.service.TheRockDigest;

public class TheRockAccountServiceRaw extends TheRockBasePollingService {

  private final TheRockDigest signatureCreator;

  private final TheRockAuthenticated theRockAuthenticated;

  private final String apiKey;

  protected TheRockAccountServiceRaw(Exchange exchange) {
    super(exchange);
    final ExchangeSpecification spec = exchange.getExchangeSpecification();
    this.theRockAuthenticated = RestProxyFactory.createProxy(TheRockAuthenticated.class, spec.getSslUri());
    apiKey = spec.getApiKey();
    this.signatureCreator = new TheRockDigest(spec.getSecretKey());
  }

  /** Withdraw using the default method */
  public TheRockWithdrawalResponse withdrawDefault(Currency currency, BigDecimal amount, String destinationAddress)
      throws TheRockException, IOException {
    final TheRockWithdrawal withdrawal = TheRockWithdrawal.createDefaultWithdrawal(currency.getCurrencyCode(), amount, destinationAddress);
    return theRockAuthenticated.withdraw(apiKey, signatureCreator, exchange.getNonceFactory(), withdrawal);
  }

  /** Withdraw to Ripple */
  public TheRockWithdrawalResponse withdrawRipple(Currency currency, BigDecimal amount, String destinationAddress)
      throws TheRockException, IOException {
    final TheRockWithdrawal withdrawal = TheRockWithdrawal.createRippleWithdrawal(currency.getCurrencyCode(), amount, destinationAddress);
    return theRockAuthenticated.withdraw(apiKey, signatureCreator, exchange.getNonceFactory(), withdrawal);
  }

  public List<TheRockBalance> balances() throws TheRockException, IOException {
    return theRockAuthenticated.balances(apiKey, signatureCreator, exchange.getNonceFactory()).getBalances();
  }
}
