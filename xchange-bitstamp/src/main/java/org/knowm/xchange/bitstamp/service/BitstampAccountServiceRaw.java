package org.knowm.xchange.bitstamp.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitstamp.BitstampAuthenticated;
import org.knowm.xchange.bitstamp.BitstampAuthenticatedV2;
import org.knowm.xchange.bitstamp.BitstampAuthenticatedV2.AccountCurrency;
import org.knowm.xchange.bitstamp.BitstampAuthenticatedV2.BankCurrency;
import org.knowm.xchange.bitstamp.BitstampAuthenticatedV2.BankWithdrawalType;
import org.knowm.xchange.bitstamp.BitstampExchange;
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
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.FundsExceededException;
import si.mazi.rescu.SynchronizedValueFactory;

/** @author gnandiga */
public class BitstampAccountServiceRaw extends BitstampBaseService {

  private final String version = "v2";

  private final BitstampDigest signatureCreator;
  private final BitstampDigestV2 signatureCreatorV2;
  private final BitstampAuthenticated bitstampAuthenticated;

  private final BitstampAuthenticatedV2 bitstampAuthenticatedV2;
  private final String apiKey;
  private final String apiKeyForV2Requests;

  private final SynchronizedValueFactory<Long> nonceFactory;
  private final SynchronizedValueFactory<String> uuidNonceFactory;
  private final SynchronizedValueFactory<String> timestampFactory;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected BitstampAccountServiceRaw(Exchange exchange) {

    super(exchange);

    this.bitstampAuthenticated =
        ExchangeRestProxyBuilder.forInterface(
                BitstampAuthenticated.class, exchange.getExchangeSpecification())
            .build();
    this.bitstampAuthenticatedV2 =
        ExchangeRestProxyBuilder.forInterface(
                BitstampAuthenticatedV2.class, exchange.getExchangeSpecification())
            .build();

    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.apiKeyForV2Requests = "BITSTAMP " + apiKey;
    this.signatureCreator =
        BitstampDigest.createInstance(
            exchange.getExchangeSpecification().getSecretKey(),
            exchange.getExchangeSpecification().getUserName(),
            exchange.getExchangeSpecification().getApiKey());

    this.signatureCreatorV2 =
            BitstampDigestV2.createInstance(
                    exchange.getExchangeSpecification().getSecretKey(),
                    exchange.getExchangeSpecification().getApiKey());

    BitstampExchange bitstampExchange = (BitstampExchange) exchange;

    this.nonceFactory = exchange.getNonceFactory();
    this.uuidNonceFactory = bitstampExchange.getUuidNonceFactory();
    this.timestampFactory = bitstampExchange.getTimestampFactory();
  }

