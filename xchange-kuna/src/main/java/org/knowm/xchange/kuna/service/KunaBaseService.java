package org.knowm.xchange.kuna.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.kuna.Kuna;
import org.knowm.xchange.kuna.KunaAuthenticated;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

/** @author Dat Bui */
public class KunaBaseService extends BaseExchangeService implements BaseService {

  private Kuna kuna;
  private KunaAuthenticated kunaAuthenticated;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor.
   *
   * @param exchange
   */
  protected KunaBaseService(Exchange exchange) {
    super(exchange);
    kuna =
        RestProxyFactory.createProxy(
            Kuna.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
    kunaAuthenticated =
        RestProxyFactory.createProxy(
            KunaAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());
    signatureCreator =
        KunaDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  protected Kuna getKuna() {
    return kuna;
  }

  protected KunaAuthenticated getKunaAuthenticated() {
    return kunaAuthenticated;
  }
}
