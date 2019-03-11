package org.knowm.xchange.liqui.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;
import org.junit.Test;
import org.knowm.xchange.liqui.dto.marketdata.LiquiTicker;
import org.knowm.xchange.liqui.dto.marketdata.result.LiquiTickersResult;

public class LiquiTickerJSONTest {

  @Test
  public void testUnmarshall() throws Exception {
    final InputStream is =
        LiquiTickerJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/liqui/marketdata/example-ticker-data.json");

    final ObjectMapper mapper = new ObjectMapper();
    final LiquiTickersResult tickersResult = mapper.readValue(is, LiquiTickersResult.class);
    final Map<String, LiquiTicker> tickers = tickersResult.getResult();

    assertThat(tickers.get("eth_zec")).isEqualTo(null);
    final LiquiTicker ethBtc = tickers.get("eth_btc");

    assertThat(ethBtc.getHigh()).isEqualTo(new BigDecimal("0.05252557"));
    assertThat(ethBtc.getLow()).isEqualTo(new BigDecimal("0.04929928"));
    assertThat(ethBtc.getAvg()).isEqualTo(new BigDecimal("0.050912425"));
    assertThat(ethBtc.getVol()).isEqualTo(new BigDecimal("136.01994065407163"));
    assertThat(ethBtc.getVolCur()).isEqualTo(new BigDecimal("2668.93695674"));
    assertThat(ethBtc.getLast()).isEqualTo(new BigDecimal("0.05057958"));
    assertThat(ethBtc.getBuy()).isEqualTo(new BigDecimal("0.05057958"));
    assertThat(ethBtc.getSell()).isEqualTo(new BigDecimal("0.05065031"));
    assertThat(ethBtc.getUpdated()).isEqualTo(1509055549L);

    final LiquiTicker ltcBtc = tickers.get("ltc_btc");
    assertThat(ltcBtc.getHigh()).isEqualTo(new BigDecimal("0.00979768"));
    assertThat(ltcBtc.getLow()).isEqualTo(new BigDecimal("0.00938322"));
    assertThat(ltcBtc.getAvg()).isEqualTo(new BigDecimal("0.00959045"));
    assertThat(ltcBtc.getVol()).isEqualTo(new BigDecimal("39.748558585758154"));
    assertThat(ltcBtc.getVolCur()).isEqualTo(new BigDecimal("4129.30243906"));
    assertThat(ltcBtc.getLast()).isEqualTo(new BigDecimal("0.0095334"));
    assertThat(ltcBtc.getBuy()).isEqualTo(new BigDecimal("0.00950768"));
    assertThat(ltcBtc.getSell()).isEqualTo(new BigDecimal("0.00955432"));
    assertThat(ltcBtc.getUpdated()).isEqualTo(1509055549L);
  }
}
