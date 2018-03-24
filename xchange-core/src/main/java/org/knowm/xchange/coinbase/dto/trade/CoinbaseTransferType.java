package org.knowm.xchange.coinbase.dto.trade;

import java.io.IOException;

import org.knowm.xchange.coinbase.dto.serialization.EnumFromStringHelper;
import org.knowm.xchange.coinbase.dto.serialization.EnumLowercaseJsonSerializer;
import org.knowm.xchange.coinbase.dto.trade.CoinbaseTransferType.CoinbaseTransferTypeDeserializer;

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
@JsonDeserialize(using = CoinbaseTransferTypeDeserializer.class)
@JsonSerialize(using = EnumLowercaseJsonSerializer.class)
public enum CoinbaseTransferType {

  BUY, SELL;

  static class CoinbaseTransferTypeDeserializer extends JsonDeserializer<CoinbaseTransferType> {

    private static final EnumFromStringHelper<CoinbaseTransferType> FROM_STRING_HELPER = new EnumFromStringHelper<>(CoinbaseTransferType.class);

    @Override
    public CoinbaseTransferType deserialize(JsonParser jsonParser, final DeserializationContext ctxt) throws IOException, JsonProcessingException {

      final ObjectCodec oc = jsonParser.getCodec();
      final JsonNode node = oc.readTree(jsonParser);
      final String jsonString = node.textValue();
      return FROM_STRING_HELPER.fromJsonString(jsonString);
    }
  }
}
