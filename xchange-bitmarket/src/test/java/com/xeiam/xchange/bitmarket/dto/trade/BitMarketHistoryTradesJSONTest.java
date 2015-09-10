package com.xeiam.xchange.bitmarket.dto.trade;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author kfonal
 */
public class BitMarketHistoryTradesJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BitMarketHistoryTradesJSONTest.class.getResourceAsStream("/trade/example-history-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitMarketHistoryTradesResponse response = mapper.readValue(is, BitMarketHistoryTradesResponse.class);

    // Verify that the example data was unmarshalled correctly
    BitMarketHistoryTrades trades = response.getData();

    assertThat(response.getSuccess()).isTrue();
    assertThat(trades.getTotal()).isEqualTo(5);
    assertThat(trades.getTrades().size()).isEqualTo(5);

    BitMarketHistoryTrade trade = trades.getTrades().get(0);

    assertThat(trade.getAmountCrypto()).isEqualTo(new BigDecimal("1.08260046"));
    assertThat(trade.getCurrencyCrypto()).isEqualTo("BTC");
    assertThat(trade.getRate()).isEqualTo(new BigDecimal("877.0000"));
    assertThat(trade.getType()).isEqualTo("sell");
    assertThat(trade.getId()).isEqualTo(389406);
  }
}
