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
package com.xeiam.xchange.cryptsy.dto.trade;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.cryptsy.dto.CryptsyOrder.CryptsyOrderType;

public class CryptsyTradeJsonTests {

  @Test
  public void testDeserializeSingleMarketTradeHistory() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptsyTradeJsonTests.class.getResourceAsStream("/trade/Sample_MyTrades_Data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    List<CryptsyTradeHistory> cryptsyTradeHistory = mapper.readValue(is, CryptsyTradeHistoryReturn.class).getReturnValue();

    CryptsyTradeHistory tradeHistory = cryptsyTradeHistory.get(0);

    assertEquals(tradeHistory.getOrderId(), 57779327);
    assertEquals(tradeHistory.getTradeId(), 29288586);
    assertEquals(tradeHistory.getTradeType(), CryptsyOrderType.Sell);
    assertEquals(tradeHistory.getInitiatingOrderType(), CryptsyOrderType.Buy);
    assertEquals(tradeHistory.getFee(), new BigDecimal("0.000045970"));
    assertEquals(tradeHistory.getPrice(), new BigDecimal("0.00011585"));
    assertEquals(tradeHistory.getQuantity(), new BigDecimal("132.27369285"));
    assertEquals(tradeHistory.getTotal(), new BigDecimal("0.01532391"));
  }

  @Test
  public void testDeserializeTradeHistory() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptsyTradeJsonTests.class.getResourceAsStream("/trade/Sample_AllMyTrades_Data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    List<CryptsyTradeHistory> cryptsyTradeHistory = mapper.readValue(is, CryptsyTradeHistoryReturn.class).getReturnValue();

    CryptsyTradeHistory tradeHistory = cryptsyTradeHistory.get(0);

    assertEquals(tradeHistory.getOrderId(), 57779327);
    assertEquals(tradeHistory.getTradeId(), 29288586);
    assertEquals(tradeHistory.getTradeType(), CryptsyOrderType.Sell);
    assertEquals(tradeHistory.getInitiatingOrderType(), CryptsyOrderType.Buy);
    assertEquals(tradeHistory.getFee(), new BigDecimal("0.000045970"));
    assertEquals(tradeHistory.getPrice(), new BigDecimal("0.00011585"));
    assertEquals(tradeHistory.getQuantity(), new BigDecimal("132.27369285"));
    assertEquals(tradeHistory.getTotal(), new BigDecimal("0.01532391"));
  }

  @Test
  public void testDeserializeSingleMarketOpenOrders() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptsyTradeJsonTests.class.getResourceAsStream("/trade/Sample_MyOrders_Data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    List<CryptsyOpenOrders> cryptsyOpenOrders = mapper.readValue(is, CryptsyOpenOrdersReturn.class).getReturnValue();

    CryptsyOpenOrders openOrder = cryptsyOpenOrders.get(0);

    assertEquals(openOrder.getOrderId(), 90039904);
    assertEquals(openOrder.getTradeType(), CryptsyOrderType.Buy);
    assertEquals(openOrder.getPrice(), new BigDecimal("0.00000001"));
    assertEquals(openOrder.getQuantityRemaining(), new BigDecimal("50000.00000000"));
    assertEquals(openOrder.getOriginalQuantity(), new BigDecimal("50051.00000000"));
    assertEquals(openOrder.getTotal(), new BigDecimal("0.00050000"));
  }

  @Test
  public void testDeserializeOpenOrders() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptsyTradeJsonTests.class.getResourceAsStream("/trade/Sample_AllMyOrders_Data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    List<CryptsyOpenOrders> cryptsyOpenOrders = mapper.readValue(is, CryptsyOpenOrdersReturn.class).getReturnValue();

    CryptsyOpenOrders openOrder = cryptsyOpenOrders.get(0);

    assertEquals(openOrder.getOrderId(), 90039904);
    assertEquals(openOrder.getMarketId(), 14);
    assertEquals(openOrder.getTradeType(), CryptsyOrderType.Buy);
    assertEquals(openOrder.getPrice(), new BigDecimal("0.00000001"));
    assertEquals(openOrder.getQuantityRemaining(), new BigDecimal("50000.10000000"));
    assertEquals(openOrder.getOriginalQuantity(), new BigDecimal("50051.00000000"));
    assertEquals(openOrder.getTotal(), new BigDecimal("0.00050000"));
  }

  @Test
  public void testDeserializePlaceOrder() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptsyTradeJsonTests.class.getResourceAsStream("/trade/Sample_PlaceOrder_Data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    int cryptsyOrderId = mapper.readValue(is, CryptsyPlaceOrderReturn.class).getReturnValue();

    assertEquals(cryptsyOrderId, 90042026);
  }

  @Test
  public void testDeserializeCancelOrder() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptsyTradeJsonTests.class.getResourceAsStream("/trade/Sample_CancelOrder_Data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    String cryptsyCancelOrder = mapper.readValue(is, CryptsyCancelOrderReturn.class).getReturnValue();

    assertEquals(cryptsyCancelOrder, "Your order #90042026 has been cancelled.");
  }

  @Test
  public void testDeserializeCancelMarketSpecificOrders() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptsyTradeJsonTests.class.getResourceAsStream("/trade/Sample_CancelMarketOrders_Data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    List<String> crytpsyCanceledOrderList = mapper.readValue(is, CryptsyCancelMultipleOrdersReturn.class).getReturnValue();

    for (String returnString : crytpsyCanceledOrderList) {
      assertEquals(returnString.matches("Your order #[0-9]{8} has been cancelled."), true);
    }
  }

  @Test
  public void testDeserializeCancelAllrders() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptsyTradeJsonTests.class.getResourceAsStream("/trade/Sample_CancelAllOrders_Data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    List<String> crytpsyCanceledOrderList = mapper.readValue(is, CryptsyCancelMultipleOrdersReturn.class).getReturnValue();

    for (String returnString : crytpsyCanceledOrderList) {
      assertEquals(returnString.matches("Your order #[0-9]{8} has been cancelled."), true);
    }
  }

  @Test
  public void testDeserializeCalculateFees() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptsyTradeJsonTests.class.getResourceAsStream("/trade/Sample_CalculateFees_Data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptsyCalculatedFees cryptsyCalculatedFees = mapper.readValue(is, CryptsyCalculatedFeesReturn.class).getReturnValue();

    assertEquals(cryptsyCalculatedFees.getFee(), new BigDecimal("0.09282000"));
    assertEquals(cryptsyCalculatedFees.getNet(), new BigDecimal("46.50282000"));
  }
}
