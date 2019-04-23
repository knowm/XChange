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

        assertThat(orderbook.getState()).isEqualTo("open");
        assertThat(orderbook.getSettlementPrice()).isEqualTo(new BigDecimal("141.78"));
        assertThat(orderbook.getInstrument()).isEqualTo("ETH-PERPETUAL");
        assertThat(orderbook.getBids()).isNotEmpty();
        assertThat(orderbook.getAsks()).isNotEmpty();
        assertThat(orderbook.getTstamp()).isEqualTo(1553979085190L);
        assertThat(orderbook.getLast()).isEqualTo(new BigDecimal("141.74"));
        assertThat(orderbook.getLow()).isEqualTo(new BigDecimal("139.8"));
        assertThat(orderbook.getHigh()).isEqualTo(new BigDecimal("145.01"));
        assertThat(orderbook.getMark()).isEqualTo(new BigDecimal("141.47"));
        assertThat(orderbook.getContractMultiplier()).isEqualTo(1);
        assertThat(orderbook.getMin()).isEqualTo(new BigDecimal("138.64"));
        assertThat(orderbook.getMax()).isEqualTo(new BigDecimal("144.3"));
    }
}