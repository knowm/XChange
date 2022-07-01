package org.knowm.xchange.deribit.v2.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.deribit.v2.dto.Kind;

public class DeribitInstrumentTest {

  @Test
  public void deserializeInstrumentTest() throws Exception {

    // given
    InputStream is =
        DeribitInstrument.class.getResourceAsStream(
            "/org/knowm/xchange/deribit/v2/dto/marketdata/example-instrument.json");

    // when
    ObjectMapper mapper = new ObjectMapper();
    DeribitInstrument instrument = mapper.readValue(is, DeribitInstrument.class);

    // then
    assertThat(instrument).isNotNull();

    assertThat(instrument.getTickSize()).isEqualTo(new BigDecimal("0.01"));
    assertThat(instrument.getSettlementPeriod()).isEqualTo("week");
    assertThat(instrument.getQuoteCurrency()).isEqualTo("USD");
    assertThat(instrument.getMinTradeAmount()).isEqualTo(new BigDecimal("1"));
    assertThat(instrument.getKind()).isEqualTo(Kind.future);
    assertThat(instrument.isActive()).isTrue();
    assertThat(instrument.getInstrumentName()).isEqualTo("BTC-15FEB19");
    assertThat(instrument.getExpirationTimestamp().getTime()).isEqualTo(1550228400000L);
    assertThat(instrument.getCreationTimestamp().getTime()).isEqualTo(1549537259000L);
    assertThat(instrument.getContractSize()).isEqualTo(10);
    assertThat(instrument.getBaseCurrency()).isEqualTo("BTC");
  }
}
