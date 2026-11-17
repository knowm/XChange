package org.knowm.xchange.gateio.dto.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class GateioFuturesAccountTest {

  @Test
  void unmarshal() throws Exception {
    InputStream is =
        GateioFuturesAccountTest.class.getResourceAsStream(
            "/__files/api_v4_futures_set_dual_mode.json");

    ObjectMapper mapper = new ObjectMapper();
    GateioFuturesAccount account = mapper.readValue(is, GateioFuturesAccount.class);

    assertThat(account).isNotNull();
    assertThat(account.getTotal()).isEqualTo(new BigDecimal("1000.5"));
    assertThat(account.getUnrealisedPnl()).isEqualTo(new BigDecimal("10.2"));
    assertThat(account.getPositionMargin()).isEqualTo(new BigDecimal("50.1"));
    assertThat(account.getOrderMargin()).isEqualTo(new BigDecimal("20.0"));
    assertThat(account.getAvailable()).isEqualTo(new BigDecimal("920.2"));
    assertThat(account.getPoint()).isEqualTo(new BigDecimal("0"));
    assertThat(account.getCurrency()).isEqualTo("usdt");
    assertThat(account.getInDualMode()).isTrue();
    assertThat(account.getPositionMode()).isEqualTo("dual");
    assertThat(account.getEnableCredit()).isFalse();
    assertThat(account.getPositionInitialMargin()).isEqualTo(new BigDecimal("50.1"));
    assertThat(account.getMaintenanceMargin()).isEqualTo(new BigDecimal("25.05"));
    assertThat(account.getBonus()).isEqualTo(new BigDecimal("0"));
    assertThat(account.getEnableEvolvedClassic()).isFalse();
    assertThat(account.getCrossAvailable()).isEqualTo(new BigDecimal("920.2"));
    assertThat(account.getIsolatedPositionMargin()).isEqualTo(new BigDecimal("50.1"));
    assertThat(account.getEnableNewDualMode()).isTrue();
    assertThat(account.getMarginMode()).isEqualTo(0);
    assertThat(account.getEnableTieredMm()).isTrue();
  }
}
