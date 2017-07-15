package org.knowm.xchange.dsx.dto.trade;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import org.junit.Test;
import org.knowm.xchange.dsx.dto.DSXReturn;
import org.knowm.xchange.dsx.dto.account.DSXCurrencyAmount;

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
    assertThat(rv.keySet()).containsAll(Collections.singletonList(956L));
    assertThat(rv.get(956L).getTimestampCreated()).isEqualTo(142123698L);
  }

  @Test
  public void testPlaceOrder() throws IOException {

    DSXTradeReturn result = getResult("/trade/example-place-order-data.json", DSXTradeReturn.class);

    DSXTradeResult rv = result.getReturnValue();
    assertThat(rv.getFunds().keySet().containsAll(Arrays.asList("BTC", "USD", "EUR", "LTC"))).isTrue();
    assertThat(rv.getFunds().get("BTC")).isEqualsToByComparingFields(new DSXCurrencyAmount(new BigDecimal("100"), new BigDecimal("95")));
    assertThat(rv.getOrderId()).isEqualTo(1067L);
  }

  @Test
  public void testCancelOrder() throws IOException {
    DSXCancelOrderReturn result = getResult("/trade/example-cancel-order-data.json", DSXCancelOrderReturn.class);

    DSXCancelOrderResult rv = result.getReturnValue();
    Map<String, DSXCurrencyAmount> funds = rv.getFunds();
    assertThat(funds.keySet().containsAll(Arrays.asList("BTC", "USD", "EUR", "LTC"))).isTrue();
    assertThat(funds.get("USD").getAvailable()).isEqualTo(new BigDecimal("9995"));
    assertThat(rv.getOrderId()).isEqualTo(1L);
  }

  private <RC extends DSXReturn> RC getResult(String file, Class<RC> resultClass) throws IOException {

    InputStream is = DSXTradeDataJSONTest.class.getResourceAsStream(file);

    return new ObjectMapper().readValue(is, resultClass);
  }
}
