package org.knowm.xchange.mexbt.dto.trade;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MeXBTServerOrderIdResponseTest {

  @Test
  public void testMeXBTOrderCreation() throws JsonParseException, JsonMappingException, IOException {
    MeXBTServerOrderIdResponse meXBTServerOrderIdResponse = new ObjectMapper()
        .readValue(MeXBTServerOrderIdResponseTest.class.getResource("server-order-id.json"), MeXBTServerOrderIdResponse.class);
    assertEquals(95084849L, meXBTServerOrderIdResponse.getServerOrderId());
    assertEquals(1435078107139L, meXBTServerOrderIdResponse.getDateTimeUtc().getTime());
  }

}
