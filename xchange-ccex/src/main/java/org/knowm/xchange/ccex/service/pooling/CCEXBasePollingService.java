package org.knowm.xchange.ccex.service.pooling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ccex.CCEXAuthenticated;
import org.knowm.xchange.ccex.service.CCEXDigest;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

/**
 * @author Andraž Prinčič
 */
public class CCEXBasePollingService extends BaseExchangeService implements BasePollingService {

	protected final String apiKey;
	protected final CCEXAuthenticated cCEXAuthenticated;
	protected final ParamsDigest signatureCreator;

	/**
	 * Constructor
	 *
	 * @param exchange
	 */
	public CCEXBasePollingService(Exchange exchange) {

		super(exchange);

		this.cCEXAuthenticated = RestProxyFactory.createProxy(CCEXAuthenticated.class,
				exchange.getExchangeSpecification().getSslUri());
		this.apiKey = exchange.getExchangeSpecification().getApiKey();
		this.signatureCreator = CCEXDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
	}
}
