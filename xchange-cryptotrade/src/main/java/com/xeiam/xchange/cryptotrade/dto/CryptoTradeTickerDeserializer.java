package com.xeiam.xchange.cryptotrade.dto;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xeiam.xchange.cryptotrade.dto.marketdata.CryptoTradeTicker;

public class CryptoTradeTickerDeserializer extends JsonDeserializer<CryptoTradeTicker> {

  private static BigDecimal getNumberIfPresent(String numberString) {

    return numberString.isEmpty() ? null : new BigDecimal(numberString);
  }

  private static CryptoTradeTicker getTickerFromJsonNode(JsonNode tickerDataNode, JsonNode statusNode) {

    final BigDecimal last = getNumberIfPresent(tickerDataNode.path("last").asText());
    final BigDecimal low = getNumberIfPresent(tickerDataNode.path("low").asText());
    final BigDecimal high = getNumberIfPresent(tickerDataNode.path("high").asText());
    final BigDecimal minAsk = getNumberIfPresent(tickerDataNode.path("min_ask").asText());
    final BigDecimal maxBid = getNumberIfPresent(tickerDataNode.path("max_bid").asText());
    BigDecimal volumeTradeCurrency = null;
    BigDecimal volumePriceCurrency = null;
    if (tickerDataNode instanceof ObjectNode) {
      final ObjectNode tickerDataObjectNode = (ObjectNode) tickerDataNode;
      final Iterator<Entry<String, JsonNode>> tickerDataFields = tickerDataObjectNode.fields();
      while (tickerDataFields.hasNext()) {
        final Entry<String, JsonNode> tickerDataEntry = tickerDataFields.next();
        if (tickerDataEntry.getKey().startsWith("vol_")) {
          if (volumeTradeCurrency == null)
            volumeTradeCurrency = getNumberIfPresent(tickerDataEntry.getValue().asText());
          else {
            volumePriceCurrency = getNumberIfPresent(tickerDataEntry.getValue().asText());
            break;
          }
        }
      }
    }

    final String status = statusNode != null ? statusNode.path("status").asText() : null;
    final String error = statusNode != null ? statusNode.path("error").asText() : null;

    return new CryptoTradeTicker(last, low, high, volumeTradeCurrency, volumePriceCurrency, minAsk, maxBid, status, error);
  }

  public static CryptoTradeTicker getTickerFromJsonNode(JsonNode tickerDataNode) {

    return getTickerFromJsonNode(tickerDataNode, null);
  }

  @Override
  public CryptoTradeTicker deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

    final ObjectCodec oc = jp.getCodec();
    final JsonNode statusNode = oc.readTree(jp);
    final JsonNode tickerDataParentNode = statusNode.path("data");

    return getTickerFromJsonNode(tickerDataParentNode, statusNode);
  }

}