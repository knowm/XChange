package org.knowm.xchange.liqui.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;
import org.junit.Test;
import org.knowm.xchange.liqui.dto.trade.LiquiTrade;
import org.knowm.xchange.liqui.dto.trade.result.LiquiTradeResult;
import org.knowm.xchange.liqui.marketdata.LiquiTickerJSONTest;

public class LiquiTradeJSONTest {

  @Test
  public void testUnmarshall() throws Exception {
    final InputStream is =
        LiquiTickerJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/liqui/marketdata/example-trade-data.json");

    final ObjectMapper mapper = new ObjectMapper();
    final LiquiTradeResult tradeResult = mapper.readValue(is, LiquiTradeResult.class);

    assertThat(tradeResult.isSuccess()).isTrue();
    final LiquiTrade trade = tradeResult.getResult();

    assertThat(trade.getReceived()).isEqualTo(new BigDecimal("2.0"));
    assertThat(trade.getRemains()).isEqualTo(new BigDecimal("5.0"));
    assertThat(trade.getOrderId()).isEqualTo(108000000L);
    assertThat(trade.getInitOrderId()).isEqualTo(108000000L);

    final Map<String, BigDecimal> funds = trade.getFunds();
    assertThat(funds.get("trx")).isEqualTo(new BigDecimal("499.0"));
    assertThat(funds.get("btc")).isEqualTo(new BigDecimal("0.0"));
  }

  @Test
  public void testError() throws Exception {
    final InputStream is =
        LiquiTickerJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/liqui/marketdata/example-error.json");

    final ObjectMapper mapper = new ObjectMapper();
    final LiquiTradeResult result = mapper.readValue(is, LiquiTradeResult.class);
  }
}
