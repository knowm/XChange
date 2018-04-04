package org.knowm.xchange.independentreserve.service;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.independentreserve.IndependentReserveAuthenticated;
import org.knowm.xchange.independentreserve.dto.IndependentReserveHttpStatusException;
import org.knowm.xchange.independentreserve.dto.account.IndependentReserveBalance;
import org.knowm.xchange.independentreserve.dto.account.IndependentReserveWithdrawDigitalCurrencyRequest;
import org.knowm.xchange.independentreserve.dto.auth.AuthAggregate;
import org.knowm.xchange.independentreserve.dto.trade.IndependentReserveSynchDigitalCurrencyDepositAddressWithBlockchainRequest;
import org.knowm.xchange.independentreserve.dto.trade.IndependentReserveSynchDigitalCurrencyDepositAddressWithBlockchainResponse;
import org.knowm.xchange.independentreserve.util.ExchangeEndpoint;
import si.mazi.rescu.RestProxyFactory;

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
        RestProxyFactory.createProxy(
            IndependentReserveAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());
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

  public void withdrawDigitalCurrency(BigDecimal amount, String withdrawalAddress, String comment)
      throws IndependentReserveHttpStatusException, IOException {
    Long nonce = exchange.getNonceFactory().createValue();
    IndependentReserveWithdrawDigitalCurrencyRequest req =
        new IndependentReserveWithdrawDigitalCurrencyRequest(
            exchange.getExchangeSpecification().getApiKey(),
            nonce,
            amount,
            withdrawalAddress,
            comment);
    req.setSignature(
        signatureCreator.digestParamsToString(
            ExchangeEndpoint.WithdrawDigitalCurrency, nonce, req.getParameters()));
    Object withdrawDigitalCurrency = independentReserveAuthenticated.withdrawDigitalCurrency(req);
  }
}
