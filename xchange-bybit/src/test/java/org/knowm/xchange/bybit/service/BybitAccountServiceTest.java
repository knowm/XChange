package org.knowm.xchange.bybit.service;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import jakarta.ws.rs.core.Response.Status;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.account.feerates.BybitFeeRate;
import org.knowm.xchange.bybit.dto.account.feerates.BybitFeeRates;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitAccountType;
import org.knowm.xchange.bybit.service.BybitTradeService.BybitCancelAllOrdersParams;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.instrument.Instrument;

public class BybitAccountServiceTest extends BaseWiremockTest {

  static BybitExchange bybitExchange;
  static BybitAccountService bybitAccountService;

  public void setUp() throws IOException {
   bybitExchange = createExchange();
    bybitAccountService =
        new BybitAccountService(bybitExchange, BybitAccountType.UNIFIED,bybitExchange.getResilienceRegistries());
  }

  @Test
  public void testGetWalletBalancesWithUnified() throws IOException {
    setUp();
    initGetStub("/v5/account/wallet-balance", "/getWalletBalance.json5");
    AccountInfo accountInfo = bybitAccountService.getAccountInfo();
    assertThat(accountInfo.getWallet().getBalance(new Currency("BTC")).getTotal())
        .isEqualTo(new BigDecimal("0"));
    assertThat(accountInfo.getWallet().getBalance(new Currency("BTC")).getAvailable())
        .isEqualTo(new BigDecimal("0"));
  }

  @Test
  public void testGetAllCoinsBalanceWithFund() throws IOException {
    BybitExchange bybitExchange = createExchange();
    BybitAccountService bybitAccountService =
        new BybitAccountService(bybitExchange, BybitAccountType.FUND,bybitExchange.getResilienceRegistries());

    initGetStub("/v5/asset/transfer/query-account-coins-balance", "/getAllCoinsBalance.json5");
    AccountInfo accountInfo = bybitAccountService.getAccountInfo();
    assertThat(accountInfo.getWallet().getBalance(new Currency("USDC")).getTotal())
        .isEqualTo(new BigDecimal("0"));
    assertThat(accountInfo.getWallet().getBalance(new Currency("USDC")).getAvailable())
        .isEqualTo(new BigDecimal("0"));
  }

  @Test
  public void testGetFeeRate() throws IOException {
    setUp();
    initGetStub("/v5/account/fee-rate", "/getFeeRates.json5");
    Instrument ETH_USDT = new CurrencyPair("ETH/USDT");
    BybitResult<BybitFeeRates> bybitFeeRatesBybitResult =
        bybitAccountService.getFeeRates(BybitCategory.SPOT, ETH_USDT);
    BybitFeeRates feeRates = bybitFeeRatesBybitResult.getResult();
    assertThat(feeRates.getList()).hasSize(1);
    BybitFeeRate feeRate = feeRates.getList().get(0);

    assertThat(feeRate.getSymbol()).isEqualTo("ETHUSDT");
    assertThat(feeRate.getTakerFeeRate()).isEqualTo("0.0006");
    assertThat(feeRate.getMakerFeeRate()).isEqualTo("0.0001");
  }

  @Test
  public void testSetLeverage() throws IOException {
    setUp();
    initPostStub("/v5/position/set-leverage", "/setLeverage.json5");
    try {
      bybitAccountService.setLeverage( new CurrencyPair("ETH/USDT"),1d);
      fail("Expected UnsupportedOperationException");
    } catch (UnsupportedOperationException ignored) {

    }
    boolean  bybitSetLeverageBybitResult =
        bybitAccountService.setLeverage( new FuturesContract("ETH/USDT/PERP"),1d);
    assertThat(bybitSetLeverageBybitResult).isTrue();
  }

  @Test
  public void testSwitchPositionMode() throws IOException {
    setUp();
    initPostStub("/v5/position/switch-mode", "/switchPositionMode.json5");

    BybitAccountService bybitAccountService = (BybitAccountService) bybitExchange.getAccountService();
      boolean bybitSwitchPositionModeResult = bybitAccountService.switchPositionMode( BybitCategory.LINEAR,new FuturesContract("BTC/USDT/PERP"),null,0);

    assertThat(bybitSwitchPositionModeResult).isTrue();
  }


}
