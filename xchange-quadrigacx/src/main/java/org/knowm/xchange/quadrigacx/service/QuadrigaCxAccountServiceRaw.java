package org.knowm.xchange.quadrigacx.service;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.quadrigacx.QuadrigaCxAuthenticated;
import org.knowm.xchange.quadrigacx.dto.account.QuadrigaCxBalance;
import org.knowm.xchange.quadrigacx.dto.account.QuadrigaCxDepositAddress;
import si.mazi.rescu.RestProxyFactory;

public class QuadrigaCxAccountServiceRaw extends QuadrigaCxBaseService {

  private final QuadrigaCxDigest signatureCreator;
  private final QuadrigaCxAuthenticated quadrigacxAuthenticated;

  protected QuadrigaCxAccountServiceRaw(Exchange exchange) {
    super(exchange);

    this.quadrigacxAuthenticated =
        RestProxyFactory.createProxy(
            QuadrigaCxAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());
    this.signatureCreator =
        QuadrigaCxDigest.createInstance(
            exchange.getExchangeSpecification().getSecretKey(),
            exchange.getExchangeSpecification().getUserName(),
            exchange.getExchangeSpecification().getApiKey());
  }

  public QuadrigaCxBalance getQuadrigaCxBalance() throws IOException {

    QuadrigaCxBalance quadrigacxBalance =
        quadrigacxAuthenticated.getBalance(
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            exchange.getNonceFactory());
    if (quadrigacxBalance.getError() != null) {
      throw new ExchangeException("Error getting balance. " + quadrigacxBalance.getError());
    }
    return quadrigacxBalance;
  }

  public String withdrawBitcoin(BigDecimal amount, final String address) throws IOException {

    final String response =
        quadrigacxAuthenticated.withdrawBitcoin(
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            exchange.getNonceFactory(),
            amount,
            address);
    if (!"ok".equals(response)) {
      throw new ExchangeException("Withdrawing funds from QuadrigaCx failed: " + response);
    }
    return response;
  }

  public QuadrigaCxDepositAddress getQuadrigaCxBitcoinDepositAddress() throws IOException {

    final QuadrigaCxDepositAddress response =
        quadrigacxAuthenticated.getBitcoinDepositAddress(
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            exchange.getNonceFactory());
    if (response.getError() != null) {
      throw new ExchangeException(
          "Requesting Bitcoin deposit address failed: " + response.getError());
    }
    return response;
  }

  public String withdrawEther(BigDecimal amount, final String address) throws IOException {

    final String response =
        quadrigacxAuthenticated.withdrawEther(
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            exchange.getNonceFactory(),
            amount,
            address);
    if (!"ok".equals(response)) {
      throw new ExchangeException("Withdrawing funds from QuadrigaCx failed: " + response);
    }
    return response;
  }

  public QuadrigaCxDepositAddress getQuadrigaCxEtherDepositAddress() throws IOException {

    final QuadrigaCxDepositAddress response =
        quadrigacxAuthenticated.getEtherDepositAddress(
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            exchange.getNonceFactory());
    if (response.getError() != null) {
      throw new ExchangeException(
          "Requesting Ether deposit address failed: " + response.getError());
    }
    return response;
  }
}
