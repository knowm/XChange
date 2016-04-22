package org.knowm.xchange.hitbtc.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HitbtcIncrementalRefreshJsonTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = HitbtcIncrementalRefreshJsonTest.class.getResourceAsStream("/marketdata/example-incremental-refresh-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
    HitbtcIncrementalRefresh incrementalRefresh = mapper.readValue(is, HitbtcIncrementalRefresh.class);

    assertThat(incrementalRefresh.getSeqNo()).isEqualTo(4494753L);
    assertThat(incrementalRefresh.getSymbol()).isEqualTo("XMRBTC");
    assertThat(incrementalRefresh.getExchangeStatus()).isEqualTo("working");
    assertThat(incrementalRefresh.getAsk()).hasSize(0);
    assertThat(incrementalRefresh.getBid()).hasSize(1);
    assertThat(incrementalRefresh.getTrade()).hasSize(1);

    HitbtcStreamingOrder bid = incrementalRefresh.getBid().get(0);
    assertThat(bid.getPrice()).isEqualTo("0.001276");
    assertThat(bid.getSize()).isEqualTo("2799");

    HitbtcStreamingTrade trade = incrementalRefresh.getTrade().get(0);
    assertThat(trade.getDate()).isEqualTo(1447559153368L);
    assertThat(trade.getPrice()).isEqualTo("0.001276");
    assertThat(trade.getAmount()).isEqualTo("747");
    assertThat(trade.getTid()).isEqualTo("4193069");
    assertThat(trade.getSide()).isEqualTo(HitbtcTrade.HitbtcTradeSide.SELL);
  }
}
