package com.xeiam.xchange.mexbt.dto.trade;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MeXBTServerOrderIdResponseTest {

  @Test
  public void testMeXBTOrderCreation() throws JsonParseException, JsonMappingException, IOException {
    MeXBTServerOrderIdResponse meXBTServerOrderIdResponse = new ObjectMapper().readValue(MeXBTServerOrderIdResponseTest.class.getResource("server-order-id.json"), MeXBTServerOrderIdResponse.class);
    System.out.println(meXBTServerOrderIdResponse.getDateTimeUtc());
  }

}
