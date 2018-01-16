package org.knowm.xchange.bitstamp.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitstamp.BitstampAuthenticated;
import org.knowm.xchange.bitstamp.BitstampAuthenticatedV2;
import org.knowm.xchange.bitstamp.BitstampV2;
import org.knowm.xchange.bitstamp.dto.BitstampException;
import org.knowm.xchange.bitstamp.dto.BitstampTransferBalanceResponse;
import org.knowm.xchange.bitstamp.dto.account.BitstampBalance;
import org.knowm.xchange.bitstamp.dto.account.BitstampDepositAddress;
import org.knowm.xchange.bitstamp.dto.account.BitstampRippleDepositAddress;
import org.knowm.xchange.bitstamp.dto.account.BitstampWithdrawal;
import org.knowm.xchange.bitstamp.dto.account.DepositTransaction;
import org.knowm.xchange.bitstamp.dto.account.WithdrawalRequest;
import org.knowm.xchange.bitstamp.dto.trade.BitstampUserTransaction;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;

import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.SynchronizedValueFactory;

/**
 * @author gnandiga
 */
public class BitstampAccountServiceRaw extends BitstampBaseService {

  private final BitstampDigest signatureCreator;
  private final BitstampAuthenticated bitstampAuthenticated;

  private final BitstampAuthenticatedV2 bitstampAuthenticatedV2;
  private final String apiKey;
  private final SynchronizedValueFactory<Long> nonceFactory;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected BitstampAccountServiceRaw(Exchange exchange) {

    super(exchange);

    this.bitstampAuthenticated = RestProxyFactory.createProxy(BitstampAuthenticated.class, exchange.getExchangeSpecification().getSslUri(),
        getClientConfig());
    this.bitstampAuthenticatedV2 = RestProxyFactory.createProxy(BitstampAuthenticatedV2.class, exchange.getExchangeSpecification().getSslUri(),
        getClientConfig());

    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator = BitstampDigest.createInstance(exchange.getExchangeSpecification().getSecretKey(), exchange.getExchangeSpecification().getUserName(), apiKey);
    this.nonceFactory = exchange.getNonceFactory();
  }

  public BitstampBalance getBitstampBalance() throws IOException {

    try {
      BitstampBalance bitstampBalance = bitstampAuthenticated.getBalance(exchange.getExchangeSpecification().getApiKey(), signatureCreator,
              exchange.getNonceFactory());
      if (bitstampBalance.getError() != null) {
        throw new ExchangeException("Error getting balance. " + bitstampBalance.getError());
      }

      return bitstampBalance;

    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampWithdrawal withdrawBtcFunds(BigDecimal amount, String address) throws IOException {

    try {
      BitstampWithdrawal response = bitstampAuthenticated.withdrawBitcoin(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory(), amount, address);

      return checkAndReturnWithdrawal(response);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampWithdrawal withdrawLtcFunds(BigDecimal amount, String address) throws IOException {

    try {
      BitstampWithdrawal response = bitstampAuthenticatedV2.withdrawLitecoin(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory(), amount, address);

      return checkAndReturnWithdrawal(response);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampWithdrawal withdrawEthFunds(BigDecimal amount, String address) throws IOException {

    try {
      BitstampWithdrawal response = bitstampAuthenticatedV2.withdrawEther(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory(), amount, address);

      return checkAndReturnWithdrawal(response);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampWithdrawal withdrawRippleFunds(BigDecimal amount, String address, String destinationTag) throws IOException {

    try {
      BitstampWithdrawal response = bitstampAuthenticatedV2.xrpWithdrawal(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory(), amount, address, destinationTag);

      return checkAndReturnWithdrawal(response);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampWithdrawal withdrawBchFunds(BigDecimal amount, String address) throws IOException {

    try {
      BitstampWithdrawal response = bitstampAuthenticatedV2.bchWithdrawal(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory(), amount, address);
      return checkAndReturnWithdrawal(response);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  private BitstampWithdrawal checkAndReturnWithdrawal(BitstampWithdrawal response) {

    try {
      if (response.hasError()) {
        throw new ExchangeException("Withdrawing funds from Bitstamp failed: " + response.toString());
      }

      return response;

    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampDepositAddress getBitstampBitcoinDepositAddress() throws IOException {

    try {
      final BitstampDepositAddress response = bitstampAuthenticated.getBitcoinDepositAddress(exchange.getExchangeSpecification().getApiKey(),
              signatureCreator, exchange.getNonceFactory());
      if (response.getError() != null) {
        throw new ExchangeException("Requesting Bitcoin deposit address failed: " + response.getError());
      }
      return response;
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampDepositAddress getBitstampLitecoinDepositAddress() throws IOException {

    try {
      final BitstampDepositAddress response = bitstampAuthenticated.getLitecoinDepositAddress(exchange.getExchangeSpecification().getApiKey(),
              signatureCreator, exchange.getNonceFactory());
      if (response.getError() != null) {
        throw new ExchangeException("Requesting Bitcoin deposit address failed: " + response.getError());
      }
      return response;
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampDepositAddress getBitstampEthereumDepositAddress() throws IOException {

    try {
      final BitstampDepositAddress response = bitstampAuthenticated.getEthereumDepositAddress(exchange.getExchangeSpecification().getApiKey(),
              signatureCreator, exchange.getNonceFactory());
      if (response.getError() != null) {
        throw new ExchangeException("Requesting Bitcoin deposit address failed: " + response.getError());
      }
      return response;
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampRippleDepositAddress getRippleDepositAddress() throws IOException {

    return bitstampAuthenticated.getRippleDepositAddress(exchange.getExchangeSpecification().getApiKey(), signatureCreator,
        exchange.getNonceFactory());
  }

  /**
   * @return true if withdrawal was successful. Note that due to a bug on Bitstamp's side, withdrawal always fails if two-factor authentication is
   * enabled for the account.
   */
  public boolean withdrawToRipple(BigDecimal amount, Currency currency, String rippleAddress) throws IOException {

    try {
    return bitstampAuthenticated.withdrawToRipple(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory(),
        amount, currency.getCurrencyCode(), rippleAddress);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public List<DepositTransaction> getUnconfirmedDeposits() throws IOException {

    try {
      final List<DepositTransaction> response = Arrays.asList(
          bitstampAuthenticated.getUnconfirmedDeposits(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory()));
      return response;
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public List<WithdrawalRequest> getWithdrawalRequests(Long timeDelta) throws IOException {

    try {
      final List<WithdrawalRequest> response = Arrays.asList(
          bitstampAuthenticatedV2.getWithdrawalRequests(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory(), timeDelta));
      return response;
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampUserTransaction[] getBitstampUserTransactions(Long numberOfTransactions, CurrencyPair pair, Long offset,
      String sort) throws IOException {

    try {
      return bitstampAuthenticatedV2.getUserTransactions(apiKey, signatureCreator, nonceFactory, new BitstampV2.Pair(pair), numberOfTransactions,
          offset, sort);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampTransferBalanceResponse transferSubAccountBalanceToMain(BigDecimal amount, String currency, String subAccount) throws IOException {
    try {
      return bitstampAuthenticatedV2.transferSubAccountBalanceToMain(apiKey, signatureCreator, nonceFactory, amount, currency, subAccount);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }
}
