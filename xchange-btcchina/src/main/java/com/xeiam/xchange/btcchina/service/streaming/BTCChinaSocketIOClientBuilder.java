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

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.xeiam.xchange.btcchina.BTCChinaUtils;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.BaseParamsDigest;

public class BTCChinaSocketIOClientBuilder {

  public static final String EVENT_TRADE = "trade";
  public static final String EVENT_TICKER = "ticker";
  public static final String EVENT_ORDER = "order";

  private static String HMAC_SHA1_ALGORITHM = BaseParamsDigest.HMAC_SHA_1;

  private final Logger log = LoggerFactory.getLogger(BTCChinaSocketIOClientBuilder.class);

  private URI uri;

  private final Set<CurrencyPair> marketData = new HashSet<CurrencyPair>(3);
  private final Set<CurrencyPair> orderFeed = new HashSet<CurrencyPair>(3);

  private String accessKey;
  private String secretKey;

  protected final IO.Options opt = new IO.Options();

  public static BTCChinaSocketIOClientBuilder create() {

    return new BTCChinaSocketIOClientBuilder();
  }

  protected BTCChinaSocketIOClientBuilder() {

    opt.reconnection = true;
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

  public BTCChinaSocketIOClientBuilder subscribeMarketData(CurrencyPair... currencyPairs) {

    this.marketData.addAll(Arrays.asList(currencyPairs));
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

    if (!this.orderFeed.isEmpty() && (this.accessKey == null || this.secretKey == null)) {
      throw new IllegalArgumentException("Access key and secret key are required to subscribe order feed.");
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
        subscribeOrderFeed();
      }

      private void subscribeMarketData() {
        for (CurrencyPair currencyPair : marketData) {
          final String market = toMarket(currencyPair);
          final String marketData = String.format("marketdata_%s", market);
          log.debug("subscribing {}", marketData);
          socket.emit("subscribe", marketData);
        }
      }

      private void subscribeOrderFeed() {
        for (CurrencyPair currencyPair : orderFeed) {
          final String market = toMarket(currencyPair);
          final String tonce = String.valueOf(BTCChinaUtils.getNonce());

          final List<String> arg = new ArrayList<String>(2);
          arg.add(getPayload(tonce, market));
          arg.add(getSign(tonce, market));

          log.debug("subscribing order feed {}", market);

          // Use 'private' method to subscribe the order feed
          socket.emit("private", arg);
        }
      }

    });

    return socket;
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
    for (byte b : a) {
      sb.append(String.format("%02x", b & 0xff));
    }
    return sb.toString();
  }

  private String toMarket(CurrencyPair currencyPair) {

    return String.format("%s%s", currencyPair.counterSymbol.toLowerCase(), currencyPair.baseSymbol.toLowerCase());
  }

}
