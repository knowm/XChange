package org.knowm.xchange.gatecoin.service;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.gatecoin.GatecoinAuthenticated;
import org.knowm.xchange.gatecoin.dto.account.Results.GatecoinBalanceResult;
import org.knowm.xchange.gatecoin.dto.account.Results.GatecoinDepositAddressResult;
import org.knowm.xchange.gatecoin.dto.account.Results.GatecoinWithdrawResult;
import si.mazi.rescu.RestProxyFactory;

/** @author sumedha */
public class GatecoinAccountServiceRaw extends GatecoinBaseService {

  private final GatecoinDigest signatureCreator;
  private final GatecoinAuthenticated gatecoinAuthenticated;

  protected GatecoinAccountServiceRaw(Exchange exchange) {

    super(exchange);
    this.gatecoinAuthenticated =
        RestProxyFactory.createProxy(
            GatecoinAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());
    this.signatureCreator =
        GatecoinDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  public GatecoinBalanceResult getGatecoinBalance() throws IOException {

    final ExchangeSpecification spec = exchange.getExchangeSpecification();
    GatecoinBalanceResult gatecoinBalanceResult =
        gatecoinAuthenticated.getUserBalance(
            spec.getApiKey(), spec.getUserName(), signatureCreator, getNow());
    if (gatecoinBalanceResult.getResponseStatus().getMessage().equalsIgnoreCase("ok")) {
      return gatecoinBalanceResult;
    }
    throw new ExchangeException(
        "Error getting balance. " + gatecoinBalanceResult.getResponseStatus().getMessage());
  }

  public GatecoinWithdrawResult withdrawGatecoinFunds(
      String currency, BigDecimal amount, final String address) throws IOException {

    GatecoinWithdrawResult gatecoinWithdrawalResult =
        gatecoinAuthenticated.withdrawCrypto(
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            getNow(),
            currency,
            address,
            amount);
    if (gatecoinWithdrawalResult.getResponseStatus().getMessage().equalsIgnoreCase("ok")) {
      return gatecoinWithdrawalResult;
    }
    throw new ExchangeException(
        "Error withdrawaing funds " + gatecoinWithdrawalResult.getResponseStatus().getMessage());
  }

  public GatecoinDepositAddressResult getGatecoinDepositAddress() throws IOException {

    GatecoinDepositAddressResult gatecoinDepositAddressResult =
        gatecoinAuthenticated.getDepositAddress(
            exchange.getExchangeSpecification().getApiKey(), signatureCreator, getNow());
    if (gatecoinDepositAddressResult.getResponseStatus().getMessage().equalsIgnoreCase("ok")) {
      return gatecoinDepositAddressResult;
    }
    throw new ExchangeException(
        "Requesting Deposit address failed: "
            + gatecoinDepositAddressResult.getResponseStatus().getMessage());
  }
}
