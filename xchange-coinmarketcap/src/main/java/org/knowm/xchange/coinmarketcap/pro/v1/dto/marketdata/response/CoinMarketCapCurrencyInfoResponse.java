package org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CoinMarketCapCurrencyInfo;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CoinMarketCapStatus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class CoinMarketCapCurrencyInfoResponse {

  private final Map<String, CoinMarketCapCurrencyInfo> currencyData;
  private final CoinMarketCapStatus status;

  public CoinMarketCapCurrencyInfoResponse(
      @JsonProperty("data")
          @JsonDeserialize(
              using = CoinMarketCapCurrencyInfoResponse.CoinMarketCapCurrencyDataDeserializer.class)
              Map<String, CoinMarketCapCurrencyInfo> currencyData,
      @JsonProperty("status") CoinMarketCapStatus status) {
    this.currencyData = currencyData;
    this.status = status;
  }

  public Map<String, CoinMarketCapCurrencyInfo> getCurrencyData() {
    return currencyData;
  }

  public CoinMarketCapStatus getStatus() {
    return status;
  }

  @Override
  public String toString() {
    return "CoinMarketCapCurrencyInfoResponse{"
        + "currencyData="
        + currencyData
        + ", status="
        + status
        + '}';
  }

    public static class CoinMarketCapCurrencyDataDeserializer
            extends JsonDeserializer<Map<String, CoinMarketCapCurrencyInfo>> {

        @Override
        public Map<String, CoinMarketCapCurrencyInfo> deserialize(JsonParser jp, DeserializationContext ctxt)
                throws IOException {
            JsonNode jsonNode = jp.getCodec().readTree(jp);
            return deserializeFromNode(jsonNode);
        }

        static Map<String, CoinMarketCapCurrencyInfo> deserializeFromNode(JsonNode jsonNode)
                throws IOException {
            Iterator<Map.Entry<String, JsonNode>> iterator = jsonNode.fields();
            Map<String, CoinMarketCapCurrencyInfo> currencyData = new HashMap<>();
            ObjectMapper mapper = new ObjectMapper();
            while (iterator.hasNext()) {
                Map.Entry<String, JsonNode> entry = iterator.next();
                CoinMarketCapCurrencyInfo currency =
                        mapper.readValue(entry.getValue().toString(), CoinMarketCapCurrencyInfo.class);
                currencyData.put(entry.getKey(), currency);
            }
            return currencyData;
        }
    }
}
