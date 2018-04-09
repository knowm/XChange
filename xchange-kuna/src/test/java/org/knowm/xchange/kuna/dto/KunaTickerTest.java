package org.knowm.xchange.kuna.dto;

import static java.lang.Double.MAX_VALUE;
import static java.lang.Double.MIN_VALUE;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.BeforeClass;
import org.junit.Test;

public class KunaTickerTest {

  private static KunaTicker ticker;

  @BeforeClass
  public static void init() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    try (InputStream file =
        KunaTickerTest.class
            .getClassLoader()
            .getResourceAsStream("org/knowm/xchange/kuna/dto/ticker.json")) {
      ticker = mapper.readValue(file, KunaTicker.class);
    }
  }

  @Test
  public void test_buy() {
    assertThat(KunaTicker.builder().withBuy(MIN_VALUE).build().getBuy())
        .isEqualTo(BigDecimal.valueOf(MIN_VALUE));
    assertThat(KunaTicker.builder().withBuy(MAX_VALUE).build().getBuy())
        .isEqualTo(BigDecimal.valueOf(MAX_VALUE));
    assertThat(KunaTicker.builder().withBuy(0).build().getBuy()).isEqualTo(BigDecimal.valueOf(0.0));

    assertThat(ticker.getBuy()).isEqualByComparingTo("399596.0");
  }

  @Test
  public void test_sell() {
    assertThat(KunaTicker.builder().withSell(MIN_VALUE).build().getSell())
        .isEqualTo(BigDecimal.valueOf(MIN_VALUE));
    assertThat(KunaTicker.builder().withSell(MAX_VALUE).build().getSell())
        .isEqualTo(BigDecimal.valueOf(MAX_VALUE));
    assertThat(KunaTicker.builder().withSell(0).build().getSell())
        .isEqualTo(BigDecimal.valueOf(0.0));

    assertThat(ticker.getSell()).isEqualByComparingTo("399600.0");
  }

  @Test
  public void test_high() {
    assertThat(KunaTicker.builder().withHigh(MIN_VALUE).build().getHigh())
        .isEqualTo(BigDecimal.valueOf(MIN_VALUE));
    assertThat(KunaTicker.builder().withHigh(MAX_VALUE).build().getHigh())
        .isEqualTo(BigDecimal.valueOf(MAX_VALUE));
    assertThat(KunaTicker.builder().withHigh(0).build().getHigh())
        .isEqualTo(BigDecimal.valueOf(0.0));

    assertThat(ticker.getHigh()).isEqualByComparingTo("414980.0");
  }

  @Test
  public void test_low() {
    assertThat(KunaTicker.builder().withLow(MIN_VALUE).build().getLow())
        .isEqualTo(BigDecimal.valueOf(MIN_VALUE));
    assertThat(KunaTicker.builder().withLow(MAX_VALUE).build().getLow())
        .isEqualTo(BigDecimal.valueOf(MAX_VALUE));
    assertThat(KunaTicker.builder().withLow(0).build().getLow()).isEqualTo(BigDecimal.valueOf(0.0));

    assertThat(ticker.getLow()).isEqualByComparingTo("384100.0");
  }

  @Test
  public void test_last() {
    assertThat(KunaTicker.builder().withLast(MIN_VALUE).build().getLast())
        .isEqualTo(BigDecimal.valueOf(MIN_VALUE));
    assertThat(KunaTicker.builder().withLast(MAX_VALUE).build().getLast())
        .isEqualTo(BigDecimal.valueOf(MAX_VALUE));
    assertThat(KunaTicker.builder().withLast(0).build().getLast())
        .isEqualTo(BigDecimal.valueOf(0.0));

    assertThat(ticker.getLast()).isEqualByComparingTo("399596.0");
  }

  @Test
  public void test_volume() {
    assertThat(KunaTicker.builder().withVol(MIN_VALUE).build().getVol())
        .isEqualTo(BigDecimal.valueOf(MIN_VALUE));
    assertThat(KunaTicker.builder().withVol(MAX_VALUE).build().getVol())
        .isEqualTo(BigDecimal.valueOf(MAX_VALUE));
    assertThat(KunaTicker.builder().withVol(0).build().getVol()).isEqualTo(BigDecimal.valueOf(0.0));

    assertThat(ticker.getVol()).isEqualByComparingTo("13.646226");
  }

  @Test
  public void test_price() {
    assertThat(KunaTicker.builder().withPrice(MIN_VALUE).build().getPrice())
        .isEqualTo(BigDecimal.valueOf(MIN_VALUE));
    assertThat(KunaTicker.builder().withPrice(MAX_VALUE).build().getPrice())
        .isEqualTo(BigDecimal.valueOf(MAX_VALUE));
    assertThat(KunaTicker.builder().withPrice(0).build().getPrice())
        .isEqualTo(BigDecimal.valueOf(0.0));

    assertThat(ticker.getPrice()).isEqualByComparingTo("5525911.130276");
  }
}
