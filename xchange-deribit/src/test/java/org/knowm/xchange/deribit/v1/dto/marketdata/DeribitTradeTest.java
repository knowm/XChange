package org.knowm.xchange.deribit.v1.dto.marketdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class DeribitTradeTest {

    @Test
    public void deserializeCurrencyTest() throws Exception {

        // given
        InputStream is =
                DeribitTrade.class.getResourceAsStream(
                        "/org/knowm/xchange/deribit/v1/dto/marketdata/example-trade.json");

        // when
        ObjectMapper mapper = new ObjectMapper();
        DeribitTrade trade = mapper.readValue(is, DeribitTrade.class);

        // then
        assertThat(trade).isNotNull();

        assertThat(trade.getTradeId()).isEqualTo(49366);
        assertThat(trade.getInstrument()).isEqualTo("BTC-25AUG17-3900-C");
        assertThat(trade.getTradeSeq()).isEqualTo(1);
        assertThat(trade.getTimeStamp()).isEqualTo(1503439494351L);
        assertThat(trade.getQuantity()).isEqualTo(5);
        assertThat(trade.getAmount()).isEqualTo(5);
        assertThat(trade.getPrice()).isEqualTo(0.055f);
        assertThat(trade.getDirection()).isEqualTo("buy");
        assertThat(trade.getTickDirection()).isEqualTo(1);
        assertThat(trade.getIndexPrice()).isEqualTo(4101.46f);
        assertThat(trade.getIv()).isEqualTo(70.71f);
    }
}