package org.knowm.xchange.service.streaming;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.java_websocket.WebSocket.READYSTATE;
import org.java_websocket.framing.Framedata.Opcode;
import org.java_websocket.framing.FramedataImpl1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.utils.Assert;

/**
 * <p>
 * Streaming market data service to provide the following to streaming market data API:
 * </p>
 * <ul>
 * <li>Connection to an upstream market data source with a configured provider</li>
 * </ul>
 */
public abstract class BaseWebSocketExchangeService extends BaseExchangeService implements StreamingExchangeService {

  private final Logger log = LoggerFactory.getLogger(BaseWebSocketExchangeService.class);
  private final Timer timer = new Timer();
  private final ExchangeStreamingConfiguration exchangeStreamingConfiguration;

  /**
   * The event queue for the consumer
   */
  protected final BlockingQueue<ExchangeEvent> consumerEventQueue = new LinkedBlockingQueue<ExchangeEvent>();

  protected ReconnectService reconnectService;

  /**
   * The exchange event producer
   */
  private WebSocketEventProducer exchangeEventProducer;

  /**
   * Constructor
   *
   * @param exchange
   * @param exchangeStreamingConfiguration
   */
  public BaseWebSocketExchangeService(Exchange exchange, ExchangeStreamingConfiguration exchangeStreamingConfiguration) {

    super(exchange);
    this.exchangeStreamingConfiguration = exchangeStreamingConfiguration;
    reconnectService = new ReconnectService(this, exchangeStreamingConfiguration);
  }

  protected synchronized void internalConnect(URI uri, ExchangeEventListener exchangeEventListener, Map<String, String> headers) {

    log.debug("internalConnect");

    // Validate inputs
    Assert.notNull(exchangeEventListener, "runnableExchangeEventListener cannot be null");

    try {
      log.debug("Attempting to open a websocket against {}", uri);
      this.exchangeEventProducer = new WebSocketEventProducer(uri.toString(), exchangeEventListener, headers, reconnectService);
      exchangeEventProducer.connect();
    } catch (URISyntaxException e) {
      throw new ExchangeException("Failed to open websocket!", e);
    }

    if (exchangeStreamingConfiguration.keepAlive()) {
      timer.schedule(new KeepAliveTask(), 15000, 15000);
    }
  }

  @Override
  public synchronized void disconnect() {

    if (exchangeEventProducer != null) {
      exchangeEventProducer.close();
    }
    log.debug("disconnect() called");
  }

  @Override
  public ExchangeEvent getNextEvent() throws InterruptedException {

    ExchangeEvent event = consumerEventQueue.take();
    return event;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int countEventsAvailable() {
    return consumerEventQueue.size();
  }

  public synchronized ExchangeEvent checkNextEvent() throws InterruptedException {

    if (consumerEventQueue.isEmpty()) {
      TimeUnit.MILLISECONDS.sleep(100);
    }
    ExchangeEvent event = consumerEventQueue.peek();
    return event;
  }

  @Override
  public void send(String msg) {

    exchangeEventProducer.send(msg);
  }

  @Override
  public READYSTATE getWebSocketStatus() {

    return exchangeEventProducer.getConnection().getReadyState();
  }

  class KeepAliveTask extends TimerTask {

    @Override
    public void run() {

      // log.debug("Keep-Alive ping sent.");
      FramedataImpl1 frame = new FramedataImpl1(Opcode.PING);
      frame.setFin(true);
      exchangeEventProducer.getConnection().sendFrame(frame);
    }
  }
}
