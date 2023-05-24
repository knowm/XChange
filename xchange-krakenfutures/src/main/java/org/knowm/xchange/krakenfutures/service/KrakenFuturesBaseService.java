package org.knowm.xchange.krakenfutures.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.krakenfutures.KrakenFuturesAuthenticated;
import org.knowm.xchange.krakenfutures.dto.trade.KrakenFuturesOpenPositions;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;

import java.io.IOException;

/** @author Jean-Christophe Laruelle */
public class KrakenFuturesBaseService extends BaseExchangeService implements BaseService {

  protected KrakenFuturesAuthenticated krakenFuturesAuthenticated;
  protected ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange of KrakenFutures
   */
  public KrakenFuturesBaseService(Exchange exchange) {

    super(exchange);

    krakenFuturesAuthenticated =
        ExchangeRestProxyBuilder.forInterface(
                KrakenFuturesAuthenticated.class, exchange.getExchangeSpecification())
            .build();
    signatureCreator =
        KrakenFuturesDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  public KrakenFuturesOpenPositions getKrakenFuturesOpenPositions() throws IOException {
    KrakenFuturesOpenPositions openPositions =
            krakenFuturesAuthenticated.openPositions(
                    exchange.getExchangeSpecification().getApiKey(),
                    signatureCreator,
                    exchange.getNonceFactory());

    if (openPositions.isSuccess()) {
      return openPositions;
    } else {
      throw new ExchangeException("Error getting CF open positions: " + openPositions.getError());
    }
  }
}
