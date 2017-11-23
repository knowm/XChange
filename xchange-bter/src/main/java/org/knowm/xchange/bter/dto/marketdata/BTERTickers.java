package org.knowm.xchange.bter.dto.marketdata;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.knowm.xchange.bter.BTERAdapters;
import org.knowm.xchange.bter.dto.BTERBaseResponse;
import org.knowm.xchange.bter.dto.marketdata.BTERTicker.BTERTickerTickerDeserializer;
import org.knowm.xchange.bter.dto.marketdata.BTERTickers.BTERTickersDeserializer;
import org.knowm.xchange.currency.CurrencyPair;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = BTERTickersDeserializer.class)
public class BTERTickers extends BTERBaseResponse {

  private final Map<CurrencyPair, BTERTicker> tickerMap;

  private BTERTickers(Map<CurrencyPair, BTERTicker> tickerMap, boolean result, String message) {

    super(result, message);
    this.tickerMap = tickerMap;
  }

  public Map<CurrencyPair, BTERTicker> getTickerMap() {

    return tickerMap;
  }

  @Override
  public String toString() {

    return "BTERTickers [tickerMap=" + tickerMap + "]";
  }

  static class BTERTickersDeserializer extends JsonDeserializer<BTERTickers> {

    @Override
    public BTERTickers deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

      Map<CurrencyPair, BTERTicker> tickerMap = new HashMap<>();
      ObjectCodec oc = jp.getCodec();
      JsonNode node = oc.readTree(jp);
      if (node.isObject()) {

        Iterator<Entry<String, JsonNode>> tickerEntryIter = node.fields();
        while (tickerEntryIter.hasNext()) {
          Entry<String, JsonNode> tickerEntryNode = tickerEntryIter.next();

          String pairString = tickerEntryNode.getKey();
          CurrencyPair pair = BTERAdapters.adaptCurrencyPair(pairString);

          JsonNode tickerNode = tickerEntryNode.getValue();
          BTERTicker ticker = BTERTickerTickerDeserializer.deserializeFromNode(tickerNode);

          tickerMap.put(pair, ticker);
        }
      }

      boolean result = true;
      String message = "";
      JsonNode resultNode = node.path("result");
      if (resultNode.isBoolean()) {
        result = resultNode.asBoolean();
        message = node.path("message").asText();
      }
      return new BTERTickers(tickerMap, result, message);
    }
  }
}
