package org.knowm.xchange.dsx.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;
import org.junit.Test;

/** @author Mikhail Wall */
public class DSXTradeHistoryJSONTest {

  @Test
  public void testUnmarhal() throws IOException {

    InputStream is =
        DSXTradeHistoryJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/dsx/dto/trade/example-trade-history-data.json");

    ObjectMapper mapper = new ObjectMapper();
    DSXTradeHistoryReturn transactions = mapper.readValue(is, DSXTradeHistoryReturn.class);
    Map<Long, DSXTradeHistoryResult> result = transactions.getReturnValue();
    assertThat(result.size()).isEqualTo(1);
    Map.Entry<Long, DSXTradeHistoryResult> firstEnrty = result.entrySet().iterator().next();

    assertThat(firstEnrty.getKey()).isEqualTo(1000L);
    assertThat(firstEnrty.getValue().getType()).isEqualTo(DSXTradeHistoryResult.Type.buy);
    assertThat(firstEnrty.getValue().getAmount()).isEqualTo(new BigDecimal("10"));
    assertThat(firstEnrty.getValue().getRate()).isEqualTo(new BigDecimal("300"));
    assertThat(firstEnrty.getValue().getOrderId()).isEqualTo(576L);
    assertThat(firstEnrty.getValue().getTimestamp()).isEqualTo(142123698L);
    assertThat(firstEnrty.getValue().getCommission()).isEqualTo(new BigDecimal("0.001"));
    assertThat(firstEnrty.getValue().getCommissionCurrency()).isEqualTo("USD");
  }
}
