package com.xeiam.xchange.okcoin.service.streaming;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.java_websocket.WebSocket.READYSTATE;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.streaming.ExchangeEvent;
import com.xeiam.xchange.service.streaming.ExchangeStreamingConfiguration;
import com.xeiam.xchange.service.streaming.StreamingExchangeService;


public class OkCoinStreamingExchangeService implements StreamingExchangeService {
  private final WebSocketBase socketBase;
  private final BlockingQueue<ExchangeEvent> eventQueue = new LinkedBlockingQueue<ExchangeEvent>();
  private final OkCoinExchangeStreamingConfiguration exchangeStreamingConfiguration;

  public OkCoinStreamingExchangeService(ExchangeSpecification exchangeSpecification, ExchangeStreamingConfiguration exchangeStreamingConfiguration) {    
    this.exchangeStreamingConfiguration = (OkCoinExchangeStreamingConfiguration) exchangeStreamingConfiguration;
    
    String sslUri = (String)exchangeSpecification.getExchangeSpecificParametersItem("Websocket_SslUri");
    
    WebSocketService socketService = new OkCoinWebSocketService(eventQueue, this.exchangeStreamingConfiguration.getMarketDataCurrencyPairs());
    socketBase = new WebSocketBase(sslUri, socketService);
  }
  
  @Override
  public void connect() {
    socketBase.start();

    for(CurrencyPair currencyPair : exchangeStreamingConfiguration.getMarketDataCurrencyPairs()) {
      String pair = currencyPair.baseSymbol.toLowerCase() + currencyPair.counterSymbol.toLowerCase();
      
      socketBase.addChannel("ok_" + pair + "_ticker");
      socketBase.addChannel("ok_" + pair + "_depth");
      socketBase.addChannel("ok_" + pair + "_trades_v1");
    }    
  }

  @Override
  public void disconnect() {
  }

  @Override
  public ExchangeEvent getNextEvent() throws InterruptedException {
    return eventQueue.take();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int countEventsAvailable() {
    return eventQueue.size();
  }

    @Override
  public void send(String msg) {
  }

  @Override
  public READYSTATE getWebSocketStatus() {
    return READYSTATE.OPEN;
  }  
}
