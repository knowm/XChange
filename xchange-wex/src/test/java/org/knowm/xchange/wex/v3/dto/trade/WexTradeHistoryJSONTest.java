package org.knowm.xchange.wex.v3.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Map.Entry;
import org.junit.Test;

/** @author Benedikt Bünz Test WexTradeHistoryReturn JSON parsing */
public class WexTradeHistoryJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        WexTradeHistoryJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/wex/v3/trade/example-trade-history-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    WexTradeHistoryReturn transactions = mapper.readValue(is, WexTradeHistoryReturn.class);
    Map<Long, WexTradeHistoryResult> result = transactions.getReturnValue();
    assertThat(result.size()).isEqualTo(32);
    Entry<Long, WexTradeHistoryResult> firstEntry = result.entrySet().iterator().next();
    // Verify that the example data was unmarshalled correctly
    assertThat(firstEntry.getKey()).isEqualTo(7258275L);
    assertThat(firstEntry.getValue().getAmount()).isEqualTo(new BigDecimal("0.1"));
    assertThat(firstEntry.getValue().getOrderId()).isEqualTo(34870919L);
    assertThat(firstEntry.getValue().getPair()).isEqualTo("btc_usd");
    assertThat(firstEntry.getValue().getRate()).isEqualTo(new BigDecimal("125.75"));
    assertThat(firstEntry.getValue().getTimestamp()).isEqualTo(1378194574L);
    assertThat(firstEntry.getValue().getType()).isEqualTo(WexTradeHistoryResult.Type.sell);
    assertThat(firstEntry.getValue().isYourOrder()).isEqualTo(false);
  }
}
