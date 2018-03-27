package org.knowm.xchange.anx.v2.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.junit.Test;

public class TradesJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        TickerJSONTest.class.getResourceAsStream("/v2/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();

    ANXTradesWrapper anxTradesWrapper = mapper.readValue(is, ANXTradesWrapper.class);

    List<ANXTrade> anxTrades = anxTradesWrapper.getANXTrades();

    assertThat(anxTrades).hasSize(2);

    ANXTrade anxTrade = anxTrades.get(0);
    assertThat(anxTrade.getAmount()).isEqualTo("0.25");
    assertThat(anxTrade.getAmountInt()).isEqualTo(25000000);
    assertThat(anxTrade.getItem()).isEqualTo("BTC");
    assertThat(anxTrade.getPrice()).isEqualTo("655");
    assertThat(anxTrade.getPriceInt()).isEqualTo(65500000);
    assertThat(anxTrade.getPriceCurrency()).isEqualTo("USD");
    assertThat(anxTrade.getPrimary()).isEqualTo("true");
    assertThat(anxTrade.getProperties()).isEqualTo("Not Supported");
    assertThat(anxTrade.getTid()).isEqualTo(1402189342525L);
    assertThat(anxTrade.getTradeType()).isEqualTo("bid");
  }
}
