package org.knowm.xchange.blockchainpit.dto.trade;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.knowm.xchange.blockchainpit.dto.PitChannel;
import org.knowm.xchange.blockchainpit.dto.PitEventType;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/** Test PitTrade JSON parsing */
public class TradeJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = TradeJSONTest.class.getResourceAsStream(
            "/org.knowm.xchange.blockchainpit.dto.trade/trade.json");
    assertThat(is).isNotNull();

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    PitTrade trade = mapper.readValue(is, PitTrade.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(trade.getSeqnum()).isEqualTo(1);
    assertThat(trade.getEvent()).isEqualTo(PitEventType.UPDATED);
    assertThat(trade.getChannel()).isEqualTo(PitChannel.TRADES);
    assertThat(trade.getSymbol()).isEqualTo("BTC-USD");
    assertThat(trade.getTimestamp()).isEqualTo("2019-08-13T11:30:06.100140Z");
    assertThat(trade.getSide()).isEqualTo("sell");
    assertThat(trade.getQty()).isEqualTo(new BigDecimal("8.5E-5"));
    assertThat(trade.getPrice()).isEqualTo(new BigDecimal("11252.4"));
    assertThat(trade.getTradeId()).isEqualTo("12884909920");
  }
}
