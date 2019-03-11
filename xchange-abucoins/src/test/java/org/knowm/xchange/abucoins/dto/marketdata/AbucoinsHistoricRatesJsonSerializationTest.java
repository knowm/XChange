package org.knowm.xchange.abucoins.dto.marketdata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

/**
 * Test that confirms we can handle array json (success case from REST call) as well as object json
 * (occurs during the error case for the REST call).
 */
public class AbucoinsHistoricRatesJsonSerializationTest {
  ObjectMapper objectMapper;

  @Before
  public void setUp() throws Exception {
    objectMapper = new ObjectMapper();
  }

  @Test
  public void testJsonArray() throws Exception {
    String json =
        "[ [ \"1505984400\", \"14209.92500000\", \"14209.92500000\", \"14209.92500000\", \"14209.92500000\", \"0.001\" ], [ \"1505984460\","
            + " \"14209.92500000\", \"14209.92500000\", \"14209.92500000\", \"14209.92500000\", \"0.00052\" ], [ \"1505984520\",\n"
            + " \"14209.92500000\", \"14209.92500000\", \"14209.92500000\", \"14209.92500000\", \"0.00068\" ] ]";
    AbucoinsHistoricRates accounts = objectMapper.readValue(json, AbucoinsHistoricRates.class);
    assertNotNull("Accounts are null", accounts);
    assertEquals("Not two elements in array", 3, accounts.historicRates.length);
  }

  @Test
  public void testErrorMessage() throws Exception {
    String json = "{ \"message\" : \"PERMISSION DENIED\" }";
    AbucoinsHistoricRates accounts = objectMapper.readValue(json, AbucoinsHistoricRates.class);
    assertNotNull("Accounts are null", accounts);
    assertNotNull("Error message not parsed", accounts.getMessage());
    assertEquals("Error message not parsed", "PERMISSION DENIED", accounts.getMessage());
  }
}
