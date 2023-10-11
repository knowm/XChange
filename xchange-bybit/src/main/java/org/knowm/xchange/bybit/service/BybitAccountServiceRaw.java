package org.knowm.xchange.bybit.service;

import static org.knowm.xchange.bybit.BybitAdapters.createBybitExceptionFromResult;

import java.io.IOException;
import java.util.Date;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.account.BybitDepositRecordsResponse;
import org.knowm.xchange.bybit.dto.account.BybitInternalDepositRecordsResponse;
import org.knowm.xchange.bybit.dto.account.BybitTransactionLogResponse;
import org.knowm.xchange.bybit.dto.account.BybitTransactionLogResponse.BybitTransactionLog.BybitTransactionLogType;
import org.knowm.xchange.bybit.dto.account.BybitTransfersResponse;
import org.knowm.xchange.bybit.dto.account.BybitTransfersResponse.BybitTransferStatus;
import org.knowm.xchange.bybit.dto.account.BybitAllCoinsBalance;
import org.knowm.xchange.bybit.dto.account.BybitFeeRates;
import org.knowm.xchange.bybit.dto.account.BybitWithdrawRecordsResponse;
import org.knowm.xchange.bybit.dto.account.BybitWithdrawRecordsResponse.BybitWithdrawRecord.BybitWithdrawType;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitAccountType;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitWalletBalance;
import org.knowm.xchange.currency.Currency;

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

  public BybitResult<BybitAllCoinsBalance> getAllCoinsBalance(
      BybitAccountType accountType, String memberId, String coin, boolean withBonus)
      throws IOException {
    BybitResult<BybitAllCoinsBalance> allCoinsBalance =
        bybitAuthenticated.getAllCoinsBalance(
            apiKey,
            signatureCreator,
            nonceFactory,
            memberId,
            accountType.name(),
            coin,
            !withBonus ? 0 : 1);
    if (!allCoinsBalance.isSuccess()) {
      throw createBybitExceptionFromResult(allCoinsBalance);
    }
    return allCoinsBalance;
  }

  public BybitResult<BybitAllCoinsBalance> getSingleCoinBalance(
      String memberId,
      String toMemberId,
      BybitAccountType accountType,
      BybitAccountType toAccountType,
      Currency coin,
      boolean withBonus,
      boolean withTransferSafeAmount,
      boolean withLtvTransferSafeAmount)
      throws IOException {
    BybitResult<BybitAllCoinsBalance> singleCoinBalance =
        bybitAuthenticated.getSingleCoinBalance(
            apiKey,
            signatureCreator,
            nonceFactory,
            memberId,
            toMemberId,
            (accountType == null) ? null : accountType.name(),
            (toAccountType == null) ? null : toAccountType.name(),
            (coin == null) ? null : coin.toString(),
            (!withBonus) ? 0 : 1,
            (!withTransferSafeAmount) ? 0 : 1,
            (!withLtvTransferSafeAmount) ? 0 : 1);
    if (!singleCoinBalance.isSuccess()) {
      throw createBybitExceptionFromResult(singleCoinBalance);
    }
    return singleCoinBalance;
  }

  public BybitResult<BybitTransfersResponse> getBybitInternalTransfers(
      String transferId,
      Currency coin,
      BybitTransferStatus status,
      Date startTime,
      Date endTime,
      Integer limit,
      String cursor)
      throws IOException {
    BybitResult<BybitTransfersResponse> internalTransfers =
        bybitAuthenticated.getInternalTransferRecords(
            apiKey,
            signatureCreator,
            nonceFactory,
            transferId,
            (coin == null) ? null : coin.toString(),
            (status == null) ? null : status.name(),
            (startTime == null) ? null : startTime.toInstant().toEpochMilli(),
            (endTime == null) ? null : endTime.toInstant().toEpochMilli(),
            limit,
            cursor);
    if (!internalTransfers.isSuccess()) {
      throw createBybitExceptionFromResult(internalTransfers);
    }
    return internalTransfers;
  }

  public BybitResult<BybitTransfersResponse> getBybitUniversalTransfers(
      String transferId,
      Currency coin,
      BybitTransferStatus status,
      Date startTime,
      Date endTime,
      Integer limit,
      String cursor)
      throws IOException {
    BybitResult<BybitTransfersResponse> universalTransfers =
        bybitAuthenticated.getUniversalTransferRecords(
            apiKey,
            signatureCreator,
            nonceFactory,
            transferId,
            (coin == null) ? null : coin.toString(),
            (status == null) ? null : status.name(),
            (startTime == null) ? null : startTime.toInstant().toEpochMilli(),
            (endTime == null) ? null : endTime.toInstant().toEpochMilli(),
            limit,
            cursor);
    if (!universalTransfers.isSuccess()) {
      throw createBybitExceptionFromResult(universalTransfers);
    }
    return universalTransfers;
  }

  public BybitResult<BybitTransactionLogResponse> getBybitLedger(
      BybitAccountType accountType,
      BybitCategory category,
      Currency currency,
      Currency baseCoin,
      BybitTransactionLogType type,
      Date startTime,
      Date endTime,
      Integer limit,
      String cursor)
      throws IOException {
    BybitResult<BybitTransactionLogResponse> ledger =
        bybitAuthenticated.getTransactionLogRecords(
            apiKey,
            signatureCreator,
            nonceFactory,
            (accountType == null) ? null : accountType.name(),
            (category == null) ? null : category.getValue(),
            (currency == null) ? null : currency.toString(),
            (baseCoin == null) ? null : baseCoin.toString(),
            (type == null) ? null : type.name(),
            (startTime == null) ? null : startTime.toInstant().toEpochMilli(),
            (endTime == null) ? null : endTime.toInstant().toEpochMilli(),
            limit,
            cursor);
    if (!ledger.isSuccess()) {
      throw createBybitExceptionFromResult(ledger);
    }
    return ledger;
  }

  public BybitResult<BybitWithdrawRecordsResponse> getBybitWithdrawRecords(
      String withdrawId,
      Currency coin,
      BybitWithdrawType withdrawType,
      Date startTime,
      Date endTime,
      Integer limit,
      String cursor
  )
      throws IOException {
    BybitResult<BybitWithdrawRecordsResponse> withdrawRecords =
        bybitAuthenticated.getWithdrawRecords(
            apiKey,
            signatureCreator,
            nonceFactory,
            withdrawId,
            (coin == null) ? null : coin.toString(),
            (withdrawType == null) ? null : withdrawType.getValue(),
            (startTime == null) ? null : startTime.toInstant().toEpochMilli(),
            (endTime == null) ? null : endTime.toInstant().toEpochMilli(),
            limit,
            cursor
            );
    if (!withdrawRecords.isSuccess()) {
      throw createBybitExceptionFromResult(withdrawRecords);
    }
    return withdrawRecords;
  }

  public BybitResult<BybitDepositRecordsResponse> getBybitDepositRecords(
      Currency coin,
      Date startTime,
      Date endTime,
      Integer limit,
      String cursor
  )
      throws IOException {
    BybitResult<BybitDepositRecordsResponse> depositRecords =
        bybitAuthenticated.getOnChainDepositRecords(
            apiKey,
            signatureCreator,
            nonceFactory,
            (coin == null) ? null : coin.toString(),
            (startTime == null) ? null : startTime.toInstant().toEpochMilli(),
            (endTime == null) ? null : endTime.toInstant().toEpochMilli(),
            limit,
            cursor
        );
    if (!depositRecords.isSuccess()) {
      throw createBybitExceptionFromResult(depositRecords);
    }
    return depositRecords;
  }

  public BybitResult<BybitInternalDepositRecordsResponse> getBybitInternalDepositRecords(
      Currency coin,
      Date startTime,
      Date endTime,
      Integer limit,
      String cursor
  )
      throws IOException {
    BybitResult<BybitInternalDepositRecordsResponse> internalDepositRecords =
        bybitAuthenticated.getInternalDepositRecords(
            apiKey,
            signatureCreator,
            nonceFactory,
            (startTime == null) ? null : startTime.toInstant().toEpochMilli(),
            (endTime == null) ? null : endTime.toInstant().toEpochMilli(),
            (coin == null) ? null : coin.toString(),
            cursor,
            limit
        );
    if (!internalDepositRecords.isSuccess()) {
      throw createBybitExceptionFromResult(internalDepositRecords);
    }
    return internalDepositRecords;
  }

  public BybitResult<BybitDepositRecordsResponse> getBybitSubAccountDepositRecords(
      String subMemberId,
      Currency coin,
      Date startTime,
      Date endTime,
      Integer limit,
      String cursor
  )
      throws IOException {
    BybitResult<BybitDepositRecordsResponse> subAccountDepositRecords =
        bybitAuthenticated.getSubAccountDepositRecords(
            apiKey,
            signatureCreator,
            nonceFactory,
            subMemberId,
            (coin == null) ? null : coin.toString(),
            (startTime == null) ? null : startTime.toInstant().toEpochMilli(),
            (endTime == null) ? null : endTime.toInstant().toEpochMilli(),
            limit,
            cursor
        );
    if (!subAccountDepositRecords.isSuccess()) {
      throw createBybitExceptionFromResult(subAccountDepositRecords);
    }
    return subAccountDepositRecords;
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
