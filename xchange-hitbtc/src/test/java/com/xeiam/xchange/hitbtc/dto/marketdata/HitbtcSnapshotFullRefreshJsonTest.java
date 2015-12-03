package com.xeiam.xchange.hitbtc.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HitbtcSnapshotFullRefreshJsonTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = HitbtcSnapshotFullRefreshJsonTest.class.getResourceAsStream("/marketdata/example-snapshot-full-refresh-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
    HitbtcSnapshotFullRefresh snapshotFullRefresh = mapper.readValue(is, HitbtcSnapshotFullRefresh.class);

    assertThat(snapshotFullRefresh.getSnapshotSeqNo()).isEqualTo(8339922L);
    assertThat(snapshotFullRefresh.getSymbol()).isEqualTo("BTCUSD");
    assertThat(snapshotFullRefresh.getExchangeStatus()).isEqualTo("working");
    assertThat(snapshotFullRefresh.getAsk()).hasSize(205);
    assertThat(snapshotFullRefresh.getBid()).hasSize(248);

    HitbtcStreamingOrder ask = snapshotFullRefresh.getAsk().get(0);
    assertThat(ask.getPrice()).isEqualTo("348.34");
    assertThat(ask.getSize()).isEqualTo("40");

    HitbtcStreamingOrder bid = snapshotFullRefresh.getBid().get(247);
    assertThat(bid.getPrice()).isEqualTo("0.02");
    assertThat(bid.getSize()).isEqualTo("36400");
  }
}
