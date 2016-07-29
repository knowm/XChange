package org.knowm.xchange.coinsetter.service.polling;

import java.io.IOException;
import java.util.UUID;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinsetter.CoinsetterException;
import org.knowm.xchange.coinsetter.dto.pricealert.request.CoinsetterPriceAlertRequest;
import org.knowm.xchange.coinsetter.dto.pricealert.response.CoinsetterPriceAlertList;
import org.knowm.xchange.coinsetter.dto.pricealert.response.CoinsetterPriceAlertResponse;
import org.knowm.xchange.coinsetter.dto.pricealert.response.CoinsetterRemovePriceAlertResponse;
import org.knowm.xchange.service.BaseExchangeService;

import si.mazi.rescu.RestProxyFactory;

/**
 * Price alert raw service.
 */
public class CoinsetterPriceAlertServiceRaw extends BaseExchangeService {

  private final org.knowm.xchange.coinsetter.rs.CoinsetterPriceAlert priceAlert;

  /**
   * Constructor
   *
   * @param exchange
   */
  public CoinsetterPriceAlertServiceRaw(Exchange exchange) {

    super(exchange);

    String baseUrl = exchange.getExchangeSpecification().getSslUri();
    priceAlert = RestProxyFactory.createProxy(org.knowm.xchange.coinsetter.rs.CoinsetterPriceAlert.class, baseUrl);
  }

  public CoinsetterPriceAlertResponse add(UUID clientSessionId, CoinsetterPriceAlertRequest request) throws CoinsetterException, IOException {

    return priceAlert.add(clientSessionId, request);
  }

  public CoinsetterPriceAlertList list(UUID clientSessionId) throws CoinsetterException, IOException {

    return priceAlert.list(clientSessionId);
  }

  public CoinsetterRemovePriceAlertResponse remove(UUID clientSessionId, UUID priceAlertId) throws CoinsetterException, IOException {

    return priceAlert.remove(clientSessionId, priceAlertId);
  }

}
