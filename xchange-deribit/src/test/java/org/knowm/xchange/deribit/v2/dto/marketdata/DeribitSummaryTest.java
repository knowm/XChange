package org.knowm.xchange.deribit.v2.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;

public class DeribitSummaryTest {

  @Test
  public void deserializeSummaryTest() throws Exception {

    // given
    InputStream is =
        DeribitSummary.class.getResourceAsStream(
            "/org/knowm/xchange/deribit/v2/dto/marketdata/example-summary.json");

    // when
    ObjectMapper mapper = new ObjectMapper();
    DeribitSummary summary = mapper.readValue(is, DeribitSummary.class);

    // then
    assertThat(summary).isNotNull();

    assertThat(summary.getVolume()).isEqualTo(new BigDecimal("0.55"));
    assertThat(summary.getUnderlyingPrice()).isEqualTo(new BigDecimal("121.38"));
    assertThat(summary.getUnderlyingIndex()).isEqualTo("index_price");
    assertThat(summary.getQuoteCurrency()).isEqualTo("USD");
    assertThat(summary.getOpenInterest()).isEqualTo(new BigDecimal("0.55"));
    assertThat(summary.getMidPrice()).isEqualTo(new BigDecimal("0.2444"));
    assertThat(summary.getMarkPrice()).isEqualTo(new BigDecimal("0.179112"));
    assertThat(summary.getLow()).isEqualTo(new BigDecimal("0.34"));
    assertThat(summary.getLast()).isEqualTo(new BigDecimal("0.34"));
    assertThat(summary.getInstrumentName()).isEqualTo("ETH-22FEB19-140-P");
    assertThat(summary.getHigh()).isEqualTo(new BigDecimal("0.34"));
    assertThat(summary.getCreationTimestamp().getTime()).isEqualTo(1550227952163L);
    assertThat(summary.getBidPrice()).isEqualTo(new BigDecimal("0.1488"));
    assertThat(summary.getBaseCurrency()).isEqualTo("ETH");
    assertThat(summary.getAskPrice()).isEqualTo(new BigDecimal("0.34"));
  }
}
