package org.knowm.xchange.lgo;

import org.knowm.xchange.ExchangeSpecification;

public final class LgoEnv {

  public static final String KEYS_URL = "Keys_Url";
  public static final String WS_URL = "Websocket_Url";
  public static final String SIGNATURE_SERVICE = "Signature_Service";

  private LgoEnv() {}

  public static ExchangeSpecification prodMarkets() {
    ExchangeSpecification result = baseSpecification();
    result.setSslUri("https://exchange-api.exchange.lgo.markets");
    result.setHost("exchange-api.exchange.lgo.markets");
    result.setExchangeSpecificParametersItem(
        KEYS_URL, "https://storage.googleapis.com/lgo-markets-keys");
    result.setExchangeSpecificParametersItem(WS_URL, "wss://ws.exchange.lgo.markets");
    return result;
  }

  public static ExchangeSpecification sandboxMarkets() {
    ExchangeSpecification result = baseSpecification();
    result.setSslUri("https://exchange-api.sandbox.lgo.markets");
    result.setHost("exchange-api.sandbox.lgo.markets");
    result.setExchangeSpecificParametersItem(
        KEYS_URL, "https://storage.googleapis.com/lgo-sandbox_batch_keys");
    result.setExchangeSpecificParametersItem(WS_URL, "wss://ws.sandbox.lgo.markets");
    return result;
  }

  private static ExchangeSpecification baseSpecification() {
    ExchangeSpecification result = new ExchangeSpecification(LgoExchange.class);
    result.setExchangeName("LGO");
    result.setExchangeDescription(
        "LGO is a fare and secure exchange for institutional and retail investors.");
    return result;
  }

  public enum SignatureService {
    PASSTHROUGHS,
    LOCAL_RSA
  }
}
