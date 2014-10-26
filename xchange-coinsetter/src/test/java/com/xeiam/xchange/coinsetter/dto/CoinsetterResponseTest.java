package com.xeiam.xchange.coinsetter.dto;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CoinsetterResponseTest {

  private ObjectMapper mapper = new ObjectMapper();

  @Test
  public void test() throws IOException {

    CoinsetterResponse response = mapper.readValue(getClass().getResource("response.json"), CoinsetterResponse.class);
    assertEquals("OK", response.getMessage());
    assertEquals("SUCCESS", response.getRequestStatus());
  }
}
