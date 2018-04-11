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
import java.util.Map.Entry;
import org.knowm.xchange.kraken.dto.marketdata.KrakenSpreads.KrakenSpreadsDeserializer;

@JsonDeserialize(using = KrakenSpreadsDeserializer.class)
public class KrakenSpreads {

  private final List<KrakenSpread> spreads;
  private final long last;

  public KrakenSpreads(List<KrakenSpread> spreads, long last) {

    this.spreads = spreads;
    this.last = last;
  }

  public long getLast() {

    return last;
  }

  public List<KrakenSpread> getSpreads() {

    return spreads;
  }

  @Override
  public String toString() {

    return "KrakenSpreads [spreads=" + spreads + ", last=" + last + "]";
  }

  static class KrakenSpreadsDeserializer extends JsonDeserializer<KrakenSpreads> {

    @Override
    public KrakenSpreads deserialize(JsonParser jsonParser, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {

      List<KrakenSpread> krakenTrades = new ArrayList<>();
      long last = 0;
      ObjectCodec oc = jsonParser.getCodec();
      JsonNode node = oc.readTree(jsonParser);
      Iterator<Entry<String, JsonNode>> tradesResultIterator = node.fields();
      while (tradesResultIterator.hasNext()) {
        Entry<String, JsonNode> entry = tradesResultIterator.next();
        String key = entry.getKey();
        JsonNode value = entry.getValue();
        if (key == "last") {
          last = value.asLong();
        } else if (value.isArray()) {
          for (JsonNode jsonSpreadNode : value) {
            long time = jsonSpreadNode.path(0).asLong();
            BigDecimal bid = new BigDecimal(jsonSpreadNode.path(1).asText());
            BigDecimal ask = new BigDecimal(jsonSpreadNode.path(2).asText());

            krakenTrades.add(new KrakenSpread(time, bid, ask));
          }
        }
      }
      return new KrakenSpreads(krakenTrades, last);
    }
  }
}
