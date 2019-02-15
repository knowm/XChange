package org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CoinMarketCapStatus;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CoinMarketCapTicker;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class CoinMarketCapTickerResponse {

  private final Map<String, CoinMarketCapTicker> tickerData;
  private final CoinMarketCapStatus status;

  public CoinMarketCapTickerResponse(
      @JsonProperty("data")
          @JsonDeserialize(
              using = CoinMarketCapTickerDataDeserializer.class)
              Map<String, CoinMarketCapTicker> tickerData,
      @JsonProperty("status") CoinMarketCapStatus status) {
    this.tickerData = tickerData;
    this.status = status;
  }

  public Map<String, CoinMarketCapTicker> getTickerData() {
    return tickerData;
  }

  public CoinMarketCapStatus getStatus() {
    return status;
  }

  @Override
  public String toString() {
    return "CoinMarketCapCurrencyInfoResponse{"
        + "tickerData="
        + tickerData
        + ", status="
        + status
        + '}';
  }

  public static class CoinMarketCapTickerDataDeserializer
          extends JsonDeserializer<Map<String, CoinMarketCapTicker>> {

    @Override
    public Map<String, CoinMarketCapTicker> deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
      JsonNode jsonNode = jp.getCodec().readTree(jp);
      return deserializeFromNode(jsonNode);
    }

    static Map<String, CoinMarketCapTicker> deserializeFromNode(JsonNode jsonNode)
            throws IOException {
      Iterator<Map.Entry<String, JsonNode>> iterator = jsonNode.fields();
      Map<String, CoinMarketCapTicker> tickerData = new HashMap<>();
      ObjectMapper mapper = new ObjectMapper();
      while (iterator.hasNext()) {
        Map.Entry<String, JsonNode> entry = iterator.next();
        CoinMarketCapTicker ticker =
                mapper.readValue(entry.getValue().toString(), CoinMarketCapTicker.class);
        tickerData.put(entry.getKey(), ticker);
      }
      return tickerData;
    }
  }
}
