package org.knowm.xchange.bitstamp.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.knowm.xchange.Exchange;
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
import org.knowm.xchange.bitstamp.dto.account.BitstampEarnSettingRequest;
import org.knowm.xchange.bitstamp.dto.account.BitstampEarnSubscribeRequest;
import org.knowm.xchange.bitstamp.dto.account.BitstampEarnSubscription;
import org.knowm.xchange.bitstamp.dto.account.BitstampEarnTerm;
import org.knowm.xchange.bitstamp.dto.account.BitstampEarnTransaction;
import org.knowm.xchange.bitstamp.dto.account.BitstampEarnType;
import org.knowm.xchange.bitstamp.dto.account.BitstampRippleDepositAddress;
import org.knowm.xchange.bitstamp.dto.account.BitstampWithdrawal;
import org.knowm.xchange.bitstamp.dto.account.DepositTransaction;
import org.knowm.xchange.bitstamp.dto.account.WithdrawalFee;
import org.knowm.xchange.bitstamp.dto.account.WithdrawalRequest;
import org.knowm.xchange.bitstamp.dto.trade.BitstampTradingFee;
import org.knowm.xchange.bitstamp.dto.trade.BitstampUserTransaction;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.FundsExceededException;
import si.mazi.rescu.SynchronizedValueFactory;

/**
 * @author gnandiga
 */
public class BitstampAccountServiceRaw extends BitstampBaseService {

  private static final String API_VERSION = "v2";

  private final BitstampDigestV2 signatureCreatorV2;

  private final BitstampAuthenticatedV2 bitstampAuthenticatedV2;
  private final String apiKeyForV2Requests;

  private final SynchronizedValueFactory<String> uuidNonceFactory;
  private final SynchronizedValueFactory<String> timestampFactory;

