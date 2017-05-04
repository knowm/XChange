package org.knowm.xchange.btce.v3.dto.trade;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

import org.junit.Test;
import org.knowm.xchange.btce.v3.dto.BTCEReturn;
import org.knowm.xchange.btce.v3.dto.trade.BTCETradeHistoryResult.Type;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test BTCETradeData JSON parsing
 */
public class BTCETradeDataJSONTest {

  @Test
  public void testOpenOrders() throws IOException {

    BTCEOpenOrdersReturn result = getResult("/v3/trade/example-open-orders-data.json", BTCEOpenOrdersReturn.class);
    // Verify that the example data was unmarshalled correctly
    Map<Long, BTCEOrder> rv = result.getReturnValue();
    assertThat(rv.keySet()).containsAll(Arrays.asList(343152L));
    assertThat(rv.get(343152L).getTimestampCreated()).isEqualTo(1342448420L);
  }
  
  @Test
  public void testOrderInfo() throws IOException {

    BTCEOrderInfoReturn result = getResult("/v3/trade/example-order-info-data.json", BTCEOrderInfoReturn.class);
    // Verify that the example data was unmarshalled correctly
    Map<Long, BTCEOrderInfoResult> rv = result.getReturnValue();
    assertThat(rv.keySet()).containsAll(Arrays.asList(343152L));
    assertThat(rv.get(343152L).getTimestampCreated()).isEqualTo(1342448420L);
    assertThat(rv.get(343152L).getStartAmount()).isEqualTo(new BigDecimal("2.00000000"));
  }

  @Test
  public void testOwnTransactions() throws IOException {

    BTCETradeHistoryReturn result = getResult("/v3/trade/example-trade-history-data.json", BTCETradeHistoryReturn.class);
    // Verify that the example data was unmarshalled correctly
    Map<Long, BTCETradeHistoryResult> rv = result.getReturnValue();
    assertThat(rv.keySet()).containsAll(Arrays.asList(7258275L, 7160193L));

    BTCETradeHistoryResult trade = rv.get(7258275L);
    assertThat(trade.getPair()).isEqualTo("btc_usd");
    assertThat(trade.getType()).isEqualTo(Type.sell);
    assertThat(trade.getAmount()).isEqualTo(new BigDecimal("0.1"));
    assertThat(trade.getOrderId()).isEqualTo(34870919L);
    assertThat(trade.isYourOrder()).isEqualTo(false);
    assertThat(trade.getTimestamp()).isEqualTo(1378194574L);
  }

  @Test
  public void testCancelOrder() throws IOException {

    BTCECancelOrderReturn result = getResult("/v3/trade/example-cancel-order-data.json", BTCECancelOrderReturn.class);
    // Verify that the example data was unmarshalled correctly
    BTCECancelOrderResult rv = result.getReturnValue();
    Map<String, BigDecimal> funds = rv.getFunds();
    assertThat(funds.keySet().containsAll(Arrays.asList("btc", "nmc", "usd"))).isTrue();
    assertThat(funds.get("usd")).isEqualTo(new BigDecimal(325));
    assertThat(rv.getOrderId()).isEqualTo(343154L);
  }

  @Test
  public void testPlaceOrder() throws IOException {

    BTCEPlaceOrderReturn result = getResult("/v3/trade/example-place-order-data.json", BTCEPlaceOrderReturn.class);
    // Verify that the example data was unmarshalled correctly
    BTCEPlaceOrderResult rv = result.getReturnValue();
    Map<String, BigDecimal> funds = rv.getFunds();
    assertThat(funds.keySet().containsAll(Arrays.asList("btc", "nmc", "usd"))).isTrue();
    assertThat(funds.get("btc")).isEqualTo(new BigDecimal("2.498"));
    assertThat(rv.getOrderId()).isEqualTo(0L);
  }

  private <RC extends BTCEReturn> RC getResult(String file, Class<RC> resultClass) throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BTCETradeDataJSONTest.class.getResourceAsStream(file);

    // Use Jackson to parse it
    return new ObjectMapper().readValue(is, resultClass);
  }
}
