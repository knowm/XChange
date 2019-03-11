package org.knowm.xchange.liqui.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.liqui.dto.LiquiTradeType;
import org.knowm.xchange.liqui.dto.trade.LiquiUserTrade;
import org.knowm.xchange.liqui.dto.trade.result.LiquiTradeHistoryResult;
import org.knowm.xchange.liqui.marketdata.LiquiTickerJSONTest;

public class LiquiTradeHistoryJSONTest {

  @Test
  public void testUnmarshall() throws Exception {
    final InputStream is =
        LiquiTickerJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/liqui/marketdata/example-tradehistory-data.json");

    final ObjectMapper mapper = new ObjectMapper();
    final LiquiTradeHistoryResult tradeHistoryResult =
        mapper.readValue(is, LiquiTradeHistoryResult.class);
    assertThat(tradeHistoryResult.isSuccess()).isTrue();

    final Map<Long, LiquiUserTrade> history = tradeHistoryResult.getResult().getHistory();
    final LiquiUserTrade trade = history.get(37225796L);

    assertThat(trade.getPair()).isEqualTo(new CurrencyPair("trx", "btc"));
    assertThat(trade.getOrderId()).isEqualTo(110486609L);
    assertThat(trade.getTradeId()).isEqualTo(37225796L);
    assertThat(trade.getType()).isEqualTo(LiquiTradeType.SELL);
    assertThat(trade.getAmount()).isEqualTo(new BigDecimal("335.0"));
    assertThat(trade.getRate()).isEqualTo(new BigDecimal("0.0000003"));
    assertThat(trade.getTimestamp()).isEqualTo(1510008279);
  }
}
