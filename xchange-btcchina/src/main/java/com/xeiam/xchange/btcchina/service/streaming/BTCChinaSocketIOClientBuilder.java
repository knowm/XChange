package com.xeiam.xchange.btcchina.service.streaming;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.xeiam.xchange.btcchina.BTCChinaUtils;
import com.xeiam.xchange.btcchina.dto.trade.streaming.request.BTCChinaPayload;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.utils.nonce.CurrentNanosecondTimeIncrementalNonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

public class BTCChinaSocketIOClientBuilder {

  public static final String EVENT_TICKER = "ticker";
  public static final String EVENT_TRADE = "trade";

  /**
   * @since <a href= "http://btcchina.org/websocket-api-market-data-documentation-en#websocket_api_v122" >WebSocket API v1.2.2</a>
   */
  public static final String EVENT_GROUPORDER = "grouporder";

  public static final String EVENT_ORDER = "order";
  public static final String EVENT_ACCOUNT_INFO = "account_info";

  private static final ObjectMapper mapper = new ObjectMapper();

  private final Logger log = LoggerFactory.getLogger(BTCChinaSocketIOClientBuilder.class);

  private URI uri;

  private final Set<CurrencyPair> marketData = new HashSet<CurrencyPair>(3);
  private final Set<CurrencyPair> grouporder = new HashSet<CurrencyPair>(3);
  private final Set<CurrencyPair> orderFeed = new HashSet<CurrencyPair>(3);

  private boolean subscribeAccountInfo;

  private String accessKey;
  private String secretKey;

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentNanosecondTimeIncrementalNonceFactory();

  protected final IO.Options opt = new IO.Options();

  public static BTCChinaSocketIOClientBuilder create() {

    return new BTCChinaSocketIOClientBuilder();
  }

  protected BTCChinaSocketIOClientBuilder() {

    opt.reconnection = true;
    /*
     * Prevents all sockets reporting the events of other socket's subscriptions.
     */
    opt.multiplex = false;
  }

  public IO.Options getIOOptions() {

    return opt;
  }

  public BTCChinaSocketIOClientBuilder setUri(URI uri) {

    this.uri = uri;
    return this;
  }

  public BTCChinaSocketIOClientBuilder setUri(String uri) {

    this.uri = URI.create(uri);
    return this;
  }

  public BTCChinaSocketIOClientBuilder subscribeAccountInfo(boolean subscribeAccountInfo) {

    this.subscribeAccountInfo = subscribeAccountInfo;
    return this;
  }

  public BTCChinaSocketIOClientBuilder subscribeMarketData(CurrencyPair... currencyPairs) {

    this.marketData.addAll(Arrays.asList(currencyPairs));
    return this;
  }

  /**
   * @since <a href= "http://btcchina.org/websocket-api-market-data-documentation-en#websocket_api_v122" >WebSocket API v1.2.2</a>
   */
  public BTCChinaSocketIOClientBuilder subscribeGrouporder(CurrencyPair... currencyPairs) {

    this.grouporder.addAll(Arrays.asList(currencyPairs));
    return this;
  }

  public BTCChinaSocketIOClientBuilder subscribeOrderFeed(CurrencyPair... currencyPairs) {

    this.orderFeed.addAll(Arrays.asList(currencyPairs));
    return this;
  }

  public BTCChinaSocketIOClientBuilder setAccessKey(String accessKey) {

    this.accessKey = accessKey;
    return this;
  }

  public BTCChinaSocketIOClientBuilder setSecretKey(String secretKey) {

    this.secretKey = secretKey;
    return this;
  }

  public Socket build() {

    if ((!this.orderFeed.isEmpty() || this.subscribeAccountInfo) && (this.accessKey == null || this.secretKey == null)) {
      throw new IllegalArgumentException("Access key and secret key are required to subscribe order feed and account info.");
    }

    final Socket socket;
    try {
      socket = IO.socket(uri, opt);
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException(e);
    }

    socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

      @Override
      public void call(Object... args) {

        subscribeMarketData();
        subscribeGrouporder();
        subscribePrivateData();
      }

      private void subscribeMarketData() {

        subscribe("marketdata", marketData);
      }

      private void subscribeGrouporder() {

        subscribe("grouporder", grouporder);
      }

      private void subscribe(String name, Iterable<CurrencyPair> currencyPairs) {

        for (CurrencyPair currencyPair : currencyPairs) {
          String market = toMarket(currencyPair);
          String subscribeData = String.format("%s_%s", name, market);
          log.debug("subscribing {}", subscribeData);
          socket.emit("subscribe", subscribeData);
        }
      }

      private void subscribePrivateData() {

        final List<String> params = buildPrivateDataParams();

        if (!params.isEmpty()) {
          BTCChinaPayload payload = getPayload(params.toArray(new String[0]));

          final List<String> arg = new ArrayList<String>(2);
          arg.add(toPostData(payload));
          arg.add(getSign(payload));

          // Use 'private' method to subscribe the order feed
          socket.emit("private", arg);
        } else {
          log.debug("No private data specified to subscribe.");
        }
      }

      private List<String> buildPrivateDataParams() {

        int capacity = orderFeed.size() + (subscribeAccountInfo ? 1 : 0);
        final List<String> params = new ArrayList<String>(capacity);

        for (CurrencyPair currencyPair : orderFeed) {
          final String market = toMarket(currencyPair);
          params.add("order_" + market);
          log.debug("subscribing order feed {}.", market);
        }

        if (subscribeAccountInfo) {
          params.add("account_info");
          log.debug("subscribing account info.");
        }

        return params;
      }

    });

    return socket;
  }

  private BTCChinaPayload getPayload(String[] params) {

    final long tonce = nonceFactory.createValue();
    final BTCChinaPayload payload = new BTCChinaPayload(tonce, accessKey, "post", "subscribe", params);
    return payload;
  }

  private String toPostData(BTCChinaPayload payload) {

    String postdata;
    try {
      postdata = mapper.writeValueAsString(payload);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException(e);
    }
    log.debug("postdata is: {}", postdata);
    return postdata;
  }

  private String getSign(BTCChinaPayload payload) {

    final String payloadParamsString = StringUtils.join(payload.getParams(), ",");
    final String params = String.format("tonce=%1$d&accesskey=%2$s&requestmethod=post&id=%1$s&method=subscribe&params=%3$s", payload.getTonce(),
        payload.getAccessKey(), payloadParamsString);
    log.debug("signature message: {}", params);
    String hash;
    try {
      hash = BTCChinaUtils.getSignature(params, secretKey);
    } catch (InvalidKeyException e) {
      throw new IllegalArgumentException(e);
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalArgumentException(e);
    }
    String userpass = accessKey + ":" + hash;
    String basicAuth = DatatypeConverter.printBase64Binary(userpass.getBytes());
    return basicAuth;
  }

  private String toMarket(CurrencyPair currencyPair) {

    return String.format("%s%s", currencyPair.counter.getCurrencyCode().toLowerCase(), currencyPair.base.getCurrencyCode().toLowerCase());
  }

}
