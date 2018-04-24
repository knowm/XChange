package org.knowm.xchange;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyAttributes;

import java.util.Objects;
import java.util.Set;

public class CurrencyTest {

  @Test
  public void testCurrencyCode() {
    assertEquals(Currency.CNY.getCodeCurrency("CNY"), Currency.CNY);
    assertEquals(Currency.CNY.getCodeCurrency("cny"), Currency.CNY);
  }

  @Test
  public void testGetInstance() {
    assertEquals(Currency.BTC, Currency.valueOf("BTC"));
    assertEquals(Currency.BTC, Currency.valueOf("btc"));
    assertEquals(new org.knowm.xchange.currency.impl.Currency("btc"), Currency.valueOf("BTC"));
  }

  @Test
  public void testGetInstanceNoCreate() {
    assertEquals(Currency.CNY, Currency.getInstanceNoCreate("CNY"));
    assertEquals(Currency.CNY, Currency.getInstanceNoCreate("cny"));
    assertEquals(
        new org.knowm.xchange.currency.impl.Currency("cny"), Currency.getInstanceNoCreate("CNY"));
  }

  @Test
  public void testEquals() {
    assertEquals(Currency.BTC, Currency.XBT);
    assertNotEquals(Currency.LTC, Currency.XBT);

    Currency btc = SerializationUtils.deserialize(SerializationUtils.serialize(Currency.BTC));
    assertEquals(Currency.BTC, btc);
    assertEquals(Currency.XBT, btc);
    assertNotEquals(Currency.LTC, btc);

    String someStackVariable="BTC";
    Currency avoidXBT =
        new Currency() {

          @Override
          public String getCode() {
            return someStackVariable;
          }

          @Override
          public CurrencyAttributes getAttributes() {
            return null;
          }

          @Override
          public boolean equals(Object obj) {
            boolean ret = false;
            if (obj instanceof Currency) {
              Currency currency = (Currency) obj;
              ret = Objects.equals(currency.getCode(), someStackVariable);
              //
            }
          return ret;}
        };
    assertEquals(avoidXBT,Currency.BTC );
    assertNotEquals(Currency.XBT, avoidXBT);
    assertNotEquals(Currency.LTC, avoidXBT);
  }


}
