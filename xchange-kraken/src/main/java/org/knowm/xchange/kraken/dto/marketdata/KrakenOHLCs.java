package org.knowm.xchange.kraken.dto.marketdata;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/** Data object representing DHLCs from Kraken */
@JsonDeserialize(using = KrakenOHLCs.KrakenOHLCsDeserializer.class)
public class KrakenOHLCs {

  private final List<KrakenOHLC> OHLCs;
  private final long last;

  public KrakenOHLCs(List<KrakenOHLC> OHLCs, long last) {
    this.OHLCs = OHLCs;
    this.last = last;
  }

  public long getLast() {
    return last;
  }

  public List<KrakenOHLC> getOHLCs() {
    return OHLCs;
  }

  @Override
  public String toString() {
    String result = "KrakenOHLCs";
    result += " last=" + last + "]";

    for (KrakenOHLC krakenOHLC : getOHLCs()) {
      result += "[OHLCs=" + krakenOHLC + "]";
    }
    return result;
  }

  static class KrakenOHLCsDeserializer extends JsonDeserializer<KrakenOHLCs> {

    @Override
    public KrakenOHLCs deserialize(JsonParser jsonParser, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {
      List<KrakenOHLC> krakenOHLCs = new ArrayList<>();
      long last = 0;
      ObjectCodec oc = jsonParser.getCodec();
      JsonNode node = oc.readTree(jsonParser);

      Iterator<Map.Entry<String, JsonNode>> tradesResultIterator = node.fields();
      while (tradesResultIterator.hasNext()) {
        Map.Entry<String, JsonNode> entry = tradesResultIterator.next();
        String key = entry.getKey();
        JsonNode value = entry.getValue();

        if (key == "last") {
          last = value.asLong();
        } else if (value.isArray()) {
          for (JsonNode jsonSpreadNode : value) {
            long time = jsonSpreadNode.path(0).asLong();
            BigDecimal open = new BigDecimal(jsonSpreadNode.path(1).asText());
            BigDecimal high = new BigDecimal(jsonSpreadNode.path(2).asText());
            BigDecimal low = new BigDecimal(jsonSpreadNode.path(3).asText());
            BigDecimal close = new BigDecimal(jsonSpreadNode.path(4).asText());
            BigDecimal vwap = new BigDecimal(jsonSpreadNode.path(5).asText());
            BigDecimal volume = new BigDecimal(jsonSpreadNode.path(6).asText());
            long count = jsonSpreadNode.path(7).asLong();
            krakenOHLCs.add(new KrakenOHLC(time, open, high, low, close, vwap, volume, count));
          }
        }
      }
      return new KrakenOHLCs(krakenOHLCs, last);
    }
  }
}
