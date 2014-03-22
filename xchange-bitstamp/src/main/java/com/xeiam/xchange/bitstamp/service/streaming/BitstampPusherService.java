/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 * Copyright (C) 2014 Ryan Sundberg <ryan.sundberg@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.bitstamp.service.streaming;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.java_websocket.WebSocket.READYSTATE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pusher.client.Pusher;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.streaming.*;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.bitstamp.BitstampAdapters;
import com.xeiam.xchange.bitstamp.dto.marketdata.BitstampOrderBook;
import com.xeiam.xchange.bitstamp.service.BitstampBaseService;
import com.xeiam.xchange.bitstamp.service.streaming.BitstampStreamingConfiguration;

/**
 * <p>
 * Streaming trade service for the MtGox exchange
 * </p>
 * <p>
 * MtGox provides a Websocket implementation
 * </p>
 */
public class BitstampPusherService extends BitstampBaseService implements StreamingExchangeService {

  private final Logger log = LoggerFactory.getLogger(BitstampPusherService.class);

  //private final ExchangeEventListener exchangeEventListener;
  private final BlockingQueue<ExchangeEvent> consumerEventQueue = new LinkedBlockingQueue<ExchangeEvent>();
  private final ObjectMapper streamObjectMapper;
  
  /**
   * Ensures that exchange-specific configuration is available
   */
  private final BitstampStreamingConfiguration configuration;
  
  private Pusher client;
  private Map<String, Channel> channels;
  private ReconnectService reconnectService;

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BitstampPusherService(ExchangeSpecification exchangeSpecification, BitstampStreamingConfiguration configuration) {

    super(exchangeSpecification);

    this.configuration = configuration;
    this.client = new Pusher(exchangeSpecification.getApiKey(), this.configuration.pusherOptions());
    this.reconnectService = new ReconnectService(this, configuration);
    this.channels = new HashMap<String, Channel>();
    
    streamObjectMapper = new ObjectMapper();
    streamObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  public void connect() {
	// Re-connect is handled by the base ReconnectService when it reads a closed conn. state
    client.connect();
    channels.clear();
    for(String name : configuration.getChannels()) {
      Channel instance = client.subscribe(name);
      if(name == "order_book") {
        bindOrderData(instance);
      }
      else if(name == "live_trades") {
        throw new UnsupportedOperationException("live_trades not implemented");
      }
      else {
        throw new IllegalArgumentException(name);
      }
      channels.put(name, instance);
    }
  }
  
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
  public ExchangeEvent getNextEvent() throws InterruptedException {
    return consumerEventQueue.take();
  }

  /**
   * <p>
   * Sends a msg over the socket.
   * </p>
   */
  public void send(String msg) {
    // There's nothing to send for the current API!
  }

  /**
   * <p>
   * Query the current state of the socket.
   * </p>
   */
  public READYSTATE getWebSocketStatus() {
    // ConnectionState: CONNECTING, CONNECTED, DISCONNECTING, DISCONNECTED, ALL
    // mapped to:
    // READYSTATE: NOT_YET_CONNECTED, CONNECTING, OPEN, CLOSING, CLOSED;
    switch(client.getConnection().getState()) {
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
      public void onEvent(String channelName, String eventName, String data) {
        ExchangeEvent xevt = null;
        try {
          OrderBook snapshot = parseOrderBook(data);
          xevt = new DefaultExchangeEvent(ExchangeEventType.SUBSCRIBE_ORDERS, data, snapshot);
        }
        catch(IOException e) {
          log.error("JSON stream error", e);
        }
        if(xevt != null)
          addToEventQueue(xevt);
      }
    };
    chan.bind("data", listener);
  }
  
  private OrderBook parseOrderBook(String rawJson) throws IOException {
    BitstampOrderBook nativeBook = streamObjectMapper.readValue(rawJson, BitstampOrderBook.class);
    //BitstampOrderBook nativeBook = new BitstampOrderBook((new Date()).getTime(), json.get("bids"), json.get("asks"));
    return BitstampAdapters.adaptOrders(nativeBook, CurrencyPair.BTC_USD, 1);
  }
  
  private void addToEventQueue(ExchangeEvent event) {
    try {
      consumerEventQueue.put(event);
    }
    catch (InterruptedException e) {
      log.debug("Event queue interrupted", e);
    }
  }
  
}
