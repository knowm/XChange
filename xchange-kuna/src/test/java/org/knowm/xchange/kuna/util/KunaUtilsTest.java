package org.knowm.xchange.kuna.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;

public class KunaUtilsTest {
  static {
    System.setProperty("user.timezone", "GMT");
  }

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

    // TODO fix this. somehow the local time zone is causing this to fail by a one-hour difference.
    //    assertThat(KunaUtils.toDate("2018-01-16T09:28:05Z")).isEqualTo("2018-01-16T09:28:05Z");
    // belongs in the adapter
    //    assertThat(KunaUtils.toDate("2018-01-16T09:28:05Z")).isEqualTo("2018-01-16T09:28:05Z");
  }
}
