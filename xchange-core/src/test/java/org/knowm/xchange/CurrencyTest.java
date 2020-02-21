package org.knowm.xchange;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.utils.ObjectMapperHelper;

public class CurrencyTest {

  @Test
  public void testCurrencyCode() {
    assertEquals(Currency.CNY.getCodeCurrency("CNY"), Currency.CNY);
    assertEquals(Currency.CNY.getCodeCurrency("cny"), Currency.CNY);
  }

  @Test
  public void testGetInstance() {
    assertEquals(Currency.BTC, Currency.getInstance("BTC"));
    assertEquals(Currency.BTC, Currency.getInstance("btc"));
    assertEquals(new Currency("btc"), Currency.getInstance("BTC"));
  }

  @Test
  public void testGetInstanceNoCreate() {
    assertEquals(Currency.CNY, Currency.getInstanceNoCreate("CNY"));
    assertEquals(Currency.CNY, Currency.getInstanceNoCreate("cny"));
    assertEquals(new Currency("cny"), Currency.getInstanceNoCreate("CNY"));
  }

  @Test
  public void testEquals() {
    assertEquals(Currency.BTC, Currency.XBT);
    assertNotEquals(Currency.LTC, Currency.XBT);

    Currency btc = SerializationUtils.deserialize(SerializationUtils.serialize(Currency.BTC));
    assertEquals(Currency.BTC, btc);
    assertEquals(Currency.XBT, btc);
    assertNotEquals(Currency.LTC, btc);
  }

  @Test
  public void testCompareTo() {

    Currency btc = Currency.BTC;
    Currency xbt = Currency.XBT;

    assertThat(btc.equals(btc));
    assertThat(btc.equals(xbt));
    assertThat(xbt.equals(btc));
    assertThat(xbt.equals(xbt));

    assertEquals(btc.compareTo(xbt), 0);
    assertEquals(xbt.compareTo(btc), 0);

    assertEquals(btc.compareTo(Currency.LTC), xbt.compareTo(Currency.LTC));
    assertEquals(btc.compareTo(Currency.ETH), xbt.compareTo(Currency.ETH));
  }

  @Test
  public void testToString() {
    assertEquals("XBT", Currency.XBT.toString());
    assertEquals("BTC", Currency.BTC.toString());
  }

  @Test
  public void testSerializeDeserialize() throws IOException {
    Currency jsonCopy = ObjectMapperHelper.viaJSON(Currency.XBT);
    assertThat(jsonCopy).isEqualTo(Currency.XBT);
  }
}
