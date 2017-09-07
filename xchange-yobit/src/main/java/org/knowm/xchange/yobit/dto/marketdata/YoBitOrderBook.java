package org.knowm.xchange.yobit.dto.marketdata;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.knowm.xchange.yobit.dto.marketdata.YoBitOrderBook.YoBitOrderBookDeserializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@JsonDeserialize(using = YoBitOrderBookDeserializer.class)
public class YoBitOrderBook {

  private List<YoBitAsksBidsData> asks;
  private List<YoBitAsksBidsData> bids;

  public YoBitOrderBook(List<YoBitAsksBidsData> asks, List<YoBitAsksBidsData> bids) {
    this.asks = asks;
    this.bids = bids;
  }

  public List<YoBitAsksBidsData> getAsks() {
    return asks;
  }

  public List<YoBitAsksBidsData> getBids() {
    return bids;
  }

  static class YoBitOrderBookDeserializer extends JsonDeserializer<YoBitOrderBook> {

    @Override
    public YoBitOrderBook deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

      ObjectCodec oc = jp.getCodec();
      JsonNode node = oc.readTree(jp);

      List<YoBitAsksBidsData> asks = parse(node.get("asks"));
      List<YoBitAsksBidsData> bids = parse(node.get("bids"));

      return new YoBitOrderBook(asks, bids);
    }

    private List<YoBitAsksBidsData> parse(JsonNode nodeArray) {
      List<YoBitAsksBidsData> res = new ArrayList<>();

      if(nodeArray != null) {
        for (JsonNode jsonNode : nodeArray) {
          res.add(new YoBitAsksBidsData(BigDecimal.valueOf(jsonNode.get(1).asDouble()), BigDecimal.valueOf(jsonNode.get(0).asDouble())));
        }
      }

      return res;
    }
  }
}