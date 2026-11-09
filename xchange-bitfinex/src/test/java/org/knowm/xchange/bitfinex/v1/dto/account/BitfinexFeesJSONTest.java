package org.knowm.xchange.bitfinex.v1.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

/** Test JSON parsing for Bitfinex fees response */
public class BitfinexFeesJSONTest {

  @Test
  void unmarshal() throws Exception {

    // Read in the JSON from the example resources
    InputStream is =
        BitfinexFeesJSONTest.class.getResourceAsStream(
            "/v1/account/example-account-info-fees.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    BitfinexTradingFeeResponse[] readValues =
        mapper.readValue(is, BitfinexTradingFeeResponse[].class);
    assertThat(readValues.length).isEqualTo(2);
    BitfinexTradingFeeResponse readValue = readValues[0];
    BitfinexTradingFeeResponse.BitfinexTradingFeeResponseRow[] responseRows =
        readValue.getTradingFees();
    BigDecimal point1 = BigDecimal.ONE.divide(new BigDecimal(10));
    BigDecimal point2 = point1.multiply(new BigDecimal(2));
    assertThat(responseRows.length).isEqualTo(3);
    assertThat(responseRows[0].getCurrency()).isEqualTo("BTC");
    assertThat(responseRows[1].getCurrency()).isEqualTo("LTC");
    assertThat(responseRows[2].getCurrency()).isEqualTo("ETH");
    for (BitfinexTradingFeeResponse.BitfinexTradingFeeResponseRow responseRow : responseRows) {
      assertThat(responseRow.getMakerFee()).isEqualTo(point1);
      assertThat(responseRow.getTakerFee()).isEqualTo(point2);
    }

    readValue = readValues[1];
    responseRows = readValue.getTradingFees();
    BigDecimal point025 = new BigDecimal(25).divide(new BigDecimal(1000));
    BigDecimal point01 = BigDecimal.ONE.divide(new BigDecimal(100));
    assertThat(responseRows.length).isEqualTo(1);
    assertThat(responseRows[0].getCurrency()).isEqualTo("DGC");
    assertThat(responseRows[0].getMakerFee()).isEqualTo(point025);
    assertThat(responseRows[0].getTakerFee()).isEqualTo(point01);
  }
}
