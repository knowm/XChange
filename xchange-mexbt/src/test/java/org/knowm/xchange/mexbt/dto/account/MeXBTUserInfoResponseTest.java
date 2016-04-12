package org.knowm.xchange.mexbt.dto.account;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MeXBTUserInfoResponseTest {

  @Test
  public void testMeXBTUserInfoResponse() throws JsonParseException, JsonMappingException, IOException {
    MeXBTUserInfoResponse me = new ObjectMapper().readValue(MeXBTUserInfoResponseTest.class.getResource("me.json"), MeXBTUserInfoResponse.class);
    assertEquals(2, me.getUserInfoKVP().length);
    assertEquals("FirstName", me.getUserInfoKVP()[0].get("key"));
    assertEquals("Zaphod", me.getUserInfoKVP()[0].get("value"));
    assertEquals("LastName", me.getUserInfoKVP()[1].get("key"));
    assertEquals("Beeblebrox", me.getUserInfoKVP()[1].get("value"));
  }

}
