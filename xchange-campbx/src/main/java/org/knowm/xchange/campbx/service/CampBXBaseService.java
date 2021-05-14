package org.knowm.xchange.campbx.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.campbx.CampBX;
import org.knowm.xchange.client.ClientConfigCustomizer;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import org.knowm.xchange.utils.CertHelper;

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

    // campbx server raises "internal error" if connected via these protocol versions
    ClientConfigCustomizer clientConfigCustomizer =
        config ->
            config.setSslSocketFactory(
                CertHelper.createRestrictedSSLSocketFactory("TLSv1", "TLSv1.1"));

    this.campBX =
        ExchangeRestProxyBuilder.forInterface(CampBX.class, exchange.getExchangeSpecification())
            .clientConfigCustomizer(clientConfigCustomizer)
            .build();
  }
}
