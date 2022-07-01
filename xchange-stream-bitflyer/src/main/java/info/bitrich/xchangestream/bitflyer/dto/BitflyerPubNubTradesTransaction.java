package info.bitrich.xchangestream.bitflyer.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** Created by Lukas Zaoralek on 15.11.17. */
public class BitflyerPubNubTradesTransaction {
  private final JsonNode jsonTrades;
  private final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

  public BitflyerPubNubTradesTransaction(JsonNode jsonTrades) {
    this.jsonTrades = jsonTrades;
  }

  public JsonNode getJsonTrades() {
    return jsonTrades;
  }

  public List<BitflyerTrade> toBitflyerTrades() {
    List<BitflyerTrade> trades = new ArrayList<>(jsonTrades.size());
    if (jsonTrades.isArray()) {
      for (JsonNode jsonTrade : jsonTrades) {
        BitflyerTrade trade = null;
        try {
          trade = mapper.treeToValue(jsonTrade, BitflyerTrade.class);
        } catch (IOException e) {
          e.printStackTrace();
        }
        trades.add(trade);
      }
    }

    return trades;
  }
}
