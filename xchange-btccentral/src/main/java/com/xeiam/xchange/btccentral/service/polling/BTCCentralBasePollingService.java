package com.xeiam.xchange.btccentral.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.btccentral.BTCCentral;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;
import com.xeiam.xchange.utils.CertHelper;

import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.RestProxyFactory;

/**
 * @author kpysniak
 */
public class BTCCentralBasePollingService extends BaseExchangeService implements BasePollingService {

  protected final BTCCentral btcCentral;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected BTCCentralBasePollingService(Exchange exchange) {

    super(exchange);

    ClientConfig config = new ClientConfig();
    config.setSslSocketFactory(CertHelper.createExpiredAcceptingSSLSocketFactory("CN=*.bitcoin-central.net,OU=EssentialSSL Wildcard,OU=Domain Control Validated"));

    this.btcCentral = RestProxyFactory.createProxy(BTCCentral.class, exchange.getExchangeSpecification().getSslUri(), config);
  }
}
