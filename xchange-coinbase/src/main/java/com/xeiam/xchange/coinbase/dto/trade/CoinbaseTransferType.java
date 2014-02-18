package com.xeiam.xchange.coinbase.dto.trade;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xeiam.xchange.coinbase.dto.serialization.EnumFromStringHelper;
import com.xeiam.xchange.coinbase.dto.serialization.EnumLowercaseJsonSerializer;
import com.xeiam.xchange.coinbase.dto.trade.CoinbaseTransferType.CoinbaseTransferTypeDeserializer;

@JsonDeserialize(using = CoinbaseTransferTypeDeserializer.class)
@JsonSerialize(using = EnumLowercaseJsonSerializer.class)
public enum CoinbaseTransferType {

  BUY, SELL;

  static class CoinbaseTransferTypeDeserializer extends JsonDeserializer<CoinbaseTransferType> {

    private static final EnumFromStringHelper<CoinbaseTransferType> FROM_STRING_HELPER = new EnumFromStringHelper<CoinbaseTransferType>(CoinbaseTransferType.class);

    @Override
    public CoinbaseTransferType deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {

      ObjectCodec oc = jsonParser.getCodec();
      JsonNode node = oc.readTree(jsonParser);
      String jsonString = node.textValue();
      return FROM_STRING_HELPER.fromJsonString(jsonString);
    }
  }
}
