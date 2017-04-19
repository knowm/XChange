package org.knowm.xchange.coinbase.dto.auth;

import java.io.IOException;

import org.knowm.xchange.coinbase.dto.auth.CoinbaseScope.CoinbaseScopeDeserializer;
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
@JsonDeserialize(using = CoinbaseScopeDeserializer.class)
@JsonSerialize(using = EnumLowercaseJsonSerializer.class)
public enum CoinbaseScope {

  ALL, MERCHANT, BALANCE, USER, ADDRESSES, BUTTONS, BUY, SELL, CONTACTS, ORDERS, TRANSACTIONS, SEND, REQUEST, TRANSFERS, RECURRING_PAYMENTS;

  static class CoinbaseScopeDeserializer extends JsonDeserializer<CoinbaseScope> {

    private static final EnumFromStringHelper<CoinbaseScope> FROM_STRING_HELPER = new EnumFromStringHelper<>(CoinbaseScope.class);

    @Override
    public CoinbaseScope deserialize(JsonParser jsonParser, final DeserializationContext ctxt) throws IOException, JsonProcessingException {

      final ObjectCodec oc = jsonParser.getCodec();
      final JsonNode node = oc.readTree(jsonParser);
      final String jsonString = node.textValue();
      return FROM_STRING_HELPER.fromJsonString(jsonString);
    }
  }
}