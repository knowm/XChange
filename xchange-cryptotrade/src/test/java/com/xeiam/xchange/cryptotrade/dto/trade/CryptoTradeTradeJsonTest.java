package com.xeiam.xchange.cryptotrade.dto.trade;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeOrderType;
import com.xeiam.xchange.cryptotrade.dto.trade.CryptoTradeTrades.CryptoTradeTrade;
import com.xeiam.xchange.currency.CurrencyPair;

public class CryptoTradeTradeJsonTest {

  @Test
  public void testDeserializePlacedBidOrderData() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptoTradeTradeJsonTest.class.getResourceAsStream("/trade/example-placed-order-return-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptoTradePlaceOrderReturn placedOrder = mapper.readValue(is, CryptoTradePlaceOrderReturn.class);

    assertThat(placedOrder.getBought()).isEqualTo("0");
    assertThat(placedOrder.getRemaining()).isEqualTo("1");
    assertThat(placedOrder.getOrderId()).isEqualTo(13);

    Map<String, BigDecimal> funds = placedOrder.getFunds();
    assertThat(funds.size()).isEqualTo(12);
    assertThat(funds.get("usd")).isEqualTo("1535.78614365");

    assertThat(placedOrder.getStatus()).isEqualTo("success");
    assertThat(placedOrder.getError()).isNull();
  }

  @Test
  public void testDeserializePlacedAskOrderData() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptoTradeTradeJsonTest.class.getResourceAsStream("/trade/example-placed-ask-order-return-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptoTradePlaceOrderReturn placedOrder = mapper.readValue(is, CryptoTradePlaceOrderReturn.class);

    assertThat(placedOrder.getSold()).isEqualTo("0");
    assertThat(placedOrder.getRemaining()).isEqualTo("3");
    assertThat(placedOrder.getOrderId()).isEqualTo(19);

    Map<String, BigDecimal> funds = placedOrder.getFunds();
    assertThat(funds.size()).isEqualTo(12);
    assertThat(funds.get("usd")).isEqualTo("1535.78614365");

    assertThat(placedOrder.getStatus()).isEqualTo("success");
    assertThat(placedOrder.getError()).isNull();
  }

  @Test
  public void testDeserializeCancelledOrderData() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptoTradeTradeJsonTest.class.getResourceAsStream("/trade/example-cancel-order-return-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptoTradeCancelOrderReturn cancelledOrder = mapper.readValue(is, CryptoTradeCancelOrderReturn.class);

    assertThat(cancelledOrder.getOrderStatus()).isEqualTo("Cancelled");
    assertThat(cancelledOrder.getOrderId()).isEqualTo(10);

    Map<String, BigDecimal> funds = cancelledOrder.getFunds();
    assertThat(funds.size()).isEqualTo(11);
    assertThat(funds.get("ltc")).isEqualTo("1000");

    assertThat(cancelledOrder.getStatus()).isEqualTo("success");
    assertThat(cancelledOrder.getError()).isNull();
  }

  @Test
  public void testDeserializeTradeHistory() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptoTradeTradeJsonTest.class.getResourceAsStream("/trade/example-trade-history-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptoTradeTrades tradeHistory = mapper.readValue(is, CryptoTradeTrades.class);

    List<CryptoTradeTrade> tradeList = tradeHistory.getTrades();
    assertThat(tradeList.size()).isEqualTo(2);

    CryptoTradeTrade trade = tradeList.get(0);
    assertThat(trade.getId()).isEqualTo(17);
    assertThat(trade.getTimestamp()).isEqualTo(1370965122);
    assertThat(trade.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
    assertThat(trade.getType()).isEqualTo(CryptoTradeOrderType.Sell);
    assertThat(trade.getAmount()).isEqualTo("0.1");
    assertThat(trade.getRate()).isEqualTo("128");
    assertThat(trade.getMyOrder()).isEqualTo(1);
  }

  @Test
  public void testDeserializeOrderHistory() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptoTradeTradeJsonTest.class.getResourceAsStream("/trade/example-order-history-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptoTradeOrders orderHistory = mapper.readValue(is, CryptoTradeOrders.class);

    List<CryptoTradeOrder> orderList = orderHistory.getOrders();
    assertThat(orderList.size()).isEqualTo(3);

    CryptoTradeOrder order = orderList.get(0);
    assertThat(order.getId()).isEqualTo(7);
    assertThat(order.getOrderDate()).isEqualTo(1370952372);
    assertThat(order.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
    assertThat(order.getType()).isEqualTo(CryptoTradeOrderType.Sell);
    assertThat(order.getInitialAmount()).isEqualTo("1");
    assertThat(order.getRemainingAmount()).isEqualTo("0");
    assertThat(order.getRate()).isEqualTo("128.7");
    assertThat(order.getStatus()).isEqualTo("Completed");
  }

  @Test
  public void testDeserializeOrderInfo() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptoTradeTradeJsonTest.class.getResourceAsStream("/trade/example-order-info-return-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptoTradeOrderInfoReturn orderInfo = mapper.readValue(is, CryptoTradeOrderInfoReturn.class);

    CryptoTradeOrder order = orderInfo.getOrder();
    assertThat(order.getId()).isEqualTo(1757393);
    assertThat(order.getOrderDate()).isEqualTo(1410020190);
    assertThat(order.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_USD);
    assertThat(order.getType()).isEqualTo(CryptoTradeOrderType.Sell);
    assertThat(order.getInitialAmount()).isEqualTo("0.0202266");
    assertThat(order.getRemainingAmount()).isEqualTo("0.0202266");
    assertThat(order.getRate()).isEqualTo("524.9998899");
    assertThat(order.getStatus()).isEqualTo("Active");
  }
}
