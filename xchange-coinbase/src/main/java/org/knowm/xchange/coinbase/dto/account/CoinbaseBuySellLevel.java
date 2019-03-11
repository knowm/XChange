package org.knowm.xchange.coinbase.dto.account;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.IOException;
import org.knowm.xchange.coinbase.dto.account.CoinbaseBuySellLevel.CoinbaseBuySellLevelDeserializer;
import org.knowm.xchange.coinbase.dto.serialization.EnumFromStringHelper;
import org.knowm.xchange.coinbase.dto.serialization.EnumLowercaseJsonSerializer;

/** @author jamespedwards42 */
@JsonDeserialize(using = CoinbaseBuySellLevelDeserializer.class)
@JsonSerialize(using = EnumLowercaseJsonSerializer.class)
public enum CoinbaseBuySellLevel {
  ONE,
  TWO,
  THREE;

  static class CoinbaseBuySellLevelDeserializer extends JsonDeserializer<CoinbaseBuySellLevel> {

    private static final EnumFromStringHelper<CoinbaseBuySellLevel> FROM_STRING_HELPER =
        new EnumFromStringHelper<>(CoinbaseBuySellLevel.class)
            .addJsonStringMapping("1", ONE)
            .addJsonStringMapping("2", TWO)
            .addJsonStringMapping("3", THREE);

    @Override
    public CoinbaseBuySellLevel deserialize(
        JsonParser jsonParser, final DeserializationContext ctxt)
        throws IOException, JsonProcessingException {

      final ObjectCodec oc = jsonParser.getCodec();
      final JsonNode node = oc.readTree(jsonParser);
      final int buySellLevel = node.asInt();
      return FROM_STRING_HELPER.fromJsonString(String.valueOf(buySellLevel));
    }
  }
}
