package org.knowm.xchange.kuna.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.assertj.core.api.AbstractDateAssert;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;

public class KunaUtilsTest {

  @Test
  public void test_toPairString() {
    assertThat(KunaUtils.toPairString(CurrencyPair.BTC_UAH)).isEqualTo("btcuah");
    assertThat(KunaUtils.toPairString(CurrencyPair.ETH_UAH)).isEqualTo("ethuah");
    assertThat(KunaUtils.toPairString(CurrencyPair.BCH_UAH)).isEqualTo("bchuah");
  }

  @Test
  public void test_to_date() {
    String dateString = "2018-01-16T14:19:24+02:00";
    Date actual = KunaUtils.toDate(dateString);
    assertThat(actual).isEqualToIgnoringHours(dateString);

    assertThat(KunaUtils.toDate("2018-01-16T09:28:05Z")).isEqualTo("2018-01-16T09:28:05Z");
  }
}