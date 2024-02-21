package org.knowm.xchange.gateio.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.exceptions.ExchangeSecurityException;
import org.knowm.xchange.exceptions.InstrumentNotValidException;
import org.knowm.xchange.exceptions.InternalServerException;
import org.knowm.xchange.gateio.GateioExchangeWiremock;
import org.knowm.xchange.gateio.dto.account.GateioAddressRecord;
import org.knowm.xchange.gateio.dto.account.GateioDepositAddress;
import org.knowm.xchange.gateio.dto.account.GateioDepositAddress.MultichainAddress;
import org.knowm.xchange.gateio.dto.account.GateioDepositRecord;
import org.knowm.xchange.gateio.dto.account.GateioDepositRecord.Status;
import org.knowm.xchange.gateio.dto.account.GateioSubAccountTransfer;
import org.knowm.xchange.gateio.dto.account.GateioWithdrawStatus;
import org.knowm.xchange.gateio.dto.account.GateioWithdrawalRecord;
import org.knowm.xchange.gateio.dto.account.GateioWithdrawalRequest;
import org.knowm.xchange.gateio.dto.account.params.GateioSubAccountTransfersParams;
import org.knowm.xchange.gateio.service.params.GateioDepositsParams;
import org.knowm.xchange.gateio.service.params.GateioWithdrawalsParams;

public class GateioAccountServiceRawTest extends GateioExchangeWiremock {

  GateioAccountServiceRaw gateioAccountServiceRaw = ((GateioAccountServiceRaw) exchange.getAccountService());

  @Test
  void getWithdrawStatus_valid() throws IOException {
    Map<String, BigDecimal> expectedWithdrawFeeByChain = new HashMap<>();
    expectedWithdrawFeeByChain.put("ETH", new BigDecimal("0.93"));
    expectedWithdrawFeeByChain.put("GTEVM", new BigDecimal("0.0049"));
    GateioWithdrawStatus expected = GateioWithdrawStatus.builder()
        .currency(Currency.getInstance("GT"))
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
  void http_500_exception_mapped() {
    assertThatExceptionOfType(InternalServerException.class)
        .isThrownBy(() -> gateioAccountServiceRaw.getWithdrawStatus(Currency.getInstance("INTERNAL-SERVER-ERROR")));
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


  @Test
  void withdrawal_records() throws IOException {
    List<GateioWithdrawalRecord> actual = gateioAccountServiceRaw.getWithdrawals(GateioWithdrawalsParams.builder().build());

    GateioWithdrawalRecord expected = GateioWithdrawalRecord.builder()
        .id("w35874123")
        .currency(Currency.getInstance("LUFFY"))
        .address("0x3dca2ae4d1d065220a731cf69f5a934914afc435")
        .amount(new BigDecimal("1030645.8587"))
        .fee(new BigDecimal("10000"))
        .txId("0x8f72d42b016a2b7b543149e707ff37fadded2ff3ef6767bee30b6003330f604b")
        .chain("ETH")
        .createdAt(Instant.parse("2023-06-01T11:34:15Z"))
        .status(GateioWithdrawalRecord.Status.DONE)
        .clientRecordId("a")
        .tag("b")
        .build();

    assertThat(actual).hasSize(1);
    assertThat(actual).first().usingRecursiveComparison().isEqualTo(expected);
  }


  @Test
  void deposit_records() throws IOException {
    List<GateioDepositRecord> actual = gateioAccountServiceRaw.getDeposits(GateioDepositsParams.builder()
        .startTime(Instant.ofEpochSecond(1685833987))
        .endTime(Instant.ofEpochSecond(1688339587))
        .build());

    GateioDepositRecord expected = GateioDepositRecord.builder()
        .id("d95198946")
        .currency(Currency.getInstance("OMN"))
        .address("0x04A09F5712F527584BAC0C7be40Ae7946f9A9587")
        .amount(new BigDecimal("2353"))
        .txId("0x34a89edf400a85a9a6a2747ebc2074ef65895345b9b7400f164602085e9b4a2e")
        .chain("OMN")
        .createdAt(Instant.parse("2023-07-01T14:12:26Z"))
        .status(Status.DONE)
        .tag("")
        .build();

    assertThat(actual).hasSize(2);
    assertThat(actual).first().usingRecursiveComparison().isEqualTo(expected);
  }


  @Test
  void withdraw() throws IOException {
    GateioWithdrawalRequest gateioWithdrawalRequest = GateioWithdrawalRequest.builder()
        .clientRecordId("valid-withdrawal-id")
        .address("6vLyxJ9dBziamyaw2vDcs9n2NwQdW1uk3aooJwrEscnA")
        .tag("")
        .chain("SOL")
        .amount(BigDecimal.valueOf(3))
        .currency(Currency.USDT)
        .build();

    GateioWithdrawalRecord actual = gateioAccountServiceRaw.withdraw(gateioWithdrawalRequest);

    GateioWithdrawalRecord expected = GateioWithdrawalRecord.builder()
        .id("w35980955")
        .clientRecordId("valid-withdrawal-id")
        .address("6vLyxJ9dBziamyaw2vDcs9n2NwQdW1uk3aooJwrEscnA ")
        .tag("")
        .chain("SOL")
        .amount(BigDecimal.valueOf(3))
        .currency(Currency.USDT)
        .status(GateioWithdrawalRecord.Status.REQUEST)
        .build();

    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);

  }


  @Test
  void saved_addresses() throws IOException {
    List<GateioAddressRecord> actual = gateioAccountServiceRaw.getSavedAddresses(Currency.USDT);

    GateioAddressRecord expected = GateioAddressRecord.builder()
        .address("6vLyxJ9dBziamyaw2vDcs9n2NwQdW1uk3aooJwrEscnA")
        .tag("")
        .chain("SOL")
        .currency(Currency.USDT)
        .name("stuff")
        .verified(true)
        .build();

    assertThat(actual).hasSize(1);
    assertThat(actual).first().usingRecursiveComparison().isEqualTo(expected);
  }


  @Test
  void sub_account_transfers() throws IOException {

    List<GateioSubAccountTransfer> actual = gateioAccountServiceRaw.getSubAccountTransfers(
        GateioSubAccountTransfersParams.builder().build());

    GateioSubAccountTransfer expected = GateioSubAccountTransfer.builder()
        .mainAccountId(10001)
        .timestamp(Instant.ofEpochSecond(1592809000))
        .source("web")
        .clientOrderId("da3ce7a088c8b0372b741419c7829033")
        .currency(Currency.BTC)
        .subAccountId(10002)
        .direction("to")
        .amount(BigDecimal.ONE)
        .subAccountType("spot")
        .build();

    assertThat(actual).hasSize(1);
    assertThat(actual).first().usingRecursiveComparison().isEqualTo(expected);
  }

}