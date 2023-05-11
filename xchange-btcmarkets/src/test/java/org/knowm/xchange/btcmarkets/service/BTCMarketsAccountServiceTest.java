package org.knowm.xchange.btcmarkets.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import org.knowm.xchange.btcmarkets.BtcMarketsAssert;
import org.knowm.xchange.btcmarkets.dto.account.BTCMarketsBalance;
import org.knowm.xchange.btcmarkets.dto.account.BTCMarketsFundtransfer;
import org.knowm.xchange.btcmarkets.dto.account.BTCMarketsFundtransferHistoryResponse;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsWithdrawCryptoRequest;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsWithdrawCryptoResponse;
import org.knowm.xchange.btcmarkets.dto.v3.account.BTCMarketsAddressesResponse;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.service.trade.params.RippleWithdrawFundsParams;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import si.mazi.rescu.SynchronizedValueFactory;

public class BTCMarketsAccountServiceTest extends BTCMarketsServiceTest {

  @Test
  public void shouldCreateAccountInfo() throws IOException {
    // given
    BTCMarketsBalance balance = parse(BTCMarketsBalance.class);

    when(btcMarketsAuthenticated.getBalance(
            Mockito.eq(SPECIFICATION_API_KEY),
            Mockito.any(SynchronizedValueFactory.class),
            Mockito.any(BTCMarketsDigest.class)))
        .thenReturn(Arrays.asList(balance));

    // when
    AccountInfo accountInfo = btcMarketsAccountService.getAccountInfo();

    // then
    assertThat(accountInfo.getTradingFee()).isNull();
    assertThat(accountInfo.getWallets()).hasSize(1);

    BtcMarketsAssert.assertEquals(
        accountInfo.getWallet().getBalance(Currency.BTC), EXPECTED_BALANCE);
  }

  @Test
  public void withdrawFundsShouldReturnNull() throws IOException {

    String status = "the-status"; // maybe the id would be more useful?
    BTCMarketsWithdrawCryptoResponse response =
        new BTCMarketsWithdrawCryptoResponse(
            true, null, null, status, "id", "desc", "ccy", BigDecimal.ONE, BigDecimal.ONE, 0L);

    when(btcMarketsAuthenticated.withdrawCrypto(
            Mockito.eq(SPECIFICATION_API_KEY),
            Mockito.any(SynchronizedValueFactory.class),
            Mockito.any(BTCMarketsDigest.class),
            Mockito.any(BTCMarketsWithdrawCryptoRequest.class)))
        .thenReturn(response);

    // when
    String result =
        btcMarketsAccountService.withdrawFunds(Currency.BTC, BigDecimal.TEN, "any address");

    assertThat(result).isNotNull();
  }

  @Test
  public void withdrawFundsShouldAppendRippleTag() throws IOException {

    String status = "the-status"; // maybe the id would be more useful?
    BTCMarketsWithdrawCryptoResponse response =
        new BTCMarketsWithdrawCryptoResponse(
            true, null, null, status, "id", "desc", "ccy", BigDecimal.ONE, BigDecimal.ONE, 0L);
    ArgumentCaptor<BTCMarketsWithdrawCryptoRequest> captor =
        ArgumentCaptor.forClass(BTCMarketsWithdrawCryptoRequest.class);

    when(btcMarketsAuthenticated.withdrawCrypto(
            Mockito.eq(SPECIFICATION_API_KEY),
            Mockito.any(SynchronizedValueFactory.class),
            Mockito.any(BTCMarketsDigest.class),
            captor.capture()))
        .thenReturn(response);

    // when
    RippleWithdrawFundsParams params = RippleWithdrawFundsParams.builder()
            .address("any address")
            .currency(Currency.BTC)
            .amount(BigDecimal.TEN)
            .tag("12345")
            .build();

    String result = btcMarketsAccountService.withdrawFunds(params);
    assertThat(captor.getValue().address).isEqualTo("any address?dt=12345");
    assertThat(result).isNotNull();
  }

  @Test
  public void shouldRequestDepositAddress() throws IOException {
    BTCMarketsAddressesResponse response = new BTCMarketsAddressesResponse("address");

    ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

    when(btcMarketsAuthenticatedV3.depositAddress(
            Mockito.eq(SPECIFICATION_API_KEY),
            Mockito.any(SynchronizedValueFactory.class),
            Mockito.any(BTCMarketsDigestV3.class),
            captor.capture()))
        .thenReturn(response);
    // when
    String address = btcMarketsAccountService.requestDepositAddress(Currency.BTC);

    // then
    assertThat(captor.getValue()).isEqualTo("BTC");
    assertThat(address).isEqualTo("address");
  }

  @Test
  public void getFundingHistoryShouldReturnFundingRecors() throws IOException {
    Date creationTime = new Date();
    Date lastUpdate = new Date();
    BTCMarketsFundtransfer fundtransfer =
        new BTCMarketsFundtransfer(
            "Complete",
            lastUpdate,
            BigDecimal.ONE,
            "desc",
            null,
            creationTime,
            12345L,
            new BTCMarketsFundtransfer.CryptoPaymentDetail("tx1", "address"),
            "BTC",
            BigDecimal.valueOf(123.45),
            "DEPOSIT");

    BTCMarketsFundtransferHistoryResponse response =
        new BTCMarketsFundtransferHistoryResponse(
            true,
            null,
            null,
            Arrays.asList(fundtransfer),
            new BTCMarketsFundtransferHistoryResponse.Paging("12345", "12345"));

    when(btcMarketsAuthenticated.fundtransferHistory(
            Mockito.eq(SPECIFICATION_API_KEY),
            Mockito.any(SynchronizedValueFactory.class),
            Mockito.any(BTCMarketsDigest.class)))
        .thenReturn(response);

    // when
    List<FundingRecord> result =
        btcMarketsAccountService.getFundingHistory(
            btcMarketsAccountService.createFundingHistoryParams());
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getType()).isEqualTo(FundingRecord.Type.DEPOSIT);
    assertThat(result.get(0).getStatus()).isEqualTo(FundingRecord.Status.COMPLETE);
    assertThat(result.get(0).getCurrency()).isEqualTo(Currency.BTC);
    assertThat(result.get(0).getInternalId()).isEqualTo("12345");
    assertThat(result.get(0).getFee()).isEqualTo(BigDecimal.ONE);
    assertThat(result.get(0).getDescription()).isEqualTo("desc");
    assertThat(result.get(0).getDate()).isEqualTo(creationTime);
    assertThat(result.get(0).getBlockchainTransactionHash()).isEqualTo("tx1");
    assertThat(result.get(0).getBalance()).isNull();
    assertThat(result.get(0).getAmount()).isEqualTo(BigDecimal.valueOf(123.45));
    assertThat(result.get(0).getAddress()).isEqualTo("address");
  }
}
