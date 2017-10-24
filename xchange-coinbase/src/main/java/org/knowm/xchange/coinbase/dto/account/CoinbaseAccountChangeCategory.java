package org.knowm.xchange.coinbase.dto.account;

import java.io.IOException;

import org.knowm.xchange.coinbase.dto.account.CoinbaseAccountChangeCategory.CoinbaseCategoryDeserializer;
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
@JsonDeserialize(using = CoinbaseCategoryDeserializer.class)
@JsonSerialize(using = EnumLowercaseJsonSerializer.class)
public enum CoinbaseAccountChangeCategory {

  TX, REQUEST, TRANSFER, INVOICE;

  static class CoinbaseCategoryDeserializer extends JsonDeserializer<CoinbaseAccountChangeCategory> {

    private static final EnumFromStringHelper<CoinbaseAccountChangeCategory> FROM_STRING_HELPER = new EnumFromStringHelper<>(
        CoinbaseAccountChangeCategory.class);

    @Override
    public CoinbaseAccountChangeCategory deserialize(JsonParser jsonParser,
        final DeserializationContext ctxt) throws IOException, JsonProcessingException {

      final ObjectCodec oc = jsonParser.getCodec();
      final JsonNode node = oc.readTree(jsonParser);
      final String jsonString = node.textValue();
      return FROM_STRING_HELPER.fromJsonString(jsonString);
    }
  }
}
