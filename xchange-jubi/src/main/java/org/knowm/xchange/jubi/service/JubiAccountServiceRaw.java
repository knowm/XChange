package org.knowm.xchange.jubi.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.jubi.JubiAuthernticated;
import org.knowm.xchange.jubi.dto.account.JubiBalance;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import java.io.IOException;

/**
 * Created by Dzf on 2017/7/8.
 */
public class JubiAccountServiceRaw extends JubiBaseService {

  private final JubiAuthernticated jubiAuthernticated;
  private final ParamsDigest signatureCreator;

  public JubiAccountServiceRaw(Exchange exchange) {
    super(exchange);
    this.jubiAuthernticated = RestProxyFactory.createProxy(JubiAuthernticated.class, exchange.getExchangeSpecification().getSslUri());
    this.signatureCreator = JubiPostBodyDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  public JubiBalance getJubiBalance() throws IOException {
    JubiBalance jubiBalance = jubiAuthernticated.getBalance(exchange.getExchangeSpecification().getApiKey(),
            exchange.getNonceFactory(), signatureCreator);
    return jubiBalance;
  }
}
