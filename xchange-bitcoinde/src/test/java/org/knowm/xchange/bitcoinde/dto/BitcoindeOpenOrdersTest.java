package org.knowm.xchange.bitcoinde.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.bitcoinde.trade.BitcoindeMyOpenOrdersWrapper;
import org.knowm.xchange.bitcoinde.trade.BitcoindeMyOrder;

class BitcoindeOpenOrdersTest {

  @Test
  void bitcoindeOpenOrders() throws Exception {

    // Read in the JSON from the example resources
    InputStream is =
        BitcoindeOpenOrdersTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitcoinde/dto/orders.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcoindeMyOpenOrdersWrapper bitcoindeOpenOrdersWrapper =
        mapper.readValue(is, BitcoindeMyOpenOrdersWrapper.class);
    //    System.out.println("bitcoindeTradesWrapper = " + bitcoindeOpenOrdersWrapper);

    // Make sure trade values are correct

    List<BitcoindeMyOrder> orders = bitcoindeOpenOrdersWrapper.getOrders();
    BitcoindeMyOrder order = orders.get(0);

    assertThat(orders.size()).isEqualTo(1);
    assertThat(order.getOrderId()).isEqualTo("VNSP86");
  }
}
