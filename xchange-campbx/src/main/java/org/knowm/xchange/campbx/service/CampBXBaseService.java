package org.knowm.xchange.campbx.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.campbx.CampBX;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import org.knowm.xchange.utils.CertHelper;
import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.RestProxyFactory;

/** @author timmolter */
public class CampBXBaseService extends BaseExchangeService implements BaseService {

  protected final CampBX campBX;

  /**
   * Constructor
   *
   * @param exchange
   */
  public CampBXBaseService(Exchange exchange) {

    super(exchange);

    ClientConfig config = getClientConfig();
    // campbx server raises "internal error" if connected via these protocol versions
    config.setSslSocketFactory(CertHelper.createRestrictedSSLSocketFactory("TLSv1", "TLSv1.1"));

    this.campBX =
        RestProxyFactory.createProxy(
            CampBX.class, exchange.getExchangeSpecification().getSslUri(), config);
  }
}
