/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.kraken.service.trading;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.kraken.dto.trade.KrakenOrder;
import com.xeiam.xchange.kraken.dto.trade.KrakenOrderDescription;
import com.xeiam.xchange.kraken.dto.trade.KrakenOrderResponse;
import com.xeiam.xchange.kraken.dto.trade.KrakenOrderStatus;
import com.xeiam.xchange.kraken.dto.trade.KrakenOrderType;
import com.xeiam.xchange.kraken.dto.trade.KrakenTrade;
import com.xeiam.xchange.kraken.dto.trade.KrakenType;
import com.xeiam.xchange.kraken.dto.trade.results.KrakenCancelOrderResult;
import com.xeiam.xchange.kraken.dto.trade.results.KrakenCancelOrderResult.KrakenCancelOrderResponse;
import com.xeiam.xchange.kraken.dto.trade.results.KrakenOpenOrdersResult;
import com.xeiam.xchange.kraken.dto.trade.results.KrakenOrderResult;
import com.xeiam.xchange.kraken.dto.trade.results.KrakenTradeHistoryResult;
import com.xeiam.xchange.kraken.dto.trade.results.KrakenTradeHistoryResult.KrakenTradeHistory;

public class KrakenTradeJsonTests {

  @Test
  public void testOrderUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = KrakenTradeJsonTests.class.getResourceAsStream("/trading/example-openorders-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenOpenOrdersResult krakenResult = mapper.readValue(is, KrakenOpenOrdersResult.class);
    Entry<String, KrakenOrder> openOrderEntry = krakenResult.getResult().getOrders().entrySet().iterator().next();
    KrakenOrder order = openOrderEntry.getValue();
    
    // Verify that the example data was unmarshalled correctly
    assertThat(openOrderEntry.getKey()).isEqualTo("OR6QMM-BCKM4-Q6YHIN");
    assertThat(order.getOpenTimestamp()).isEqualTo(1380586080.222);
    assertThat(order.getPrice()).isEqualTo("0.00000");
    assertThat(order.getVolume()).isEqualTo("0.01000000");
    assertThat(order.getVolumeExecuted()).isEqualTo("0.00000000");
    assertThat(order.getStatus()).isEqualTo(KrakenOrderStatus.OPEN);
    KrakenOrderDescription orderDescription = order.getOrderDescription();
    assertThat(orderDescription.getAssetPair()).isEqualTo("LTCEUR");
    assertThat(orderDescription.getLeverage()).isEqualTo("none");
    assertThat(orderDescription.getOrderDescription()).isEqualTo("buy 0.01000000 LTCEUR @ limit 13.00000");
    assertThat(orderDescription.getOrderType()).isEqualTo(KrakenOrderType.LIMIT);
    assertThat(orderDescription.getType()).isEqualTo(KrakenType.BUY);
    assertThat(orderDescription.getPrice()).isEqualTo("13.00000");
    assertThat(orderDescription.getSecondaryPrice()).isEqualTo("0");

  }
  
  @Test
  public void testTradeHistoryUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = KrakenTradeJsonTests.class.getResourceAsStream("/trading/example-tradehistory-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenTradeHistoryResult krakenResult = mapper.readValue(is, KrakenTradeHistoryResult.class);
    KrakenTradeHistory krakenTradeHistory = krakenResult.getResult();
    Map<String, KrakenTrade> krakenTradeHistoryMap = krakenTradeHistory.getTrades();
    KrakenTrade trade = krakenTradeHistoryMap.get("TY5BYV-WJUQF-XPYEYD");
    
    assertThat(trade.getAssetPair()).isEqualTo("XXBTXLTC");
    assertThat(trade.getPrice()).isEqualTo("32.07562");
    assertThat(trade.getCost()).isEqualTo("16.03781");
    assertThat(trade.getFee()).isEqualTo("0.03208");
    assertThat(trade.getMargin()).isEqualTo("0.00000");
    assertThat(trade.getVolume()).isEqualTo("0.50000000");
    assertThat(trade.getOrderTxId()).isEqualTo("ONRNOX-DVI4W-76DL6Q");
    assertThat(trade.getUnixTimestamp()).isEqualTo(1389071942.2089);
    assertThat(trade.getType()).isEqualTo(KrakenType.SELL);
    assertThat(trade.getOrderType()).isEqualTo(KrakenOrderType.MARKET);
    assertThat(trade.getMiscellaneous()).isEqualTo("");
  }
  
  @Test
  public void testCancelOrderUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = KrakenTradeJsonTests.class.getResourceAsStream("/trading/example-cancelorder-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenCancelOrderResult krakenResult = mapper.readValue(is, KrakenCancelOrderResult.class);
    KrakenCancelOrderResponse cancelOrderResponse = krakenResult.getResult();
    
    assertThat(cancelOrderResponse.getCount()).isEqualTo(1);
    assertFalse(cancelOrderResponse.isPending());
  }
  
  @Test
  public void testAddOrderResponseUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = KrakenTradeJsonTests.class.getResourceAsStream("/trading/example-addorder-response-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenOrderResult krakenResult = mapper.readValue(is, KrakenOrderResult.class);
    KrakenOrderResponse orderResponse = krakenResult.getResult();
    
    assertThat(orderResponse.getDescription().getOrderDescription()).isEqualTo("sell 0.01000000 XBTLTC @ limit 100.00000");
    assertThat(orderResponse.getTransactionId()).isNull();
  }
}
