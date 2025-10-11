package org.knowm.xchange.dase;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.CurrencyPair;

public class DaseAdaptersMarketStringTest {

  @Test
  public void toCurrencyPair_validVariants() {
    assertThat(DaseAdapters.toCurrencyPair("ada-eur")).isEqualTo(CurrencyPair.ADA_EUR);
    assertThat(DaseAdapters.toCurrencyPair("ADA-EUR")).isEqualTo(CurrencyPair.ADA_EUR);
    assertThat(DaseAdapters.toCurrencyPair(" Ada-Eur ")).isEqualTo(CurrencyPair.ADA_EUR);
  }

  @Test
  public void toCurrencyPair_invalidOrNull() {
    assertThat(DaseAdapters.toCurrencyPair(null)).isNull();
    assertThat(DaseAdapters.toCurrencyPair("BAD")).isNull();
    assertThat(DaseAdapters.toCurrencyPair("BTC-EUR-EXTRA")).isNull();
  }
}
