package org.knowm.xchange.bitbns.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitbns.Bitbns;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

import si.mazi.rescu.RestProxyFactory;

public class BitbnsBaseService extends BaseExchangeService implements BaseService {

	protected final Bitbns bitBns;
	
	protected BitbnsBaseService(Exchange exchange) {
		super(exchange);
		bitBns=RestProxyFactory.createProxy(
				Bitbns.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
		
	}

}
