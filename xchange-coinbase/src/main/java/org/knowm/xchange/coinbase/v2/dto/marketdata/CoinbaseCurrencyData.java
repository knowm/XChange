package org.knowm.xchange.coinbase.v2.dto.marketdata;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class CoinbaseCurrencyData {

  private List<CoinbaseCurrency> data;

  public List<CoinbaseCurrency> getData() {
    return Collections.unmodifiableList(data);
  }

  public void setData(List<CoinbaseCurrency> data) {
    this.data = data;
  }

  @JsonDeserialize(using = CoinbaseCurrencyDeserializer.class)
  public static class CoinbaseCurrency {
    private final String name;
    private final String id;

    public CoinbaseCurrency(String name, final String id) {
      this.name = name;
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public String getId() {
      return id;
    }

    @Override
    public int hashCode() {
      return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      CoinbaseCurrency other = (CoinbaseCurrency) obj;
      return id.equals(other.id);
    }

    @Override
    public String toString() {
      return id + " (" + name + ")";
    }
  }

  // [TODO] can we not do this with @JsonCreator
  static class CoinbaseCurrencyDeserializer extends JsonDeserializer<CoinbaseCurrency> {

    @Override
    public CoinbaseCurrency deserialize(JsonParser jp, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {
      ObjectCodec oc = jp.getCodec();
      JsonNode node = oc.readTree(jp);
      String name = node.get("name").asText();
      String id = node.get("id").asText();
      return new CoinbaseCurrency(name, id);
    }
  }
}
