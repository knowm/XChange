package org.knowm.xchange.kuna.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class KunaTimeTickerTest {

  private static KunaTimeTicker ticker;

  @BeforeClass
  public static void init() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    File file = new File("src/test/resources/mock/time_ticker.json");
    ticker = mapper.readValue(file, KunaTimeTicker.class);
  }

  @Test
  public void test_timestamp() {
    assertThat(ticker.getTimestamp()).isEqualTo(1516087580);
  }
}