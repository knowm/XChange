package org.knowm.xchange.enigma.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.enigma.dto.account.EnigmaBalance;
import org.knowm.xchange.enigma.dto.marketdata.EnigmaProduct;
import org.knowm.xchange.enigma.dto.trade.EnigmaRiskLimit;
import si.mazi.rescu.HttpStatusIOException;

public class EnigmaAccountServiceRaw extends EnigmaBaseService {

  public EnigmaAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public EnigmaRiskLimit getRiskLimits(String accessToken) throws IOException {
    try {
      return this.enigmaAuthenticated.getAccountRiskLimits(accessToken);
    } catch (HttpStatusIOException httpStatusIOException) {
      throw handleError(httpStatusIOException);
    }
  }

  public EnigmaBalance getBalance(String accessToken, String infrastructure) throws IOException {
    try {
      return this.getBalance(accessToken, infrastructure);
    } catch (HttpStatusIOException httpStatusIOException) {
      throw handleError(httpStatusIOException);
    }
  }

  public List<EnigmaProduct> getProducts(String accessToken) throws IOException {
    try {
      return this.enigmaAuthenticated.getProducts(accessToken);
    } catch (HttpStatusIOException httpStatusIOException) {
      throw handleError(httpStatusIOException);
    }
  }
}
