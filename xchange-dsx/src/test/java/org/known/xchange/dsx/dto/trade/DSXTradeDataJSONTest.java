package org.known.xchange.dsx.dto.trade;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

import org.junit.Test;
import org.known.xchange.dsx.dto.DSXReturn;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test DSXTradeData JSON parsing
 *
 * @author Mikhail Wall
 */
public class DSXTradeDataJSONTest {

  @Test
  public void testOpenOrders() throws IOException {

    DSXActiveOrdersReturn result = getResult("/trade/example-open-orders-data.json", DSXActiveOrdersReturn.class);
    Map<Long, DSXOrder> rv = result.getReturnValue();
    assertThat(rv.keySet()).containsAll(Arrays.asList(956L));
    assertThat(rv.get(956L).getTimestampCreated()).isEqualTo(142123698L);
  }

  @Test
  public void testPlaceOrder() throws IOException {

    DSXTradeReturn result = getResult("/trade/example-place-order-data.json", DSXTradeReturn.class);

    DSXTradeResult rv = result.getReturnValue();
    Map<String, BigDecimal> funds = rv.getFunds();
    assertThat(funds.keySet().containsAll(Arrays.asList("btc", "ltc", "eur", "rub", "usd"))).isTrue();
    assertThat(funds.get("btc")).isEqualTo(new BigDecimal("3.75"));
    assertThat(rv.getOrderId()).isEqualTo(1067L);
  }

  @Test
  public void testCancelOrder() throws IOException {
    DSXCancelOrderReturn result = getResult("/trade/example-cancel-order-data.json", DSXCancelOrderReturn.class);

    DSXCancelOrderResult rv = result.getReturnValue();
    Map<String, BigDecimal> funds = rv.getFunds();
    assertThat(funds.keySet().containsAll(Arrays.asList("btc", "usd", "ltc", "eur")));
    assertThat(funds.get("usd")).isEqualTo(new BigDecimal("325"));
    assertThat(rv.getOrderId()).isEqualTo(1067L);
  }
  private <RC extends DSXReturn> RC getResult(String file, Class<RC> resultClass) throws IOException {

    InputStream is = DSXTradeDataJSONTest.class.getResourceAsStream(file);

    return new ObjectMapper().readValue(is, resultClass);
  }
}
