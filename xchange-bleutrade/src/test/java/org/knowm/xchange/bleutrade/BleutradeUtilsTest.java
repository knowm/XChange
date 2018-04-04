package org.knowm.xchange.bleutrade;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;

public class BleutradeUtilsTest {

  @Test
  public void shouldConvertCurrencyPairToString() {
    assertThat(BleutradeUtils.toPairString(CurrencyPair.BTC_AUD)).isEqualTo("BTC_AUD");
    assertThat(BleutradeUtils.toPairString(new CurrencyPair("BLEU", "AUD"))).isEqualTo("BLEU_AUD");
  }

  @Test
  public void shouldConvertStringToCurrencyPair() {
    assertThat(BleutradeUtils.toCurrencyPair("BTC_AUD")).isEqualTo(CurrencyPair.BTC_AUD);
    assertThat(BleutradeUtils.toCurrencyPair("BLEU_AUD"))
        .isEqualTo(new CurrencyPair("BLEU", "AUD"));
  }

  @Test
  public void shouldConvertStringToDate() {
    assertThat(BleutradeUtils.toDate("2015-12-14 11:27:16.323").getTime())
        .isEqualTo(1450092436323L);
    assertThat(BleutradeUtils.toDate("2015-12-14 11:15:25").getTime()).isEqualTo(1450091725000L);
    assertThat(BleutradeUtils.toDate("yyyy-MM-dd")).isNull();
    assertThat(BleutradeUtils.toDate("")).isNull();
  }
}
