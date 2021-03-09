package org.knowm.xchange.bitcoinde.v4.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;

/** @author matthewdowney */
public class BitcoindeCompactOrderbookWrapperTest {

  @Test
  public void testBitcoindeCompactOrderbookWrapper()
      throws JsonParseException, JsonMappingException, IOException {
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
