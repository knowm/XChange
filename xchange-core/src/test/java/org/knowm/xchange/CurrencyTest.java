package org.knowm.xchange;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.utils.ObjectMapperHelper;

class CurrencyTest {

  @Test
  void currencyCode() {
    assertThat(Currency.CNY.getCodeCurrency("CNY")).isEqualTo(Currency.CNY);
    assertThat(Currency.CNY.getCodeCurrency("cny")).isEqualTo(Currency.CNY);
  }

  @Test
  void getInstance() {
    assertThat(Currency.getInstance("BTC")).isEqualTo(Currency.BTC);
    assertThat(Currency.getInstance("btc")).isEqualTo(Currency.BTC);
    assertThat(Currency.getInstance("BTC")).isEqualTo(new Currency("btc"));
  }

  @Test
  void getInstanceNoCreate() {
    assertThat(Currency.getInstanceNoCreate("CNY")).isEqualTo(Currency.CNY);
    assertThat(Currency.getInstanceNoCreate("cny")).isEqualTo(Currency.CNY);
    assertThat(Currency.getInstanceNoCreate("CNY")).isEqualTo(new Currency("cny"));
  }

  @Test
  void equals() {
    assertThat(Currency.XBT).isEqualTo(Currency.BTC);
    assertThat(Currency.XBT).isNotEqualTo(Currency.LTC);

    Currency btc = SerializationUtils.deserialize(SerializationUtils.serialize(Currency.BTC));
    assertThat(btc).isEqualTo(Currency.BTC);
    assertThat(btc).isEqualTo(Currency.XBT);
    assertThat(btc).isNotEqualTo(Currency.LTC);
  }

  @Test
  void testToString() {
    assertThat(Currency.XBT.toString()).isEqualTo("XBT");
    assertThat(Currency.BTC.toString()).isEqualTo("BTC");
  }

  @Test
  void serializeDeserialize() throws Exception {
    Currency jsonCopy = ObjectMapperHelper.viaJSON(Currency.XBT);
    assertThat(jsonCopy).isEqualTo(Currency.XBT);
  }
}
