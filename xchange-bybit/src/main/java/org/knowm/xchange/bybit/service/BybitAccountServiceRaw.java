package org.knowm.xchange.bybit.service;

import static org.knowm.xchange.bybit.BybitAdapters.createBybitExceptionFromResult;

import java.io.IOException;
import java.util.Date;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.account.BybitInternalTransfersResponse;
import org.knowm.xchange.bybit.dto.account.BybitInternalTransfersResponse.BybitTransferStatus;
import org.knowm.xchange.bybit.dto.account.allcoins.BybitAllCoinsBalance;
import org.knowm.xchange.bybit.dto.account.feerates.BybitFeeRates;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitAccountType;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitWalletBalance;

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

  public BybitResult<BybitAllCoinsBalance> getAllCoinsBalance(BybitAccountType accountType, String memberId, String coin, Integer withBonus)
      throws IOException {
    BybitResult<BybitAllCoinsBalance> allCoinsBalance =
        bybitAuthenticated.getAllCoinsBalance(
            apiKey,
            signatureCreator,
            nonceFactory,
            memberId,
            accountType.name(),
            coin,
            withBonus
        );
    if (!allCoinsBalance.isSuccess()) {
      throw createBybitExceptionFromResult(allCoinsBalance);
    }
    return allCoinsBalance;
  }

  public BybitResult<BybitInternalTransfersResponse> getBybitInternalTransfers(String transferId, String coin, BybitTransferStatus status, Date startTime, Date endTime, Integer limit, String cursor)
      throws IOException {
    BybitResult<BybitInternalTransfersResponse> internalTransfers =
        bybitAuthenticated.getInternalTransferRecords(
            apiKey,
            signatureCreator,
            nonceFactory,
            transferId,
            coin,
            (status == null) ? null : status.name(),
            (startTime == null) ? null : startTime.toInstant().toEpochMilli(),
            (endTime == null) ? null : endTime.toInstant().toEpochMilli(),
            limit,
            cursor
        );
    if (!internalTransfers.isSuccess()) {
      throw createBybitExceptionFromResult(internalTransfers);
    }
    return internalTransfers;
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
