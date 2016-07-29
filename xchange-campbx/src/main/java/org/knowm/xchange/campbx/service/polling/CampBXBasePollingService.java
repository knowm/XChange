package org.knowm.xchange.campbx.service.polling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.campbx.CampBX;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;
import org.knowm.xchange.utils.CertHelper;

import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.RestProxyFactory;

/**
 * @author timmolter
 */
public class CampBXBasePollingService extends BaseExchangeService implements BasePollingService {

  protected final CampBX campBX;

  /**
   * Constructor
   *
   * @param exchange
   */
  public CampBXBasePollingService(Exchange exchange) {

    super(exchange);

    ClientConfig config = new ClientConfig();
    // campbx server raises "internal error" if connected via these protocol versions
    config.setSslSocketFactory(CertHelper.createRestrictedSSLSocketFactory("TLSv1", "TLSv1.1"));

    this.campBX = RestProxyFactory.createProxy(CampBX.class, exchange.getExchangeSpecification().getSslUri(), config);
  }
}
