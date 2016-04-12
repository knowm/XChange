package org.knowm.xchange.coinsetter.dto;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import org.knowm.xchange.coinsetter.ObjectMapperHelper;

public class CoinsetterResponseTest {

  @Test
  public void test() throws IOException {

    CoinsetterResponse response = ObjectMapperHelper.readValue(getClass().getResource("response.json"), CoinsetterResponse.class);
    assertEquals("OK", response.getMessage());
    assertEquals("SUCCESS", response.getRequestStatus());
  }
}
