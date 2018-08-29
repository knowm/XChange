package org.knowm.xchange.cryptonit2.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptonit2.CryptonitAuthenticated;
import org.knowm.xchange.cryptonit2.CryptonitAuthenticatedV2;
import org.knowm.xchange.cryptonit2.CryptonitV2;
import org.knowm.xchange.cryptonit2.dto.CryptonitException;
import org.knowm.xchange.cryptonit2.dto.CryptonitTransferBalanceResponse;
import org.knowm.xchange.cryptonit2.dto.account.CryptonitBalance;
import org.knowm.xchange.cryptonit2.dto.account.CryptonitDepositAddress;
import org.knowm.xchange.cryptonit2.dto.account.CryptonitRippleDepositAddress;
import org.knowm.xchange.cryptonit2.dto.account.CryptonitWithdrawal;
import org.knowm.xchange.cryptonit2.dto.account.DepositTransaction;
import org.knowm.xchange.cryptonit2.dto.account.WithdrawalRequest;
import org.knowm.xchange.cryptonit2.dto.trade.CryptonitUserTransaction;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.FundsExceededException;
import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.SynchronizedValueFactory;

/** @author gnandiga */
public class CryptonitAccountServiceRaw extends CryptonitBaseService {

  private final CryptonitDigest signatureCreator;
  private final CryptonitAuthenticated cryptonitAuthenticated;

  private final CryptonitAuthenticatedV2 cryptonitAuthenticatedV2;
  private final String apiKey;
  private final SynchronizedValueFactory<Long> nonceFactory;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected CryptonitAccountServiceRaw(Exchange exchange) {

    super(exchange);

    this.cryptonitAuthenticated =
        RestProxyFactory.createProxy(
            CryptonitAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());
    this.cryptonitAuthenticatedV2 =
        RestProxyFactory.createProxy(
            CryptonitAuthenticatedV2.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());

    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator =
        CryptonitDigest.createInstance(
            exchange.getExchangeSpecification().getSecretKey(),
            exchange.getExchangeSpecification().getUserName(),
            apiKey);
    this.nonceFactory = exchange.getNonceFactory();
  }

  public CryptonitBalance getCryptonitBalance() throws IOException {

    try {
      CryptonitBalance cryptonitBalance =
          cryptonitAuthenticated.getBalance(
              exchange.getExchangeSpecification().getApiKey(),
              signatureCreator,
              exchange.getNonceFactory());
      if (cryptonitBalance.getError() != null) {
        throw new ExchangeException("Error getting balance. " + cryptonitBalance.getError());
      }

      return cryptonitBalance;

    } catch (CryptonitException e) {
      throw handleError(e);
    }
  }

  public CryptonitWithdrawal withdrawBtcFunds(BigDecimal amount, String address)
      throws IOException {

    try {
      CryptonitWithdrawal response =
          cryptonitAuthenticated.withdrawBitcoin(
              exchange.getExchangeSpecification().getApiKey(),
              signatureCreator,
              exchange.getNonceFactory(),
              amount,
              address);

      return checkAndReturnWithdrawal(response);
    } catch (CryptonitException e) {
      throw handleError(e);
    }
  }

  public CryptonitWithdrawal withdrawLtcFunds(BigDecimal amount, String address)
      throws IOException {

    try {
      CryptonitWithdrawal response =
          cryptonitAuthenticatedV2.withdrawLitecoin(
              exchange.getExchangeSpecification().getApiKey(),
              signatureCreator,
              exchange.getNonceFactory(),
              amount,
              address);

      return checkAndReturnWithdrawal(response);
    } catch (CryptonitException e) {
      throw handleError(e);
    }
  }

  public CryptonitWithdrawal withdrawEthFunds(BigDecimal amount, String address)
      throws IOException {

    try {
      CryptonitWithdrawal response =
          cryptonitAuthenticatedV2.withdrawEther(
              exchange.getExchangeSpecification().getApiKey(),
              signatureCreator,
              exchange.getNonceFactory(),
              amount,
              address);

      return checkAndReturnWithdrawal(response);
    } catch (CryptonitException e) {
      throw handleError(e);
    }
  }

  public CryptonitWithdrawal withdrawRippleFunds(
      BigDecimal amount, String address, String destinationTag) throws IOException {

    try {
      CryptonitWithdrawal response =
          cryptonitAuthenticatedV2.xrpWithdrawal(
              exchange.getExchangeSpecification().getApiKey(),
              signatureCreator,
              exchange.getNonceFactory(),
              amount,
              address,
              destinationTag);

      return checkAndReturnWithdrawal(response);
    } catch (CryptonitException e) {
      throw handleError(e);
    }
  }

  public CryptonitWithdrawal withdrawBchFunds(BigDecimal amount, String address)
      throws IOException {

    try {
      CryptonitWithdrawal response =
          cryptonitAuthenticatedV2.bchWithdrawal(
              exchange.getExchangeSpecification().getApiKey(),
              signatureCreator,
              exchange.getNonceFactory(),
              amount,
              address);
      return checkAndReturnWithdrawal(response);
    } catch (CryptonitException e) {
      throw handleError(e);
    }
  }

