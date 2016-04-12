package org.knowm.xchange.btccentral.service.polling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.btccentral.BTCCentral;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;
import org.knowm.xchange.utils.CertHelper;

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
    config.setSslSocketFactory(
        CertHelper.createExpiredAcceptingSSLSocketFactory("CN=*.bitcoin-central.net,OU=EssentialSSL Wildcard,OU=Domain Control Validated"));

    this.btcCentral = RestProxyFactory.createProxy(BTCCentral.class, exchange.getExchangeSpecification().getSslUri(), config);
  }
}
