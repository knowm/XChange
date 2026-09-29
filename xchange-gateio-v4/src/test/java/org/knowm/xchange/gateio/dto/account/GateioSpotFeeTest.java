package org.knowm.xchange.gateio.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

public class GateioSpotFeeTest {

  @Test
  public void testUnmarshal() throws IOException {
    InputStream is = getClass().getResourceAsStream("/__files/api_v4_spot_fee.json");
    ObjectMapper mapper = new ObjectMapper();
    GateioSpotFee gateioSpotFee = mapper.readValue(is, GateioSpotFee.class);

    assertThat(gateioSpotFee.getUserId()).isEqualTo(10001L);
    assertThat(gateioSpotFee.getTakerFee()).isEqualTo(new BigDecimal("0.002"));
    assertThat(gateioSpotFee.getMakerFee()).isEqualTo(new BigDecimal("0.002"));
    assertThat(gateioSpotFee.getRpiMakerFee()).isEqualTo(new BigDecimal("-0.00175"));
    assertThat(gateioSpotFee.getGtDiscount()).isFalse();
    assertThat(gateioSpotFee.getGtTakerFee()).isEqualTo(new BigDecimal("0"));
    assertThat(gateioSpotFee.getGtMakerFee()).isEqualTo(new BigDecimal("0"));
    assertThat(gateioSpotFee.getLoanFee()).isEqualTo(new BigDecimal("0.18"));
    assertThat(gateioSpotFee.getPointType()).isEqualTo("1");
    assertThat(gateioSpotFee.getFuturesTakerFee()).isEqualTo(new BigDecimal("-0.00025"));
    assertThat(gateioSpotFee.getFuturesMakerFee()).isEqualTo(new BigDecimal("0.00075"));
    assertThat(gateioSpotFee.getFuturesRpiMakerFee()).isEqualTo(new BigDecimal("-0.00175"));
    assertThat(gateioSpotFee.getDeliveryTakerFee()).isEqualTo(new BigDecimal("0.00016"));
    assertThat(gateioSpotFee.getDeliveryMakerFee()).isEqualTo(new BigDecimal("-0.00015"));
    assertThat(gateioSpotFee.getDebitFee()).isEqualTo(3);
    assertThat(gateioSpotFee.getRpiMm()).isEqualTo(2);
  }
}
