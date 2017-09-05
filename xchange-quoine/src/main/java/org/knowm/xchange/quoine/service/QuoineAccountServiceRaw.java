package org.knowm.xchange.quoine.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.quoine.dto.account.BitcoinAccount;
import org.knowm.xchange.quoine.dto.account.FiatAccount;
import org.knowm.xchange.quoine.dto.account.QuoineAccountBalance;
import org.knowm.xchange.quoine.dto.account.QuoineTradingAccountInfo;
import org.knowm.xchange.utils.Assert;

import si.mazi.rescu.HttpStatusIOException;

public class QuoineAccountServiceRaw extends QuoineBaseService {

  /**
   * Constructor
   */
  protected QuoineAccountServiceRaw(Exchange exchange) {

    super(exchange);

    Assert.notNull(exchange.getExchangeSpecification().getSslUri(), "Exchange specification URI cannot be null");
  }

  public FiatAccount[] getQuoineFiatAccountInfo() throws IOException {
    try {
      return quoine.getFiatAccountInfo(QUOINE_API_VERSION, signatureCreator, contentType);
    } catch (HttpStatusIOException e) {
      throw new ExchangeException(e.getHttpBody(), e);
    }
  }

  public BitcoinAccount[] getQuoineCryptoAccountInfo() throws IOException {
    try {
      return quoine.getCryptoAccountInfo(QUOINE_API_VERSION, signatureCreator, contentType);
    } catch (HttpStatusIOException e) {
      throw new ExchangeException(e.getHttpBody(), e);
    }
  }

  public QuoineTradingAccountInfo[] getQuoineTradingAccountInfo() throws IOException {

    try {
      return quoine.getTradingAccountInfo(QUOINE_API_VERSION, signatureCreator, contentType);
    } catch (HttpStatusIOException e) {
      throw new ExchangeException(e.getHttpBody(), e);
    }
  }

  public QuoineAccountBalance[] getQuoineAccountBalance() throws IOException {
    try {
      return quoine.getAllBalance(QUOINE_API_VERSION, signatureCreator, contentType);
    } catch (HttpStatusIOException e) {
      throw new ExchangeException(e.getHttpBody(), e);
    }
  }

}
