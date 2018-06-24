package org.knowm.xchange.liqui.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.liqui.dto.LiquiTradeType;
import org.knowm.xchange.liqui.dto.trade.LiquiOrderInfo;
import org.knowm.xchange.liqui.dto.trade.result.LiquiOrderInfoResult;
import org.knowm.xchange.liqui.marketdata.LiquiTickerJSONTest;

public class LiquiOrderInfoJSONTest {

  @Test
  public void testUnmarshall() throws Exception {
    final InputStream is =
        LiquiTickerJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/liqui/marketdata/example-orderinfo-data.json");

    final ObjectMapper mapper = new ObjectMapper();
    final LiquiOrderInfoResult orderInfoResult = mapper.readValue(is, LiquiOrderInfoResult.class);
    assertThat(orderInfoResult.isSuccess()).isTrue();

    final Map<Long, LiquiOrderInfo> orders = orderInfoResult.getResult();
    final LiquiOrderInfo order = orders.get(108998425L);
    assertThat(order).isNotNull();

    assertThat(order.getPair()).isEqualTo(new CurrencyPair("trx", "btc"));
    assertThat(order.getType()).isEqualTo(LiquiTradeType.SELL);
    assertThat(order.getAmount()).isEqualTo(new BigDecimal("1.0"));
    assertThat(order.getStartAmount()).isEqualTo(new BigDecimal("1.0"));
    assertThat(order.getRate()).isEqualTo(new BigDecimal("0.0001"));
    assertThat(order.getTimestampCreated()).isEqualTo(1509885443L);
  }
}
