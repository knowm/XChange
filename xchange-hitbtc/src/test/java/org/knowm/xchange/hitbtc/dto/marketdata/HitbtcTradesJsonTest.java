package org.knowm.xchange.hitbtc.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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

    List<HitbtcTrade> trades = hitbtcTrades.getHitbtcTrades();
    assertThat(trades).hasSize(5);
    HitbtcTrade trade = trades.get(0);
    assertThat(trade.getDate()).isEqualTo(1447538550006L);
    assertThat(trade.getPrice()).isEqualTo("347.65");
    assertThat(trade.getAmount()).isEqualTo("0.21");
    assertThat(trade.getTid()).isEqualTo("4191471");
    assertThat(trade.getSide()).isEqualTo(HitbtcTrade.HitbtcTradeSide.BUY);
  }
}
