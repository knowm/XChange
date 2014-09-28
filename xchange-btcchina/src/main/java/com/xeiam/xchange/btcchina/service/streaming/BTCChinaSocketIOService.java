package com.xeiam.xchange.btcchina.service.streaming;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.java_websocket.WebSocket.READYSTATE;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btcchina.BTCChinaExchange;
import com.xeiam.xchange.btcchina.BTCChinaUtils;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.BaseParamsDigest;
import com.xeiam.xchange.service.streaming.DefaultExchangeEvent;
import com.xeiam.xchange.service.streaming.ExchangeEvent;
import com.xeiam.xchange.service.streaming.ExchangeEventType;
import com.xeiam.xchange.service.streaming.StreamingExchangeService;

public class BTCChinaSocketIOService extends BaseExchangeService implements StreamingExchangeService {

  private static String HMAC_SHA1_ALGORITHM = BaseParamsDigest.HMAC_SHA_1;

  private final Logger log = LoggerFactory.getLogger(BTCChinaSocketIOService.class);

  private final BlockingQueue<ExchangeEvent> consumerEventQueue = new LinkedBlockingQueue<ExchangeEvent>();

  private final String accessKey;
  private final String secretKey;

  private final BTCChinaStreamingConfiguration exchangeStreamingConfiguration;
  private final Socket socket;

  private READYSTATE webSocketStatus = READYSTATE.NOT_YET_CONNECTED;

  public BTCChinaSocketIOService(ExchangeSpecification exchangeSpecification, BTCChinaStreamingConfiguration exchangeStreamingConfiguration) {

    super(exchangeSpecification);

    this.accessKey = exchangeSpecification.getApiKey();
    this.secretKey = exchangeSpecification.getSecretKey();

    this.exchangeStreamingConfiguration = exchangeStreamingConfiguration;

    final String uri = (String) exchangeSpecification.getExchangeSpecificParametersItem(BTCChinaExchange.WEBSOCKET_URI_KEY);

    final IO.Options opt = new IO.Options();
    opt.reconnection = true;

    try {
      socket = IO.socket(uri, opt);
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException(e);
    }

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

    putEvent(new DefaultExchangeEvent(exchangeEventType, null));
  }

  private void putEvent(ExchangeEventType exchangeEventType, JSONObject data, Object payload) {

    putEvent(new DefaultExchangeEvent(exchangeEventType, data.toString(), payload));
  }

  private void listen() {

    socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

      @Override
      public void call(Object... args) {

        log.debug("connected");
        webSocketStatus = READYSTATE.OPEN;
        putEvent(ExchangeEventType.CONNECT);

        final List<String> markets = new ArrayList<String>(exchangeStreamingConfiguration.getCurrencyPairs().length);
        for (CurrencyPair currencyPair : exchangeStreamingConfiguration.getCurrencyPairs()) {
          markets.add(toMarket(currencyPair));
        }

        if (exchangeStreamingConfiguration.isSubscribeMarketData()) {
          subscribeMarketData(markets);
        }

        if (exchangeStreamingConfiguration.isSubscribeOrderFeed()) {
          subscribeOrderFeed(markets);
        }
      }

      private void subscribeMarketData(final List<String> markets) {

        for (String market : markets) {
          final String marketData = String.format("marketdata_%s", market);
          log.debug("subscribing {}", marketData);
          socket.emit("subscribe", marketData);
        }
      }

      private void subscribeOrderFeed(final List<String> markets) {

        // Use 'private' method to subscribe the order feed
        for (String market : markets) {
          final String tonce = String.valueOf(BTCChinaUtils.getNonce());

          final List<String> arg = new ArrayList<String>(2);
          arg.add(getPayload(tonce, market));
          arg.add(getSign(tonce, market));

          log.debug("subscribing order {}", market);
          socket.emit("private", arg);
        }
      }

    }).on("trade", new Emitter.Listener() {
  
      @Override
      public void call(Object... args) {

        // receive the trade message
        JSONObject json = (JSONObject) args[0];
        log.debug("{}", json);
        putEvent(ExchangeEventType.TRADE, json, BTCChinaJSONObjectAdapters.adaptTrade(json));
      }
    }).on("ticker", new Emitter.Listener() {
  
      @Override
      public void call(Object... args) {

        // receive the ticker message
        JSONObject json = (JSONObject) args[0];
        log.debug("{}", json);
        putEvent(ExchangeEventType.TICKER, json, BTCChinaJSONObjectAdapters.adaptTicker(json));
      }
    }).on("order", new Emitter.Listener() {
  
      @Override
      public void call(Object... args) {

        // receive your order feed
        JSONObject json = (JSONObject) args[0];
        log.debug("{}", json);
        putEvent(ExchangeEventType.USER_ORDER, json, BTCChinaJSONObjectAdapters.adaptOrder(json));
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

  private String getPayload(String tonce, String market) {

    String postdata = String.format("{\"tonce\":\"%1$s\",\"accesskey\":\"%2$s\",\"requestmethod\": \"post\",\"id\":\"%1$s\",\"method\": \"subscribe\", \"params\": [\"order_%3$s\"]}",
        tonce, accessKey, market);

    log.debug("postdata is: {}", postdata);
    return postdata;
  }

  private String getSign(String tonce, String market) {

    String params = String.format("tonce=%1$s&accesskey=%2$s&requestmethod=post&id=%1$s&method=subscribe&params=order_%3$s", tonce, accessKey, market);
    String hash;
    try {
      hash = getSignature(params, secretKey);
    } catch (InvalidKeyException e) {
      throw new IllegalArgumentException(e);
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalArgumentException(e);
    }
    String userpass = accessKey + ":" + hash;
    String basicAuth = DatatypeConverter.printBase64Binary(userpass.getBytes());
    return basicAuth;
  }

  private String getSignature(String data, String key) throws NoSuchAlgorithmException, InvalidKeyException {

    // get an hmac_sha1 key from the raw key bytes
    SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
    // get an hmac_sha1 Mac instance and initialize with the signing key
    Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
    mac.init(signingKey);
    // compute the hmac on input data bytes
    byte[] rawHmac = mac.doFinal(data.getBytes());
    return byteArrayToHex(rawHmac);
  }

  private String byteArrayToHex(byte[] a) {

    StringBuilder sb = new StringBuilder();
    for (byte b : a)
      sb.append(String.format("%02x", b & 0xff));
    return sb.toString();
  }

  private String toMarket(CurrencyPair currencyPair) {

    return String.format("%s%s", currencyPair.counterSymbol.toLowerCase(), currencyPair.baseSymbol.toLowerCase());
  }

}
