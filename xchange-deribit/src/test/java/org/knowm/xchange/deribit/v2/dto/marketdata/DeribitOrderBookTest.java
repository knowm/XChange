package org.knowm.xchange.deribit.v2.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;

public class DeribitOrderBookTest {

  @Test
  public void deserializeOrderBookTest() throws Exception {

    // given
    InputStream is =
        DeribitOrderBook.class.getResourceAsStream(
            "/org/knowm/xchange/deribit/v2/dto/marketdata/example-orderbook.json");

    // when
    ObjectMapper mapper = new ObjectMapper();
    DeribitOrderBook orderBook = mapper.readValue(is, DeribitOrderBook.class);

    // then
    assertThat(orderBook).isNotNull();

    assertThat(orderBook.getTimestamp().getTime()).isEqualTo(1550757626706L);
    assertThat(orderBook.getStats()).isNotNull();
    assertThat(orderBook.getState()).isEqualTo("open");
    assertThat(orderBook.getSettlementPrice()).isEqualTo(new BigDecimal("3925.85"));
    assertThat(orderBook.getOpenInterest()).isEqualTo(new BigDecimal("45.27600333464605"));
    assertThat(orderBook.getMinPrice()).isEqualTo(new BigDecimal("3932.22"));
    assertThat(orderBook.getMaxPrice()).isEqualTo(new BigDecimal("3971.74"));
    assertThat(orderBook.getMarkPrice()).isEqualTo(new BigDecimal("3931.97"));
    assertThat(orderBook.getLastPrice()).isEqualTo(new BigDecimal("3955.75"));
    assertThat(orderBook.getInstrumentName()).isEqualTo("BTC-PERPETUAL");
    assertThat(orderBook.getIndexPrice()).isEqualTo(new BigDecimal("3910.46"));
    assertThat(orderBook.getFunding8h()).isEqualTo(new BigDecimal("0.00455263"));
    assertThat(orderBook.getCurrentFunding()).isEqualTo(new BigDecimal("0.00500063"));
    assertThat(orderBook.getChangeId()).isEqualTo(474988L);
    assertThat(orderBook.getBids()).isNotEmpty();
    assertThat(orderBook.getBestBidPrice()).isEqualTo(new BigDecimal("3955.75"));
    assertThat(orderBook.getBestBidAmount()).isEqualTo(new BigDecimal("30.0"));
    assertThat(orderBook.getBestAskPrice()).isEqualTo(new BigDecimal("0.0"));
    assertThat(orderBook.getBestAskAmount()).isEqualTo(new BigDecimal("0.0"));
    assertThat(orderBook.getAsks()).isNotNull();
  }
}
