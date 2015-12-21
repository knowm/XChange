package com.xeiam.xchange.bleutrade.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.currency.CurrencyPair;

import java.io.IOException;
import java.io.InputStream;

import static org.fest.assertions.api.Assertions.assertThat;

public class BleutradeDtoTestSupport {

  protected static final CurrencyPair BLEU_BTC_CP = new CurrencyPair("BLEU", "BTC");

  private static ObjectMapper mapper = new ObjectMapper();

  protected static <E> E parse(Class<E> type) throws IOException {
    String coreName = getBaseFileName(type);
    return parse(coreName, type);
  }

  protected static <E> String getBaseFileName(Class<E> type) {
    final String pfx = "Bleutrade";
    assertThat(type.getSimpleName()).startsWith(pfx);
    String coreName = type.getSimpleName().substring(pfx.length());
    if (coreName.endsWith("[]")) {
      coreName = coreName.substring(0, coreName.length() - 2) + "s";
    }
    return coreName;
  }

  protected static <E> E parse(String baseName, Class<E> type) throws IOException {
    InputStream is = getStream(baseName);
    return mapper.readValue(is, type);
  }

  protected static InputStream getStream(String baseName) {
    return BleutradeDtoTest.class.getResourceAsStream(String.format("/%s.json", baseName));
  }


}
