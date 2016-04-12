package org.knowm.xchange.btcchina.service.streaming;

import static org.knowm.xchange.btcchina.service.streaming.BTCChinaSocketIOClientBuilder.EVENT_ACCOUNT_INFO;
import static org.knowm.xchange.btcchina.service.streaming.BTCChinaSocketIOClientBuilder.EVENT_GROUPORDER;
import static org.knowm.xchange.btcchina.service.streaming.BTCChinaSocketIOClientBuilder.EVENT_ORDER;
import static org.knowm.xchange.btcchina.service.streaming.BTCChinaSocketIOClientBuilder.EVENT_TICKER;
import static org.knowm.xchange.btcchina.service.streaming.BTCChinaSocketIOClientBuilder.EVENT_TRADE;

import java.net.URI;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.java_websocket.WebSocket.READYSTATE;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcchina.BTCChinaExchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.streaming.ExchangeEvent;
import org.knowm.xchange.service.streaming.ExchangeEventType;
import org.knowm.xchange.service.streaming.StreamingExchangeService;

public class BTCChinaSocketIOService extends BaseExchangeService implements StreamingExchangeService {

  private final Logger log = LoggerFactory.getLogger(BTCChinaSocketIOService.class);

  private final BlockingQueue<ExchangeEvent> consumerEventQueue = new LinkedBlockingQueue<ExchangeEvent>();

  private final Socket socket;

  private READYSTATE webSocketStatus = READYSTATE.NOT_YET_CONNECTED;

  public BTCChinaSocketIOService(Exchange exchange, BTCChinaStreamingConfiguration exchangeStreamingConfiguration) {

    super(exchange);

    final String uri = (String) exchange.getExchangeSpecification().getExchangeSpecificParametersItem(BTCChinaExchange.WEBSOCKET_URI_KEY);

    socket = BTCChinaSocketIOClientBuilder.create().setUri(URI.create(uri)).setAccessKey(exchange.getExchangeSpecification().getApiKey())
        .setSecretKey(exchange.getExchangeSpecification().getSecretKey())
        .subscribeAccountInfo(exchangeStreamingConfiguration.isSubscribeAccountInfo())
        .subscribeMarketData(exchangeStreamingConfiguration.getMarketDataCurrencyPairs())
        .subscribeGrouporder(exchangeStreamingConfiguration.getGrouporderCurrencyPairs())
        .subscribeOrderFeed(exchangeStreamingConfiguration.getOrderFeedCurrencyPairs()).build();

    listen();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void connect() {

    socket.connect();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void disconnect() {

    socket.disconnect();
  }

  /**
   * {@inheritDoc}
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
   * {@inheritDoc}
   */
  @Override
  public void send(String msg) {

    // There's nothing to send for the current API!
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public READYSTATE getWebSocketStatus() {

    return webSocketStatus;
  }

  private void putEvent(ExchangeEvent event) {

    try {
      consumerEventQueue.put(event);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private void putEvent(ExchangeEventType exchangeEventType) {

    putEvent(new BTCChinaExchangeEvent(exchangeEventType));
  }

  private void putEvent(ExchangeEventType exchangeEventType, JSONObject data, Object payload) {

    putEvent(new BTCChinaExchangeEvent(exchangeEventType, data, payload));
  }

  private void listen() {

    socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

      @Override
      public void call(Object... args) {

        log.debug("connected");
        webSocketStatus = READYSTATE.OPEN;
        putEvent(ExchangeEventType.CONNECT);
      }
    }).on(EVENT_TRADE, new Emitter.Listener() {

      @Override
      public void call(Object... args) {

        // receive the trade message
        JSONObject json = (JSONObject) args[0];
        log.debug("{}: {}", EVENT_TRADE, json);
        putEvent(ExchangeEventType.TRADE, json, BTCChinaJSONObjectAdapters.adaptTrade(json));
      }
    }).on(EVENT_TICKER, new Emitter.Listener() {

      @Override
      public void call(Object... args) {

        // receive the ticker message
        JSONObject json = (JSONObject) args[0];
        log.debug("{}: {}", EVENT_TICKER, json);
        putEvent(ExchangeEventType.TICKER, json, BTCChinaJSONObjectAdapters.adaptTicker(json));
      }
    }).on(EVENT_GROUPORDER, new Emitter.Listener() {

      @Override
      public void call(Object... args) {

        // receive the market depth feed
        JSONObject json = (JSONObject) args[0];
        log.debug("{}: {}", EVENT_GROUPORDER, json);
        putEvent(ExchangeEventType.DEPTH, json, BTCChinaJSONObjectAdapters.adaptDepth(json));
      }
    }).on(EVENT_ORDER, new Emitter.Listener() {

      @Override
      public void call(Object... args) {

        // receive your order feed
        JSONObject json = (JSONObject) args[0];
        log.debug("{}: {}", EVENT_ORDER, json);
        putEvent(ExchangeEventType.USER_ORDER, json, BTCChinaJSONObjectAdapters.adaptOrder(json));
      }
    }).on(EVENT_ACCOUNT_INFO, new Emitter.Listener() {

      @Override
      public void call(Object... args) {

        // receive account balance changes
        JSONObject json = (JSONObject) args[0];
        log.debug("{}: {}", EVENT_ACCOUNT_INFO, json);
        putEvent(ExchangeEventType.USER_WALLET, json, BTCChinaJSONObjectAdapters.adaptBalance(json));
      }
    }).on(Socket.EVENT_RECONNECTING, new Emitter.Listener() {

      @Override
      public void call(Object... args) {

        log.debug("reconnecting");
        webSocketStatus = READYSTATE.CONNECTING;
      }
    }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

      @Override
      public void call(Object... args) {

        log.debug("disconnected");
        webSocketStatus = READYSTATE.CLOSED;
        putEvent(ExchangeEventType.DISCONNECT);
      }
    });
  }

}
