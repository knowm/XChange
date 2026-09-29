package org.knowm.xchange.bitcoinde.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class BitcoindeTradesTest {

  @Test
  void bitcoindeOrderBook() throws Exception {

    // Read in the JSON from the example resources
    InputStream is =
        BitcoindeTradesTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitcoinde/dto/trades.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcoindeTradesWrapper bitcoindeTradesWrapper =
        mapper.readValue(is, BitcoindeTradesWrapper.class);
    //    System.out.println("bitcoindeTradesWrapper = " + bitcoindeTradesWrapper);

    // Make sure trade values are correct

    BitcoindeTrade[] trades = bitcoindeTradesWrapper.getTrades();

    assertThat(trades[0].getDate()).isEqualTo(1500718454L);
    assertThat(new BigDecimal("2391.48")).isEqualTo(trades[0].getPrice());
    assertThat(new BigDecimal("0.90000000")).isEqualTo(trades[0].getAmount());
    assertThat(trades[0].getTid()).isEqualTo(2844384);
  }
}
