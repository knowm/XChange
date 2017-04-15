package org.knowm.xchange.coinbase.dto.merchant;

import java.io.IOException;

import org.knowm.xchange.coinbase.dto.merchant.CoinbaseButtonStyle.CoinbaseButtonStyleDeserializer;
import org.knowm.xchange.coinbase.dto.serialization.EnumFromStringHelper;
import org.knowm.xchange.coinbase.dto.serialization.EnumLowercaseJsonSerializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author jamespedwards42
 */
@JsonDeserialize(using = CoinbaseButtonStyleDeserializer.class)
@JsonSerialize(using = EnumLowercaseJsonSerializer.class)
public enum CoinbaseButtonStyle {

  BUY_NOW_LARGE, BUY_NOW_SMALL, DONATION_LARGE, DONATION_SMALL, SUBSCRIPTION_LARGE, SUBSCRIPTION_SMALL, CUSTOM_LARGE, CUSTOM_SMALL, NONE;

  static class CoinbaseButtonStyleDeserializer extends JsonDeserializer<CoinbaseButtonStyle> {

    private static final EnumFromStringHelper<CoinbaseButtonStyle> FROM_STRING_HELPER = new EnumFromStringHelper<>(CoinbaseButtonStyle.class);

    @Override
    public CoinbaseButtonStyle deserialize(JsonParser jsonParser, final DeserializationContext ctxt) throws IOException, JsonProcessingException {

      final ObjectCodec oc = jsonParser.getCodec();
      final JsonNode node = oc.readTree(jsonParser);
      final String jsonString = node.textValue();
      return FROM_STRING_HELPER.fromJsonString(jsonString);
    }
  }
}
