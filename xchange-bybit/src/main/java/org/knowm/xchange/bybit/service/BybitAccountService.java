package org.knowm.xchange.bybit.service;

import static org.knowm.xchange.bybit.BybitAdapters.adaptBybitBalances;
import static org.knowm.xchange.bybit.BybitAdapters.convertToBybitSymbol;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.bybit.BybitAdapters;
import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.account.BybitAccountInfoResponse;
import org.knowm.xchange.bybit.dto.account.allcoins.BybitAllCoinsBalance;
import org.knowm.xchange.bybit.dto.account.feerates.BybitFeeRates;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitAccountBalance;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitAccountType;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitWalletBalance;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.account.AccountService;

public class BybitAccountService extends BybitAccountServiceRaw implements AccountService {

  private final BybitAccountType accountType;

  public BybitAccountService(
      BybitExchange exchange,
      BybitAccountType accountType,
      ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
    this.accountType = accountType;
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    List<Wallet> adaptedWallets = getAdaptedWallets();
    return new AccountInfo(adaptedWallets);
  }

  /**
   * According to the risk limit, leverage affects the maximum position value that can be opened,
   * that is, the greater the leverage, the smaller the maximum position value that can be opened,
   * and vice versa
   *
   * @return true, if success
   */
  public boolean setLeverage(Instrument instrument, double leverage) throws IOException {
    BybitCategory category = BybitAdapters.getCategory(instrument);
    int retCode = setLeverageRaw(category, convertToBybitSymbol(instrument), leverage).getRetCode();
    return retCode == 0 || retCode == 110043;
  }

  /**
   * @param mode 0: Merged Single. 3: Both Sides
   * @throws IOException
   */
  public boolean switchPositionMode(
      BybitCategory category, Instrument instrument, String coin, int mode) throws IOException {
    String symbol = "";
    if (instrument != null) {
      symbol = BybitAdapters.convertToBybitSymbol(instrument);
    }
    int retCode = switchPositionModeRaw(category, symbol, coin, mode).getRetCode();
    return retCode == 0 || retCode == 110025;
  }

  private List<Wallet> getAdaptedWallets() throws IOException {
    switch (accountType) {
      case CONTRACT:
      case UNIFIED:
      case SPOT:
        return getAdaptedBalanceWallets();
      case INVESTMENT:
      case OPTION:
      case FUND:
        return getAdaptedAllCoinsWallets();
      default:
        throw new IllegalStateException("Unexpected value: " + accountType);
    }
  }

  private List<Wallet> getAdaptedAllCoinsWallets() throws IOException {
    BybitResult<BybitAllCoinsBalance> allCoinsBalanceResult = getAllCoinsBalance(accountType);
    BybitAllCoinsBalance allCoinsBalance = allCoinsBalanceResult.getResult();
    List<Wallet> wallets = new ArrayList<>();
    wallets.add(adaptBybitBalances(allCoinsBalance));
    return wallets;
  }

  private List<Wallet> getAdaptedBalanceWallets() throws IOException {
    BybitResult<BybitWalletBalance> walletBalances = getWalletBalances(accountType);
    BybitWalletBalance walletBalancesResult = walletBalances.getResult();
    List<BybitAccountBalance> accounts = walletBalancesResult.getList();
    return accounts.stream()
        .map(bybitAccountBalance -> adaptBybitBalances(bybitAccountBalance.getCoin()))
        .collect(Collectors.toList());
  }

  public BybitResult<BybitFeeRates> getFeeRates(BybitCategory category, Instrument instrument)
      throws IOException {
    String symbol = "";
    if (instrument != null) {
      symbol = BybitAdapters.convertToBybitSymbol(instrument);
    }
    return getFeeRatesRaw(category, symbol);
  }

  /** Query the account information, like margin mode, account mode, etc. */
  public BybitAccountInfoResponse accountInfo() throws IOException {
    return accountInfoRaw().getResult();
  }
}
