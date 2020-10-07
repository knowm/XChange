package org.knowm.xchange.coindcx.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coindcx.Coindcx;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

import si.mazi.rescu.RestProxyFactory;

public class CoindcxBaseService extends BaseExchangeService implements BaseService {

	protected final Coindcx coindcx;
	
	protected CoindcxBaseService(Exchange exchange) {
		super(exchange);
		coindcx=RestProxyFactory.createProxy(
				Coindcx.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
		
	}

}
