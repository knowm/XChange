package org.knowm.xchange.mexbt.dto.account;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MeXBTDepositAddressesResponseTest {

  @Test
  public void testMeXBTDepositAddressesResponse() throws JsonParseException, JsonMappingException, IOException {
    MeXBTDepositAddressesResponse depositAddressesResponse = new ObjectMapper()
        .readValue(MeXBTDepositAddressesResponseTest.class.getResource("deposit-addresses.json"), MeXBTDepositAddressesResponse.class);
    MeXBTDepositAddress[] addresses = depositAddressesResponse.getAddresses();
    assertEquals(2, addresses.length);
    assertEquals("BTC", addresses[0].getName());
    assertEquals("1yL8LFT5qqzPJY3hMRQCJd5CTs2F7SHjv", addresses[0].getDepositAddress());
  }

}
