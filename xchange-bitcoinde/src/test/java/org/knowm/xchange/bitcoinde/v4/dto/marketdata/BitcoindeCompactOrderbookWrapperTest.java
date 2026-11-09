package org.knowm.xchange.bitcoinde.v4.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.junit.jupiter.api.Test;

/**
 * @author matthewdowney
 */
class BitcoindeCompactOrderbookWrapperTest {

  @Test
  void bitcoindeCompactOrderbookWrapper() throws Exception {
    // Read in the JSON from the example resources
    final InputStream is =
        BitcoindeCompactOrderbookWrapperTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitcoinde/v4/dto/compact_orderbook.json");

    // Use Jackson to parse it
    final ObjectMapper mapper = new ObjectMapper();
    final BitcoindeCompactOrderbookWrapper bitcoindeOrderBook =
        mapper.readValue(is, BitcoindeCompactOrderbookWrapper.class);
    final BitcoindeCompactOrders orders = bitcoindeOrderBook.getBitcoindeOrders();

    // Make sure asks are correct
    assertThat(orders.getAsks()[0].getPrice()).isEqualByComparingTo("2461.61");
    assertThat(orders.getAsks()[0].getAmount()).isEqualByComparingTo("0.0406218");

    // Make sure bids are correct
    assertThat(orders.getBids()[0].getPrice()).isEqualByComparingTo("1200");
    assertThat(orders.getBids()[0].getAmount()).isEqualByComparingTo("8.333");
  }
}
