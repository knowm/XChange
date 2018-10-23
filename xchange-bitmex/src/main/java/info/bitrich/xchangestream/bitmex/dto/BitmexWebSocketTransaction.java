package info.bitrich.xchangestream.bitmex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by Lukas Zaoralek on 13.11.17.
 */
public class BitmexWebSocketTransaction {
    private final String table;
    private final String action;
    private final JsonNode data;
    private final ObjectMapper mapper = new ObjectMapper();

    public BitmexWebSocketTransaction(@JsonProperty("table") String table,
                                      @JsonProperty("action") String action,
                                      @JsonProperty("data") JsonNode data) {
        this.table = table;
        this.action = action;
        this.data = data;
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public BitmexLimitOrder[] toBitmexOrderbookLevels() {
        BitmexLimitOrder[] levels = new BitmexLimitOrder[data.size()];
        for (int i = 0; i < data.size(); i++) {
            JsonNode jsonLevel = data.get(i);
            try {
                levels[i] = mapper.readValue(jsonLevel.toString(), BitmexLimitOrder.class);
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
            bitmexTicker = mapper.readValue(data.get(0).toString(), BitmexTicker.class);
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
                trades[i] = mapper.readValue(jsonTrade.toString(), BitmexTrade.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return trades;
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
