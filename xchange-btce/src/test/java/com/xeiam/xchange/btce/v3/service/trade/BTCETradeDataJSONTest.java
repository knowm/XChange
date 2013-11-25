/**
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.btce.v3.service.trade;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.btce.v3.dto.BTCEReturn;
import com.xeiam.xchange.btce.v3.dto.trade.BTCECancelOrderResult;
import com.xeiam.xchange.btce.v3.dto.trade.BTCECancelOrderReturn;
import com.xeiam.xchange.btce.v3.dto.trade.BTCEOpenOrdersReturn;
import com.xeiam.xchange.btce.v3.dto.trade.BTCEOrder;
import com.xeiam.xchange.btce.v3.dto.trade.BTCEPlaceOrderResult;
import com.xeiam.xchange.btce.v3.dto.trade.BTCEPlaceOrderReturn;
import com.xeiam.xchange.btce.v3.dto.trade.BTCETradeHistoryResult;
import com.xeiam.xchange.btce.v3.dto.trade.BTCETradeHistoryResult.Type;
import com.xeiam.xchange.btce.v3.dto.trade.BTCETradeHistoryReturn;

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
