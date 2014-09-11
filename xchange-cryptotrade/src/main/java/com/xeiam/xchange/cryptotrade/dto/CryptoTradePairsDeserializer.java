package com.xeiam.xchange.cryptotrade.dto;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.utils.jackson.CurrencyPairDeserializer;

public class CryptoTradePairsDeserializer extends JsonDeserializer<Map<CurrencyPair, CryptoTradePair>> {

  @Override
  public Map<CurrencyPair, CryptoTradePair> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

    final ObjectCodec oc = jp.getCodec();
    final JsonNode rootNode = oc.readTree(jp);
    final ArrayNode currencyPairs = (ArrayNode) rootNode.path("currency_pairs");
    final ArrayNode securityPairs = (ArrayNode) rootNode.path("security_pairs");

    final Map<CurrencyPair, CryptoTradePair> pairs = new HashMap<CurrencyPair, CryptoTradePair>();
    for (JsonNode pairInfo : currencyPairs) {
      final CryptoTradePairType type = CryptoTradePairType.normal_pair;
      String label = pairInfo.fieldNames().next();
      final CurrencyPair pair = CurrencyPairDeserializer.getCurrencyPairFromString(label);
      final CryptoTradePair info = CryptoTradePairDeserializer.getPairFromJsonNode(pairInfo.elements().next());
      final BigDecimal minOrderAMount = info.getMinOrderAmount();
      final int decimals = info.getDecimals();
      pairs.put(pair, new CryptoTradePair(label, type, minOrderAMount, decimals));
    }
    for (JsonNode pairInfo : securityPairs) {
      /*
       * XXX: Currently, no information returned for security_pairs
       */
      final CryptoTradePairType type = CryptoTradePairType.security_pair;
      final String label = pairInfo.asText();
      final CurrencyPair pair = CurrencyPairDeserializer.getCurrencyPairFromString(label);
      pairs.put(pair, new CryptoTradePair(label, type, null, 0));
    }
    return pairs;
  }

}
