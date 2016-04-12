package org.knowm.xchange.bitstamp.service.streaming;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.java_websocket.WebSocket.READYSTATE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pusher.client.Pusher;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitstamp.BitstampAdapters;
import org.knowm.xchange.bitstamp.dto.marketdata.BitstampStreamingOrderBook;
import org.knowm.xchange.bitstamp.dto.marketdata.BitstampStreamingTransaction;
import org.knowm.xchange.bitstamp.dto.marketdata.BitstampTransaction;
import org.knowm.xchange.bitstamp.service.polling.BitstampBasePollingService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.service.streaming.DefaultExchangeEvent;
import org.knowm.xchange.service.streaming.ExchangeEvent;
import org.knowm.xchange.service.streaming.ExchangeEventType;
import org.knowm.xchange.service.streaming.StreamingExchangeService;

/**
 * <p>
 * Streaming trade service for the Bitstamp exchange
 * </p>
 */
public class BitstampPusherService extends BitstampBasePollingService implements StreamingExchangeService {

  private final Logger logger = LoggerFactory.getLogger(BitstampPusherService.class);

  // private final ExchangeEventListener exchangeEventListener;
  private final BlockingQueue<ExchangeEvent> consumerEventQueue = new LinkedBlockingQueue<ExchangeEvent>();
  private final ObjectMapper streamObjectMapper;

  /**
   * Ensures that exchange-specific configuration is available
   */
  private final BitstampStreamingConfiguration configuration;

  private Pusher client;
  private Map<String, Channel> channels;

  // private ReconnectService reconnectService;

  /**
   * Constructor
   *
   * @param exchange
   * @param configuration
   */
  public BitstampPusherService(Exchange exchange, BitstampStreamingConfiguration configuration) {

    super(exchange);

    this.configuration = configuration;
    client = new Pusher(configuration.getPusherKey(), configuration.pusherOptions());
    // reconnectService = new ReconnectService(this, configuration);
    channels = new HashMap<String, Channel>();

    streamObjectMapper = new ObjectMapper();
    streamObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  @Override
  public void connect() {

    // Re-connect is handled by the base ReconnectService when it reads a closed
    // conn. state
    client.connect();
    channels.clear();
    for (String name : configuration.getChannels()) {
      Channel instance = client.subscribe(name);
      if (name.equals("order_book")) {
        bindOrderData(instance);
      } else if (name.equals("diff_order_book")) {
        bindDiffOrderData(instance);
      } else if (name.equals("live_trades")) {
        bindTradeData(instance);
      } else {
        throw new IllegalArgumentException(name);
      }
      channels.put(name, instance);
    }
  }

  @Override
  public void disconnect() {

    client.disconnect();
    channels.clear();
  }

  /**
   * <p>
   * Returns next event in consumer event queue, then removes it.
   * </p>
   *
   * @return An ExchangeEvent
   */
  @Override
  public ExchangeEvent getNextEvent() throws InterruptedException {

    return consumerEventQueue.take();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int countEventsAvailable() {
    return consumerEventQueue.size();
  }

  /**
   * <p>
   * Sends a msg over the socket.
   * </p>
   */
  @Override
  public void send(String msg) {

    // There's nothing to send for the current API!
  }

  /**
   * <p>
   * Query the current state of the socket.
   * </p>
   */
  @Override
  public READYSTATE getWebSocketStatus() {

    // ConnectionState: CONNECTING, CONNECTED, DISCONNECTING, DISCONNECTED, ALL
    // mapped to:
    // READYSTATE: NOT_YET_CONNECTED, CONNECTING, OPEN, CLOSING, CLOSED;
    switch (client.getConnection().getState()) {
    case CONNECTING:
      return READYSTATE.CONNECTING;

    case CONNECTED:
      return READYSTATE.OPEN;

    case DISCONNECTING:
      return READYSTATE.CLOSING;

    case DISCONNECTED:
      return READYSTATE.CLOSED;

    default:
      return READYSTATE.NOT_YET_CONNECTED;
    }
  }

  private void bindOrderData(Channel chan) {

    SubscriptionEventListener listener = new SubscriptionEventListener() {

      @Override
      public void onEvent(String channelName, String eventName, String data) {

        ExchangeEvent xevt = null;
        try {
          OrderBook snapshot = parseOrderBook(data);
          xevt = new DefaultExchangeEvent(ExchangeEventType.SUBSCRIBE_ORDERS, data, snapshot);
        } catch (IOException e) {
          logger.error("JSON stream error", e);
        }
        if (xevt != null) {
          addToEventQueue(xevt);
        }
      }
    };
    chan.bind("data", listener);
  }

  private void bindDiffOrderData(Channel chan) {

    SubscriptionEventListener listener = new SubscriptionEventListener() {

      @Override
      public void onEvent(String channelName, String eventName, String data) {

        ExchangeEvent xevt = null;
        try {
          OrderBook delta = parseOrderBook(data);
          xevt = new DefaultExchangeEvent(ExchangeEventType.DEPTH, data, delta);
        } catch (IOException e) {
          logger.error("JSON stream error", e);
        }
        if (xevt != null) {
          addToEventQueue(xevt);
        }
      }
    };
    chan.bind("data", listener);
  }

  private OrderBook parseOrderBook(String rawJson) throws IOException {

    BitstampStreamingOrderBook bitstampOrderBook = streamObjectMapper.readValue(rawJson, BitstampStreamingOrderBook.class);

    List<LimitOrder> asks = BitstampAdapters.createOrders(CurrencyPair.BTC_USD, Order.OrderType.ASK, bitstampOrderBook.getAsks());
    List<LimitOrder> bids = BitstampAdapters.createOrders(CurrencyPair.BTC_USD, Order.OrderType.BID, bitstampOrderBook.getBids());
    return new OrderBook(null, asks, bids);
  }

  private void bindTradeData(Channel chan) {

    SubscriptionEventListener listener = new SubscriptionEventListener() {

      @Override
      public void onEvent(String channelName, String eventName, String data) {

        ExchangeEvent exchangeEvent = null;
        try {
          Trade trade = parseTrade(data);
          exchangeEvent = new DefaultExchangeEvent(ExchangeEventType.TRADE, data, trade);
        } catch (IOException e) {
          logger.error("JSON stream error", e);
        }
        if (exchangeEvent != null) {
          addToEventQueue(exchangeEvent);
        }
      }
    };
    chan.bind("trade", listener);
  }

  private Trade parseTrade(String rawJson) throws IOException {

    BitstampTransaction transaction = streamObjectMapper.readValue(rawJson, BitstampStreamingTransaction.class);
    return new Trade(null, transaction.getAmount(), CurrencyPair.BTC_USD, transaction.getPrice(), null, String.valueOf(transaction.getTid()));
  }

  private void addToEventQueue(ExchangeEvent event) {

    try {
      consumerEventQueue.put(event);
    } catch (InterruptedException e) {
      logger.debug("Event queue interrupted", e);
    }
  }

}
