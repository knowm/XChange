package org.knowm.xchange.coinmarketcap.dto.marketdata;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@JsonDeserialize(using = CoinMarketCapArrayData.CoinMarketCapTickersDeserializer.class)
public class CoinMarketCapArrayData<T> {

  private List<T> data;

  private CoinMarketCapArrayData(List<T> data) {

    this.data = data;
  }

  public List<T> getData() {
    return data;
  }

  static class CoinMarketCapTickersDeserializer<T>
      extends JsonDeserializer<CoinMarketCapArrayData<CoinMarketCapTicker>> {

    @Override
    public CoinMarketCapArrayData<CoinMarketCapTicker> deserialize(
        JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

      ObjectCodec oc = jp.getCodec();
      JsonNode node = oc.readTree(jp);

      if (node.isObject()) {
        List<CoinMarketCapTicker> tickers = new LinkedList<>();
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(
            CoinMarketCapTicker.class, new CoinMarketCapTicker.CoinMarketCapTickerDeserializer());
        mapper.registerModule(module);
        for (JsonNode child : node.get("data")) {
          tickers.add(mapper.treeToValue(child, CoinMarketCapTicker.class));
        }

        return new CoinMarketCapArrayData<>(tickers);
      }
      return null;
    }
  }
}
