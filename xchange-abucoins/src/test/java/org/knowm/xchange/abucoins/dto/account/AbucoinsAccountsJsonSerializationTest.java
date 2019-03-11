package org.knowm.xchange.abucoins.dto.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

/**
 * Test that confirms we can handle array json (success case from REST call) as well as object json
 * (occurs during the error case for the REST call).
 */
public class AbucoinsAccountsJsonSerializationTest {
  ObjectMapper objectMapper;

  @Before
  public void setUp() throws Exception {
    objectMapper = new ObjectMapper();
  }

  @Test
  public void testJsonArray() throws Exception {
    String json =
        "[ { \"id\": \"3-BTC\", \"currency\": \"BTC\", \"balance\": 13.38603805, \"available\": 13.38589212, \"available_btc\": 13.38589212,"
            + "\"hold\": 0.00014593, \"profile_id\": 3  }, { \"id\": \"3-ETH\", \"currency\": \"ETH\", \"balance\": 133.48685448, \"available\": "
            + "133.48685448, \"available_btc\": 9.38012126, \"hold\": 0, \"profile_id\": 3 } ]";
    AbucoinsAccounts accounts = objectMapper.readValue(json, AbucoinsAccounts.class);
    assertNotNull("Accounts are null", accounts);
    assertEquals("Not two elements in array", 2, accounts.accounts.length);
  }

  @Test
  public void testErrorMessage() throws Exception {
    String json = "{ \"message\" : \"PERMISSION DENIED\" }";
    AbucoinsAccounts accounts = objectMapper.readValue(json, AbucoinsAccounts.class);
    assertNotNull("Accounts are null", accounts);
    assertEquals("Wrong number of elements", 1, accounts.accounts.length);
    assertEquals(
        "Error message not parsed", "PERMISSION DENIED", accounts.accounts[0].getMessage());
  }
}
