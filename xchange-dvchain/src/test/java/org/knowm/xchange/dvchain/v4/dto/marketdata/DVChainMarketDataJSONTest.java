package org.knowm.xchange.dvchain.v4.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.dvchain.dto.marketdata.DVChainLevel;
import org.knowm.xchange.dvchain.dto.marketdata.DVChainMarketResponse;

class DVChainMarketDataJSONTest {

  @Test
  void unmarshal() throws Exception {

    // Read in the JSON from the example resources
    InputStream is =
        DVChainMarketDataJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/dvchain/v4/marketdata/example-prices-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    DVChainMarketResponse readValue = mapper.readValue(is, DVChainMarketResponse.class);

    assertThat(readValue.getMarketData().keySet().contains("BTC")).isTrue();
    assertThat(readValue.getMarketData().keySet().contains("ETH")).isTrue();
    assertThat(readValue.getMarketData().get("BTC").getExpiresAt().longValue())
        .isEqualTo(1539626145342L);
    assertThat(readValue.getMarketData().get("ETH").getExpiresAt().longValue())
        .isEqualTo(1539626145341L);

    List<DVChainLevel> levels = readValue.getMarketData().get("BTC").getLevels();

    assertThat(new BigDecimal("6218.61")).isEqualTo(levels.get(0).getBuyPrice());
    assertThat(new BigDecimal("6181.4")).isEqualTo(levels.get(0).getSellPrice());
    assertThat(new BigDecimal("1")).isEqualTo(levels.get(0).getMaxQuantity());

    assertThat(new BigDecimal("6228.63")).isEqualTo(levels.get(1).getBuyPrice());
    assertThat(new BigDecimal("6171.43")).isEqualTo(levels.get(1).getSellPrice());
    assertThat(new BigDecimal("5")).isEqualTo(levels.get(1).getMaxQuantity());

    assertThat(new BigDecimal("6238.66")).isEqualTo(levels.get(2).getBuyPrice());
    assertThat(new BigDecimal("6161.46")).isEqualTo(levels.get(2).getSellPrice());
    assertThat(new BigDecimal("10")).isEqualTo(levels.get(2).getMaxQuantity());
  }
}
