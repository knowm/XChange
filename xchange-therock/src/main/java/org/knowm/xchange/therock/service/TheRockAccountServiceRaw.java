package org.knowm.xchange.therock.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.therock.TheRockAuthenticated;
import org.knowm.xchange.therock.dto.TheRockException;
import org.knowm.xchange.therock.dto.account.TheRockBalance;
import org.knowm.xchange.therock.dto.account.TheRockWithdrawal;
import org.knowm.xchange.therock.dto.account.TheRockWithdrawalResponse;

import si.mazi.rescu.RestProxyFactory;

public class TheRockAccountServiceRaw extends TheRockBaseService {

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

  /**
   * Withdraw using the default method
   */
  public TheRockWithdrawalResponse withdrawDefault(Currency currency, BigDecimal amount,
      String destinationAddress) throws TheRockException, IOException {
    final TheRockWithdrawal withdrawal = TheRockWithdrawal.createDefaultWithdrawal(currency.getCurrencyCode(), amount, destinationAddress);
    return theRockAuthenticated.withdraw(apiKey, signatureCreator, exchange.getNonceFactory(), withdrawal);
  }

  /**
   * Withdraw to Ripple
   */
  public TheRockWithdrawalResponse withdrawRipple(Currency currency, BigDecimal amount,
      String destinationAddress, Long destinationTag) throws TheRockException, IOException {
    final TheRockWithdrawal withdrawal = TheRockWithdrawal.createRippleWithdrawal(currency.getCurrencyCode(), amount
            , destinationAddress, destinationTag);
    return theRockAuthenticated.withdraw(apiKey, signatureCreator, exchange.getNonceFactory(), withdrawal);
  }

  public List<TheRockBalance> balances() throws TheRockException, IOException {
    return theRockAuthenticated.balances(apiKey, signatureCreator, exchange.getNonceFactory()).getBalances();
  }
}
