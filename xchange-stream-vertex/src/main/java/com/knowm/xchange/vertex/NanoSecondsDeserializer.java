package com.knowm.xchange.vertex;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.Instant;

public class NanoSecondsDeserializer extends JsonDeserializer<Instant> {

  private static final BigDecimal NANOS_PER_MILLI = new BigDecimal(1000000);

  private static final CacheLoader<String, Instant> parser = new CacheLoader<>() {
    @Override
    public Instant load(String str) {
      BigInteger nano = new BigInteger(str);
      return Instant.ofEpochMilli(new BigDecimal(nano).divide(NANOS_PER_MILLI, RoundingMode.FLOOR).longValue());
    }
  };

  public static Instant parse(String str) {
    return instantCache.getUnchecked(str);
  }

  private static final LoadingCache<String, Instant> instantCache = CacheBuilder.newBuilder().maximumSize(1000).build(parser);

  @Override
  public Instant deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    return instantCache.getUnchecked(p.getValueAsString());

  }
}
