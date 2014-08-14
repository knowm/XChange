package com.xeiam.xchange.cryptotrade.dto;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeOrderType.CryptTradeOrderTypeDeserializer;

@JsonDeserialize(using = CryptTradeOrderTypeDeserializer.class)
public enum CryptoTradeOrderType {

  Buy, Sell;

  static class CryptTradeOrderTypeDeserializer extends JsonDeserializer<CryptoTradeOrderType> {

    @Override
    public CryptoTradeOrderType deserialize(final JsonParser jsonParser, final DeserializationContext ctxt) throws IOException, JsonProcessingException {

      final ObjectCodec oc = jsonParser.getCodec();
      final JsonNode node = oc.readTree(jsonParser);
      final String orderType = node.asText();
      return CryptoTradeOrderType.valueOf(orderType);
    }
  }
}
