package org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@JsonDeserialize(using = CoinMarketCapCurrencyData.CoinMarketCapCurrencyDataDeserializer.class)
public class CoinMarketCapCurrencyData {

  @JsonIgnore
  Map<String, CoinMarketCapCurrencyInfo> currencyMap;

  public CoinMarketCapCurrencyData(Map<String, CoinMarketCapCurrencyInfo> currencyMap) {
    this.currencyMap = currencyMap;
  }

  public Map<String, CoinMarketCapCurrencyInfo> getCurrencyMap() {
    return currencyMap;
  }

  public void setCurrencyMap(Map<String, CoinMarketCapCurrencyInfo> currencyMap) {
    this.currencyMap = currencyMap;
  }

  @Override
  public String toString() {
    return "CoinMarketCapCurrencyData{" + "currencyMap=" + currencyMap + '}';
  }

  public static class CoinMarketCapCurrencyDataDeserializer
      extends JsonDeserializer<CoinMarketCapCurrencyData> {

    @Override
    public CoinMarketCapCurrencyData deserialize(JsonParser jp, DeserializationContext ctxt)
        throws IOException {
      JsonNode jsonNode = jp.getCodec().readTree(jp);
      return deserializeFromNode(jsonNode);
    }

    public static CoinMarketCapCurrencyData deserializeFromNode(JsonNode jsonNode)
        throws IOException {
      Iterator<Map.Entry<String, JsonNode>> iterator = jsonNode.fields();
      Map<String, CoinMarketCapCurrencyInfo> currencyMap = new HashMap<>();
      ObjectMapper mapper = new ObjectMapper();
      while (iterator.hasNext()) {
        Map.Entry<String, JsonNode> entry = iterator.next();
        CoinMarketCapCurrencyInfo currency =
            mapper.readValue(entry.getValue().toString(), CoinMarketCapCurrencyInfo.class);
        currencyMap.put(entry.getKey(), currency);
      }
      return new CoinMarketCapCurrencyData(currencyMap);
    }
  }
}
