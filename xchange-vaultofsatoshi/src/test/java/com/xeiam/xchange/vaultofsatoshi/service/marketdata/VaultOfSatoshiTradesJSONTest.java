package com.xeiam.xchange.vaultofsatoshi.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VosTrade;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VosResponse;

/**
 * Test VaultOfSatoshiTrades JSON parsing
 */
public class VaultOfSatoshiTradesJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = VaultOfSatoshiTradesJSONTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    JavaType type = mapper.getTypeFactory().constructParametricType(List.class, VosTrade.class);
    JavaType type2 = mapper.getTypeFactory().constructParametricType(VosResponse.class, type);
    VosResponse<List<VosTrade>> vosTrades = mapper.readValue(is, type2);

    // Verify that the example data was unmarshalled correctly
    assertThat(vosTrades.getData().get(0).getPrice().getValue()).isEqualTo(new BigDecimal("641.18165850"));
  }
}
