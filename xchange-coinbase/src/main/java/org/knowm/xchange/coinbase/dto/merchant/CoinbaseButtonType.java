package org.knowm.xchange.coinbase.dto.merchant;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.IOException;
import org.knowm.xchange.coinbase.dto.merchant.CoinbaseButtonType.CoinbaseButtonTypeDeserializer;
import org.knowm.xchange.coinbase.dto.serialization.EnumFromStringHelper;
import org.knowm.xchange.coinbase.dto.serialization.EnumLowercaseJsonSerializer;

/** @author jamespedwards42 */
@JsonDeserialize(using = CoinbaseButtonTypeDeserializer.class)
@JsonSerialize(using = EnumLowercaseJsonSerializer.class)
public enum CoinbaseButtonType {
  BUY_NOW,
  DONATION,
  SUBSCRIPTION;

  static class CoinbaseButtonTypeDeserializer extends JsonDeserializer<CoinbaseButtonType> {

    private static final EnumFromStringHelper<CoinbaseButtonType> FROM_STRING_HELPER =
        new EnumFromStringHelper<>(CoinbaseButtonType.class);

    @Override
    public CoinbaseButtonType deserialize(JsonParser jsonParser, final DeserializationContext ctxt)
        throws IOException, JsonProcessingException {

      final ObjectCodec oc = jsonParser.getCodec();
      final JsonNode node = oc.readTree(jsonParser);
      final String jsonString = node.textValue();
      return FROM_STRING_HELPER.fromJsonString(jsonString);
    }
  }
}
