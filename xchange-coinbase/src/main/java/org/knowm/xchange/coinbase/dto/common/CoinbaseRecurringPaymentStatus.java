package org.knowm.xchange.coinbase.dto.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.IOException;
import org.knowm.xchange.coinbase.dto.common.CoinbaseRecurringPaymentStatus.CoinbaseRecurringPaymentStatusDeserializer;
import org.knowm.xchange.coinbase.dto.serialization.EnumFromStringHelper;
import org.knowm.xchange.coinbase.dto.serialization.EnumLowercaseJsonSerializer;

/** @author jamespedwards42 */
@JsonDeserialize(using = CoinbaseRecurringPaymentStatusDeserializer.class)
@JsonSerialize(using = EnumLowercaseJsonSerializer.class)
public enum CoinbaseRecurringPaymentStatus {
  NEW,
  ACTIVE,
  PAUSED,
  COMPLETED,
  CANCELED;

  static class CoinbaseRecurringPaymentStatusDeserializer
      extends JsonDeserializer<CoinbaseRecurringPaymentStatus> {

    private static final EnumFromStringHelper<CoinbaseRecurringPaymentStatus> FROM_STRING_HELPER =
        new EnumFromStringHelper<>(CoinbaseRecurringPaymentStatus.class);

    @Override
    public CoinbaseRecurringPaymentStatus deserialize(
        JsonParser jsonParser, final DeserializationContext ctxt)
        throws IOException, JsonProcessingException {

      final ObjectCodec oc = jsonParser.getCodec();
      final JsonNode node = oc.readTree(jsonParser);
      final String jsonString = node.textValue();
      return FROM_STRING_HELPER.fromJsonString(jsonString);
    }
  }
}
