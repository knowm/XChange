package org.knowm.xchange.btcmarkets.service;

// Note:
//    I tried my best to get powermock to work after adding a logback.xml file to suppress verbose
// logging but there are some Java 9* issues
//    arising with powermock that I just cannot resolve. Therefore I decided to comment out all the
// powermock code in this module since it's the
//    only module using powermock. The dependencies have also been removed from the project.
// Hopefully someone will upgrade all these test to use
//    Mokito.

// import static org.assertj.core.api.Assertions.assertThat;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.eq;
// import static org.mockito.BDDMockito.given;
// import static org.powermock.api.mockito.PowerMockito.mock;
// import static org.powermock.api.mockito.PowerMockito.when;
//
// import java.io.IOException;
// import java.math.BigDecimal;
// import java.util.Arrays;
// import java.util.Date;
// import java.util.List;
//
// import org.junit.Before;
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.knowm.xchange.ExchangeFactory;
// import org.knowm.xchange.ExchangeSpecification;
// import org.knowm.xchange.btcmarkets.BTCMarkets;
// import org.knowm.xchange.btcmarkets.BTCMarketsAuthenticated;
// import org.knowm.xchange.btcmarkets.BTCMarketsAuthenticatedV3;
// import org.knowm.xchange.btcmarkets.BTCMarketsExchange;
// import org.knowm.xchange.btcmarkets.BtcMarketsAssert;
// import org.knowm.xchange.btcmarkets.dto.account.BTCMarketsBalance;
// import org.knowm.xchange.btcmarkets.dto.account.BTCMarketsFundtransfer;
// import org.knowm.xchange.btcmarkets.dto.account.BTCMarketsFundtransferHistoryResponse;
// import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsWithdrawCryptoRequest;
// import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsWithdrawCryptoResponse;
// import org.knowm.xchange.btcmarkets.dto.v3.account.BTCMarketsAddressesResponse;
// import org.knowm.xchange.client.ExchangeRestProxyBuilder;
// import org.knowm.xchange.currency.Currency;
// import org.knowm.xchange.dto.account.AccountInfo;
// import org.knowm.xchange.dto.account.FundingRecord;
// import org.knowm.xchange.service.trade.params.RippleWithdrawFundsParams;
// import org.mockito.ArgumentCaptor;
// import org.mockito.Mockito;
// import org.powermock.api.mockito.PowerMockito;
// import org.powermock.core.classloader.annotations.PrepareForTest;
// import org.powermock.modules.junit4.PowerMockRunner;
//
// import si.mazi.rescu.SynchronizedValueFactory;
//
// @RunWith(PowerMockRunner.class)
// @PrepareForTest(ExchangeRestProxyBuilder.class)
public class BTCMarketsAccountServiceTest extends BTCMarketsTestSupport {

