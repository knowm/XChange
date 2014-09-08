package com.xeiam.xchange.cryptotrade.dto.marketdata;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeBaseResponse;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeTickersDeserializer;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.utils.jackson.CurrencyPairDeserializer;

public class CryptoTradeTickers extends CryptoTradeBaseResponse {

  private final Map<CurrencyPair, CryptoTradeTicker> tickers;

  public CryptoTradeTickers(@JsonProperty("status") String status, @JsonProperty("error") String error,
		  @JsonProperty("data") @JsonDeserialize(using = CryptoTradeTickersDataDeserializer.class) Map<CurrencyPair, CryptoTradeTicker> tickers) {

    super(status, error);
    this.tickers = tickers;
  }

  public Map<CurrencyPair, CryptoTradeTicker> getTickers() {

    return tickers;
  }

  @Override
  public String toString() {

    return "CryptoTradeTickers [tickers=" + getTickers() + "]";
  }

  public static class CryptoTradeTickersDataDeserializer extends JsonDeserializer<Map<CurrencyPair, CryptoTradeTicker>> {

    @Override
    public Map<CurrencyPair, CryptoTradeTicker> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

      final ObjectCodec oc = jp.getCodec();
      final JsonNode rootNode = oc.readTree(jp);

      final Map<CurrencyPair, CryptoTradeTicker> tickers = new HashMap<CurrencyPair, CryptoTradeTicker>();
      if (rootNode.size() == 1) {
        final ObjectNode dataNode = (ObjectNode) rootNode.get(0);
        final Iterator<Entry<String, JsonNode>> tickersArray = dataNode.fields();
        while (tickersArray.hasNext()) {
          final Entry<String, JsonNode> tickerData = tickersArray.next();
          final CurrencyPair pair = CurrencyPairDeserializer.getCurrencyPairFromString(tickerData.getKey());
          final CryptoTradeTicker ticker = CryptoTradeTickersDeserializer.getTickerFromJsonNode(tickerData.getValue());
          tickers.put(pair, ticker);
        }
      }
      return tickers;
    }
  }

}