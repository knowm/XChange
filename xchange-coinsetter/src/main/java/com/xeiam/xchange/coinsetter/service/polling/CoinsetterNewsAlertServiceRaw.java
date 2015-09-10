package com.xeiam.xchange.coinsetter.service.polling;

import java.io.IOException;
import java.util.UUID;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.coinsetter.CoinsetterException;
import com.xeiam.xchange.coinsetter.dto.newsalert.response.CoinsetterNewsAlertList;
import com.xeiam.xchange.service.BaseExchangeService;

import si.mazi.rescu.RestProxyFactory;

/**
 * News alert raw service.
 */
public class CoinsetterNewsAlertServiceRaw extends BaseExchangeService {

  private final com.xeiam.xchange.coinsetter.rs.CoinsetterNewsAlert newsAlert;

  /**
   * Constructor
   *
   * @param exchange
   */
  public CoinsetterNewsAlertServiceRaw(Exchange exchange) {

    super(exchange);
    String baseUrl = exchange.getExchangeSpecification().getSslUri();
    newsAlert = RestProxyFactory.createProxy(com.xeiam.xchange.coinsetter.rs.CoinsetterNewsAlert.class, baseUrl);
  }

  public CoinsetterNewsAlertList list(UUID clientSessionId) throws CoinsetterException, IOException {

    return newsAlert.list(clientSessionId);
  }

}
