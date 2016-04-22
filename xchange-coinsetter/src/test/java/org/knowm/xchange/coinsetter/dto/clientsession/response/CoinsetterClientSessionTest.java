package org.knowm.xchange.coinsetter.dto.clientsession.response;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.UUID;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.knowm.xchange.coinsetter.ObjectMapperHelper;

public class CoinsetterClientSessionTest {

  @Test
  public void test() throws JsonParseException, JsonMappingException, IOException {

    CoinsetterClientSession coinsetterClientSession = ObjectMapperHelper.readValue(getClass().getResource("login.json"),
        CoinsetterClientSession.class);
    assertEquals(UUID.fromString("f7a5a8c3-23f9-4ca5-96bf-6f756c0d2155"), coinsetterClientSession.getUuid());
    assertEquals("ACTIVE", coinsetterClientSession.getCustomerStatus());
    assertEquals("ACTIVE", coinsetterClientSession.getCustomerPasswordStatus());
    assertEquals(UUID.fromString("f8703bf2-9a89-4fa6-bacc-8b719b5e08a9"), coinsetterClientSession.getCustomerUuid());
    assertEquals("OK", coinsetterClientSession.getMessage());
    assertEquals("SUCCESS", coinsetterClientSession.getRequestStatus());
    assertEquals("johndoe", coinsetterClientSession.getUsername());
  }

}
