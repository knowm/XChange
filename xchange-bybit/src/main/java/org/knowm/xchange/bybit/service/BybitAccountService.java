package org.knowm.xchange.bybit.service;

import static org.knowm.xchange.bybit.BybitAdapters.adaptBybitBalances;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import static org.knowm.xchange.bybit.BybitAdapters.adaptBybitBalances;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.account.allcoins.BybitAllCoinsBalance;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitAccountBalance;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitAccountType;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitWalletBalance;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.service.account.AccountService;

public class BybitAccountService extends BybitAccountServiceRaw implements AccountService {

  private final BybitAccountType accountType;

  public BybitAccountService(Exchange exchange, BybitAccountType accountType) {
    super(exchange);
    this.accountType = accountType;
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    List<Wallet> adaptedWallets = getAdaptedWallets();
    return new AccountInfo(adaptedWallets);
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
}
