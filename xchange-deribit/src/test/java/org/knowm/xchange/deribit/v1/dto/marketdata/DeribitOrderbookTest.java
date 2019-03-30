package org.knowm.xchange.deribit.v1.dto.marketdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class DeribitOrderbookTest {

    @Test
    public void deserializeCurrencyTest() throws Exception {

        // given
        InputStream is =
                DeribitOrderbook.class.getResourceAsStream(
                        "/org/knowm/xchange/deribit/v1/dto/marketdata/example-orderbook.json");

        // when
        ObjectMapper mapper = new ObjectMapper();
        DeribitOrderbook orderbook = mapper.readValue(is, DeribitOrderbook.class);

        // then
        assertThat(orderbook).isNotNull();

        assertThat(orderbook.getState()).isEqualTo("open");
        assertThat(orderbook.getSettlementPrice()).isEqualTo(141.78f);
        assertThat(orderbook.getInstrument()).isEqualTo("ETH-PERPETUAL");
        assertThat(orderbook.getBids()).isNotEmpty();
        assertThat(orderbook.getAsks()).isNotEmpty();
        assertThat(orderbook.getTstamp()).isEqualTo(1553979085190L);
        assertThat(orderbook.getLast()).isEqualTo(141.74f);
        assertThat(orderbook.getLow()).isEqualTo(139.8f);
        assertThat(orderbook.getHigh()).isEqualTo(145.01f);
        assertThat(orderbook.getMark()).isEqualTo(141.47f);
        assertThat(orderbook.getContractMultiplier()).isEqualTo(1);
        assertThat(orderbook.getMin()).isEqualTo(138.64f);
        assertThat(orderbook.getMax()).isEqualTo(144.3f);
    }
}