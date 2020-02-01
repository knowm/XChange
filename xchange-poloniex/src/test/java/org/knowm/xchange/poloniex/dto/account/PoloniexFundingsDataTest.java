package org.knowm.xchange.poloniex.dto.account;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import org.assertj.core.data.Offset;
import org.junit.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexLoansDataTest;
import org.knowm.xchange.poloniex.dto.trade.PoloniexAdjustment;
import org.knowm.xchange.poloniex.dto.trade.PoloniexDeposit;
import org.knowm.xchange.poloniex.dto.trade.PoloniexDepositsWithdrawalsResponse;
import org.knowm.xchange.poloniex.dto.trade.PoloniexWithdrawal;

public class PoloniexFundingsDataTest {

  @Test
  public void testUnmarshallDepositsWithdrawalsResponse()
      throws JsonParseException, JsonMappingException, IOException {

    final InputStream is =
        PoloniexLoansDataTest.class.getResourceAsStream(
            "/org/knowm/xchange/poloniex/dto/account/deposits-withdrawals.json");

    final ObjectMapper mapper =
        new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    final PoloniexDepositsWithdrawalsResponse response =
        mapper.readValue(is, PoloniexDepositsWithdrawalsResponse.class);

    assertThat(response.getDeposits()).hasSize(1);

    final PoloniexAdjustment adjustment = response.getAdjustments().get(0);
    assertThat(adjustment.getCurrency()).isEqualTo(Currency.USDT.getCurrencyCode());
    assertThat(adjustment.getAmount())
        .isCloseTo(new BigDecimal("0.01752476"), Offset.offset(BigDecimal.ZERO));
    assertThat(adjustment.getTimestamp()).isEqualTo(new Date(1564782686000L));
    assertThat(adjustment.getStatus()).isEqualTo("COMPLETE");
    assertThat(adjustment.getCategory()).isEqualTo("adjustment");
    assertThat(adjustment.getReason()).isEqualTo("USDT_AIDROP");
    assertThat(adjustment.getAdjustmentTitle()).isEqualTo("USDT-TRON Airdrop");
    assertThat(adjustment.getAdjustmentShortTitle()).isEqualTo("");
    assertThat(adjustment.getAdjustmentDesc()).isEqualTo("Your airdrop for Aug 2, 2019.");
    assertThat(adjustment.getAdjustmentShortDesc()).isEqualTo("For Aug 2, 2019");
    assertThat(adjustment.getAdjustmentHelp())
        .isEqualTo(
            "https://support.poloniex.circle.com/hc/en-us/articles/360029433451-USDT-TRON-Airdrop-and-Other-Frequently-Asked-Questions");

    assertThat(response.getWithdrawals()).hasSize(1);

    final PoloniexDeposit deposit = response.getDeposits().get(0);
    assertThat(deposit.getCurrency()).isEqualTo(Currency.ETH.getCurrencyCode());
    assertThat(deposit.getAddress()).isEqualTo("0xf015a632ac1d13aeca3513c8c76c96334c5861a3");
    assertThat(deposit.getAmount())
        .isCloseTo(new BigDecimal("8.995"), Offset.offset(BigDecimal.ZERO));
    assertThat(deposit.getConfirmations()).isEqualTo(38);
    assertThat(deposit.getTxid())
        .isEqualTo("0xa7bc2c38e97c1ed9cee7436da5912ba8c7d721dce9884ca8f6ab3e35da31dd44");
    assertThat(deposit.getTimestamp()).isEqualTo(new Date(1556178200000L));
    assertThat(deposit.getStatus()).isEqualTo("COMPLETE");
    assertThat(deposit.getDepositNumber()).isEqualTo(152426993L);
    assertThat(deposit.getCategory()).isEqualTo("deposit");

    assertThat(response.getWithdrawals()).hasSize(1);

    final PoloniexWithdrawal withdrawal = response.getWithdrawals().get(0);
    assertThat(withdrawal.getWithdrawalNumber()).isEqualTo(19322067L);
    assertThat(withdrawal.getCurrency()).isEqualTo(Currency.ETH.getCurrencyCode());
    assertThat(withdrawal.getAddress()).isEqualTo("0x4120F5b5A6adA3B543da0535e7a9844689f5016C");
    assertThat(withdrawal.getAmount())
        .isCloseTo(new BigDecimal("2.0"), Offset.offset(BigDecimal.ZERO));
    assertThat(withdrawal.getFee())
        .isCloseTo(new BigDecimal("0.01"), Offset.offset(BigDecimal.ZERO));
    assertThat(withdrawal.getTimestamp()).isEqualTo(new Date(1556435569000L));
    assertThat(withdrawal.getStatus())
        .isEqualTo("COMPLETE: 0xf85d3870a4c9a077cd333f59ad49db7f2b21e4a67326961c09d629f6a7bb2e9d");
    assertThat(withdrawal.getIpAddress()).isEqualTo("127.0.0.1");
    assertThat(withdrawal.getPaymentID()).isEqualTo(null);
  }
}
