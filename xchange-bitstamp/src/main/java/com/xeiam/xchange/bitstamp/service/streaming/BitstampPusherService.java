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
import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.java_websocket.WebSocket.READYSTATE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pusher.client.Pusher;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.streaming.*;
import com.xeiam.xchange.dto.marketdata.OrderBookUpdate;
/**
 * <p>
 * Streaming trade service for the MtGox exchange
 * </p>
 * <p>
 * MtGox provides a Websocket implementation
 * </p>
 */
public class BitstampPusherService extends BaseExchangeService implements StreamingExchangeService {

  private final Logger logger = LoggerFactory.getLogger(BitstampPusherService.class);

  //private final ExchangeEventListener exchangeEventListener;
  private final BlockingQueue<ExchangeEvent> consumerEventQueue = new LinkedBlockingQueue<ExchangeEvent>();

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

    super(exchangeSpecification, configuration);

    this.configuration = configuration;
    this.client = new Pusher(exchangeSpecification.getApiKey(), this.configuration.pusherOptions());
    this.reconnectService = new ReconnectService(this, exchangeStreamingConfiguration);
    this.channels = new HashMap<String, Channel>();
  }

  public void connect() {
	// Re-connect is handled by the base ReconnectService when it reads a closed conn. state
    client.connect();
    channels.clear();
    for(String name : configuration.getChannels()) {
      Channel instance = client.subscribe(name);
      switch(name) {
        case "order_book":
          bindOrderData(channel);
          break;
        case "live_trades":
          throw new NotImplementedException("live_trades not implemented");
          break;
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
  public ExchangeEvent getNextEvent() { //throws InterruptedException
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

  @Override
  public Collection<CurrencyPair> getExchangeSymbols() {
    return null;
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
      case ConnectionState.CONNECTING:
        return READYSTATE.CONNECTING;
        
      case ConnectionState.CONNECTED:
        return READYSTATE.OPEN;
        
      case ConnectionState.DISCONNECTING:
        return READYSTATE.CLOSING;
        
      case ConnectionState.DISCONNECTED:
        return READYSTATE.CLOSED;
        
      default:
        return READYSTATE.NOT_YET_CONNECTED;
    }
  }
  
  private void bindOrderData(Channel chan) {
    SubscriptionEventListener listener = new SubscriptionEventListener() {
      public void onEvent(String channelName, String eventName, String data) {
        OrderBookUpdate update = parseOrderBookUpdate(data);
        ExchangeEvent xEvt = new DefaultExchangeEvent(ExchangeEventType.DEPTH, data, update);
      }
    };
    chan.bind("data", listener);
  }
  
  private static void parseOrderBookUpdate(String json) {
    return null;
  }
  
}
