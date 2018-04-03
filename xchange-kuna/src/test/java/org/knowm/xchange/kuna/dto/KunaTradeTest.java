package org.knowm.xchange.kuna.dto;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.knowm.xchange.kuna.dto.enums.KunaSide.BUY;
import static org.knowm.xchange.kuna.dto.enums.KunaSide.SELL;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.kuna.util.KunaUtils;

public class KunaTradeTest {
  private static KunaTrade trade;

  @BeforeClass
  public static void init() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    InputStream file =
        new Object() {}.getClass()
            .getClassLoader()
            .getResourceAsStream("org/knowm/xchange/kuna/dto/trade.json");
    trade = mapper.readValue(file, KunaTrade.class);
  }

  @Test
  public void test_id() {
    assertEquals(0, KunaTrade.builder().withId(0).build().getId());
    assertEquals(MIN_VALUE, KunaTrade.builder().withId(MIN_VALUE).build().getId());
    assertEquals(MAX_VALUE, KunaTrade.builder().withId(MAX_VALUE).build().getId());

    assertThat(trade.getId()).isEqualTo(338428);
  }

  @Test
  public void test_side() {
    assertThat(KunaTrade.builder().withSide(null).build().getSide()).isNull();
    assertThat(KunaTrade.builder().withSide(BUY.name().toUpperCase()).build().getSide())
        .isEqualByComparingTo(BUY);
    assertThat(KunaTrade.builder().withSide(SELL.name().toUpperCase()).build().getSide())
        .isEqualByComparingTo(SELL);
    assertThat(KunaTrade.builder().withSide(BUY.name().toLowerCase()).build().getSide())
        .isEqualByComparingTo(BUY);
    assertThat(KunaTrade.builder().withSide(SELL.name().toLowerCase()).build().getSide())
        .isEqualByComparingTo(SELL);

    assertThat(trade.getSide()).isNull();
  }

  @Test
  public void test_price() {
    assertEquals(ZERO, KunaTrade.builder().withPrice(ZERO).build().getPrice());
    assertEquals(ONE, KunaTrade.builder().withPrice(ONE).build().getPrice());
    assertEquals(TEN, KunaTrade.builder().withPrice(TEN).build().getPrice());

    assertThat(trade.getPrice()).isEqualByComparingTo("380505.0");
  }

  @Test
  public void test_market() {
    assertThat(KunaTrade.builder().withMarket(null).build().getMarket()).isNull();
    assertThat(KunaTrade.builder().withMarket("").build().getMarket()).isEmpty();

    assertThat(trade.getMarket()).isEqualTo("btcuah");
  }

  @Test
  public void test_createdAt() {
    assertThat(KunaTrade.builder().withCreatedAt(null).build().getCreatedAt()).isNull();
    assertThat(
            KunaTrade.builder().withCreatedAt(KunaUtils.format(new Date())).build().getCreatedAt())
        .isEqualToIgnoringSeconds(new Date());
    assertThat(trade.getCreatedAt()).isEqualToIgnoringHours("2018-01-16T14:19:24+02:00");
  }

  @Test
  public void test_volume() {
    assertEquals(ZERO, KunaTrade.builder().withVolume(ZERO).build().getVolume());
    assertEquals(ONE, KunaTrade.builder().withVolume(ONE).build().getVolume());
    assertEquals(TEN, KunaTrade.builder().withVolume(TEN).build().getVolume());

    assertThat(trade.getVolume()).isEqualByComparingTo("0.03536");
  }

  @Test
  public void test_funds() {
    assertEquals(ZERO, KunaTrade.builder().withFunds(ZERO).build().getFunds());
    assertEquals(ONE, KunaTrade.builder().withFunds(ONE).build().getFunds());
    assertEquals(TEN, KunaTrade.builder().withFunds(TEN).build().getFunds());

    assertThat(trade.getFunds()).isEqualByComparingTo("13454.6568");
  }
}
