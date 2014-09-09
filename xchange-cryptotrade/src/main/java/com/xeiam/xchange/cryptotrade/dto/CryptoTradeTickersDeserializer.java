package com.xeiam.xchange.cryptotrade.dto;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradeTicker;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.utils.jackson.CurrencyPairDeserializer;

public class CryptoTradeTickersDeserializer extends JsonDeserializer<Map<CurrencyPair, CryptoTradeTicker>> {

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
        final CryptoTradeTicker ticker = CryptoTradeTickerDeserializer.getTickerFromJsonNode(tickerData.getValue());
        tickers.put(pair, ticker);
      }
    }
    return tickers;
  }

}
