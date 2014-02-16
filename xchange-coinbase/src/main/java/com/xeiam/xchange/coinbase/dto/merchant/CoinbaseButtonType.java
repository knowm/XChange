package com.xeiam.xchange.coinbase.dto.merchant;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xeiam.xchange.coinbase.dto.EnumFromStringHelper;
import com.xeiam.xchange.coinbase.dto.EnumLowercaseJsonSerializer;
import com.xeiam.xchange.coinbase.dto.merchant.CoinbaseButtonType.CoinbaseButtonTypeDeserializer;


@JsonDeserialize(using = CoinbaseButtonTypeDeserializer.class)
@JsonSerialize(using = EnumLowercaseJsonSerializer.class)
public enum CoinbaseButtonType {

  BUY_NOW, DONATION, SUBSCRIPTION;

  static class CoinbaseButtonTypeDeserializer extends JsonDeserializer<CoinbaseButtonType> {

    private static final EnumFromStringHelper<CoinbaseButtonType> FROM_STRING_HELPER = new EnumFromStringHelper<CoinbaseButtonType>(CoinbaseButtonType.class);

    @Override
    public CoinbaseButtonType deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {

      ObjectCodec oc = jsonParser.getCodec();
      JsonNode node = oc.readTree(jsonParser);
      String jsonString = node.textValue();
      return FROM_STRING_HELPER.fromJsonString(jsonString);
    }
  }
}
