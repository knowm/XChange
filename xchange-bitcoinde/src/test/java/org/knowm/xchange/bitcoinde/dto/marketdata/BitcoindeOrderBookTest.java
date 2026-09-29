package org.knowm.xchange.bitcoinde.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

/**
 * @author matthewdowney
 */
class BitcoindeOrderBookTest {

  @Test
  void bitcoindeOrderBook() throws Exception {

    // Read in the JSON from the example resources
    InputStream is =
        BitcoindeOrderBookTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitcoinde/dto/orderbook.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcoindeOrderbookWrapper bitcoindeOrderBook =
        mapper.readValue(is, BitcoindeOrderbookWrapper.class);

    // Make sure asks are correct
    assertThat(new BigDecimal("2461.61"))
        .isEqualTo(bitcoindeOrderBook.getBitcoindeOrders().getAsks()[0].getPrice());
    assertThat(new BigDecimal("0.0406218"))
        .isEqualTo(bitcoindeOrderBook.getBitcoindeOrders().getAsks()[0].getAmount());

    // Make sure bids are correct
    assertThat(new BigDecimal("1200"))
        .isEqualTo(bitcoindeOrderBook.getBitcoindeOrders().getBids()[0].getPrice());
    assertThat(new BigDecimal("8.333"))
        .isEqualTo(bitcoindeOrderBook.getBitcoindeOrders().getBids()[0].getAmount());
  }
}
