package org.knowm.xchange.krakenfutures.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import org.junit.Test;
import org.knowm.xchange.krakenfutures.dto.trade.KrakenFuturesOrderSide;
import org.knowm.xchange.krakenfutures.dto.trade.KrakenFuturesOrderStatus;
import org.knowm.xchange.krakenfutures.dto.trade.KrakenFuturesOrderType;

/** @author Jean-Christophe Laruelle */
public class KrakenFuturesOpenOrdersJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        KrakenFuturesOpenOrdersJSONTest.class.getResourceAsStream(
                "/org/knowm/xchange/krakenfutures/dto/marketdata/example-openOrders-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    KrakenFuturesOpenOrders cryptoFacilitiesOpenOrders =
        mapper.readValue(is, KrakenFuturesOpenOrders.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(cryptoFacilitiesOpenOrders.isSuccess()).isTrue();

    List<KrakenFuturesOpenOrder> orders = cryptoFacilitiesOpenOrders.getOrders();

    assertThat(orders.size()).isEqualTo(2);

    Iterator<KrakenFuturesOpenOrder> it = orders.iterator();
    KrakenFuturesOpenOrder ord = it.next();

    assertThat(ord.getStatus()).isEqualTo(KrakenFuturesOrderStatus.partiallyFilled);
    assertThat(ord.getOrderId()).isEqualTo("c18f0c17-9971-40e6-8e5b-10df05d422f0");
    assertThat(ord.getOrderType()).isEqualTo(KrakenFuturesOrderType.lmt);
    assertThat(ord.getSymbol()).isEqualTo("f-xbt:usd-sep16");
    assertThat(ord.getSide()).isEqualTo(KrakenFuturesOrderSide.sell);
    assertThat(ord.getUnfilledSize()).isEqualTo(new BigDecimal("2"));
    assertThat(ord.getFilledSize()).isEqualTo(new BigDecimal("3"));
    assertThat(ord.getUnfilledSize().add(ord.getFilledSize())).isEqualTo(new BigDecimal("5"));
    assertThat(ord.getLimitPrice()).isEqualTo(new BigDecimal("430.11"));
  }
}
