package org.knowm.xchange.deribit.v2.dto.marketdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.InputStream;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class DeribitSummaryTest {

    @Test
    public void deserializeSummaryTest() throws Exception {

        // given
        InputStream is =
                DeribitSummary.class.getResourceAsStream(
                        "/org/knowm/xchange/deribit/v1/dto/marketdata/example-summary.json");

        // when
        ObjectMapper mapper = new ObjectMapper();
        DeribitSummary summary = mapper.readValue(is, DeribitSummary.class);

        // then
        assertThat(summary).isNotNull();

        assertThat(summary.getCurrentFunding()).isEqualTo(new BigDecimal("-0.00034500919497000614"));
        assertThat(summary.getFunding8h()).isEqualTo(new BigDecimal("-0.00016358808971835147"));
        assertThat(summary.getInstrumentName()).isEqualTo("BTC-PERPETUAL");
        assertThat(summary.getOpenInterest()).isEqualTo(new BigDecimal("13673303.5"));
        assertThat(summary.getOpenInterestAmount()).isEqualTo(new BigDecimal("136733035"));
        assertThat(summary.getHigh()).isEqualTo(new BigDecimal("4099.75"));
        assertThat(summary.getLow()).isEqualTo(new BigDecimal("4056"));
        assertThat(summary.getVolume()).isEqualTo(new BigDecimal("1388007"));
        assertThat(summary.getVolumeUsd()).isEqualTo(new BigDecimal("13880070"));
        assertThat(summary.getVolumeBtc()).isEqualTo(new BigDecimal("3402.11339229"));
        assertThat(summary.getLast()).isEqualTo(new BigDecimal("4091"));
        assertThat(summary.getBidPrice()).isEqualTo(new BigDecimal("4091"));
        assertThat(summary.getAskPrice()).isEqualTo(new BigDecimal("4091.25"));
        assertThat(summary.getMidPrice()).isEqualTo(new BigDecimal("4091.13"));
        assertThat(summary.getEstDelPrice()).isEqualTo(new BigDecimal("4094.63"));
        assertThat(summary.getMarkPrice()).isEqualTo(new BigDecimal("4091.17"));
        assertThat(summary.getCreated()).isEqualTo("2019-03-31 13:41:37 GMT");
    }

}