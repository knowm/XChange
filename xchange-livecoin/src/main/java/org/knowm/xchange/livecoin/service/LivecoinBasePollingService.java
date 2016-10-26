package org.knowm.xchange.livecoin.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.livecoin.Livecoin;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;

import si.mazi.rescu.RestProxyFactory;

public class LivecoinBasePollingService<T extends Livecoin> extends BaseExchangeService implements BasePollingService {

	protected final T coinbaseEx;

	public LivecoinBasePollingService(Class<T> type, Exchange exchange) {
		super(exchange);

		this.coinbaseEx = RestProxyFactory.createProxy(type, exchange.getExchangeSpecification().getSslUri());
	}

}