  private CryptonitWithdrawal checkAndReturnWithdrawal(CryptonitWithdrawal response) {

    try {
      if (response.hasError()) {
        if (response.toString().contains("You have only"))
          throw new FundsExceededException(response.toString());
        else
          throw new ExchangeException(
              "Withdrawing funds from Cryptonit failed: " + response.toString());
      }

      return response;

    } catch (CryptonitException e) {
      throw handleError(e);
    }
  }

  public CryptonitDepositAddress getCryptonitBitcoinDepositAddress() throws IOException {

    try {
      final CryptonitDepositAddress response =
          cryptonitAuthenticated.getBitcoinDepositAddress(
              exchange.getExchangeSpecification().getApiKey(),
              signatureCreator,
              exchange.getNonceFactory());
      if (response.getError() != null) {
        throw new ExchangeException(
            "Requesting Bitcoin deposit address failed: " + response.getError());
      }
      return response;
    } catch (CryptonitException e) {
      throw handleError(e);
    }
  }

  public CryptonitDepositAddress getCryptonitLitecoinDepositAddress() throws IOException {

    try {
      final CryptonitDepositAddress response =
          cryptonitAuthenticated.getLitecoinDepositAddress(
              exchange.getExchangeSpecification().getApiKey(),
              signatureCreator,
              exchange.getNonceFactory());
      if (response.getError() != null) {
        throw new ExchangeException(
            "Requesting Bitcoin deposit address failed: " + response.getError());
      }
      return response;
    } catch (CryptonitException e) {
      throw handleError(e);
    }
  }

  public CryptonitDepositAddress getCryptonitEthereumDepositAddress() throws IOException {

    try {
      final CryptonitDepositAddress response =
          cryptonitAuthenticated.getEthereumDepositAddress(
              exchange.getExchangeSpecification().getApiKey(),
              signatureCreator,
              exchange.getNonceFactory());
      if (response.getError() != null) {
        throw new ExchangeException(
            "Requesting Bitcoin deposit address failed: " + response.getError());
      }
      return response;
    } catch (CryptonitException e) {
      throw handleError(e);
    }
  }

  public CryptonitRippleDepositAddress getRippleDepositAddress() throws IOException {

    return cryptonitAuthenticated.getRippleDepositAddress(
        exchange.getExchangeSpecification().getApiKey(),
        signatureCreator,
        exchange.getNonceFactory());
  }

  /**
   * @return true if withdrawal was successful. Note that due to a bug on Cryptonit's side,
   *     withdrawal always fails if two-factor authentication is enabled for the account.
   */
  public boolean withdrawToRipple(BigDecimal amount, Currency currency, String rippleAddress)
      throws IOException {

    try {
      return cryptonitAuthenticated.withdrawToRipple(
          exchange.getExchangeSpecification().getApiKey(),
          signatureCreator,
          exchange.getNonceFactory(),
          amount,
          currency.getCurrencyCode(),
          rippleAddress);
    } catch (CryptonitException e) {
      throw handleError(e);
    }
  }

  public List<DepositTransaction> getUnconfirmedDeposits() throws IOException {

    try {
      final List<DepositTransaction> response =
          Arrays.asList(
              cryptonitAuthenticated.getUnconfirmedDeposits(
                  exchange.getExchangeSpecification().getApiKey(),
                  signatureCreator,
                  exchange.getNonceFactory()));
      return response;
    } catch (CryptonitException e) {
      throw handleError(e);
    }
  }

  public List<WithdrawalRequest> getWithdrawalRequests(Long timeDelta) throws IOException {

    try {
      final List<WithdrawalRequest> response =
          Arrays.asList(
              cryptonitAuthenticatedV2.getWithdrawalRequests(
                  exchange.getExchangeSpecification().getApiKey(),
                  signatureCreator,
                  exchange.getNonceFactory(),
                  timeDelta));
      return response;
    } catch (CryptonitException e) {
      throw handleError(e);
    }
  }

  public CryptonitUserTransaction[] getCryptonitUserTransactions(
      Long numberOfTransactions, CurrencyPair pair, Long offset, String sort) throws IOException {

    try {
      return cryptonitAuthenticatedV2.getUserTransactions(
          apiKey,
          signatureCreator,
          nonceFactory,
          new CryptonitV2.Pair(pair),
          numberOfTransactions,
          offset,
          sort);
    } catch (CryptonitException e) {
      throw handleError(e);
    }
  }

  public CryptonitUserTransaction[] getCryptonitUserTransactions(
      Long numberOfTransactions, Long offset, String sort) throws IOException {

    try {
      return cryptonitAuthenticatedV2.getUserTransactions(
          apiKey, signatureCreator, nonceFactory, numberOfTransactions, offset, sort);
    } catch (CryptonitException e) {
      throw handleError(e);
    }
  }

  public CryptonitTransferBalanceResponse transferSubAccountBalanceToMain(
      BigDecimal amount, String currency, String subAccount) throws IOException {
    try {
      return cryptonitAuthenticatedV2.transferSubAccountBalanceToMain(
          apiKey, signatureCreator, nonceFactory, amount, currency, subAccount);
    } catch (CryptonitException e) {
      throw handleError(e);
    }
  }
}
