package com.xeiam.xchange.atlasats.services;

import java.io.IOException;
import java.util.Collection;

import org.java_websocket.WebSocket.READYSTATE;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.streaming.BaseWebSocketExchangeService;
import com.xeiam.xchange.service.streaming.ExchangeEvent;
import com.xeiam.xchange.service.streaming.ExchangeStreamingConfiguration;
import com.xeiam.xchange.service.streaming.StreamingExchangeService;

public class AtlasStreamingExchangeService extends BaseWebSocketExchangeService
		implements StreamingExchangeService {

	public AtlasStreamingExchangeService(
			ExchangeSpecification exchangeSpecification,
			ExchangeStreamingConfiguration exchangeStreamingConfiguration) {
		super(exchangeSpecification, exchangeStreamingConfiguration);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void connect() {
		// TODO Auto-generated method stub

	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub

	}

	@Override
	public ExchangeEvent getNextEvent() throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void send(String msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public READYSTATE getWebSocketStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<CurrencyPair> getExchangeSymbols() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
