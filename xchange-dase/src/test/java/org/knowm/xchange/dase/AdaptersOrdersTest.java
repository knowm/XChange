package org.knowm.xchange.dase;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dase.dto.trade.DaseOrder;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;

public class AdaptersOrdersTest {

  @Test
  public void adaptOrder_limit_buy_open() throws Exception {
    InputStream is =
        getClass().getResourceAsStream("/org/knowm/xchange/dase/dto/trade/example-orders.json");
    DaseOrder dto =
        new ObjectMapper()
            .readTree(is)
            .get("orders")
            .elements()
            .next()
            .traverse(new ObjectMapper())
            .readValueAs(DaseOrder.class);

    Order o = DaseAdapters.adaptOrder(dto);
    assertThat(o).isInstanceOf(LimitOrder.class);
    assertThat(o.getInstrument()).isEqualTo(CurrencyPair.BTC_EUR);
    assertThat(o.getType()).isEqualTo(Order.OrderType.BID);
    assertThat(o.getStatus()).isEqualTo(Order.OrderStatus.NEW);
  }

  @Test
  public void adaptOrder_market_sell_closed() {
    DaseOrder dto =
        new DaseOrder(
            "id",
            "pf",
            "ADA-EUR",
            "market",
            "sell",
            "10.0",
            null,
            null,
            "10.0",
            "185.0",
            "18.5",
            "closed",
            null,
            1750000000000L,
            null,
            null);
    Order o = DaseAdapters.adaptOrder(dto);
    assertThat(o).isInstanceOf(MarketOrder.class);
    assertThat(o.getType()).isEqualTo(Order.OrderType.ASK);
    assertThat(o.getStatus()).isEqualTo(Order.OrderStatus.FILLED);
  }
}
