package org.knowm.xchange.yobit.dto.marketdata;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.yobit.YoBitAdapters;
import org.knowm.xchange.yobit.dto.marketdata.YoBitPair.YoBitPairDeserializer;
import org.knowm.xchange.yobit.dto.marketdata.YoBitPairs.YoBitPricesDeserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = YoBitPricesDeserializer.class)
public class YoBitPairs {
  private final Map<CurrencyPair, YoBitPair> pair;

  private YoBitPairs(Map<CurrencyPair, YoBitPair> pair) {
    this.pair = pair;
  }

  public Map<CurrencyPair, YoBitPair> getPrice() {
    return pair;
  }

  @Override
  public String toString() {
    return "YoBitPairs [pair=" + pair + "]";
  }

  static class YoBitPricesDeserializer extends JsonDeserializer<YoBitPairs> {

    @Override
    public YoBitPairs deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

      Map<CurrencyPair, YoBitPair> priceMap = new HashMap<>();
      ObjectCodec oc = jp.getCodec();
      JsonNode node = oc.readTree(jp);
      if (node.isObject()) {
        Iterator<Entry<String, JsonNode>> priceEntryIter = node.fields();
        while (priceEntryIter.hasNext()) {
          Entry<String, JsonNode> priceEntryNode = priceEntryIter.next();

          String pairString = priceEntryNode.getKey();
          CurrencyPair pair = YoBitAdapters.adaptCurrencyPair(pairString);

          JsonNode priceNode = priceEntryNode.getValue();
          YoBitPair price = YoBitPairDeserializer.deserializeFromNode(priceNode);

          priceMap.put(pair, price);
        }
      }

      return new YoBitPairs(priceMap);
    }
  }
}
