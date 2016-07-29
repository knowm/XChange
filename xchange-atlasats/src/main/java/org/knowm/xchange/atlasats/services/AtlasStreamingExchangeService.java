package org.knowm.xchange.atlasats.services;

import org.java_websocket.WebSocket.READYSTATE;

import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.service.streaming.BaseWebSocketExchangeService;
import org.knowm.xchange.service.streaming.ExchangeEvent;
import org.knowm.xchange.service.streaming.ExchangeStreamingConfiguration;
import org.knowm.xchange.service.streaming.StreamingExchangeService;

public class AtlasStreamingExchangeService extends BaseWebSocketExchangeService implements StreamingExchangeService {

  public AtlasStreamingExchangeService(ExchangeSpecification exchangeSpecification, ExchangeStreamingConfiguration exchangeStreamingConfiguration) {

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

}
