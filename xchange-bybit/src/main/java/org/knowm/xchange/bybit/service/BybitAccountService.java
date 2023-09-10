package org.knowm.xchange.bybit.service;

import static org.knowm.xchange.bybit.BybitAdapters.adaptBybitBalances;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.account.BybitAccountBalance;
import org.knowm.xchange.bybit.dto.account.BybitAccountType;
import org.knowm.xchange.bybit.dto.account.BybitWalletBalance;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.service.account.AccountService;

public class BybitAccountService extends BybitAccountServiceRaw implements AccountService {

  public BybitAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    BybitResult<BybitWalletBalance> walletBalances = getWalletBalances(BybitAccountType.UNIFIED);
    BybitWalletBalance walletBalancesResult = walletBalances.getResult();
    List<BybitAccountBalance> accounts = walletBalancesResult.getList();
    List<Wallet> adaptedWallets =
        accounts.stream()
            .map(bybitAccountBalance -> adaptBybitBalances(bybitAccountBalance.getCoins()))
            .collect(Collectors.toList());
    return new AccountInfo(adaptedWallets);
  }
}
