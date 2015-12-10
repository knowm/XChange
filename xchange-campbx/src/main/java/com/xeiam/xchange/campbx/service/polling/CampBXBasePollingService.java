package com.xeiam.xchange.campbx.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.campbx.CampBX;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;
import com.xeiam.xchange.utils.CertHelper;

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
