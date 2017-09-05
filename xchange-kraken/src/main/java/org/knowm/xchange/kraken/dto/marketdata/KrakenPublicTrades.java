package org.knowm.xchange.kraken.dto.marketdata;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.knowm.xchange.kraken.dto.marketdata.KrakenPublicTrades.KrakenTradesDeserializer;
import org.knowm.xchange.kraken.dto.trade.KrakenOrderType;
import org.knowm.xchange.kraken.dto.trade.KrakenType;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = KrakenTradesDeserializer.class)
public class KrakenPublicTrades {

  private final List<KrakenPublicTrade> trades;
  private final long last;

  public KrakenPublicTrades(List<KrakenPublicTrade> trades, long last) {

    this.trades = trades;
    this.last = last;
  }

  public long getLast() {

    return last;
  }

  public List<KrakenPublicTrade> getTrades() {

    return trades;
  }

  @Override
  public String toString() {

    return "KrakenTrades [trades=" + trades + ", last=" + last + "]";
  }

  static class KrakenTradesDeserializer extends JsonDeserializer<KrakenPublicTrades> {

    @Override
    public KrakenPublicTrades deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {

      List<KrakenPublicTrade> krakenTrades = new ArrayList<>();
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
          for (JsonNode tradeJsonNode : value) {
            BigDecimal price = new BigDecimal(tradeJsonNode.path(0).asText());
            BigDecimal volume = new BigDecimal(tradeJsonNode.path(1).asText());
            double time = tradeJsonNode.path(2).asDouble();
            KrakenType type = KrakenType.fromString(tradeJsonNode.path(3).asText());
            KrakenOrderType orderType = KrakenOrderType.fromString(tradeJsonNode.path(4).asText());
            String miscellaneous = tradeJsonNode.path(5).asText();

            krakenTrades.add(new KrakenPublicTrade(price, volume, time, type, orderType, miscellaneous));
          }
        }
      }
      return new KrakenPublicTrades(krakenTrades, last);
    }

  }
}
