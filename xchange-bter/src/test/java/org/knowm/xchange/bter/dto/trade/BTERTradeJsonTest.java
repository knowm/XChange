package org.knowm.xchange.bter.dto.trade;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;
import org.knowm.xchange.bter.dto.BTEROrderType;
import org.knowm.xchange.currency.CurrencyPair;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BTERTradeJsonTest {

  @Test
  public void testDeserializeOrderList() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BTERTradeJsonTest.class.getResourceAsStream("/trade/example-order-list-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTEROpenOrders openOrders = mapper.readValue(is, BTEROpenOrders.class);

    assertThat(openOrders.isResult()).isTrue();
    assertThat(openOrders.getMessage()).isEqualTo("Success");

    List<BTEROpenOrder> openOrderList = openOrders.getOrders();
    assertThat(openOrderList).hasSize(1);

    BTEROpenOrder openOrder = openOrderList.get(0);
    assertThat(openOrder.getId()).isEqualTo("12941907");
    assertThat(openOrder.getSellCurrency()).isEqualTo("LTC");
    assertThat(openOrder.getBuyCurrency()).isEqualTo("BTC");
    assertThat(openOrder.getSellAmount()).isEqualTo("0.384");
    assertThat(openOrder.getBuyAmount()).isEqualTo("0.010176");
  }

  @Test
  public void testDeserializeOrderResult() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BTERTradeJsonTest.class.getResourceAsStream("/trade/example-order-result-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTERPlaceOrderReturn orderReturn = mapper.readValue(is, BTERPlaceOrderReturn.class);

    assertThat(orderReturn.isResult()).isTrue();
    assertThat(orderReturn.getMessage()).isEqualTo("Success");
    assertThat(orderReturn.getOrderId()).isEqualTo("123456");
  }

  @Test
  public void testDeserializeOrderStatus() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = BTERTradeJsonTest.class.getResourceAsStream("/trade/example-order-status-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BTEROrderStatus orderStatus = mapper.readValue(is, BTEROrderStatus.class);

    assertThat(orderStatus.isResult()).isTrue();
    assertThat(orderStatus.getMessage()).isEqualTo("Success");
    assertThat(orderStatus.getId()).isEqualTo("12942570");
    assertThat(orderStatus.getStatus()).isEqualTo("open");
    assertThat(orderStatus.getCurrencyPair()).isEqualTo(CurrencyPair.LTC_BTC);
    assertThat(orderStatus.getType()).isEqualTo(BTEROrderType.SELL);
    assertThat(orderStatus.getRate()).isEqualTo("0.0265");
    assertThat(orderStatus.getAmount()).isEqualTo("0.384");
    assertThat(orderStatus.getInitialRate()).isEqualTo("0.0265");
    assertThat(orderStatus.getInitialAmount()).isEqualTo("0.384");
  }
}
