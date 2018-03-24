package org.knowm.xchange.liqui.trade;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;
import org.knowm.xchange.liqui.dto.trade.result.LiquiCancelOrderResult;
import org.knowm.xchange.liqui.marketdata.LiquiTickerJSONTest;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LiquiCancelOrderJSONTest {
  @Test
  public void testUnmarshall() throws Exception {
    final InputStream is = LiquiTickerJSONTest.class.getResourceAsStream("/trade/example-cancelorder-data.json");

    final ObjectMapper mapper = new ObjectMapper();
    final LiquiCancelOrderResult cancelOrderResult = mapper.readValue(is, LiquiCancelOrderResult.class);
    assertThat(cancelOrderResult.isSuccess()).isTrue();

    assertThat(cancelOrderResult.getResult().getOrderId()).isEqualTo(108998425L);
    assertThat(cancelOrderResult.getResult().getFunds().get("trx")).isEqualTo(new BigDecimal("500.0"));
    assertThat(cancelOrderResult.getResult().getFunds().get("btc")).isEqualTo(new BigDecimal("0.0"));
  }
}
