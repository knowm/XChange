package org.knowm.xchange.latoken.dto.exchangeinfo;

import static org.junit.Assert.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.latoken.dto.account.LatokenBalanceTest;

public class LatokenPairTest {

  LatokenPair pair;

  @Before
  public void testSetup() throws Exception {
    // Read in the JSON from the example resources
    InputStream is =
        LatokenBalanceTest.class.getResourceAsStream(
            "/org/knowm/xchange/latoken/dto/exchangeinfo/latoken-pair-response.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    pair = mapper.readValue(is, LatokenPair.class);
  }

  @Test
  public void testLatokenPair() {
    assertNotNull(pair);
  }

  @Test
  public void testGetPairId() {
    assertNotNull(pair.getPairId());
  }

  @Test
  public void testGetSymbol() {
    assertNotNull(pair.getSymbol());
  }

  @Test
  public void testGetBaseCurrency() {
    assertNotNull(pair.getBaseCurrency());
  }

  @Test
  public void testGetCounterCurrency() {
    assertNotNull(pair.getCounterCurrency());
  }

  @Test
  public void testGetMakerFee() {
    assertNotNull(pair.getMakerFee());
  }

  @Test
  public void testGetTakerFee() {
    assertNotNull(pair.getTakerFee());
  }

  @Test
  public void testGetPricePrecision() {
    assertNotNull(pair.getPricePrecision());
  }

  @Test
  public void testGetAmountPrecision() {
    assertNotNull(pair.getAmountPrecision());
  }

  @Test
  public void testGetMinOrderAmount() {
    assertNotNull(pair.getMinOrderAmount());
  }

  @Test
  public void testToString() {
    assertNotNull(pair.toString());
  }
}
