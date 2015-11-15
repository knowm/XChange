package com.xeiam.xchange.hitbtc.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HitbtcTradesJsonTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = HitbtcTradesJsonTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();

    HitbtcTrades hitbtcTrades = mapper.readValue(is, HitbtcTrades.class);
    
    HitbtcTrade[] trades = hitbtcTrades.getHitbtcTrades();
    assertThat(trades).hasSize(5);
    assertThat(trades[0].getDate()).isEqualTo(1447538550006L);
    assertThat(trades[0].getPrice()).isEqualTo("347.65");
    assertThat(trades[0].getAmount()).isEqualTo("0.21");
    assertThat(trades[0].getTid()).isEqualTo("4191471");
    assertThat(trades[0].getSide()).isEqualTo("buy");
  }
}
