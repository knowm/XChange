package com.xeiam.xchange.coinsetter.service.polling;

import java.io.IOException;
import java.util.UUID;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.coinsetter.CoinsetterException;
import com.xeiam.xchange.coinsetter.dto.pricealert.request.CoinsetterPriceAlertRequest;
import com.xeiam.xchange.coinsetter.dto.pricealert.response.CoinsetterPriceAlertList;
import com.xeiam.xchange.coinsetter.dto.pricealert.response.CoinsetterPriceAlertResponse;
import com.xeiam.xchange.coinsetter.dto.pricealert.response.CoinsetterRemovePriceAlertResponse;
import com.xeiam.xchange.service.BaseExchangeService;

import si.mazi.rescu.RestProxyFactory;

/**
 * Price alert raw service.
 */
public class CoinsetterPriceAlertServiceRaw extends BaseExchangeService {

  private final com.xeiam.xchange.coinsetter.rs.CoinsetterPriceAlert priceAlert;

  /**
   * Constructor
   *
   * @param exchange
   */
  public CoinsetterPriceAlertServiceRaw(Exchange exchange) {

    super(exchange);

    String baseUrl = exchange.getExchangeSpecification().getSslUri();
    priceAlert = RestProxyFactory.createProxy(com.xeiam.xchange.coinsetter.rs.CoinsetterPriceAlert.class, baseUrl);
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