  protected BitstampAccountServiceRaw(Exchange exchange) {

    super(exchange);

    this.bitstampAuthenticatedV2 =
        ExchangeRestProxyBuilder.forInterface(
                BitstampAuthenticatedV2.class, exchange.getExchangeSpecification())
            .build();

    this.apiKeyForV2Requests = "BITSTAMP " + exchange.getExchangeSpecification().getApiKey();

    this.signatureCreatorV2 =
        BitstampDigestV2.createInstance(
            exchange.getExchangeSpecification().getSecretKey(),
            exchange.getExchangeSpecification().getApiKey());

    BitstampExchange bitstampExchange = (BitstampExchange) exchange;

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
              API_VERSION);
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
    return withdrawBitstampFunds(currency, amount, address, null);
  }

  /**
   * This method can withdraw any currency if withdrawal endpoint is configured in
   * BitstampAuthenticatedV2
   */
  public BitstampWithdrawal withdrawBitstampFunds(
      Currency currency, BigDecimal amount, final String address, final String tag)
      throws IOException {
    BitstampWithdrawal response;

    if (currency.equals(Currency.XRP)) {
      Long dt = null;
      try {
        dt = Long.valueOf(tag);
      } catch (NumberFormatException e) {
        // dt may be part of address,
      }
      response = withdrawRippleFunds(amount, address, dt);

    } else if (currency.equals(Currency.XLM)) {
      response = withdrawXLM(amount, address, tag);
    } else {
      response =
          checkAndReturnWithdrawal(
              bitstampAuthenticatedV2.withdrawCrypto(
                  apiKeyForV2Requests,
                  signatureCreatorV2,
                  uuidNonceFactory,
                  timestampFactory,
                  API_VERSION,
                  currency.getCurrencyCode().toLowerCase(),
                  address,
                  amount,
                  null,
                  null,
                  null,
                  null,
                  null,
                  null,
                  null));
    }

    if (response.error != null) {
      throw new ExchangeException("Failed to withdraw: " + response.error);
    }

    if (response.getId() == null) {
      return null;
    }

    return response;
  }

  public BitstampWithdrawal withdrawRippleFunds(
      BigDecimal amount, String address, Long destinationTag) throws IOException {
    BitstampRippleDepositAddress addressAndDt;

    // even if tag was not explicitly provided in method call, it can still be there as part of
    // address as addr?dt=tag
    addressAndDt = new BitstampRippleDepositAddress(null, address, destinationTag);

    try {
      BitstampWithdrawal response =
          bitstampAuthenticatedV2.withdrawXRP(
              apiKeyForV2Requests,
              signatureCreatorV2,
              uuidNonceFactory,
              timestampFactory,
              API_VERSION,
              amount,
              addressAndDt.getAddress(),
              addressAndDt.getDestinationTag());

      return checkAndReturnWithdrawal(response);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampWithdrawal withdrawXLM(BigDecimal amount, String address, String memo)
      throws IOException {

    try {
      Long longMemo = null;
      if (memo != null) {
        try {
          longMemo = Long.valueOf(memo);
        } catch (NumberFormatException exception) {
          throw new RuntimeException("Bitstamp supports only numbers for xlm memo field");
        }
      }

      BitstampWithdrawal response =
          bitstampAuthenticatedV2.withdrawXLM(
              apiKeyForV2Requests,
              signatureCreatorV2,
              uuidNonceFactory,
              timestampFactory,
              API_VERSION,
              amount,
              address,
              longMemo);

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
          throw new ExchangeException("Withdrawing funds from Bitstamp failed: " + response);
        }
      }

      return response;

    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampDepositAddress getBitstampBitcoinDepositAddress() throws IOException {

    try {
      final BitstampDepositAddress bitcoinDepositAddress =
          bitstampAuthenticatedV2.getBitcoinDepositAddress(
              apiKeyForV2Requests,
              signatureCreatorV2,
              uuidNonceFactory,
              timestampFactory,
              API_VERSION);
      if (bitcoinDepositAddress.getError() != null) {
        throw new ExchangeException(
            "Requesting Bitcoin deposit address failed: " + bitcoinDepositAddress.getError());
      }
      return bitcoinDepositAddress;
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public BitstampDepositAddress getBitstampBitcoinCashDepositAddress() throws IOException {

    try {
      final BitstampDepositAddress response =
          bitstampAuthenticatedV2.getBitcoinCashDepositAddress(
              apiKeyForV2Requests,
              signatureCreatorV2,
              uuidNonceFactory,
              timestampFactory,
              API_VERSION);
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
          bitstampAuthenticatedV2.getLitecoinDepositAddress(
              apiKeyForV2Requests,
              signatureCreatorV2,
              uuidNonceFactory,
              timestampFactory,
              API_VERSION);
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
          bitstampAuthenticatedV2.getEthereumDepositAddress(
              apiKeyForV2Requests,
              signatureCreatorV2,
              uuidNonceFactory,
              timestampFactory,
              API_VERSION);
      if (response.getError() != null) {
        throw new ExchangeException(
            "Requesting Bitcoin deposit address failed: " + response.getError());
      }
      return response;
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  /** Ripple IOU deposit address */
  public BitstampRippleDepositAddress getRippleDepositAddress() throws IOException {

    return bitstampAuthenticatedV2.getRippleIOUDepositAddress(
        apiKeyForV2Requests, signatureCreatorV2, uuidNonceFactory, timestampFactory, API_VERSION);
  }

  /** XRP deposit address */
  public BitstampRippleDepositAddress getXRPDepositAddress() throws IOException {

    return bitstampAuthenticatedV2.getXRPDepositAddress(
        apiKeyForV2Requests, signatureCreatorV2, uuidNonceFactory, timestampFactory, API_VERSION);
  }

  /**
   * @return true if withdrawal was successful. Note that due to a bug on Bitstamp's side,
   *     withdrawal always fails if two-factor authentication is enabled for the account.
   */
  public boolean withdrawToRipple(BigDecimal amount, Currency currency, String rippleAddress)
      throws IOException {

    try {
      return bitstampAuthenticatedV2.withdrawToRipple(
          apiKeyForV2Requests,
          signatureCreatorV2,
          uuidNonceFactory,
          timestampFactory,
          API_VERSION,
          amount,
          currency.getCurrencyCode(),
          rippleAddress);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public List<DepositTransaction> getUnconfirmedDeposits() throws IOException {

    try {
      return Arrays.asList(
          bitstampAuthenticatedV2.getUnconfirmedBTCDeposits(
              apiKeyForV2Requests,
              signatureCreatorV2,
              uuidNonceFactory,
              timestampFactory,
              API_VERSION));
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public List<WithdrawalRequest> getWithdrawalRequests(Long timeDelta) throws IOException {

    try {
      return bitstampAuthenticatedV2.getWithdrawalRequests(
          apiKeyForV2Requests,
          signatureCreatorV2,
          uuidNonceFactory,
          timestampFactory,
          API_VERSION,
          timeDelta);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public List<WithdrawalFee> getWithdrawalFees() throws IOException {

    try {
      return bitstampAuthenticatedV2.getWithdrawalFees(
          apiKeyForV2Requests, signatureCreatorV2, uuidNonceFactory, timestampFactory, API_VERSION);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public List<BitstampTradingFee> getTradingFees() throws IOException {
    try {
      return bitstampAuthenticatedV2.getTradingFees(
          apiKeyForV2Requests, signatureCreatorV2, uuidNonceFactory, timestampFactory, API_VERSION);
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
          API_VERSION,
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
          API_VERSION,
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
          API_VERSION,
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
              API_VERSION,
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
              API_VERSION,
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

  public List<BitstampEarnSubscription> getEarnSubscriptions() throws IOException {
    try {
      return bitstampAuthenticatedV2.getEarnSubscriptions(
          apiKeyForV2Requests, signatureCreatorV2, uuidNonceFactory, timestampFactory, API_VERSION);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  /**
   * Get Earn transaction history.
   *
   * <p><strong>Note:</strong> Query parameters are currently not supported due to Bitstamp API
   * signature validation requirements. All parameters are ignored and the endpoint is called
   * without query parameters.
   *
   * @param limit Maximum number of transactions to return (ignored - not supported)
   * @param offset Number of transactions to skip (ignored - not supported)
   * @param currency Optional currency filter (ignored - not supported)
   * @param quoteCurrency Optional quote currency for value calculation (ignored - not supported)
   * @return List of Earn transactions
   * @throws IOException if the request fails
   */
  public List<BitstampEarnTransaction> getEarnTransactions(
      Integer limit, Integer offset, String currency, String quoteCurrency) throws IOException {
    try {
      // Query parameters cause signature validation failures - always call without them
      return bitstampAuthenticatedV2.getEarnTransactions(
          apiKeyForV2Requests,
          signatureCreatorV2,
          uuidNonceFactory,
          timestampFactory,
          API_VERSION,
          null, // limit ignored
          null, // offset ignored
          null, // currency ignored
          null); // quoteCurrency ignored
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public void subscribeToEarn(
      String currency, BitstampEarnType earnType, BitstampEarnTerm earnTerm, BigDecimal amount)
      throws IOException {
    try {
      BitstampEarnSubscribeRequest request =
          new BitstampEarnSubscribeRequest(currency, earnType, earnTerm, amount);
      bitstampAuthenticatedV2.subscribeToEarn(
          apiKeyForV2Requests,
          signatureCreatorV2,
          uuidNonceFactory,
          timestampFactory,
          API_VERSION,
          request);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public void unsubscribeFromEarn(
      String currency, BitstampEarnType earnType, BitstampEarnTerm earnTerm, BigDecimal amount)
      throws IOException {
    try {
      BitstampEarnSubscribeRequest request =
          new BitstampEarnSubscribeRequest(currency, earnType, earnTerm, amount);
      bitstampAuthenticatedV2.unsubscribeFromEarn(
          apiKeyForV2Requests,
          signatureCreatorV2,
          uuidNonceFactory,
          timestampFactory,
          API_VERSION,
          request);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }

  public void manageEarnSubscriptionSetting(
      BitstampEarnSettingRequest.Setting setting, String currency, BitstampEarnType earnType)
      throws IOException {
    try {
      BitstampEarnSettingRequest request =
          new BitstampEarnSettingRequest(setting, currency, earnType);
      bitstampAuthenticatedV2.manageEarnSubscriptionSetting(
          apiKeyForV2Requests,
          signatureCreatorV2,
          uuidNonceFactory,
          timestampFactory,
          API_VERSION,
          request);
    } catch (BitstampException e) {
      throw handleError(e);
    }
  }
}
