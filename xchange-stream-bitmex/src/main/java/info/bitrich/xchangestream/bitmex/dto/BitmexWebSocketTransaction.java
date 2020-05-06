package info.bitrich.xchangestream.bitmex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import java.io.IOException;

/** Created by Lukas Zaoralek on 13.11.17. */
public class BitmexWebSocketTransaction {
  private static final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();
  private final String table;
  private final String action;
  private final JsonNode data;

  public BitmexWebSocketTransaction(
      @JsonProperty("table") String table,
      @JsonProperty("action") String action,
      @JsonProperty("data") JsonNode data) {
    this.table = table;
    this.action = action;
    this.data = data;
  }

  public BitmexLimitOrder[] toBitmexOrderbookLevels() {
    BitmexLimitOrder[] levels = new BitmexLimitOrder[data.size()];
    for (int i = 0; i < data.size(); i++) {
      JsonNode jsonLevel = data.get(i);
      try {
        levels[i] = mapper.treeToValue(jsonLevel, BitmexLimitOrder.class);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return levels;
  }

  public BitmexOrderbook toBitmexOrderbook() {
    BitmexLimitOrder[] levels = toBitmexOrderbookLevels();
    return new BitmexOrderbook(levels);
  }

  public BitmexTicker toBitmexTicker() {
    BitmexTicker bitmexTicker = null;
    try {
      bitmexTicker = mapper.treeToValue(data.get(0), BitmexTicker.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return bitmexTicker;
  }

  public BitmexTrade[] toBitmexTrades() {
    BitmexTrade[] trades = new BitmexTrade[data.size()];
    for (int i = 0; i < data.size(); i++) {
      JsonNode jsonTrade = data.get(i);
      try {
        trades[i] = mapper.treeToValue(jsonTrade, BitmexTrade.class);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return trades;
  }

  public BitmexOrder[] toBitmexOrders() {
    BitmexOrder[] orders = new BitmexOrder[this.data.size()];
    for (int i = 0; i < this.data.size(); ++i) {
      JsonNode jsonOrder = this.data.get(i);

      try {
        orders[i] = (BitmexOrder) this.mapper.readValue(jsonOrder.toString(), BitmexOrder.class);
      } catch (IOException var5) {
        var5.printStackTrace();
      }
    }

    return orders;
  }

  public BitmexFunding toBitmexFunding() {
    BitmexFunding funding = null;
    try {
      funding = this.mapper.readValue(this.data.get(0).toString(), BitmexFunding.class);
    } catch (IOException var5) {
      var5.printStackTrace();
    }
    return funding;
  }

  public String getTable() {
    return table;
  }

  public String getAction() {
    return action;
  }

  public JsonNode getData() {
    return data;
  }
}
