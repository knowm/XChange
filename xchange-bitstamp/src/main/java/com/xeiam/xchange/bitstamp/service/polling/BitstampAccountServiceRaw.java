package com.xeiam.xchange.bitstamp.service.polling;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitstamp.BitstampAuthenticated;
import com.xeiam.xchange.bitstamp.dto.account.BitstampBalance;
import com.xeiam.xchange.bitstamp.dto.account.BitstampDepositAddress;
import com.xeiam.xchange.bitstamp.dto.account.BitstampRippleDepositAddress;
import com.xeiam.xchange.bitstamp.dto.account.BitstampWithdrawal;
import com.xeiam.xchange.bitstamp.dto.account.DepositTransaction;
import com.xeiam.xchange.bitstamp.dto.account.WithdrawalRequest;
import com.xeiam.xchange.bitstamp.service.BitstampDigest;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.exceptions.ExchangeException;

/**
 * @author gnandiga
 */
public class BitstampAccountServiceRaw extends BitstampBasePollingService {

  private final BitstampDigest signatureCreator;
  private final BitstampAuthenticated bitstampAuthenticated;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected BitstampAccountServiceRaw(Exchange exchange) {

    super(exchange);

    this.bitstampAuthenticated = RestProxyFactory.createProxy(BitstampAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    this.signatureCreator = BitstampDigest.createInstance(exchange.getExchangeSpecification().getSecretKey(),
        exchange.getExchangeSpecification().getUserName(), exchange.getExchangeSpecification().getApiKey());
  }

  public BitstampBalance getBitstampBalance() throws IOException {

    BitstampBalance bitstampBalance = bitstampAuthenticated.getBalance(exchange.getExchangeSpecification().getApiKey(), signatureCreator,
        exchange.getNonceFactory());
    if (bitstampBalance.getError() != null) {
      throw new ExchangeException("Error getting balance. " + bitstampBalance.getError());
    }
    return bitstampBalance;
  }

  public BitstampWithdrawal withdrawBitstampFunds(BigDecimal amount, final String address) throws IOException {

    final BitstampWithdrawal response = bitstampAuthenticated.withdrawBitcoin(exchange.getExchangeSpecification().getApiKey(), signatureCreator,
        exchange.getNonceFactory(), amount, address);
    if (response.getError() != null) {
      throw new ExchangeException("Withdrawing funds from Bitstamp failed: " + response.getError());
    }
    return response;

  }

  public BitstampDepositAddress getBitstampBitcoinDepositAddress() throws IOException {

    final BitstampDepositAddress response = bitstampAuthenticated.getBitcoinDepositAddress(exchange.getExchangeSpecification().getApiKey(),
        signatureCreator, exchange.getNonceFactory());
    if (response.getError() != null) {
      throw new ExchangeException("Requesting Bitcoin deposit address failed: " + response.getError());
    }
    return response;
  }

  public BitstampRippleDepositAddress getRippleDepositAddress() throws IOException {

    return bitstampAuthenticated.getRippleDepositAddress(exchange.getExchangeSpecification().getApiKey(), signatureCreator,
        exchange.getNonceFactory());
  }

  /**
   * @return true if withdrawal was successful. Note that due to a bug on Bitstamp's side, withdrawal always fails if two-factor authentication is
   *         enabled for the account.
   */
  public boolean withdrawToRipple(BigDecimal amount, Currency currency, String rippleAddress) throws IOException {

    return bitstampAuthenticated.withdrawToRipple(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory(),
        amount, currency.getCurrencyCode(), rippleAddress);
  }

  public List<DepositTransaction> getUnconfirmedDeposits() throws IOException {

    final List<DepositTransaction> response = Arrays.asList(
        bitstampAuthenticated.getUnconfirmedDeposits(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory()));
    return response;
  }

  public List<WithdrawalRequest> getWithdrawalRequests() throws IOException {

    final List<WithdrawalRequest> response = Arrays.asList(
        bitstampAuthenticated.getWithdrawalRequests(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory()));
    return response;
  }

}
