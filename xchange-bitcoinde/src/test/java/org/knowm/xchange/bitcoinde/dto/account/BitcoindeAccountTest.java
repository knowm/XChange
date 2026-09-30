package org.knowm.xchange.bitcoinde.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

/**
 * @author kaiserfr
 */
class BitcoindeAccountTest {

  @Test
  void bitcoindeOrderBook() throws Exception {

    // Read in the JSON from the example resources
    InputStream is =
        BitcoindeAccountTest.class.getResourceAsStream(
            "/org/knowm/xchange/bitcoinde/dto/account.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitcoindeAccountWrapper bitcoindeTradesWrapper =
        mapper.readValue(is, BitcoindeAccountWrapper.class);
    //    System.out.println("bitcoindeTradesWrapper = " + bitcoindeTradesWrapper);

    // Make sure trade values are correct

    BigDecimal btcBalance =
        bitcoindeTradesWrapper.getData().getBalances().getBtc().getAvailableAmount();
    BigDecimal ethBalance =
        bitcoindeTradesWrapper.getData().getBalances().getEth().getAvailableAmount();

    BigDecimal reservedAmount =
        bitcoindeTradesWrapper.getData().getFidorReservation().getAvailableAmount();

    //    System.out.println(btcBalance);
    //    System.out.println(ethBalance);
    //    System.out.println(reservedAmount);

    assertThat(new BigDecimal("0.009")).isEqualTo(btcBalance);
    assertThat(new BigDecimal("0.06463044")).isEqualTo(ethBalance);
    assertThat(new BigDecimal("2000")).isEqualTo(reservedAmount);
  }
}
