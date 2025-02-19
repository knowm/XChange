package info.bitrich.xchangestream.bitmex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.bitmex.config.Config;
import java.io.IOException;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Builder
@Jacksonized
public class BitmexWebSocketTransaction {

  private static final ObjectMapper mapper = Config.getInstance().getObjectMapper();

  @JsonProperty("table")
  private String table;

  @JsonProperty("action")
  private String action;

  @JsonProperty("data")
  private JsonNode data;

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

  public BitmexPrivateExecution[] toBitmexPrivateExecutions() {
    try {
      return mapper.treeToValue(data, BitmexPrivateExecution[].class);
    } catch (IOException e) {
      log.error("execution array mapping exception", e);
    }
    return new BitmexPrivateExecution[]{};
  }

  public BitmexPosition[] toBitmexPositions() {
    try {
      return mapper.treeToValue(data, BitmexPosition[].class);
    } catch (IOException e) {
      log.error("position array mapping exception", e);
    }
    return new BitmexPosition[]{};
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

}
