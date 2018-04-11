package org.knowm.xchange.coinbase.dto.marketdata;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import org.knowm.xchange.coinbase.dto.marketdata.CoinbaseCurrency.CoinbaseCurrencyDeserializer;

/** @author jamespedwards42 */
@JsonDeserialize(using = CoinbaseCurrencyDeserializer.class)
public class CoinbaseCurrency {

  private final String name;
  private final String isoCode;

  private CoinbaseCurrency(String name, final String isoCode) {

    this.name = name;
    this.isoCode = isoCode;
  }

  public String getName() {

    return name;
  }

  public String getIsoCode() {

    return isoCode;
  }

  @Override
  public String toString() {

    return "CoinbaseCurrency [name=" + name + ", isoCode=" + isoCode + "]";
  }

  static class CoinbaseCurrencyDeserializer extends JsonDeserializer<CoinbaseCurrency> {

    @Override
    public CoinbaseCurrency deserialize(JsonParser jp, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {

      ObjectCodec oc = jp.getCodec();
      JsonNode node = oc.readTree(jp);
      if (node.isArray()) {
        String name = node.path(0).asText();
        String isoCode = node.path(1).asText();
        return new CoinbaseCurrency(name, isoCode);
      }
      return null;
    }
  }
}
