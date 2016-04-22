package org.knowm.xchange.mexbt.service.streaming;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.java_websocket.WebSocket.READYSTATE;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.mexbt.MeXBTExchange;
import org.knowm.xchange.service.streaming.ExchangeEvent;
import org.knowm.xchange.service.streaming.StreamingExchangeService;

public class MeXBTStreamingService implements StreamingExchangeService {

  private final BlockingQueue<ExchangeEvent> exchangeEvents = new LinkedBlockingQueue<ExchangeEvent>();
  private final Exchange exchange;
  private final MeXBTTickerClientEndpoint tickerClientEndpoint;
  private final Map<String, MeXBTTradesAndOrdersClientEndpoint> tradesAndOrdersClientEndpoints;
  private Session tickerSession;
  private final Map<String, Session> tradesAndOrdersSessions;

  public MeXBTStreamingService(Exchange exchange) {
    this(exchange, new MeXBTExchangeStreamingConfiguration(true, new String[] {}));
  }

  public MeXBTStreamingService(Exchange exchange, MeXBTExchangeStreamingConfiguration configuration) {
    this.exchange = exchange;
    this.tradesAndOrdersSessions = new HashMap<String, Session>(configuration.getInses().length);
    if (configuration.isSubscribeTicker()) {
      this.tickerClientEndpoint = new MeXBTTickerClientEndpoint(exchangeEvents);
    } else {
      this.tickerClientEndpoint = null;
    }
    this.tradesAndOrdersClientEndpoints = new HashMap<String, MeXBTTradesAndOrdersClientEndpoint>(configuration.getInses().length);
    for (String ins : configuration.getInses()) {
      this.tradesAndOrdersClientEndpoints.put(ins, new MeXBTTradesAndOrdersClientEndpoint(exchangeEvents, ins));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void connect() {
    WebSocketContainer container = ContainerProvider.getWebSocketContainer();
    final URI wssTickerUri, wssTradesAndOrdersUri;
    try {
      wssTickerUri = new URI((String) exchange.getExchangeSpecification().getExchangeSpecificParametersItem(MeXBTExchange.WSS_TICKER_URI_KEY));
      wssTradesAndOrdersUri = new URI(
          (String) exchange.getExchangeSpecification().getExchangeSpecificParametersItem(MeXBTExchange.WSS_TRADES_AND_ORDERS_URI_KEY));
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }

    try {
      if (tickerClientEndpoint != null) {
        tickerSession = container.connectToServer(tickerClientEndpoint, wssTickerUri);
      }
      for (Map.Entry<String, MeXBTTradesAndOrdersClientEndpoint> entry : this.tradesAndOrdersClientEndpoints.entrySet()) {
        Session session = container.connectToServer(entry.getValue(), wssTradesAndOrdersUri);
        this.tradesAndOrdersSessions.put(entry.getKey(), session);
      }
    } catch (DeploymentException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void disconnect() {
    try {
      tickerSession.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ExchangeEvent getNextEvent() throws InterruptedException {
    return exchangeEvents.take();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int countEventsAvailable() {
    return exchangeEvents.size();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void send(String msg) {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public READYSTATE getWebSocketStatus() {
    if (tickerClientEndpoint != null && tickerSession == null) {
      return READYSTATE.NOT_YET_CONNECTED;
    }

    final List<Session> sessions = new ArrayList<Session>(this.tradesAndOrdersClientEndpoints.size() + 1);
    if (tickerClientEndpoint != null) {
      sessions.add(tickerSession);
    }

    // any session is null, consider as not yet connected.
    for (Map.Entry<String, MeXBTTradesAndOrdersClientEndpoint> entry : tradesAndOrdersClientEndpoints.entrySet()) {
      Session session = this.tradesAndOrdersSessions.get(entry.getKey());
      if (session == null) {
        return READYSTATE.NOT_YET_CONNECTED;
      } else {
        sessions.add(session);
      }
    }

    // any session is not open, consider as closed.
    for (Session session : sessions) {
      if (!session.isOpen()) {
        return READYSTATE.CLOSED;
      }
    }

    return READYSTATE.OPEN;
  }

}
