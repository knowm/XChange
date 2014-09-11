package com.xeiam.xchange.cryptotrade.dto;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class CryptoTradePairDeserializer extends JsonDeserializer<CryptoTradePair> {

  private static CryptoTradePair getPairFromJsonNode(JsonNode pairDataNode, JsonNode statusNode) {

    /*
     * 'getpairs' and 'getpair' API methods
     */
    final int decimals = pairDataNode.get("decimal_places").asInt();
    final BigDecimal minOrderAmount = new BigDecimal(pairDataNode.get("min_amount").asText());
    /*
     * Only 'getpair' method
     */
    final String label = pairDataNode.has("label") ? pairDataNode.get("label").asText() : null;
    final CryptoTradePairType type = pairDataNode.has("type")
        ? CryptoTradePairType.valueOf(pairDataNode.get("type").asText())
        : null;

    final String status = statusNode != null ? statusNode.path("status").asText() : null;
    final String error = statusNode != null ? statusNode.path("error").asText() : null;

    return new CryptoTradePair(label, type, minOrderAmount, decimals, status, error);
  }

  public static CryptoTradePair getPairFromJsonNode(JsonNode pairDataNode) {

    return getPairFromJsonNode(pairDataNode, null);
  }

  @Override
  public CryptoTradePair deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

    final ObjectCodec oc = jp.getCodec();
    final JsonNode statusNode = oc.readTree(jp);
    final JsonNode pairDataNode = statusNode.path("data");

    return getPairFromJsonNode(pairDataNode, statusNode);
  }

}