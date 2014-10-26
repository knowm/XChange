package com.xeiam.xchange.coinsetter.service.polling;

import java.io.IOException;
import java.util.UUID;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.coinsetter.CoinsetterException;
import com.xeiam.xchange.coinsetter.dto.newsalert.response.CoinsetterNewsAlertList;

public class CoinsetterNewsAlertServiceRaw extends CoinsetterBasePollingService {

  private final com.xeiam.xchange.coinsetter.rs.CoinsetterNewsAlert newsAlert;

  /**
   * @param exchangeSpecification
   */
  public CoinsetterNewsAlertServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    String baseUrl = exchangeSpecification.getSslUri();
    newsAlert = RestProxyFactory.createProxy(com.xeiam.xchange.coinsetter.rs.CoinsetterNewsAlert.class, baseUrl);
  }

  public CoinsetterNewsAlertList list(UUID clientSessionId) throws CoinsetterException, IOException {

    return newsAlert.list(clientSessionId);
  }

}
