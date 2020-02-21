package org.knowm.xchange.gatehub.dto;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.knowm.xchange.currency.Currency;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = Network.Serializer.class)
@JsonDeserialize(using = Network.Deserializer.class)
public enum Network {
  ripple(2, null), ethereum(5, Currency.ETH);

  private static final Map<Integer, Network> byNetworkId = new HashMap<>();
  private static final Map<Currency, Network> byCurrency = new HashMap<>();

  static {
    for (Network network : values()) {
      byNetworkId.put(network.networkId, network);
      byCurrency.put(network.currency, network);
    }
  }

  private final int networkId;
  private final Currency currency;

  public static Network getNative(Currency currency) {
    return Objects.requireNonNull(byCurrency.get(currency), "Unsupported currency: " + currency);
  }

  @JsonCreator
  Network(int networkId, Currency currency) {
    this.networkId = networkId;
    this.currency = currency;
  }

  public static class Serializer extends JsonSerializer<Network> {
    @Override public void serialize(Network value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
      gen.writeNumber(value.networkId);
    }
  }

  public static class Deserializer extends JsonDeserializer<Network> {
    @Override public Network deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
      int intValue = p.getIntValue();
      return Objects.requireNonNull(byNetworkId.get(intValue), "Unsupported currency: " + intValue);
    }
  }
}
