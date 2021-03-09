package org.knowm.xchange.bitmex.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class BitmexDecimalDeserializer extends NumberDeserializers.BigDecimalDeserializer {
  private static final BigDecimal SATOSHIS_BY_BTC = BigDecimal.valueOf(100_000_000L);
  private static final int SCALE = 8;

  @Override
  public BigDecimal deserialize(final JsonParser p, final DeserializationContext ctxt)
      throws IOException {
    final BigDecimal decimalAsInteger = super.deserialize(p, ctxt);
    return decimalAsInteger.divide(SATOSHIS_BY_BTC, SCALE, RoundingMode.UNNECESSARY);
  }
}
