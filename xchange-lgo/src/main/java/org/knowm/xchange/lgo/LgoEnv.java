package org.knowm.xchange.lgo;

import org.knowm.xchange.ExchangeSpecification;

public final class LgoEnv {

  public static final String KEYS_URL = "Keys_Url";
  public static final String WS_URL = "Websocket_Url";
  public static final String SIGNATURE_SERVICE = "Signature_Service";
  public static final String SHOULD_ENCRYPT_ORDERS = "Encrypt_Orders";

  private LgoEnv() {}

  public static ExchangeSpecification prod() {
    ExchangeSpecification result = baseSpecification();
    result.setSslUri("https://exchange-api.exchange.lgo.markets");
    result.setHost("exchange-api.exchange.lgo.markets");
    result.setExchangeSpecificParametersItem(
        KEYS_URL, "https://storage.googleapis.com/lgo-markets-keys");
    result.setExchangeSpecificParametersItem(WS_URL, "wss://ws.exchange.lgo.markets/");
    return result;
  }

  public static ExchangeSpecification sandbox() {
    ExchangeSpecification result = baseSpecification();
    result.setSslUri("https://exchange-api.sandbox.lgo.markets");
    result.setHost("exchange-api.sandbox.lgo.markets");
    result.setExchangeSpecificParametersItem(
        KEYS_URL, "https://storage.googleapis.com/lgo-sandbox_batch_keys");
    result.setExchangeSpecificParametersItem(WS_URL, "wss://ws.sandbox.lgo.markets/");
    return result;
  }

  public static ExchangeSpecification devel() {
    ExchangeSpecification result = baseSpecification();
    result.setSslUri("https://exchange-api.devel.z.lgo.ninja");
    result.setHost("exchange-api.devel.z.lgo.ninja");
    result.setExchangeSpecificParametersItem(
        KEYS_URL, "https://storage.googleapis.com/lgo-devel_batch_keys");
    result.setExchangeSpecificParametersItem(WS_URL, "wss://ws.devel.z.lgo.ninja/");
    return result;
  }

  public static ExchangeSpecification local() {
    ExchangeSpecification result = baseSpecification();
    result.setSslUri("http://localhost:8083");
    result.setHost("localhost");
    result.setExchangeSpecificParametersItem(KEYS_URL, "http://localhost:3001/keys");
    result.setExchangeSpecificParametersItem(WS_URL, "ws://localhost:8084/");
    return result;
  }

  private static ExchangeSpecification baseSpecification() {
    ExchangeSpecification result = new ExchangeSpecification(LgoExchange.class);
    result.setExchangeName("LGO");
    result.setExchangeSpecificParametersItem(SHOULD_ENCRYPT_ORDERS, false);
    result.setExchangeDescription(
        "LGO is a fare and secure exchange for institutional and retail investors.");
    return result;
  }

  public enum SignatureService {
    PASSTHROUGHS,
    LOCAL_RSA
  }
}
