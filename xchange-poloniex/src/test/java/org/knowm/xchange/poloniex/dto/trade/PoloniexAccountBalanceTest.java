package org.knowm.xchange.poloniex.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;
import org.junit.Test;

public class PoloniexAccountBalanceTest {

  @Test
  public void testUnmarshal() throws IOException {
    // Read in the JSON from the example resources
    InputStream is =
        PoloniexAccountBalanceTest.class.getResourceAsStream(
            "/org/knowm/xchange/poloniex/dto/trade/order-balances.json");

    ObjectMapper mapper = new ObjectMapper();
    PoloniexAccountBalance balance = mapper.readValue(is, PoloniexAccountBalance.class);
    Map<String, BigDecimal> exchangeBalance = balance.getExchangeBalance();
    assertThat(exchangeBalance.size()).isEqualTo(6);
    assertThat(exchangeBalance.get("BTC")).isEqualTo(new BigDecimal("1.19042859"));
    assertThat(exchangeBalance.get("BTM")).isEqualTo(new BigDecimal("386.52379392"));
    assertThat(exchangeBalance.get("CHA")).isEqualTo(new BigDecimal("0.50000000"));
    assertThat(exchangeBalance.get("DASH")).isEqualTo(new BigDecimal("120.00000000"));
    assertThat(exchangeBalance.get("STR")).isEqualTo(new BigDecimal("3205.32958001"));
    assertThat(exchangeBalance.get("VNL")).isEqualTo(new BigDecimal("9673.22570147"));

    Map<String, BigDecimal> lendingBalance = balance.getLendingBalance();
    assertThat(lendingBalance.size()).isEqualTo(2);
    assertThat(lendingBalance.get("DASH")).isEqualTo(new BigDecimal("0.01174765"));
    assertThat(lendingBalance.get("LTC")).isEqualTo(new BigDecimal("11.99936230"));

    Map<String, BigDecimal> marginBalance = balance.getMarginBalance();
    assertThat(marginBalance.size()).isEqualTo(3);
    assertThat(marginBalance.get("BTC")).isEqualTo(new BigDecimal("3.90015637"));
    assertThat(marginBalance.get("DASH")).isEqualTo(new BigDecimal("250.00238240"));
    assertThat(marginBalance.get("XMR")).isEqualTo(new BigDecimal("497.12028113"));
  }
}
