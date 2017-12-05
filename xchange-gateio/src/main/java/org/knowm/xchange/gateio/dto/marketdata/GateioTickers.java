package org.knowm.xchange.gateio.dto.marketdata;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.knowm.xchange.gateio.GateioAdapters;
import org.knowm.xchange.gateio.dto.GateioBaseResponse;
import org.knowm.xchange.gateio.dto.marketdata.GateioTickers.BTERTickersDeserializer;
import org.knowm.xchange.currency.CurrencyPair;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = BTERTickersDeserializer.class)
public class GateioTickers extends GateioBaseResponse {

  private final Map<CurrencyPair, GateioTicker> tickerMap;

  private GateioTickers(Map<CurrencyPair, GateioTicker> tickerMap, boolean result, String message) {

    super(result, message);
    this.tickerMap = tickerMap;
  }

  public Map<CurrencyPair, GateioTicker> getTickerMap() {

    return tickerMap;
  }

  @Override
  public String toString() {

    return "GateioTickers [tickerMap=" + tickerMap + "]";
  }

  static class BTERTickersDeserializer extends JsonDeserializer<GateioTickers> {

    @Override
    public GateioTickers deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

      Map<CurrencyPair, GateioTicker> tickerMap = new HashMap<>();
      ObjectCodec oc = jp.getCodec();
      JsonNode node = oc.readTree(jp);
      if (node.isObject()) {

        Iterator<Entry<String, JsonNode>> tickerEntryIter = node.fields();
        while (tickerEntryIter.hasNext()) {
          Entry<String, JsonNode> tickerEntryNode = tickerEntryIter.next();

          String pairString = tickerEntryNode.getKey();
          CurrencyPair pair = GateioAdapters.adaptCurrencyPair(pairString);

          JsonNode tickerNode = tickerEntryNode.getValue();
          GateioTicker ticker = GateioTicker.BTERTickerTickerDeserializer.deserializeFromNode(tickerNode);

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
      return new GateioTickers(tickerMap, result, message);
    }
  }
}
