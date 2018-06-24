package org.knowm.xchange.liqui.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.knowm.xchange.liqui.dto.LiquiTradeType;
import org.knowm.xchange.liqui.dto.marketdata.LiquiPublicTrade;
import org.knowm.xchange.liqui.dto.marketdata.LiquiPublicTrades;
import org.knowm.xchange.liqui.dto.marketdata.result.LiquiPublicTradesResult;

public class LiquiTradesJSONTest {

  @Test
  public void testUnmarshall() throws Exception {
    final InputStream is =
        LiquiTickerJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/liqui/marketdata/example-trades-data.json");

    final ObjectMapper mapper = new ObjectMapper();
    final LiquiPublicTradesResult tradesResult =
        mapper.readValue(is, LiquiPublicTradesResult.class);
    final Map<String, LiquiPublicTrades> result = tradesResult.getResult();

    assertThat(result.get("eth_zec")).isEqualTo(null);
    final LiquiPublicTrades ethBtc = result.get("eth_btc");
    final List<LiquiPublicTrade> ethTrades = ethBtc.getTrades();

    assertThat(ethTrades.get(0).getType()).isEqualTo(LiquiTradeType.BUY);
    assertThat(ethTrades.get(0).getPrice()).isEqualTo(new BigDecimal("0.05308935"));
    assertThat(ethTrades.get(0).getAmount()).isEqualTo(new BigDecimal("0.130412"));
    assertThat(ethTrades.get(0).getTradeId()).isEqualTo(35093453L);
    assertThat(ethTrades.get(0).getTimestamp()).isEqualTo(1509285104L);

    assertThat(ethTrades.get(1).getType()).isEqualTo(LiquiTradeType.BUY);
    assertThat(ethTrades.get(1).getPrice()).isEqualTo(new BigDecimal("0.05308936"));
    assertThat(ethTrades.get(1).getAmount()).isEqualTo(new BigDecimal("0.13055678"));
    assertThat(ethTrades.get(1).getTradeId()).isEqualTo(35093435L);
    assertThat(ethTrades.get(1).getTimestamp()).isEqualTo(1509285101L);

    assertThat(ethTrades.get(2).getType()).isEqualTo(LiquiTradeType.BUY);
    assertThat(ethTrades.get(2).getPrice()).isEqualTo(new BigDecimal("0.0530532"));
    assertThat(ethTrades.get(2).getAmount()).isEqualTo(new BigDecimal("0.5402235"));
    assertThat(ethTrades.get(2).getTradeId()).isEqualTo(35093405L);
    assertThat(ethTrades.get(2).getTimestamp()).isEqualTo(1509285093L);

    final LiquiPublicTrades ltcBtc = result.get("ltc_btc");
    final List<LiquiPublicTrade> ltcTrades = ltcBtc.getTrades();

    assertThat(ltcTrades.get(0).getType()).isEqualTo(LiquiTradeType.BUY);
    assertThat(ltcTrades.get(0).getPrice()).isEqualTo(new BigDecimal("0.00972064"));
    assertThat(ltcTrades.get(0).getAmount()).isEqualTo(new BigDecimal("0.17742352"));
    assertThat(ltcTrades.get(0).getTradeId()).isEqualTo(35093212L);
    assertThat(ltcTrades.get(0).getTimestamp()).isEqualTo(1509285037L);

    assertThat(ltcTrades.get(1).getType()).isEqualTo(LiquiTradeType.BUY);
    assertThat(ltcTrades.get(1).getPrice()).isEqualTo(new BigDecimal("0.00972064"));
    assertThat(ltcTrades.get(1).getAmount()).isEqualTo(new BigDecimal("0.17742352"));
    assertThat(ltcTrades.get(1).getTradeId()).isEqualTo(35093203L);
    assertThat(ltcTrades.get(1).getTimestamp()).isEqualTo(1509285035L);

    assertThat(ltcTrades.get(2).getType()).isEqualTo(LiquiTradeType.SELL);
    assertThat(ltcTrades.get(2).getPrice()).isEqualTo(new BigDecimal("0.0096956"));
    assertThat(ltcTrades.get(2).getAmount()).isEqualTo(new BigDecimal("2.13100861"));
    assertThat(ltcTrades.get(2).getTradeId()).isEqualTo(35093113L);
    assertThat(ltcTrades.get(2).getTimestamp()).isEqualTo(1509285003L);
  }
}
