package org.knowm.xchange.btcmarkets.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.powermock.api.mockito.PowerMockito.mock;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.btcmarkets.BTCMarketsAuthenticated;
import org.knowm.xchange.btcmarkets.BTCMarketsExchange;
import org.knowm.xchange.btcmarkets.BtcMarketsAssert;
import org.knowm.xchange.btcmarkets.dto.account.BTCMarketsBalance;
import org.knowm.xchange.btcmarkets.dto.account.BTCMarketsFundtransfer;
import org.knowm.xchange.btcmarkets.dto.account.BTCMarketsFundtransferHistoryResponse;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsWithdrawCryptoRequest;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsWithdrawCryptoResponse;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import si.mazi.rescu.SynchronizedValueFactory;

@RunWith(PowerMockRunner.class)
@PrepareForTest(BTCMarketsAuthenticated.class)
public class BTCMarketsAccountServiceTest extends BTCMarketsTestSupport {

  private BTCMarketsAccountService accountService;

  @Before
  public void setUp() {
    BTCMarketsExchange exchange =
        (BTCMarketsExchange)
            ExchangeFactory.INSTANCE.createExchange(BTCMarketsExchange.class.getCanonicalName());
    ExchangeSpecification specification = exchange.getExchangeSpecification();
    specification.setUserName(SPECIFICATION_USERNAME);
    specification.setApiKey(SPECIFICATION_API_KEY);
    specification.setSecretKey(SPECIFICATION_SECRET_KEY);

    accountService = new BTCMarketsAccountService(exchange);
  }

  @Test
  public void shouldCreateAccountInfo() throws IOException {
    // given
    BTCMarketsBalance balance = parse(BTCMarketsBalance.class);

    BTCMarketsAuthenticated btcm = mock(BTCMarketsAuthenticated.class);
    PowerMockito.when(
            btcm.getBalance(
                Mockito.eq(SPECIFICATION_API_KEY),
                Mockito.any(SynchronizedValueFactory.class),
                Mockito.any(BTCMarketsDigest.class)))
        .thenReturn(Arrays.asList(balance));
    Whitebox.setInternalState(accountService, "btcm", btcm);

    // when
    AccountInfo accountInfo = accountService.getAccountInfo();

    // then
    assertThat(accountInfo.getUsername()).isEqualTo(SPECIFICATION_USERNAME);
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

    BTCMarketsAuthenticated btcm = mock(BTCMarketsAuthenticated.class);
    PowerMockito.when(
            btcm.withdrawCrypto(
                Mockito.eq(SPECIFICATION_API_KEY),
                Mockito.any(SynchronizedValueFactory.class),
                Mockito.any(BTCMarketsDigest.class),
                Mockito.any(BTCMarketsWithdrawCryptoRequest.class)))
        .thenReturn(response);
    Whitebox.setInternalState(accountService, "btcm", btcm);

    // when
    String result = accountService.withdrawFunds(Currency.BTC, BigDecimal.TEN, "any address");

    assertThat(result).isNull();
  }

  @Test(expected = NotYetImplementedForExchangeException.class)
  public void shouldFailWhenRequestDepositAddress() throws IOException {
    // when
    accountService.requestDepositAddress(Currency.BTC);

    // then
    fail(
        "BTCMarketsAccountService should throw NotYetImplementedForExchangeException when call requestDepositAddress");
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

    BTCMarketsAuthenticated btcm = mock(BTCMarketsAuthenticated.class);
    PowerMockito.when(
            btcm.fundtransferHistory(
                Mockito.eq(SPECIFICATION_API_KEY),
                Mockito.any(SynchronizedValueFactory.class),
                Mockito.any(BTCMarketsDigest.class)))
        .thenReturn(response);
    Whitebox.setInternalState(accountService, "btcm", btcm);

    // when
    List<FundingRecord> result =
        accountService.getFundingHistory(accountService.createFundingHistoryParams());
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
