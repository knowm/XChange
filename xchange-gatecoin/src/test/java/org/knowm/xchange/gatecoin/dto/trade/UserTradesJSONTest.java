package org.knowm.xchange.gatecoin.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.gatecoin.dto.trade.Results.GatecoinTradeHistoryResult;

public class UserTradesJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        UserTradesJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/gatecoin/dto/trade/example-user-trades.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    GatecoinTradeHistoryResult tradesHistoryResult =
        mapper.readValue(is, GatecoinTradeHistoryResult.class);
    GatecoinTradeHistory[] tradesHistory = tradesHistoryResult.getTransactions();

    assertThat(tradesHistoryResult.getResponseStatus().getMessage()).isEqualTo("OK");
    assertThat(tradesHistory.length).isEqualTo(4);
    assertThat(tradesHistory[0].getPrice()).isEqualTo(new BigDecimal("1960"));
    assertThat(tradesHistory[0].getTransactionId()).isEqualTo(42239);
    assertThat(tradesHistory[0].getAskOrderID()).isEqualTo("BK11432053513");

    assertThat(tradesHistory[1].getPrice()).isEqualTo(new BigDecimal("125"));
  }
}
