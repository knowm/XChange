package org.knowm.xchange.kuna.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.BeforeClass;
import org.junit.Test;

public class KunaTimeTickerTest {

  private static KunaTimeTicker ticker;

  @BeforeClass
  public static void init() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    InputStream file =
        new Object() {}.getClass()
            .getClassLoader()
            .getResourceAsStream("org/knowm/xchange/kuna/dto/time_ticker.json");
    ticker = mapper.readValue(file, KunaTimeTicker.class);
  }

  @Test
  public void test_timestamp() {
    assertThat(ticker.getTimestamp()).isEqualTo(1516087580);
  }
}
