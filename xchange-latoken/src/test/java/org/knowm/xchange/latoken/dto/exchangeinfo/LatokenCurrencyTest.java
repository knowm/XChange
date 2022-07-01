package org.knowm.xchange.latoken.dto.exchangeinfo;

import static org.junit.Assert.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.latoken.dto.account.LatokenBalanceTest;

public class LatokenCurrencyTest {

  LatokenCurrency currency;

  @Before
  public void testSetup() throws Exception {
    // Read in the JSON from the example resources
    InputStream is =
        LatokenBalanceTest.class.getResourceAsStream(
            "/org/knowm/xchange/latoken/dto/exchangeinfo/latoken-currency-response.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    currency = mapper.readValue(is, LatokenCurrency.class);
  }

  @Test
  public void testLatokenCurrency() {
    assertNotNull(currency);
  }

  @Test
  public void testGetCurrencyId() {
    assertNotNull(currency.getCurrencyId());
  }

  @Test
  public void testGetSymbol() {
    assertNotNull(currency.getSymbol());
  }

  @Test
  public void testGetName() {
    assertNotNull(currency.getName());
  }

  @Test
  public void testGetPrecision() {
    assertNotNull(currency.getPrecision());
  }

  @Test
  public void testGetType() {
    assertNotNull(currency.getType());
  }

  @Test
  public void testGetFee() {
    assertNotNull(currency.getFee());
  }

  @Test
  public void testToString() {
    assertNotNull(currency.toString());
  }
}
