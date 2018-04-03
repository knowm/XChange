package org.knowm.xchange.liqui.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;
import org.junit.Test;
import org.knowm.xchange.liqui.dto.marketdata.LiquiPairInfo;
import org.knowm.xchange.liqui.dto.marketdata.result.LiquiInfoResult;

public class LiquiInfoJSONTest {

  @Test
  public void testUnmarshall() throws Exception {
    final InputStream is =
        LiquiTickerJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/liqui/marketdata/example-info-data.json");

    final ObjectMapper mapper = new ObjectMapper();
    final LiquiInfoResult infoResult = mapper.readValue(is, LiquiInfoResult.class);
    final Map<String, LiquiPairInfo> result = infoResult.getResult();

    assertThat(result.get("eth_zec")).isEqualTo(null);
    assertThat(infoResult.getServerTime()).isEqualTo(1509054237L);

    final LiquiPairInfo ethBtc = result.get("eth_btc");
    assertThat(ethBtc.getDecimalPlaces()).isEqualTo(8);
    assertThat(ethBtc.getFee()).isEqualTo(new BigDecimal("0.25"));
    assertThat(ethBtc.getMaxAmount()).isEqualTo(new BigDecimal("1000000000.0"));
    assertThat(ethBtc.getMaxPrice()).isEqualTo(new BigDecimal("1.0"));
    assertThat(ethBtc.getMinAmount()).isEqualTo(new BigDecimal("0.00000001"));
    assertThat(ethBtc.getMinPrice()).isEqualTo(new BigDecimal("0.00000001"));
    assertThat(ethBtc.getMinTotal()).isEqualTo(new BigDecimal("0.0001"));
    assertThat(ethBtc.isHidden()).isFalse();

    final LiquiPairInfo ltcBtc = result.get("ltc_btc");
    assertThat(ltcBtc.getDecimalPlaces()).isEqualTo(8);
    assertThat(ltcBtc.getFee()).isEqualTo(new BigDecimal("0.25"));
    assertThat(ltcBtc.getMaxAmount()).isEqualTo(new BigDecimal("1000000.0"));
    assertThat(ltcBtc.getMaxPrice()).isEqualTo(new BigDecimal("1.0"));
    assertThat(ltcBtc.getMinAmount()).isEqualTo(new BigDecimal("0.00000001"));
    assertThat(ltcBtc.getMinPrice()).isEqualTo(new BigDecimal("0.00000001"));
    assertThat(ltcBtc.getMinTotal()).isEqualTo(new BigDecimal("0.0001"));
    assertThat(ltcBtc.isHidden()).isFalse();
  }
}
