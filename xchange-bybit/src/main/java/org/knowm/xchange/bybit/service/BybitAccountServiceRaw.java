package org.knowm.xchange.bybit.service;

import static org.knowm.xchange.bybit.BybitAdapters.createBybitExceptionFromResult;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.account.allcoins.BybitAllCoinsBalance;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitAccountType;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitWalletBalance;

import org.knowm.xchange.bybit.dto.account.BybitBalances;

public class BybitAccountServiceRaw extends BybitBaseService {

  public BybitAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public BybitResult<BybitWalletBalance> getWalletBalances(BybitAccountType accountType)
      throws IOException {
    BybitResult<BybitWalletBalance> walletBalances =
        bybitAuthenticated.getWalletBalance(apiKey, accountType, nonceFactory, signatureCreator);
    if (!walletBalances.isSuccess()) {
      throw createBybitExceptionFromResult(walletBalances);
    }
    return walletBalances;
  }

  public BybitResult<BybitAllCoinsBalance> getAllCoinsBalance(BybitAccountType accountType)
      throws IOException {
    BybitResult<BybitAllCoinsBalance> allCoinsBalance =
        bybitAuthenticated.getAllCoinsBalance(apiKey, accountType, nonceFactory, signatureCreator);
    if (!allCoinsBalance.isSuccess()) {
      throw createBybitExceptionFromResult(allCoinsBalance);
    }
    return allCoinsBalance;
  }
}
