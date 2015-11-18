package com.xeiam.xchange.gatecoin.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.gatecoin.GatecoinAuthenticated;
import com.xeiam.xchange.gatecoin.dto.account.Results.GatecoinBalanceResult;
import com.xeiam.xchange.gatecoin.dto.account.Results.GatecoinDepositAddressResult;
import com.xeiam.xchange.gatecoin.dto.account.Results.GatecoinWithdrawResult;
import com.xeiam.xchange.gatecoin.service.GatecoinDigest;
import si.mazi.rescu.RestProxyFactory;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author sumedha
 */
public class GatecoinAccountServiceRaw extends GatecoinBasePollingService {

  private final GatecoinDigest signatureCreator;
  private final GatecoinAuthenticated gatecoinAuthenticated;

  protected GatecoinAccountServiceRaw(Exchange exchange) {

    super(exchange);
    this.gatecoinAuthenticated = RestProxyFactory.createProxy(GatecoinAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    this.signatureCreator = GatecoinDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  public GatecoinBalanceResult getGatecoinBalance() throws IOException {

    final ExchangeSpecification spec = exchange.getExchangeSpecification();
    GatecoinBalanceResult gatecoinBalanceResult = gatecoinAuthenticated.getUserBalance(spec.getApiKey(), spec.getUserName(), signatureCreator, getNow());
    if (gatecoinBalanceResult.getResponseStatus().getMessage().equalsIgnoreCase("ok")) {
      return gatecoinBalanceResult;
    }
    throw new ExchangeException("Error getting balance. " + gatecoinBalanceResult.getResponseStatus().getMessage());
  }

  public GatecoinWithdrawResult withdrawGatecoinFunds(String currency, BigDecimal amount, final String address) throws IOException {

    GatecoinWithdrawResult gatecoinWithdrawalResult = gatecoinAuthenticated.withdrawBitcoin(exchange.getExchangeSpecification().getApiKey(),
        signatureCreator,
        getNow(),
        currency,
        address,
        amount);
    if (gatecoinWithdrawalResult.getResponseStatus().getMessage().equalsIgnoreCase("ok")) {
      return gatecoinWithdrawalResult;
    }
    throw new ExchangeException("Error withdrawaing funds " + gatecoinWithdrawalResult.getResponseStatus().getMessage());
  }

  public GatecoinDepositAddressResult getGatecoinDepositAddress() throws IOException {

    GatecoinDepositAddressResult gatecoinDepositAddressResult = gatecoinAuthenticated.getDepositAddress(exchange.getExchangeSpecification().getApiKey(), signatureCreator, getNow());
    if (gatecoinDepositAddressResult.getResponseStatus().getMessage().equalsIgnoreCase("ok")) {
      return gatecoinDepositAddressResult;
    }
    throw new ExchangeException("Requesting Deposit address failed: " + gatecoinDepositAddressResult.getResponseStatus().getMessage());
  }
}
