package com.xeiam.xchange.mexbt.dto.trade;

import java.io.IOException;
import java.util.Date;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MeXBTServerOrderIdResponseTest {

  @Test
  public void testMeXBTOrderCreation() throws JsonParseException, JsonMappingException, IOException {
    MeXBTServerOrderIdResponse meXBTOrderCreation = new ObjectMapper().readValue(MeXBTServerOrderIdResponseTest.class.getResource("server-order-id.json"), MeXBTServerOrderIdResponse.class);
    System.out.println(meXBTOrderCreation.getDateTimeUtc());
    System.out.println(new Date().getTime());
  }

}
