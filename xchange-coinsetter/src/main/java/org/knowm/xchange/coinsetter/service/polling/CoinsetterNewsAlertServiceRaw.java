package org.knowm.xchange.coinsetter.service.polling;

import java.io.IOException;
import java.util.UUID;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinsetter.CoinsetterException;
import org.knowm.xchange.coinsetter.dto.newsalert.response.CoinsetterNewsAlertList;
import org.knowm.xchange.service.BaseExchangeService;

import si.mazi.rescu.RestProxyFactory;

/**
 * News alert raw service.
 */
public class CoinsetterNewsAlertServiceRaw extends BaseExchangeService {

  private final org.knowm.xchange.coinsetter.rs.CoinsetterNewsAlert newsAlert;

  /**
   * Constructor
   *
   * @param exchange
   */
  public CoinsetterNewsAlertServiceRaw(Exchange exchange) {

    super(exchange);
    String baseUrl = exchange.getExchangeSpecification().getSslUri();
    newsAlert = RestProxyFactory.createProxy(org.knowm.xchange.coinsetter.rs.CoinsetterNewsAlert.class, baseUrl);
  }

  public CoinsetterNewsAlertList list(UUID clientSessionId) throws CoinsetterException, IOException {

    return newsAlert.list(clientSessionId);
  }

}
