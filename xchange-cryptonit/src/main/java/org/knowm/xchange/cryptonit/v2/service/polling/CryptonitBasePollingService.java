package org.knowm.xchange.cryptonit.v2.service.polling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptonit.v2.Cryptonit;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;
import org.knowm.xchange.utils.CertHelper;

import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.RestProxyFactory;

public class CryptonitBasePollingService extends BaseExchangeService implements BasePollingService {

  protected final Cryptonit cryptonit;

  /**
   * Constructor
   *
   * @param exchange
   */
  public CryptonitBasePollingService(Exchange exchange) {

    super(exchange);

    ClientConfig config = new ClientConfig();
    // cryptonit server disconnects immediately or raises "protocol version" if connected via these protocol versions
    config.setSslSocketFactory(CertHelper.createRestrictedSSLSocketFactory("SSLv2Hello", "TLSv1", "TLSv1.1"));

    this.cryptonit = RestProxyFactory.createProxy(Cryptonit.class, exchange.getExchangeSpecification().getSslUri(), config);
  }

}
