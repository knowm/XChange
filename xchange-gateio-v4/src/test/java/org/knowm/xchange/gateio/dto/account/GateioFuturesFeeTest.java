package org.knowm.xchange.gateio.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;

import org.junit.Test;

public class GateioFuturesFeeTest {

  @Test
  public void testUnmarshal() throws IOException {
    InputStream is =
        GateioFuturesFeeTest.class.getResourceAsStream(
            "/__files/api_v4_futures_fee.json");

    ObjectMapper mapper = new ObjectMapper();
    Map<String, GateioFuturesFee> fees =
        mapper.readValue(is, new TypeReference<Map<String, GateioFuturesFee>>() {
        });

    assertThat(fees).hasSize(2);
    assertThat(fees.get("1INCH_USDT").getTakerFee()).isEqualTo(new BigDecimal("0.00025"));
    assertThat(fees.get("1INCH_USDT").getMakerFee()).isEqualTo(new BigDecimal("-0.00010"));
    assertThat(fees.get("AAVE_USDT").getTakerFee()).isEqualTo(new BigDecimal("0.00025"));
    assertThat(fees.get("AAVE_USDT").getMakerFee()).isEqualTo(new BigDecimal("-0.00010"));
  }
}
