package org.knowm.xchange.independentreserve.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.independentreserve.IndependentReserveAuthenticated;
import org.knowm.xchange.independentreserve.dto.IndependentReserveHttpStatusException;
import org.knowm.xchange.independentreserve.dto.account.IndependentReserveBalance;
import org.knowm.xchange.independentreserve.dto.account.IndependentReserveBrokerageFeeRequest;
import org.knowm.xchange.independentreserve.dto.account.IndependentReserveBrokerageFeeResponse;
import org.knowm.xchange.independentreserve.dto.account.IndependentReserveDepositAddressRequest;
import org.knowm.xchange.independentreserve.dto.account.IndependentReserveDepositAddressResponse;
import org.knowm.xchange.independentreserve.dto.account.IndependentReserveWithdrawDigitalCurrencyRequest;
import org.knowm.xchange.independentreserve.dto.auth.AuthAggregate;
import org.knowm.xchange.independentreserve.dto.trade.IndependentReserveSynchDigitalCurrencyDepositAddressWithBlockchainRequest;
import org.knowm.xchange.independentreserve.dto.trade.IndependentReserveSynchDigitalCurrencyDepositAddressWithBlockchainResponse;
import org.knowm.xchange.independentreserve.dto.trade.IndependentReserveTransaction;
import org.knowm.xchange.independentreserve.dto.trade.IndependentReserveTransactionsRequest;
import org.knowm.xchange.independentreserve.dto.trade.IndependentReserveTransactionsResponse;
import org.knowm.xchange.independentreserve.util.ExchangeEndpoint;

/** Author: Kamil Zbikowski Date: 4/10/15 */
public class IndependentReserveAccountServiceRaw extends IndependentReserveBaseService {

  private final IndependentReserveDigest signatureCreator;
  private final IndependentReserveAuthenticated independentReserveAuthenticated;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected IndependentReserveAccountServiceRaw(Exchange exchange) {

    super(exchange);

    this.independentReserveAuthenticated =
        ExchangeRestProxyBuilder.forInterface(
                IndependentReserveAuthenticated.class, exchange.getExchangeSpecification())
            .build();
    this.signatureCreator =
        IndependentReserveDigest.createInstance(
            exchange.getExchangeSpecification().getSecretKey(),
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getSslUri());
  }

  public IndependentReserveBalance getIndependentReserveBalance() throws IOException {
    Long nonce = exchange.getNonceFactory().createValue();
    String apiKey = exchange.getExchangeSpecification().getApiKey();
    AuthAggregate authAggregate = new AuthAggregate(apiKey, nonce);

    authAggregate.setSignature(
        signatureCreator.digestParamsToString(
            ExchangeEndpoint.GET_ACCOUNTS, nonce, authAggregate.getParameters()));
    IndependentReserveBalance independentReserveBalance =
        independentReserveAuthenticated.getBalance(authAggregate);
    if (independentReserveBalance == null) {
      throw new ExchangeException("Error getting balance");
    }
    return independentReserveBalance;
  }

  public IndependentReserveSynchDigitalCurrencyDepositAddressWithBlockchainResponse
      synchDigitalCurrencyDepositAddressWithBlockchain(String depositAddress) throws IOException {
    Long nonce = exchange.getNonceFactory().createValue();
    String apiKey = exchange.getExchangeSpecification().getApiKey();
    IndependentReserveSynchDigitalCurrencyDepositAddressWithBlockchainRequest req =
        new IndependentReserveSynchDigitalCurrencyDepositAddressWithBlockchainRequest(
            apiKey, nonce, depositAddress);
    req.setSignature(
        signatureCreator.digestParamsToString(
            ExchangeEndpoint.SYNCH_DIGITAL_CURRENCY_DEPOSIT_ADDRESS_WITH_BLOCKCHAIN,
            nonce,
            req.getParameters()));
    return independentReserveAuthenticated.synchDigitalCurrencyDepositAddressWithBlockchain(req);
  }

  public String getDigitalCurrencyDepositAddress(String currency) throws ExchangeException {
    Long nonce = exchange.getNonceFactory().createValue();
    IndependentReserveDepositAddressRequest request =
        new IndependentReserveDepositAddressRequest(
            exchange.getExchangeSpecification().getApiKey(), nonce, currency);
    request.setSignature(
        signatureCreator.digestParamsToString(
            ExchangeEndpoint.GET_DIGITAL_CURRENCY_DEPOSIT_ADDRESS, nonce, request.getParameters()));
    try {
      IndependentReserveDepositAddressResponse response =
          independentReserveAuthenticated.getDigitalCurrencyDepositAddress(request);
      return response.getDepositAddress();
    } catch (IOException e) {
      throw new ExchangeException(e.getMessage(), e);
    }
  }

  public void withdrawDigitalCurrency(
      BigDecimal amount,
      String withdrawalAddress,
      String comment,
      String primaryCurrencyCode,
      String destinationTag)
      throws IndependentReserveHttpStatusException, IOException {
    Long nonce = exchange.getNonceFactory().createValue();
    IndependentReserveWithdrawDigitalCurrencyRequest req =
        new IndependentReserveWithdrawDigitalCurrencyRequest(
            exchange.getExchangeSpecification().getApiKey(),
            nonce,
            amount,
            withdrawalAddress,
            comment,
            primaryCurrencyCode,
            destinationTag);
    req.setSignature(
        signatureCreator.digestParamsToString(
            ExchangeEndpoint.WITHDRAW_DIGITAL_CURRENCY, nonce, req.getParameters()));
    Object withdrawDigitalCurrency = independentReserveAuthenticated.withdrawDigitalCurrency(req);
  }

  IndependentReserveTransactionsResponse getTransactions(
      String account,
      Date fromTimestampUtc,
      Date toTimestampUt,
      IndependentReserveTransaction.Type[] txTypes,
      int pageIndex,
      int pageSize)
      throws IndependentReserveHttpStatusException, IOException {
    Long nonce = exchange.getNonceFactory().createValue();

    IndependentReserveTransactionsRequest req =
        new IndependentReserveTransactionsRequest(
            exchange.getExchangeSpecification().getApiKey(),
            nonce,
            account,
            fromTimestampUtc,
            toTimestampUt,
            txTypes,
            pageIndex,
            pageSize);
    req.setSignature(
        signatureCreator.digestParamsToString(
            ExchangeEndpoint.GET_TRANSACTIONS, nonce, req.getParameters()));

    return independentReserveAuthenticated.getTransactions(req);
  }

  public IndependentReserveBrokerageFeeResponse getBrokerageFees() throws IOException {
    Long nonce = exchange.getNonceFactory().createValue();
    IndependentReserveBrokerageFeeRequest req =
        new IndependentReserveBrokerageFeeRequest(
            exchange.getExchangeSpecification().getApiKey(), nonce);
    req.setSignature(
        signatureCreator.digestParamsToString(
            ExchangeEndpoint.GET_BROKER_FEES, nonce, req.getParameters()));
    return independentReserveAuthenticated.getBrokerageFees(req);
  }
}