  //  private BTCMarketsAuthenticated btcm;
  //  private BTCMarketsAuthenticatedV3 btcmv3;
  //  private BTCMarkets btcMarkets;
  //  private BTCMarketsAccountService accountService;
  //
  //  @Before
  //  public void setUp() {
  //    btcm = mock(BTCMarketsAuthenticated.class);
  //    btcmv3 = mock(BTCMarketsAuthenticatedV3.class);
  //    PowerMockito.mockStatic(ExchangeRestProxyBuilder.class);
  //
  //    final ExchangeSpecification defaultExchangeSpecification =
  //        new BTCMarketsExchange().getDefaultExchangeSpecification();
  //
  //    ExchangeRestProxyBuilder<BTCMarketsAuthenticated> exchangeRestProxyBuilderMock =
  //        mock(ExchangeRestProxyBuilder.class);
  //    given(
  //            ExchangeRestProxyBuilder.forInterface(
  //                eq(BTCMarketsAuthenticated.class), eq(defaultExchangeSpecification)))
  //        .willReturn(exchangeRestProxyBuilderMock);
  //    ExchangeRestProxyBuilder<BTCMarketsAuthenticatedV3> exchangeRestProxyBuilderV3Mock =
  //        mock(ExchangeRestProxyBuilder.class);
  //    ExchangeRestProxyBuilder<BTCMarkets> exchangeRestProxyBuilderBTCMarketsMock =
  //        mock(ExchangeRestProxyBuilder.class);
  //
  //    given(ExchangeRestProxyBuilder.forInterface(eq(BTCMarketsAuthenticated.class), any()))
  //        .willReturn(exchangeRestProxyBuilderMock);
  //    given(ExchangeRestProxyBuilder.forInterface(eq(BTCMarketsAuthenticatedV3.class), any()))
  //        .willReturn(exchangeRestProxyBuilderV3Mock);
  //    given(ExchangeRestProxyBuilder.forInterface(eq(BTCMarkets.class), any()))
  //        .willReturn(exchangeRestProxyBuilderBTCMarketsMock);
  //
  //    when(exchangeRestProxyBuilderMock.build()).thenReturn(btcm);
  //    when(exchangeRestProxyBuilderV3Mock.build()).thenReturn(btcmv3);
  //    when(exchangeRestProxyBuilderBTCMarketsMock.build()).thenReturn(btcMarkets);
  //    BTCMarketsExchange exchange =
  //        (BTCMarketsExchange)
  //
  // ExchangeFactory.INSTANCE.createExchange(BTCMarketsExchange.class.getCanonicalName());
  //    ExchangeSpecification specification = exchange.getExchangeSpecification();
  //    specification.setUserName(SPECIFICATION_USERNAME);
  //    specification.setApiKey(SPECIFICATION_API_KEY);
  //    specification.setSecretKey(SPECIFICATION_SECRET_KEY);
  //
  //    accountService = new BTCMarketsAccountService(exchange);
  //  }
  //
  //  @Test
  //  public void shouldCreateAccountInfo() throws IOException {
  //    // given
  //    BTCMarketsBalance balance = parse(BTCMarketsBalance.class);
  //
  //    PowerMockito.when(
  //            btcm.getBalance(
  //                Mockito.eq(SPECIFICATION_API_KEY),
  //                Mockito.any(SynchronizedValueFactory.class),
  //                Mockito.any(BTCMarketsDigest.class)))
  //        .thenReturn(Arrays.asList(balance));
  //
  //    // when
  //    AccountInfo accountInfo = accountService.getAccountInfo();
  //
  //    // then
  //    assertThat(accountInfo.getUsername()).isEqualTo(SPECIFICATION_USERNAME);
  //    assertThat(accountInfo.getTradingFee()).isNull();
  //    assertThat(accountInfo.getWallets()).hasSize(1);
  //
  //    BtcMarketsAssert.assertEquals(
  //        accountInfo.getWallet().getBalance(Currency.BTC), EXPECTED_BALANCE);
  //  }
  //
  //  @Test
  //  public void withdrawFundsShouldReturnNull() throws IOException {
  //
  //    String status = "the-status"; // maybe the id would be more useful?
  //    BTCMarketsWithdrawCryptoResponse response =
  //        new BTCMarketsWithdrawCryptoResponse(
  //            true, null, null, status, "id", "desc", "ccy", BigDecimal.ONE, BigDecimal.ONE, 0L);
  //
  //    PowerMockito.when(
  //            btcm.withdrawCrypto(
  //                Mockito.eq(SPECIFICATION_API_KEY),
  //                Mockito.any(SynchronizedValueFactory.class),
  //                Mockito.any(BTCMarketsDigest.class),
  //                Mockito.any(BTCMarketsWithdrawCryptoRequest.class)))
  //        .thenReturn(response);
  //
  //    // when
  //    String result = accountService.withdrawFunds(Currency.BTC, BigDecimal.TEN, "any address");
  //
  //    assertThat(result).isNull();
  //  }
  //
  //  @Test
  //  public void withdrawFundsShouldAppendRippleTag() throws IOException {
  //
  //    String status = "the-status"; // maybe the id would be more useful?
  //    BTCMarketsWithdrawCryptoResponse response =
  //        new BTCMarketsWithdrawCryptoResponse(
  //            true, null, null, status, "id", "desc", "ccy", BigDecimal.ONE, BigDecimal.ONE, 0L);
  //    ArgumentCaptor<BTCMarketsWithdrawCryptoRequest> captor =
  //        ArgumentCaptor.forClass(BTCMarketsWithdrawCryptoRequest.class);
  //
  //    PowerMockito.when(
  //            btcm.withdrawCrypto(
  //                Mockito.eq(SPECIFICATION_API_KEY),
  //                Mockito.any(SynchronizedValueFactory.class),
  //                Mockito.any(BTCMarketsDigest.class),
  //                captor.capture()))
  //        .thenReturn(response);
  //
  //    // when
  //    RippleWithdrawFundsParams params =
  //        new RippleWithdrawFundsParams("any address", Currency.BTC, BigDecimal.TEN, "12345");
  //    String result = accountService.withdrawFunds(params);
  //    assertThat(captor.getValue().address).isEqualTo("any address?dt=12345");
  //    assertThat(result).isNull();
  //  }
  //
  //  @Test
  //  public void shouldRequestDepositAddress() throws IOException {
  //    BTCMarketsAddressesResponse response = new BTCMarketsAddressesResponse("address");
  //
  //    ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
  //
  //    PowerMockito.when(
  //            btcmv3.depositAddress(
  //                Mockito.eq(SPECIFICATION_API_KEY),
  //                Mockito.any(SynchronizedValueFactory.class),
  //                Mockito.any(BTCMarketsDigestV3.class),
  //                captor.capture()))
  //        .thenReturn(response);
  //    // when
  //    String address = accountService.requestDepositAddress(Currency.BTC);
  //
  //    // then
  //    assertThat(captor.getValue()).isEqualTo("BTC");
  //    assertThat(address).isEqualTo("address");
  //  }
  //
  //  @Test
  //  public void getFundingHistoryShouldReturnFundingRecors() throws IOException {
  //    Date creationTime = new Date();
  //    Date lastUpdate = new Date();
  //    BTCMarketsFundtransfer fundtransfer =
  //        new BTCMarketsFundtransfer(
  //            "Complete",
  //            lastUpdate,
  //            BigDecimal.ONE,
  //            "desc",
  //            null,
  //            creationTime,
  //            12345L,
  //            new BTCMarketsFundtransfer.CryptoPaymentDetail("tx1", "address"),
  //            "BTC",
  //            BigDecimal.valueOf(123.45),
  //            "DEPOSIT");
  //
  //    BTCMarketsFundtransferHistoryResponse response =
  //        new BTCMarketsFundtransferHistoryResponse(
  //            true,
  //            null,
  //            null,
  //            Arrays.asList(fundtransfer),
  //            new BTCMarketsFundtransferHistoryResponse.Paging("12345", "12345"));
  //
  //    PowerMockito.when(
  //            btcm.fundtransferHistory(
  //                Mockito.eq(SPECIFICATION_API_KEY),
  //                Mockito.any(SynchronizedValueFactory.class),
  //                Mockito.any(BTCMarketsDigest.class)))
  //        .thenReturn(response);
  //
  //    // when
  //    List<FundingRecord> result =
  //        accountService.getFundingHistory(accountService.createFundingHistoryParams());
  //    assertThat(result).hasSize(1);
  //    assertThat(result.get(0).getType()).isEqualTo(FundingRecord.Type.DEPOSIT);
  //    assertThat(result.get(0).getStatus()).isEqualTo(FundingRecord.Status.COMPLETE);
  //    assertThat(result.get(0).getCurrency()).isEqualTo(Currency.BTC);
  //    assertThat(result.get(0).getInternalId()).isEqualTo("12345");
  //    assertThat(result.get(0).getFee()).isEqualTo(BigDecimal.ONE);
  //    assertThat(result.get(0).getDescription()).isEqualTo("desc");
  //    assertThat(result.get(0).getDate()).isEqualTo(creationTime);
  //    assertThat(result.get(0).getBlockchainTransactionHash()).isEqualTo("tx1");
  //    assertThat(result.get(0).getBalance()).isNull();
  //    assertThat(result.get(0).getAmount()).isEqualTo(BigDecimal.valueOf(123.45));
  //    assertThat(result.get(0).getAddress()).isEqualTo("address");
  //  }
}
