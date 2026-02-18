package org.knowm.xchange.bitstamp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Date;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.exceptions.ExchangeException;

public class BitstampUtilsTest {

  @Test
  public void testParseDateWithMillis() {
    final String strDateFromBitstamp = "2020-09-01 05:55:04.399000";

    // strDateFromBitstamp converted at https://www.epochconverter.com
    final long epochMillis = 1598939704399L;

    final Date convertedDate = BitstampUtils.parseDate(strDateFromBitstamp);

    assertThat(convertedDate).isNotNull();
    assertThat(convertedDate.getTime()).isEqualTo(epochMillis);
  }

  @Test
  public void testParseDateWithoutMillis() {
    final String strDateFromBitstamp = "2020-09-01 05:55:04";

    // strDateFromBitstamp converted at https://www.epochconverter.com
    final long epochMillis = 1598939704000L;

    final Date convertedDate = BitstampUtils.parseDate(strDateFromBitstamp);

    assertThat(convertedDate).isNotNull();
    assertThat(convertedDate.getTime()).isEqualTo(epochMillis);
  }

  @Test
  public void testParseDateWithInvalidFormat() {
    final String strDateWithInvalidFormat = "2020-09-01T05:55:04.399000";

    assertThatThrownBy(() -> BitstampUtils.parseDate(strDateWithInvalidFormat))
        .isInstanceOf(ExchangeException.class)
        .hasMessage("Illegal date/time format: " + strDateWithInvalidFormat);
  }

  @Test
  public void testParseDateWithIso8601Format() {
    final String strDateIso8601 = "2025-11-15T02:09:13+00:00";

    // strDateIso8601 converted at https://www.epochconverter.com
    final long epochMillis = 1763172553000L;

    final Date convertedDate = BitstampUtils.parseDate(strDateIso8601);

    assertThat(convertedDate).isNotNull();
    assertThat(convertedDate.getTime()).isEqualTo(epochMillis);
  }

  @Test
  public void testParseDateNull() {
    final Date convertedDate = BitstampUtils.parseDate(null);

    assertThat(convertedDate).isNull();
  }
}
