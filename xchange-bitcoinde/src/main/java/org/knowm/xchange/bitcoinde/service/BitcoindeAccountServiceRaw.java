package org.knowm.xchange.bitcoinde.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitcoinde.dto.BitcoindeException;
import org.knowm.xchange.bitcoinde.dto.account.BitcoindeAccountWrapper;

import si.mazi.rescu.SynchronizedValueFactory;

/**
 * 
 * @author kaiserfr
 *
 */
public class BitcoindeAccountServiceRaw extends BitcoindeBaseService {
	private final SynchronizedValueFactory<Long> nonceFactory;

	protected BitcoindeAccountServiceRaw(Exchange exchange) {
		super(exchange);
		this.nonceFactory = exchange.getNonceFactory();
	}

	public BitcoindeAccountWrapper getBitcoindeAccount() throws IOException {
		try {
			return bitcoinde.getAccount(apiKey, nonceFactory, signatureCreator);
		} catch (BitcoindeException e) {
			throw handleError(e);
		}
	}
}
