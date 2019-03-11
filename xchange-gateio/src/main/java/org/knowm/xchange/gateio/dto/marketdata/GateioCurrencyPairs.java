package org.knowm.xchange.gateio.dto.marketdata;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.gateio.GateioAdapters;
import org.knowm.xchange.gateio.dto.marketdata.GateioCurrencyPairs.BTERCurrencyPairsDeserializer;

@JsonDeserialize(using = BTERCurrencyPairsDeserializer.class)
public class GateioCurrencyPairs {

  private final Set<CurrencyPair> pairs;

  private GateioCurrencyPairs(Set<CurrencyPair> pairs) {

    this.pairs = pairs;
  }

  public Collection<CurrencyPair> getPairs() {

    return pairs;
  }

  @Override
  public String toString() {

    return "GateioCurrencyPairs [pairs=" + pairs + "]";
  }

  static class BTERCurrencyPairsDeserializer extends JsonDeserializer<GateioCurrencyPairs> {

    @Override
    public GateioCurrencyPairs deserialize(JsonParser jp, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {

      final Set<CurrencyPair> pairs = new HashSet<>();
      final ObjectCodec oc = jp.getCodec();
      final JsonNode node = oc.readTree(jp);
      if (node.isArray()) {
        for (JsonNode pairNode : node) {
          pairs.add(GateioAdapters.adaptCurrencyPair(pairNode.asText()));
        }
      }
      return new GateioCurrencyPairs(pairs);
    }
  }
}
