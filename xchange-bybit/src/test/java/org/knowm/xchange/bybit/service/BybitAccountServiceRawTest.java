package org.knowm.xchange.bybit.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.account.allcoins.BybitAllCoinBalance;
import org.knowm.xchange.bybit.dto.account.allcoins.BybitAllCoinsBalance;
import org.knowm.xchange.bybit.dto.account.feerates.BybitFeeRate;
import org.knowm.xchange.bybit.dto.account.feerates.BybitFeeRates;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitAccountBalance;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitAccountType;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitCoinWalletBalance;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitWalletBalance;

public class BybitAccountServiceRawTest extends BaseWiremockTest {

  private BybitAccountServiceRaw bybitAccountServiceRaw;

  @Before
  public void setUp() throws Exception {
    Exchange bybitExchange = createExchange();
    bybitAccountServiceRaw = new BybitAccountServiceRaw(bybitExchange);
  }

  @Test
  public void testGetWalletBalances() throws IOException {
    initGetStub("/v5/account/wallet-balance", "/getWalletBalance.json5");

    BybitResult<BybitWalletBalance> walletBalances =
        bybitAccountServiceRaw.getWalletBalances(BybitAccountType.UNIFIED);

    BybitWalletBalance walletBalance = walletBalances.getResult();

    assertThat(walletBalance.getList()).hasSize(1);
    BybitAccountBalance accountBalance = walletBalance.getList().get(0);

    assertThat(accountBalance.getTotalEquity()).isEqualTo("3.31216591");
    assertThat(accountBalance.getAccountIMRate()).isEqualTo("0");
    assertThat(accountBalance.getTotalMarginBalance()).isEqualTo("3.00326056");
    assertThat(accountBalance.getTotalInitialMargin()).isEqualTo("0");
    assertThat(accountBalance.getAccountType()).isEqualTo(BybitAccountType.UNIFIED);
    assertThat(accountBalance.getTotalAvailableBalance()).isEqualTo("3.00326056");
    assertThat(accountBalance.getAccountMMRate()).isEqualTo("0");
    assertThat(accountBalance.getTotalPerpUPL()).isEqualTo("0");
    assertThat(accountBalance.getTotalWalletBalance()).isEqualTo("3.00326056");
    assertThat(accountBalance.getAccountLTV()).isEqualTo("0");
    assertThat(accountBalance.getTotalMaintenanceMargin()).isEqualTo("0");

    assertThat(accountBalance.getCoin()).hasSize(1);
    List<BybitCoinWalletBalance> coins = accountBalance.getCoin();

    assertThat(coins.get(0).getAvailableToBorrow()).isEqualTo("3");
    assertThat(coins.get(0).getBonus()).isEqualTo("0");
    assertThat(coins.get(0).getAccruedInterest()).isEqualTo("0");
    assertThat(coins.get(0).getAvailableToWithdraw()).isEqualTo("0");
    assertThat(coins.get(0).getTotalOrderIM()).isEqualTo("0");
    assertThat(coins.get(0).getEquity()).isEqualTo("0");
    assertThat(coins.get(0).getTotalPositionMM()).isEqualTo("0");
    assertThat(coins.get(0).getUsdValue()).isEqualTo("0");
    assertThat(coins.get(0).getUnrealisedPnl()).isEqualTo("0");
    assertThat(coins.get(0).isCollateralSwitch()).isEqualTo(true);
    assertThat(coins.get(0).getBorrowAmount()).isEqualTo("0.0");
    assertThat(coins.get(0).getTotalPositionIM()).isEqualTo("0");
    assertThat(coins.get(0).getWalletBalance()).isEqualTo("0");
    assertThat(coins.get(0).getFree()).isNull();
    assertThat(coins.get(0).getCumRealisedPnl()).isEqualTo("0");
    assertThat(coins.get(0).getLocked()).isEqualTo("0");
    assertThat(coins.get(0).isMarginCollateral()).isEqualTo(true);
    assertThat(coins.get(0).getCoin()).isEqualTo("BTC");
  }

  @Test
  public void testGetAllCoinsBalances() throws IOException {
    initGetStub("/v5/asset/transfer/query-account-coins-balance", "/getAllCoinsBalance.json5");

    BybitResult<BybitAllCoinsBalance> coinsBalanceBybitResult =
        bybitAccountServiceRaw.getAllCoinsBalance(BybitAccountType.FUND, null, null, null);

    BybitAllCoinsBalance coinsBalance = coinsBalanceBybitResult.getResult();

    assertThat(coinsBalance.getMemberId()).isEqualTo("XXXX");
    assertThat(coinsBalance.getAccountType()).isEqualTo(BybitAccountType.FUND);

    assertThat(coinsBalance.getBalance()).hasSize(1);
    BybitAllCoinBalance coinBalance = coinsBalance.getBalance().get(0);

    assertThat(coinBalance.getCoin()).isEqualTo("USDC");
    assertThat(coinBalance.getTransferBalance()).isEqualTo("0");
    assertThat(coinBalance.getWalletBalance()).isEqualTo("0");
    assertThat(coinBalance.getBonus()).isNull();
  }

  @Test
  public void testGetFeeRates() throws IOException {
    initGetStub("/v5/account/fee-rate", "/getFeeRates.json5");

    BybitResult<BybitFeeRates> bybitFeeRatesBybitResult =
        bybitAccountServiceRaw.getFeeRates(BybitCategory.SPOT, "ETHUSDT");

    BybitFeeRates feeRates = bybitFeeRatesBybitResult.getResult();

    assertThat(feeRates.getList()).hasSize(1);
    BybitFeeRate feeRate = feeRates.getList().get(0);

    assertThat(feeRate.getSymbol()).isEqualTo("ETHUSDT");
    assertThat(feeRate.getTakerFeeRate()).isEqualTo("0.0006");
    assertThat(feeRate.getMakerFeeRate()).isEqualTo("0.0001");
  }
}
