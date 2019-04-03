package org.knowm.xchange.cryptonit2.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptonit2.CryptonitAuthenticated;
import org.knowm.xchange.cryptonit2.CryptonitAuthenticatedV2;
import org.knowm.xchange.cryptonit2.CryptonitV2;
import org.knowm.xchange.cryptonit2.dto.CryptonitException;
import org.knowm.xchange.cryptonit2.dto.account.CryptonitBalance;
import org.knowm.xchange.cryptonit2.dto.account.CryptonitWithdrawal;
import org.knowm.xchange.cryptonit2.dto.account.SepaWithdrawParams;
import org.knowm.xchange.cryptonit2.dto.account.WithdrawalRequest;
import org.knowm.xchange.cryptonit2.dto.trade.CryptonitUserTransaction;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.FundsExceededException;
import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.SynchronizedValueFactory;

/** @author yurivin */
public class CryptonitAccountServiceRaw extends CryptonitBaseService {

  private final CryptonitDigest signatureCreator;
  private final CryptonitAuthenticated cryptonitAuthenticated;

  private final CryptonitAuthenticatedV2 cryptonitAuthenticatedV2;
  private final String apiKey;
  private final SynchronizedValueFactory<Long> nonceFactory;
  private final ObjectMapper mapper = new ObjectMapper();
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

  public CryptonitWithdrawal withdrawCrypto(BigDecimal amount, String address, Currency currency)
      throws IOException {

    try {
      CryptonitWithdrawal response =
          cryptonitAuthenticated.cryptoWithdrawal(
              exchange.getExchangeSpecification().getApiKey(),
              signatureCreator,
              exchange.getNonceFactory(),
              currency.toString(),
              amount,
              address,
              "qazzaq");

      return checkAndReturnWithdrawal(response);
    } catch (CryptonitException e) {
      throw handleError(e);
    }
  }

  public CryptonitWithdrawal withdrawSepa(SepaWithdrawParams params) throws IOException {

    try {
      CryptonitWithdrawal response =
          cryptonitAuthenticated.fiatWithdrawal(
              exchange.getExchangeSpecification().getApiKey(),
              signatureCreator,
              exchange.getNonceFactory(),
              params.currency.toString(),
              params.amount,
              params.methodId,
              "qazzaq",
              params.params.toJson());

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
}
