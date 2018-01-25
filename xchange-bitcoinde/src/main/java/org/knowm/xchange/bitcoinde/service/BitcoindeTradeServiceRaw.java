package org.knowm.xchange.bitcoinde.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitcoinde.dto.BitcoindeException;
import org.knowm.xchange.bitcoinde.trade.BitcoindeMyOpenOrdersWrapper;

import si.mazi.rescu.SynchronizedValueFactory;

public class BitcoindeTradeServiceRaw extends BitcoindeBaseService {
	private final SynchronizedValueFactory<Long> nonceFactory;

	protected BitcoindeTradeServiceRaw(Exchange exchange) {
		super(exchange);
		this.nonceFactory = exchange.getNonceFactory();
	}

	public BitcoindeMyOpenOrdersWrapper getBitcoindeOpenOrders() throws IOException {
		try {
			return bitcoinde.getOrders(apiKey, nonceFactory, signatureCreator);
		} catch (BitcoindeException e) {
			throw handleError(e);
		}
	}
}
