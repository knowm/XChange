package com.xeiam.xchange.hitbtc.service.polling;

import java.util.concurrent.atomic.AtomicInteger;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.hitbtc.HitbtcAuthenticated;
import com.xeiam.xchange.hitbtc.service.HitbtcBaseService;
import com.xeiam.xchange.hitbtc.service.HitbtcHmacDigest;

/**
 * @author kpysniak
 */
public abstract class HitbtcBasePollingService extends HitbtcBaseService {
	private static final long START_MILLIS = 1356998400000L; // Jan 1st, 2013 in milliseconds from epoch
	private static final AtomicInteger lastNonce = new AtomicInteger((int) ((System.currentTimeMillis() - START_MILLIS) / 250L));

	protected final HitbtcAuthenticated hitbtc;
	protected final String apiKey;
	protected final ParamsDigest signatureCreator;

	/**
	 * Constructor Initialize common properties from the exchange specification
	 *
	 * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
	 */
	protected HitbtcBasePollingService(ExchangeSpecification exchangeSpecification) {
		super(exchangeSpecification);

		this.hitbtc = RestProxyFactory.createProxy(HitbtcAuthenticated.class, exchangeSpecification.getSslUri());
		this.apiKey = exchangeSpecification.getApiKey();
		this.signatureCreator = HitbtcHmacDigest.createInstance(exchangeSpecification.getSecretKey());

	}

	protected  long nextNonce() {
		return lastNonce.incrementAndGet();
	}
}
