package org.knowm.xchange.kuna.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.kuna.Kuna;
import org.knowm.xchange.kuna.KunaAuthenticated;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

/** @author Dat Bui */
public class KunaBaseService extends BaseExchangeService implements BaseService {

  private final Kuna kuna;
  private final KunaAuthenticated kunaAuthenticated;

  /**
   * Constructor.
   *
   * @param exchange
   */
  protected KunaBaseService(Exchange exchange) {
    super(exchange);
    kuna =
        ExchangeRestProxyBuilder.forInterface(Kuna.class, exchange.getExchangeSpecification())
            .build();
    kunaAuthenticated =
        ExchangeRestProxyBuilder.forInterface(
                KunaAuthenticated.class, exchange.getExchangeSpecification())
            .build();
  }

  protected Kuna getKuna() {
    return kuna;
  }

  protected KunaAuthenticated getKunaAuthenticated() {
    return kunaAuthenticated;
  }
}
