package org.knowm.xchange.wazirx.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import org.knowm.xchange.wazirx.Wazirx;

import si.mazi.rescu.RestProxyFactory;

public class WazirxBaseService extends BaseExchangeService implements BaseService {

	protected final Wazirx wazirx;
	
	protected WazirxBaseService(Exchange exchange) {
		super(exchange);
		wazirx=RestProxyFactory.createProxy(
				Wazirx.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
		
	}

}
