package org.knowm.xchange.cryptonit.v2.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptonit.v2.Cryptonit;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import org.knowm.xchange.utils.CertHelper;
import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.RestProxyFactory;

public class CryptonitBaseService extends BaseExchangeService implements BaseService {

  protected final Cryptonit cryptonit;

  /**
   * Constructor
   *
   * @param exchange
   */
  public CryptonitBaseService(Exchange exchange) {

    super(exchange);

    ClientConfig config = getClientConfig();
    // cryptonit server disconnects immediately or raises "protocol version" if connected via these
    // protocol versions
    config.setSslSocketFactory(
        CertHelper.createRestrictedSSLSocketFactory("SSLv2Hello", "TLSv1", "TLSv1.1"));

    this.cryptonit =
        RestProxyFactory.createProxy(
            Cryptonit.class, exchange.getExchangeSpecification().getSslUri(), config);
  }
}
