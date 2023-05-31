package org.knowm.xchange.gateio.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.exceptions.ExchangeSecurityException;
import org.knowm.xchange.exceptions.InstrumentNotValidException;
import org.knowm.xchange.gateio.GateioExchangeWiremock;
import org.knowm.xchange.gateio.dto.account.GateioDepositAddress;
import org.knowm.xchange.gateio.dto.account.GateioDepositAddress.MultichainAddress;
import org.knowm.xchange.gateio.dto.account.GateioWithdrawStatus;

public class GateioAccountServiceRawTest extends GateioExchangeWiremock {

  GateioAccountServiceRaw gateioAccountServiceRaw = ((GateioAccountServiceRaw) exchange.getAccountService());

  @Test
  void getWithdrawStatus_valid() throws IOException {
    Map<String, BigDecimal> expectedWithdrawFeeByChain = new HashMap<>();
    expectedWithdrawFeeByChain.put("ETH", new BigDecimal("0.93"));
    expectedWithdrawFeeByChain.put("GTEVM", new BigDecimal("0.0049"));
    GateioWithdrawStatus expected = GateioWithdrawStatus.builder()
        .currency("GT")
        .name("GateToken")
        .nameCN("狗头")
        .depositFee(BigDecimal.ZERO)
        .withdrawPercent("0%")
        .withdrawFee(new BigDecimal("0.02"))
        .withdrawDailyLimit(new BigDecimal("500000"))
        .withdrawDailyLimitLeft(new BigDecimal("499999"))
        .minWithdrawAmount(new BigDecimal("0.12"))
        .maxWithdrawAmount(new BigDecimal("499999"))
        .withdrawFeeByChain(expectedWithdrawFeeByChain)
        .build();

    List<GateioWithdrawStatus> status = gateioAccountServiceRaw.getWithdrawStatus(null);

    assertThat(status).hasSize(2);
    GateioWithdrawStatus actualGt = status.get(0);
    assertThat(actualGt).isEqualTo(expected);
    assertThat(actualGt.getWithdrawRate()).isEqualTo(new BigDecimal("0.00"));

  }


  @Test
  void http_401_exception_mapped() {
    assertThatExceptionOfType(ExchangeSecurityException.class)
        .isThrownBy(() -> gateioAccountServiceRaw.getWithdrawStatus(Currency.getInstance("THROW_401")));
    assertThatExceptionOfType(ExchangeSecurityException.class)
        .isThrownBy(() -> gateioAccountServiceRaw.getWithdrawStatus(Currency.getInstance("INVALID_KEY")));
  }


  @Test
  void http_403_exception_mapped() {
    assertThatExceptionOfType(ExchangeSecurityException.class)
        .isThrownBy(() -> gateioAccountServiceRaw.getWithdrawStatus(Currency.getInstance("RETURN-FORBIDDEN")));
  }


  @Test
  void invalid_currency_exception_mapped() {
    assertThatExceptionOfType(InstrumentNotValidException.class)
        .isThrownBy(() -> gateioAccountServiceRaw.getWithdrawStatus(Currency.getInstance("invalid-currency")));
  }


  @Test
  void pending_deposit_address() throws IOException {
    GateioDepositAddress a = gateioAccountServiceRaw.getDepositAddress(Currency.getInstance("ITA"));

    MultichainAddress expected = MultichainAddress.builder()
        .chain("CHZ")
        .failed(true)
        .address("")
        .memo("")
        .memoType("")
        .build();

    assertThat(a.getMultichainAddresses()).hasSize(1);
    assertThat(a.getMultichainAddresses().get(0)).isEqualTo(expected);
  }


  @Test
  void valid_deposit_address() throws IOException {
    GateioDepositAddress a = gateioAccountServiceRaw.getDepositAddress(Currency.getInstance("ABBC"));

    MultichainAddress expected = MultichainAddress.builder()
        .chain("ABBC")
        .failed(false)
        .address("gateioioabbc")
        .memo("00cceed3f9cb5069")
        .memoType("Memo")
        .build();

    assertThat(a.getMultichainAddresses()).hasSize(1);
    assertThat(a.getMultichainAddresses().get(0)).isEqualTo(expected);
  }


}