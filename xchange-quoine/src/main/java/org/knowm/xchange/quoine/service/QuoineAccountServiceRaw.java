package org.knowm.xchange.quoine.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.quoine.dto.account.BitcoinAccount;
import org.knowm.xchange.quoine.dto.account.FiatAccount;
import org.knowm.xchange.quoine.dto.account.QuoineAccountBalance;
import org.knowm.xchange.quoine.dto.account.QuoineTradingAccountInfo;
import org.knowm.xchange.quoine.dto.trade.QuoineTransaction;
import org.knowm.xchange.quoine.dto.trade.QuoineTransactionsResponse;
import org.knowm.xchange.utils.Assert;
import si.mazi.rescu.HttpStatusIOException;

public class QuoineAccountServiceRaw extends QuoineBaseService {

  /** Constructor */
  protected QuoineAccountServiceRaw(Exchange exchange) {

    super(exchange);

    Assert.notNull(
        exchange.getExchangeSpecification().getSslUri(),
        "Exchange specification URI cannot be null");
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

  public List<QuoineTransaction> depositHistory(Currency currency, Integer limit, Integer page)
      throws IOException {
    QuoineTransactionsResponse response =
        quoine.transactions(
            QUOINE_API_VERSION,
            signatureCreator,
            contentType,
            currency.getCurrencyCode(),
            "funding",
            limit,
            page);
    return response.models;
  }

  public List<QuoineTransaction> withdrawalHistory(Currency currency, Integer limit, Integer page)
      throws IOException {
    QuoineTransactionsResponse response =
        quoine.transactions(
            QUOINE_API_VERSION,
            signatureCreator,
            contentType,
            currency.getCurrencyCode(),
            "withdrawal",
            limit,
            page);
    return response.models;
  }
}
