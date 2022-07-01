package org.knowm.xchange.latoken.dto.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class LatokenBalanceTest {

  LatokenBalance balance;

  @Before
  public void testSetup() throws Exception {
    // Read in the JSON from the example resources
    InputStream is =
        LatokenBalanceTest.class.getResourceAsStream(
            "/org/knowm/xchange/latoken/dto/account/latoken-balances-response.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    List<List<LatokenBalance>> readValues =
        mapper.readValue(is, new TypeReference<List<List<LatokenBalance>>>() {});
    assertEquals(1, readValues.size());
    assertEquals(1, readValues.get(0).size());

    balance = readValues.get(0).get(0);
  }

  @Test
  public void testLatokenBalance() {
    assertNotNull(balance);
  }

  @Test
  public void testGetCurrencyId() {
    assertNotNull(balance.getCurrencyId());
  }

  @Test
  public void testGetSymbol() {
    assertNotNull(balance.getSymbol());
  }

  @Test
  public void testGetName() {
    assertNotNull(balance.getName());
  }

  @Test
  public void testGetAmount() {
    assertNotNull(balance.getAmount());
  }

  @Test
  public void testGetAvailable() {
    assertNotNull(balance.getAvailable());
  }

  @Test
  public void testGetFrozen() {
    assertNotNull(balance.getFrozen());
  }

  @Test
  public void testGetPending() {
    assertNotNull(balance.getPending());
  }

  @Test
  public void testToString() {
    assertNotNull(balance.toString());
  }
}
