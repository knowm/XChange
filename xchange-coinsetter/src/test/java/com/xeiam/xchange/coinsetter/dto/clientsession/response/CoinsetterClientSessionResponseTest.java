package com.xeiam.xchange.coinsetter.dto.clientsession.response;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CoinsetterClientSessionResponseTest {

  private ObjectMapper mapper = new ObjectMapper();

  @Test
  public void test() throws IOException {

    CoinsetterClientSessionResponse response = mapper.readValue(getClass().getResource("heartbeat.json"), CoinsetterClientSessionResponse.class);
    assertEquals("OK", response.getMessage());
    assertEquals("SUCCESS", response.getRequestStatus());
  }
}
