package org.knowm.xchange.deribit.v2.dto.marketdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.InputStream;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class DeribitOrderbookTest {

    @Test
    public void deserializeOrderbookTest() throws Exception {

        // given
        InputStream is =
                DeribitOrderbook.class.getResourceAsStream(
                        "/org/knowm/xchange/deribit/v2/dto/marketdata/example-orderbook.json");

        // when
        ObjectMapper mapper = new ObjectMapper();
        DeribitOrderbook orderbook = mapper.readValue(is, DeribitOrderbook.class);

        // then
        assertThat(orderbook).isNotNull();

        assertThat(orderbook.getTimestamp()).isEqualTo(1550757626706L);
        assertThat(orderbook.getStats()).isNotNull();
        assertThat(orderbook.getState()).isEqualTo("open");
        assertThat(orderbook.getSettlementPrice()).isEqualTo(new BigDecimal("3925.85"));
        assertThat(orderbook.getOpenInterest()).isEqualTo(new BigDecimal("45.27600333464605"));
        assertThat(orderbook.getMinPrice()).isEqualTo(new BigDecimal("3932.22"));
        assertThat(orderbook.getMaxPrice()).isEqualTo(new BigDecimal("3971.74"));
        assertThat(orderbook.getMarkPrice()).isEqualTo(new BigDecimal("3931.97"));
        assertThat(orderbook.getLastPrice()).isEqualTo(new BigDecimal("3955.75"));
        assertThat(orderbook.getInstrumentName()).isEqualTo("BTC-PERPETUAL");
        assertThat(orderbook.getIndexPrice()).isEqualTo(new BigDecimal("3910.46"));
        assertThat(orderbook.getFunding8h()).isEqualTo(new BigDecimal("0.00455263"));
        assertThat(orderbook.getCurrentFunding()).isEqualTo(new BigDecimal("0.00500063"));
        assertThat(orderbook.getChangeId()).isEqualTo(474988L);
        assertThat(orderbook.getBids()).isNotEmpty();
        assertThat(orderbook.getBestBidPrice()).isEqualTo(new BigDecimal("3955.75"));
        assertThat(orderbook.getBestBidAmount()).isEqualTo(new BigDecimal("30.0"));
        assertThat(orderbook.getBestAskPrice()).isEqualTo(new BigDecimal("0.0"));
        assertThat(orderbook.getBestAskAmount()).isEqualTo(new BigDecimal("0.0"));
        assertThat(orderbook.getAsks()).isNotNull();
    }
}