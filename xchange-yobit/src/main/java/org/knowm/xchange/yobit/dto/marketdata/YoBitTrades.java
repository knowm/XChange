package org.knowm.xchange.yobit.dto.marketdata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.knowm.xchange.yobit.dto.marketdata.YoBitTrades.YoBitTradesDeserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = YoBitTradesDeserializer.class)
public class YoBitTrades {

  public final Map<String, List<YoBitTrade>> trades;

  public YoBitTrades(Map<String, List<YoBitTrade>> trades) {
    super();
    this.trades = trades;
  }

  public Map<String, List<YoBitTrade>> getTrades() {
    return trades;
  }

  static class YoBitTradesDeserializer extends JsonDeserializer<YoBitTrades> {

    @Override
    public YoBitTrades deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
      Map<String, List<YoBitTrade>> trades = new HashMap<>();

      ObjectCodec oc = p.getCodec();
      JsonNode node = oc.readTree(p);

      if (node.isObject()) {
        Iterator<Entry<String, JsonNode>> priceEntryIter = node.fields();
        while (priceEntryIter.hasNext()) {
          Entry<String, JsonNode> priceEntryNode = priceEntryIter.next();

          JsonNode priceNode = priceEntryNode.getValue();
          String ccyPair = priceEntryNode.getKey();

          List<YoBitTrade> res = new ArrayList<>();
          trades.put(ccyPair, res);

          if (priceNode.isArray()) {
            for (JsonNode jsonNode : priceNode) {
              ObjectMapper jsonObjectMapper = new ObjectMapper();
              res.add(jsonObjectMapper.convertValue(jsonNode, YoBitTrade.class));
            }
          }
        }
      }

      return new YoBitTrades(trades);
    }

  }
}
