package org.knowm.xchange.deribit.v2.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.deribit.v2.dto.Direction;

public class DeribitTradeTest {

  @Test
  public void deserializeTradeTest() throws Exception {

    // given
    InputStream is =
        DeribitTrade.class.getResourceAsStream(
            "/org/knowm/xchange/deribit/v2/dto/marketdata/example-trade.json");

    // when
    ObjectMapper mapper = new ObjectMapper();
    DeribitTrade trade = mapper.readValue(is, DeribitTrade.class);

    // then
    assertThat(trade).isNotNull();

    assertThat(trade.getTradeSeq()).isEqualTo(2427);
    assertThat(trade.getTradeId()).isEqualTo("48470");
    assertThat(trade.getTickDirection()).isEqualTo(2);
    assertThat(trade.getPrice()).isEqualTo(new BigDecimal("3610"));
    assertThat(trade.getInstrumentName()).isEqualTo("BTC-PERPETUAL");
    assertThat(trade.getIndexPrice()).isEqualTo(new BigDecimal("3579.08"));
    assertThat(trade.getDirection()).isEqualTo(Direction.sell);
    assertThat(trade.getAmount()).isEqualTo(new BigDecimal("10"));
  }
}
