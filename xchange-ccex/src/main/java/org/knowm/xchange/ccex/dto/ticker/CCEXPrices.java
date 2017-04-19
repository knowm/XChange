package org.knowm.xchange.ccex.dto.ticker;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.knowm.xchange.ccex.CCEXAdapters;
import org.knowm.xchange.ccex.dto.ticker.CCEXPrice.CCEXPriceDeserializer;
import org.knowm.xchange.ccex.dto.ticker.CCEXPrices.CCEXPricesDeserializer;
import org.knowm.xchange.currency.CurrencyPair;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = CCEXPricesDeserializer.class)
public class CCEXPrices {

  private final Map<CurrencyPair, CCEXPrice> priceMap;

  private CCEXPrices(Map<CurrencyPair, CCEXPrice> priceMap) {
    this.priceMap = priceMap;
  }

  public Map<CurrencyPair, CCEXPrice> getTickerMap() {
    return priceMap;
  }

  @Override
  public String toString() {
    return "CCEXPrices [priceMap=" + priceMap + "]";
  }

  static class CCEXPricesDeserializer extends JsonDeserializer<CCEXPrices> {

    @Override
    public CCEXPrices deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

      Map<CurrencyPair, CCEXPrice> priceMap = new HashMap<>();
      ObjectCodec oc = jp.getCodec();
      JsonNode node = oc.readTree(jp);
      if (node.isObject()) {

        Iterator<Entry<String, JsonNode>> priceEntryIter = node.fields();
        while (priceEntryIter.hasNext()) {
          Entry<String, JsonNode> priceEntryNode = priceEntryIter.next();

          String pairString = priceEntryNode.getKey();
          CurrencyPair pair = CCEXAdapters.adaptCurrencyPair(pairString);

          JsonNode priceNode = priceEntryNode.getValue();
          CCEXPrice price = CCEXPriceDeserializer.deserializeFromNode(priceNode);

          priceMap.put(pair, price);
        }
      }

      return new CCEXPrices(priceMap);
    }
  }
}