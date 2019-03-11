package org.knowm.xchange.bleutrade.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.knowm.xchange.bleutrade.BleutradeTestData;

public class BleutradeDtoTestSupport extends BleutradeTestData {

  private static ObjectMapper mapper = new ObjectMapper();

  protected static <E> E parse(Class<E> type) throws IOException {
    String coreName = getBaseFileName(type);
    return parse("org/knowm/xchange/bleutrade/dto/" + coreName, type);
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
