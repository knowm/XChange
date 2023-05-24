package org.knowm.xchange.krakenfutures.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.krakenfutures.dto.account.KrakenFuturesAccounts;
import org.knowm.xchange.exceptions.ExchangeException;

/** @author Jean-Christophe Laruelle */
public class KrakenFuturesAccountServiceRaw extends KrakenFuturesBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public KrakenFuturesAccountServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public KrakenFuturesAccounts getKrakenFuturesAccounts() throws IOException {

    KrakenFuturesAccounts krakenFuturesAccounts =
        krakenFuturesAuthenticated.accounts(
            exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            exchange.getNonceFactory());

    if (krakenFuturesAccounts.isSuccess()) {
      return krakenFuturesAccounts;
    } else {
      throw new ExchangeException(
          "Error getting CF accounts info: " + krakenFuturesAccounts.getError());
    }
  }
}
