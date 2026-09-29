package org.knowm.xchange.cryptocom.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.cryptocom.dto.CryptoComResponse;

public class CryptoComPublicTradeTest {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void testUnmarshalTrades() throws IOException {
    // given
    String resource = "/org/knowm/xchange/cryptocom/dto/marketdata/get-trades.json";

    // when
    CryptoComResponse response =
        CryptoComTestSupport.readResponse(CryptoComPublicTradeTest.class, resource, objectMapper);
    List<CryptoComPublicTrade> trades =
        CryptoComTestSupport.readDataList(response, objectMapper, CryptoComPublicTrade.class);

    // then
    assertThat(trades).hasSize(5);

    CryptoComPublicTrade trade = trades.get(0);
    assertThat(trade.getTradeId()).isEqualTo("1785085681122335713");
    assertThat(trade.getTimestamp()).isEqualTo(1785085681122L);
    assertThat(new BigDecimal(trade.getQuantity())).isEqualByComparingTo(new BigDecimal("0.00223"));
    assertThat(new BigDecimal(trade.getPrice())).isEqualByComparingTo(new BigDecimal("64685.97"));
    assertThat(trade.getSide()).isEqualTo("buy");
    assertThat(trade.getInstrumentName()).isEqualTo("BTC_USDT");
    assertThat(trade.getTradeMatchId()).isEqualTo("4611686018687161658");
  }
}
