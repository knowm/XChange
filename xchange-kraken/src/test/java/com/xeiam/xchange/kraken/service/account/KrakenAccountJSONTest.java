package com.xeiam.xchange.kraken.service.account;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xchange.kraken.dto.account.KrakenBalanceResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.kraken.service.marketdata.KrakenDepthJSONTest;


public class KrakenAccountJSONTest {

  @Before
  public void setUp() throws Exception {

  }

  @Test
  public void testUnmarshal() throws IOException{

    // Read in the JSON from the example resources
    InputStream is = KrakenDepthJSONTest.class.getResourceAsStream("/account/example-balance-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenBalanceResult krakenBalance = mapper.readValue(is, KrakenBalanceResult.class);
    Assert.assertEquals(2, krakenBalance.getResult().size());
    assertThat(krakenBalance.getResult().get("ZUSD")).isNull();
    assertThat(krakenBalance.getResult().get("ZEUR")).isEqualTo("1.0539");

  }

}