  public BitstampBalance getBitstampBalance() throws IOException {

    try {
      BitstampBalance bitstampBalance =
          bitstampAuthenticatedV2.getBalance(
              apiKeyForV2Requests,
              signatureCreatorV2,
              uuidNonceFactory,
              timestampFactory,
              version
          );
      if (bitstampBalance.getError() != null) {
        throw new ExchangeException("Error getting balance. " + bitstampBalance.getError());
      }

      return bitstampBalance;

    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampWithdrawal withdrawBitstampFunds(
      Currency currency, BigDecimal amount, final String address) throws IOException {
    BitstampWithdrawal response = null;

    if (currency.equals(Currency.XRP)) {
      BitstampRippleDepositAddress addressAndDt = new BitstampRippleDepositAddress(null, address);
      response =
          withdrawRippleFunds(
              amount, addressAndDt.getAddress(), Long.toString(addressAndDt.getDestinationTag()));
    } else if (currency.equals(Currency.BTC)) {
      response = withdrawBtcFunds(amount, address);
    } else if (currency.equals(Currency.LTC)) {
      response = withdrawLtcFunds(amount, address);
    } else if (currency.equals(Currency.BCH)) {
      response = withdrawBchFunds(amount, address);
    } else if (currency.equals(Currency.ETH)) {
      response = withdrawEthFunds(amount, address);
    } else {
      throw new ExchangeException(
          String.format(
              "Withdrawing funds from Bitstamp failed.Unsupported currency %s", currency));
    }

    if (response.error != null) {
      throw new ExchangeException("Failed to withdraw: " + response.error);
    }

    if (response.getId() == null) {
      return null;
    }

    return response;
  }

  public BitstampWithdrawal withdrawBtcFunds(BigDecimal amount, String address) throws IOException {

    try {
      BitstampWithdrawal response =
          bitstampAuthenticated.withdrawBitcoin(
              apiKey,
              signatureCreator,
              nonceFactory,
              amount,
              address);

      return checkAndReturnWithdrawal(response);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampWithdrawal withdrawLtcFunds(BigDecimal amount, String address) throws IOException {

    try {
      BitstampWithdrawal response =
          bitstampAuthenticatedV2.withdrawLitecoin(
              apiKeyForV2Requests,
              signatureCreatorV2,
              uuidNonceFactory,
              timestampFactory,
              version,
              amount,
              address);

      return checkAndReturnWithdrawal(response);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampWithdrawal withdrawEthFunds(BigDecimal amount, String address) throws IOException {

    try {
      BitstampWithdrawal response =
          bitstampAuthenticatedV2.withdrawEther(
              apiKeyForV2Requests,
              signatureCreatorV2,
              uuidNonceFactory,
              timestampFactory,
              version,
              amount,
              address);

      return checkAndReturnWithdrawal(response);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampWithdrawal withdrawRippleFunds(
      BigDecimal amount, String address, String destinationTag) throws IOException {

    try {
      BitstampWithdrawal response =
          bitstampAuthenticatedV2.xrpWithdrawal(
              apiKeyForV2Requests,
              signatureCreatorV2,
              uuidNonceFactory,
              timestampFactory,
              version,
              amount,
              address,
              destinationTag);

      return checkAndReturnWithdrawal(response);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampWithdrawal withdrawBchFunds(BigDecimal amount, String address) throws IOException {

    try {
      BitstampWithdrawal response =
          bitstampAuthenticatedV2.bchWithdrawal(
              apiKeyForV2Requests,
              signatureCreatorV2,
              uuidNonceFactory,
              timestampFactory,
              version,
              amount,
              address);
      return checkAndReturnWithdrawal(response);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  private BitstampWithdrawal checkAndReturnWithdrawal(BitstampWithdrawal response) {

    try {
      if (response.hasError()) {
        if (response.toString().contains("You have only")) {
          throw new FundsExceededException(response.toString());
        } else {
          throw new ExchangeException(
              "Withdrawing funds from Bitstamp failed: " + response.toString());
        }
      }

      return response;

    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampDepositAddress getBitstampBitcoinDepositAddress() throws IOException {

    try {
      final BitstampDepositAddress response =
          bitstampAuthenticated.getBitcoinDepositAddress(
              apiKey,
              signatureCreator,
              nonceFactory);
      if (response.getError() != null) {
        throw new ExchangeException(
            "Requesting Bitcoin deposit address failed: " + response.getError());
      }
      return response;
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampDepositAddress getBitstampBitcoinCashDepositAddress() throws IOException {

    try {
      final BitstampDepositAddress response =
          bitstampAuthenticated.getBitcoinCashDepositAddress(
              apiKey,
              signatureCreator,
              nonceFactory);
      if (response.getError() != null) {
        throw new ExchangeException(
            "Requesting Bitcoin deposit address failed: " + response.getError());
      }
      return response;
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampDepositAddress getBitstampLitecoinDepositAddress() throws IOException {

    try {
      final BitstampDepositAddress response =
          bitstampAuthenticated.getLitecoinDepositAddress(
              apiKey,
              signatureCreator,
              nonceFactory);
      if (response.getError() != null) {
        throw new ExchangeException(
            "Requesting Bitcoin deposit address failed: " + response.getError());
      }
      return response;
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampDepositAddress getBitstampEthereumDepositAddress() throws IOException {

    try {
      final BitstampDepositAddress response =
          bitstampAuthenticated.getEthereumDepositAddress(
              apiKey,
              signatureCreator,
              nonceFactory);
      if (response.getError() != null) {
        throw new ExchangeException(
            "Requesting Bitcoin deposit address failed: " + response.getError());
      }
      return response;
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampRippleDepositAddress getRippleDepositAddress() throws IOException {

    return bitstampAuthenticated.getRippleDepositAddress(
        apiKey,
        signatureCreator,
        nonceFactory);
  }

  /**
   * @return true if withdrawal was successful. Note that due to a bug on Bitstamp's side,
   *     withdrawal always fails if two-factor authentication is enabled for the account.
   */
  public boolean withdrawToRipple(BigDecimal amount, Currency currency, String rippleAddress)
      throws IOException {

    try {
      return bitstampAuthenticated.withdrawToRipple(
          apiKey,
          signatureCreator,
          nonceFactory,
          amount,
          currency.getCurrencyCode(),
          rippleAddress);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public List<DepositTransaction> getUnconfirmedDeposits() throws IOException {

    try {
      final List<DepositTransaction> response =
          Arrays.asList(
              bitstampAuthenticated.getUnconfirmedDeposits(
                  apiKey,
                  signatureCreator,
                  nonceFactory));
      return response;
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public List<WithdrawalRequest> getWithdrawalRequests(Long timeDelta) throws IOException {

    try {
      final List<WithdrawalRequest> response =
          Arrays.asList(
              bitstampAuthenticatedV2.getWithdrawalRequests(
                  apiKeyForV2Requests,
                  signatureCreatorV2,
                  uuidNonceFactory,
                  timestampFactory,
                  version,
                  timeDelta));
      return response;
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampUserTransaction[] getBitstampUserTransactions(
      Long numberOfTransactions,
      CurrencyPair pair,
      Long offset,
      String sort,
      Long sinceTimestamp,
      String sinceId)
      throws IOException {

    try {
      return bitstampAuthenticatedV2.getUserTransactions(
          apiKeyForV2Requests,
          signatureCreatorV2,
          uuidNonceFactory,
          timestampFactory,
          version,
          new BitstampV2.Pair(pair),
          numberOfTransactions,
          offset,
          sort,
          sinceTimestamp,
          sinceId);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampUserTransaction[] getBitstampUserTransactions(
      Long numberOfTransactions, Long offset, String sort, Long sinceTimestamp, String sinceId)
      throws IOException {

    try {
      return bitstampAuthenticatedV2.getUserTransactions(
          apiKeyForV2Requests,
          signatureCreatorV2,
          uuidNonceFactory,
          timestampFactory,
          version,
          numberOfTransactions,
          offset,
          sort,
          sinceTimestamp,
          sinceId);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampTransferBalanceResponse transferSubAccountBalanceToMain(
      BigDecimal amount, String currency, String subAccount) throws IOException {
    try {
      return bitstampAuthenticatedV2.transferSubAccountBalanceToMain(
          apiKeyForV2Requests,
          signatureCreatorV2,
          uuidNonceFactory,
          timestampFactory,
          version,
          amount,
          currency,
          subAccount);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampWithdrawal withdrawSepa(
      BigDecimal amount,
      String name,
      String IBAN,
      String BIK,
      String address,
      String postalCode,
      String city,
      String countryAlpha2)
      throws IOException {

    return withdrawSepa(amount, name, IBAN, BIK, address, postalCode, city, countryAlpha2, null);
  }

  public BitstampWithdrawal withdrawSepa(
      BigDecimal amount,
      String name,
      String IBAN,
      String BIK,
      String address,
      String postalCode,
      String city,
      String countryAlpha2,
      String comment)
      throws IOException {

    try {
      BitstampWithdrawal response =
          bitstampAuthenticatedV2.bankWithdrawal(
              apiKeyForV2Requests,
              signatureCreatorV2,
              uuidNonceFactory,
              timestampFactory,
              version,
              amount,
              BitstampAuthenticatedV2.AccountCurrency.EUR,
              name,
              IBAN,
              BIK,
              address,
              postalCode,
              city,
              countryAlpha2,
              BankWithdrawalType.sepa,
              null,
              null,
              null,
              null,
              null,
              null,
              comment);

      return checkAndReturnWithdrawal(response);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampWithdrawal withdrawInternational(
      BigDecimal amount,
      String name,
      String IBAN,
      String BIK,
      String address,
      String postalCode,
      String city,
      String countryAlpha2,
      String bankName,
      String bankAddress,
      String bankPostalCode,
      String bankCity,
      String bankCountryAlpha2,
      BankCurrency bankReceiverCurrency)
      throws IOException {

    return withdrawInternational(
        amount,
        name,
        IBAN,
        BIK,
        address,
        postalCode,
        city,
        countryAlpha2,
        bankName,
        bankAddress,
        bankPostalCode,
        bankCity,
        bankCountryAlpha2,
        bankReceiverCurrency,
        null);
  }

  public BitstampWithdrawal withdrawInternational(
      BigDecimal amount,
      String name,
      String IBAN,
      String BIK,
      String address,
      String postalCode,
      String city,
      String countryAlpha2,
      String bankName,
      String bankAddress,
      String bankPostalCode,
      String bankCity,
      String bankCountryAlpha2,
      BankCurrency bankReceiverCurrency,
      String comment)
      throws IOException {

    try {
      BitstampWithdrawal response =
          bitstampAuthenticatedV2.bankWithdrawal(
              apiKeyForV2Requests,
              signatureCreatorV2,
              uuidNonceFactory,
              timestampFactory,
              version,
              amount,
              AccountCurrency.EUR,
              name,
              IBAN,
              BIK,
              address,
              postalCode,
              city,
              countryAlpha2,
              BankWithdrawalType.international,
              bankName,
              bankAddress,
              bankPostalCode,
              bankCity,
              bankCountryAlpha2,
              bankReceiverCurrency,
              comment);

      return checkAndReturnWithdrawal(response);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }
}
