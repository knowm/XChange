package org.knowm.xchange.cryptowatch.dto.marketdata;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/** @author massi.gerardi */
@JsonDeserialize(using = CryptowatchOHLCs.CryptowatchOHLCsDeserializer.class)
@Getter
@AllArgsConstructor
@ToString
public class CryptowatchOHLCs {

  private final Map<Integer, List<CryptowatchOHLC>> OHLCs;

  static class CryptowatchOHLCsDeserializer extends JsonDeserializer<CryptowatchOHLCs> {

    @Override
    public CryptowatchOHLCs deserialize(JsonParser jsonParser, DeserializationContext ctxt)
        throws IOException {
      Map<Integer, List<CryptowatchOHLC>> cwOHLCs = new HashMap<>();
      ObjectCodec oc = jsonParser.getCodec();
      JsonNode node = oc.readTree(jsonParser);

      Iterator<Map.Entry<String, JsonNode>> tradesResultIterator = node.fields();
      while (tradesResultIterator.hasNext()) {
        Map.Entry<String, JsonNode> entry = tradesResultIterator.next();
        Integer key = Integer.valueOf(entry.getKey());
        JsonNode value = entry.getValue();

        List<CryptowatchOHLC> ohlcs = new ArrayList<>();
        for (JsonNode jsonSpreadNode : value) {
          long time = jsonSpreadNode.path(0).asLong();
          BigDecimal open = new BigDecimal(jsonSpreadNode.path(1).asText());
          BigDecimal high = new BigDecimal(jsonSpreadNode.path(2).asText());
          BigDecimal low = new BigDecimal(jsonSpreadNode.path(3).asText());
          BigDecimal close = new BigDecimal(jsonSpreadNode.path(4).asText());
          BigDecimal volume = new BigDecimal(jsonSpreadNode.path(5).asText());
          BigDecimal vwap = new BigDecimal(jsonSpreadNode.path(6).asText());
          long count = jsonSpreadNode.path(7).asLong();
          ohlcs.add(new CryptowatchOHLC(time, open, high, low, close, vwap, volume, count));
        }
        cwOHLCs.put(key, ohlcs);
      }
      return new CryptowatchOHLCs(cwOHLCs);
    }
  }
}
