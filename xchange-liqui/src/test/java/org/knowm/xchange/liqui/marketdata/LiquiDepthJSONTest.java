package org.knowm.xchange.liqui.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.knowm.xchange.liqui.dto.marketdata.LiquiDepth;
import org.knowm.xchange.liqui.dto.marketdata.LiquiPublicAsk;
import org.knowm.xchange.liqui.dto.marketdata.LiquiPublicBid;
import org.knowm.xchange.liqui.dto.marketdata.result.LiquiDepthResult;

public class LiquiDepthJSONTest {

  @Test
  public void testUnmarshall() throws Exception {
    final InputStream is =
        LiquiTickerJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/liqui/marketdata/example-depth-data.json");

    final ObjectMapper mapper = new ObjectMapper();
    final LiquiDepthResult depthResult = mapper.readValue(is, LiquiDepthResult.class);
    final Map<String, LiquiDepth> result = depthResult.getResult();

    assertThat(result.get("eth_zec")).isEqualTo(null);

    final LiquiDepth ethBtc = result.get("eth_btc");
    final List<LiquiPublicAsk> ethAsks = ethBtc.getAsks();
    assertThat(ethAsks.get(0).getPrice()).isEqualTo(new BigDecimal("0.05282309"));
    assertThat(ethAsks.get(1).getPrice()).isEqualTo(new BigDecimal("0.05282838"));
    assertThat(ethAsks.get(2).getPrice()).isEqualTo(new BigDecimal("0.05284231"));

    assertThat(ethAsks.get(0).getVolume()).isEqualTo(new BigDecimal("3.64594274"));
    assertThat(ethAsks.get(1).getVolume()).isEqualTo(new BigDecimal("0.00191186"));
    assertThat(ethAsks.get(2).getVolume()).isEqualTo(new BigDecimal("0.10936782"));

    final List<LiquiPublicBid> ethBids = ethBtc.getBids();
    assertThat(ethBids.get(0).getPrice()).isEqualTo(new BigDecimal("0.05257814"));
    assertThat(ethBids.get(1).getPrice()).isEqualTo(new BigDecimal("0.05257709"));
    assertThat(ethBids.get(2).getPrice()).isEqualTo(new BigDecimal("0.05234513"));

    assertThat(ethBids.get(0).getVolume()).isEqualTo(new BigDecimal("9.62186081"));
    assertThat(ethBids.get(1).getVolume()).isEqualTo(new BigDecimal("2.67897679"));
    assertThat(ethBids.get(2).getVolume()).isEqualTo(new BigDecimal("0.20048711"));

    final LiquiDepth ltcBtc = result.get("ltc_btc");
    final List<LiquiPublicAsk> ltcAsks = ltcBtc.getAsks();
    assertThat(ltcAsks.get(0).getPrice()).isEqualTo(new BigDecimal("0.00949897"));
    assertThat(ltcAsks.get(1).getPrice()).isEqualTo(new BigDecimal("0.00950312"));
    assertThat(ltcAsks.get(2).getPrice()).isEqualTo(new BigDecimal("0.00950313"));

    assertThat(ltcAsks.get(0).getVolume()).isEqualTo(new BigDecimal("0.33874761"));
    assertThat(ltcAsks.get(1).getVolume()).isEqualTo(new BigDecimal("44.4"));
    assertThat(ltcAsks.get(2).getVolume()).isEqualTo(new BigDecimal("1.27266461"));

    final List<LiquiPublicBid> ltcBids = ltcBtc.getBids();
    assertThat(ltcBids.get(0).getPrice()).isEqualTo(new BigDecimal("0.00942261"));
    assertThat(ltcBids.get(1).getPrice()).isEqualTo(new BigDecimal("0.0094226"));
    assertThat(ltcBids.get(2).getPrice()).isEqualTo(new BigDecimal("0.00941349"));

    assertThat(ltcBids.get(0).getVolume()).isEqualTo(new BigDecimal("1.0506643"));
    assertThat(ltcBids.get(1).getVolume()).isEqualTo(new BigDecimal("10.612782"));
    assertThat(ltcBids.get(2).getVolume()).isEqualTo(new BigDecimal("1.3852986"));
  }
}
