package org.knowm.xchange.bybit.service;

import static org.knowm.xchange.bybit.BybitAdapters.createBybitExceptionFromResult;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.account.allcoins.BybitAllCoinsBalance;
import org.knowm.xchange.bybit.dto.account.feerates.BybitFeeRates;
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
        bybitAuthenticated.getWalletBalance(
            apiKey, signatureCreator, nonceFactory, accountType.name());
    if (!walletBalances.isSuccess()) {
      throw createBybitExceptionFromResult(walletBalances);
    }
    return walletBalances;
  }

  public BybitResult<BybitAllCoinsBalance> getAllCoinsBalance(BybitAccountType accountType)
      throws IOException {
    BybitResult<BybitAllCoinsBalance> allCoinsBalance =
        bybitAuthenticated.getAllCoinsBalance(
            apiKey, signatureCreator, nonceFactory, accountType.name());
    if (!allCoinsBalance.isSuccess()) {
      throw createBybitExceptionFromResult(allCoinsBalance);
    }
    return allCoinsBalance;
  }

  public BybitResult<BybitFeeRates> getFeeRates(BybitCategory category, String symbol)
      throws IOException {
    BybitResult<BybitFeeRates> bybitFeeRatesResult =
        bybitAuthenticated.getFeeRates(
            apiKey, signatureCreator, nonceFactory, category.getValue(), symbol);
    if (!bybitFeeRatesResult.isSuccess()) {
      throw createBybitExceptionFromResult(bybitFeeRatesResult);
    }
    return bybitFeeRatesResult;
  }
}
