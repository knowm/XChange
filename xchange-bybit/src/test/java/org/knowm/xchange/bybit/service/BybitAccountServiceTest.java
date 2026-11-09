package org.knowm.xchange.bybit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitAccountType;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Fee;
import org.knowm.xchange.instrument.Instrument;

@Disabled
public class BybitAccountServiceTest extends BaseWiremockTest {

  static BybitExchange bybitExchange;
  static BybitAccountService bybitAccountService;

  public void setUp() throws IOException {
    bybitExchange = createExchange();
    bybitAccountService =
        new BybitAccountService(
            bybitExchange, BybitAccountType.UNIFIED, bybitExchange.getResilienceRegistries());
  }

  @Test
  void getWalletBalancesWithUnified() throws Exception {
    setUp();
    initGetStub("/v5/account/wallet-balance", "/getWalletBalance.json5");
    AccountInfo accountInfo = bybitAccountService.getAccountInfo();
    assertThat(accountInfo.getWallet().getBalance(new Currency("BTC")).getTotal())
        .isEqualTo(new BigDecimal("0"));
    assertThat(accountInfo.getWallet().getBalance(new Currency("BTC")).getAvailable())
        .isEqualTo(new BigDecimal("0"));
  }

  @Test
  void getAllCoinsBalanceWithFund() throws Exception {
    BybitExchange bybitExchange = createExchange();
    BybitAccountService bybitAccountService =
        new BybitAccountService(
            bybitExchange, BybitAccountType.FUND, bybitExchange.getResilienceRegistries());

    initGetStub("/v5/asset/transfer/query-account-coins-balance", "/getAllCoinsBalance.json5");
    AccountInfo accountInfo = bybitAccountService.getAccountInfo();
    assertThat(accountInfo.getWallet().getBalance(new Currency("USDC")).getTotal())
        .isEqualTo(new BigDecimal("0"));
    assertThat(accountInfo.getWallet().getBalance(new Currency("USDC")).getAvailable())
        .isEqualTo(new BigDecimal("0"));
  }

  @Test
  void getFeeRate() throws Exception {
    setUp();
    initGetStub("/v5/account/fee-rate", "/getFeeRates.json5");
    Instrument ETH_USDT = new CurrencyPair("ETH/USDT");
    Map<Instrument, Fee> feeMap =
        bybitAccountService.getDynamicTradingFeesByInstrument(BybitCategory.SPOT.getValue());
    Fee feeRate = feeMap.get(ETH_USDT);

    assertThat(feeRate.getTakerFee()).isEqualTo("0.0006");
    assertThat(feeRate.getMakerFee()).isEqualTo("0.0001");
  }

  @Test
  void setLeverage() throws Exception {
    setUp();
    initPostStub("/v5/position/set-leverage", "/setLeverage.json5");
    assertThatThrownBy(() -> bybitAccountService.setLeverage(new CurrencyPair("ETH/USDT"), 1))
        .isInstanceOf(UnsupportedOperationException.class);
    boolean bybitSetLeverageBybitResult =
        bybitAccountService.setLeverage(new FuturesContract("ETH/USDT/PERP"), 1);
    assertThat(bybitSetLeverageBybitResult).isTrue();
  }

  @Test
  void switchPositionMode() throws Exception {
    setUp();
    initPostStub("/v5/position/switch-mode", "/switchPositionMode.json5");

    BybitAccountService bybitAccountService =
        (BybitAccountService) bybitExchange.getAccountService();
    boolean bybitSwitchPositionModeResult =
        bybitAccountService.switchPositionMode(
            BybitCategory.LINEAR, new FuturesContract("BTC/USDT/PERP"), null, 0);

    assertThat(bybitSwitchPositionModeResult).isTrue();
  }
}
