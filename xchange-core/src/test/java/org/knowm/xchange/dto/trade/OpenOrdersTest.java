package org.knowm.xchange.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.utils.ObjectMapperHelper;

public class OpenOrdersTest {

  @Test
  public void testSerializationDeserialization() throws IOException {
    LimitOrder limitOrder =
        new LimitOrder.Builder(OrderType.ASK, CurrencyPair.ADA_BNB).id("FOO").build();
    StopOrder stopOrder =
        new StopOrder.Builder(OrderType.ASK, CurrencyPair.ADA_BNB).id("BAR").build();
    List<LimitOrder> visibleOrders = new ArrayList<>();
    List<Order> hiddenOrders = new ArrayList<>();
    visibleOrders.add(limitOrder);
    hiddenOrders.add(stopOrder);
    OpenOrders openOrders = new OpenOrders(visibleOrders, hiddenOrders);
    OpenOrders jsonCopy = ObjectMapperHelper.viaJSON(openOrders);
    assertThat(jsonCopy.getOpenOrders()).isEqualTo(openOrders.getOpenOrders());
    assertThat(jsonCopy.getHiddenOrders()).isEqualTo(openOrders.getHiddenOrders());
  }
}
