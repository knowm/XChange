package info.bitrich.xchangestream.bitmex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Created by Lukas Zaoralek on 13.11.17. */
public class BitmexWebSocketTransaction {
  private static final Logger log = LoggerFactory.getLogger(BitmexWebSocketTransaction.class);
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
        log.error("limit order mapping exception", e);
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
      log.error("ticker mapping exception", e);
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
        log.error("trade array mapping exception", e);
      }
    }

    return trades;
  }

  public BitmexOrder[] toBitmexOrders() {
    BitmexOrder[] orders = new BitmexOrder[this.data.size()];
    for (int i = 0; i < this.data.size(); ++i) {
      JsonNode jsonOrder = this.data.get(i);

      try {
        orders[i] = mapper.treeToValue(jsonOrder, BitmexOrder.class);
      } catch (IOException e) {
        log.error("orders mapping exception", e);
      }
    }

    return orders;
  }

  public BitmexFunding toBitmexFunding() {
    BitmexFunding funding = null;
    try {
      funding = mapper.treeToValue(this.data.get(0), BitmexFunding.class);
    } catch (IOException e) {
      log.error("funding mapping exception", e);
    }
    return funding;
  }

  public RawOrderBook toRawOrderBook() {
    try {
      return mapper.treeToValue(this.data.get(0), RawOrderBook.class);
    } catch (JsonProcessingException e) {
      log.error("raw order book mapping exception", e);
      return null;
    }
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
