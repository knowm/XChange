package org.knowm.xchange.bleutrade.dto;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;

import org.knowm.xchange.bleutrade.BleutradeTestData;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BleutradeDtoTestSupport extends BleutradeTestData {

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
