package org.knowm.xchange.livecoin.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.livecoin.LIVECOIN;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;

import si.mazi.rescu.RestProxyFactory;

public class LIVECOINBasePollingService<T extends LIVECOIN> extends BaseExchangeService implements BasePollingService {

	protected final T coinbaseEx;

	public LIVECOINBasePollingService(Class<T> type, Exchange exchange) {
		super(exchange);

		this.coinbaseEx = RestProxyFactory.createProxy(type, exchange.getExchangeSpecification().getSslUri());
	}

}
