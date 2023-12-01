package org.knowm.xchange.btcturk.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.btcturk.dto.marketdata.BTCTurkTickerTest;

/**
 * @author mertguner
 */
public class BTCTurkBalanceTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        BTCTurkTickerTest.class.getResourceAsStream(
            "/org/knowm/xchange/btcturk/dto/account/example-balance-data.json");
    ObjectMapper mapper = new ObjectMapper();
    BTCTurkAccountBalance btcTurkTicker = mapper.readValue(is, BTCTurkAccountBalance.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(btcTurkTicker.getTry_balance()).isEqualTo(new BigDecimal("10.0"));
    assertThat(btcTurkTicker.getBtc_balance()).isEqualTo(new BigDecimal("0.1124"));
    assertThat(btcTurkTicker.getEth_balance()).isEqualTo(new BigDecimal("0.6316092"));
    assertThat(btcTurkTicker.getXrp_balance()).isEqualTo(new BigDecimal("10.54896"));
    assertThat(btcTurkTicker.getLtc_balance()).isEqualTo(new BigDecimal("0.0"));
    assertThat(btcTurkTicker.getUsdt_balance()).isEqualTo(new BigDecimal("0.0"));
    assertThat(btcTurkTicker.getXlm_balance()).isEqualTo(new BigDecimal("0.0"));
    assertThat(btcTurkTicker.getTry_reserved()).isEqualTo(new BigDecimal("0.0"));
    assertThat(btcTurkTicker.getBtc_reserved()).isEqualTo(new BigDecimal("0.0"));
    assertThat(btcTurkTicker.getEth_reserved()).isEqualTo(new BigDecimal("0.6316092"));
    assertThat(btcTurkTicker.getXrp_reserved()).isEqualTo(new BigDecimal("0.0"));
    assertThat(btcTurkTicker.getLtc_reserved()).isEqualTo(new BigDecimal("0.0"));
    assertThat(btcTurkTicker.getUsdt_reserved()).isEqualTo(new BigDecimal("0.0"));
    assertThat(btcTurkTicker.getXlm_reserved()).isEqualTo(new BigDecimal("0.0"));
    assertThat(btcTurkTicker.getTry_available()).isEqualTo(new BigDecimal("0.0"));
    assertThat(btcTurkTicker.getBtc_available()).isEqualTo(new BigDecimal("0.0"));
    assertThat(btcTurkTicker.getEth_available()).isEqualTo(new BigDecimal("0.0"));
    assertThat(btcTurkTicker.getXrp_available()).isEqualTo(new BigDecimal("0.0"));
    assertThat(btcTurkTicker.getLtc_available()).isEqualTo(new BigDecimal("0.0"));
    assertThat(btcTurkTicker.getUsdt_available()).isEqualTo(new BigDecimal("0.0"));
    assertThat(btcTurkTicker.getXlm_available()).isEqualTo(new BigDecimal("0.0"));
    assertThat(btcTurkTicker.getBtctry_taker_fee_percentage())
        .isEqualTo(new BigDecimal("0.0021186440000000"));
    assertThat(btcTurkTicker.getBtctry_maker_fee_percentage())
        .isEqualTo(new BigDecimal("0.0012711860000000"));
    assertThat(btcTurkTicker.getEthtry_taker_fee_percentage())
        .isEqualTo(new BigDecimal("0.0012711860000000"));
    assertThat(btcTurkTicker.getEthtry_maker_fee_percentage()).isEqualTo(new BigDecimal("0.0"));
    assertThat(btcTurkTicker.getEthbtc_taker_fee_percentage())
        .isEqualTo(new BigDecimal("0.0021186440000000"));
    assertThat(btcTurkTicker.getEthbtc_maker_fee_percentage())
        .isEqualTo(new BigDecimal("0.0012711860000000"));
    assertThat(btcTurkTicker.getXrptry_taker_fee_percentage())
        .isEqualTo(new BigDecimal("0.0021186440000000"));
    assertThat(btcTurkTicker.getXrptry_maker_fee_percentage())
        .isEqualTo(new BigDecimal("0.0012711860000000"));
    assertThat(btcTurkTicker.getLtctry_taker_fee_percentage())
        .isEqualTo(new BigDecimal("0.0021186440000000"));
    assertThat(btcTurkTicker.getLtctry_maker_fee_percentage())
        .isEqualTo(new BigDecimal("0.0012711860000000"));
    assertThat(btcTurkTicker.getUsdttry_taker_fee_percentage())
        .isEqualTo(new BigDecimal("0.0021186440000000"));
    assertThat(btcTurkTicker.getUsdttry_maker_fee_percentage())
        .isEqualTo(new BigDecimal("0.0012711860000000"));
    assertThat(btcTurkTicker.getXlmtry_taker_fee_percentage())
        .isEqualTo(new BigDecimal("0.0021186440000000"));
    assertThat(btcTurkTicker.getXlmtry_maker_fee_percentage())
        .isEqualTo(new BigDecimal("0.0012711860000000"));
  }
}
