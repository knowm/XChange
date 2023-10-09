package org.knowm.xchange.bybit.service;

import static org.knowm.xchange.bybit.BybitAdapters.adaptBybitDepositRecords;
import static org.knowm.xchange.bybit.BybitAdapters.adaptBybitInternalDepositRecords;
import static org.knowm.xchange.bybit.BybitAdapters.adaptBybitInternalTransfers;
import static org.knowm.xchange.bybit.BybitAdapters.adaptBybitWithdrawRecords;

import com.google.common.collect.Sets;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bybit.BybitAdapters;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.account.BybitDepositRecordsResponse;
import org.knowm.xchange.bybit.dto.account.BybitInternalDepositRecordsResponse;
import org.knowm.xchange.bybit.dto.account.BybitTransactionLogResponse;
import org.knowm.xchange.bybit.dto.account.BybitTransfersResponse;
import org.knowm.xchange.bybit.dto.account.BybitWithdrawRecordsResponse;
import org.knowm.xchange.bybit.dto.account.BybitWithdrawRecordsResponse.BybitWithdrawRecord.BybitWithdrawType;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitAccountType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.account.Wallet.WalletFeature;
import org.knowm.xchange.dto.account.params.FundingRecordParamAll;
import org.knowm.xchange.service.account.AccountService;

public class BybitAccountService extends BybitAccountServiceRaw implements AccountService {

  private final BybitAccountType accountType;

  private static final Integer MAX_PAGINATION_LIMIT = 50;

  public BybitAccountService(Exchange exchange, BybitAccountType accountType) {
    super(exchange);
    this.accountType = accountType;
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    List<Wallet> wallets = new ArrayList<>();

    if(accountType == BybitAccountType.UNIFIED){
      wallets.add(BybitAdapters.adaptBybitBalances(getAllCoinsBalance(BybitAccountType.UNIFIED, null, null, false).getResult(),
          Sets.newHashSet(WalletFeature.MARGIN_TRADING, WalletFeature.TRADING, WalletFeature.FUTURES_TRADING, WalletFeature.OPTIONS_TRADING)));
    } else if(accountType == BybitAccountType.CLASSIC) {
      wallets.add(BybitAdapters.adaptBybitBalances(getAllCoinsBalance(BybitAccountType.SPOT, null, null, false).getResult(),
          Sets.newHashSet(WalletFeature.TRADING, WalletFeature.MARGIN_TRADING)));
    }

    return new AccountInfo(wallets);
  }

  @Override
  public AccountInfo getSubAccountInfo(String subAccountId) throws IOException {
    List<Wallet> wallets = new ArrayList<>();

    if(accountType == BybitAccountType.UNIFIED){
      wallets.add(BybitAdapters.adaptBybitBalances(getAllCoinsBalance(BybitAccountType.UNIFIED, subAccountId, null, false).getResult(),
          Sets.newHashSet(WalletFeature.MARGIN_TRADING, WalletFeature.TRADING, WalletFeature.FUTURES_TRADING, WalletFeature.OPTIONS_TRADING)));
    } else if(accountType == BybitAccountType.CLASSIC) {
      wallets.add(BybitAdapters.adaptBybitBalances(getAllCoinsBalance(BybitAccountType.SPOT, subAccountId, null, false).getResult(),
          Sets.newHashSet(WalletFeature.TRADING, WalletFeature.MARGIN_TRADING)));
    }

    return new AccountInfo(wallets);
  }

  @Override
  public List<FundingRecord> getInternalTransferHistory(FundingRecordParamAll params) throws IOException {

    List<FundingRecord> fundingRecordList = new ArrayList<>();

    BybitTransfersResponse res = getBybitUniversalTransfers(
        params.getTransferId(),
        params.getCurrency(),
        BybitAdapters.convertToBybitStatus(params.getStatus()),
        params.getStartTime(),
        params.getEndTime(),
        (params.getLimit() == null) ? MAX_PAGINATION_LIMIT : params.getLimit(), // 50 is the maximum
        null
    ).getResult();

    fundingRecordList.addAll(BybitAdapters.adaptBybitUniversalTransfers(res.getInternalTransfers()));

    if(params.isUsePagination()){
      String nextPageCursor = res.getNextPageCursor();

      while (nextPageCursor != null && !nextPageCursor.isEmpty()) {
        res = getBybitUniversalTransfers(
            params.getTransferId(),
            params.getCurrency(),
            BybitAdapters.convertToBybitStatus(params.getStatus()),
            params.getStartTime(),
            params.getEndTime(),
            (params.getLimit() == null) ? MAX_PAGINATION_LIMIT : params.getLimit(), // 50 is the maximum
            res.getNextPageCursor()
        ).getResult();

        fundingRecordList.addAll(BybitAdapters.adaptBybitUniversalTransfers(res.getInternalTransfers()));
        nextPageCursor = res.getNextPageCursor();
      }
    }

    return fundingRecordList;
  }

  @Override
  public List<FundingRecord> getWalletTransferHistory(FundingRecordParamAll params)
      throws IOException {
    List<FundingRecord> fundingRecordList = new ArrayList<>();

    BybitTransfersResponse res = getBybitInternalTransfers(
        params.getTransferId(),
        params.getCurrency(),
        BybitAdapters.convertToBybitStatus(params.getStatus()),
        params.getStartTime(),
        params.getEndTime(),
        (params.getLimit() == null) ? MAX_PAGINATION_LIMIT : params.getLimit(), // 50 is the maximum
        null
    ).getResult();

    fundingRecordList.addAll(adaptBybitInternalTransfers(res.getInternalTransfers()));

    if(params.isUsePagination()){
      String nextPageCursor = res.getNextPageCursor();

      while (nextPageCursor != null && !nextPageCursor.isEmpty()) {
        res = getBybitInternalTransfers(
            params.getTransferId(),
            params.getCurrency(),
            BybitAdapters.convertToBybitStatus(params.getStatus()),
            params.getStartTime(),
            params.getEndTime(),
            (params.getLimit() == null) ? MAX_PAGINATION_LIMIT : params.getLimit(), // 50 is the maximum
            res.getNextPageCursor()
        ).getResult();

        fundingRecordList.addAll(adaptBybitInternalTransfers(res.getInternalTransfers()));
        nextPageCursor = res.getNextPageCursor();
      }
    }

    return fundingRecordList;
  }

  @Override
  public List<FundingRecord> getWithdrawHistory(FundingRecordParamAll params) throws IOException {
    List<FundingRecord> fundingRecordList = new ArrayList<>();

    BybitWithdrawRecordsResponse res = getBybitWithdrawRecords(
        params.getTransferId(),
        params.getCurrency(),
        BybitWithdrawType.ALL,
        params.getStartTime(),
        params.getEndTime(),
        (params.getLimit() == null) ? MAX_PAGINATION_LIMIT : params.getLimit(), // 50 is the maximum
        null
    ).getResult();

    fundingRecordList.addAll(adaptBybitWithdrawRecords(res.getRows()));

    if(params.isUsePagination()){
      String nextPageCursor = res.getNextPageCursor();

      while (nextPageCursor != null && !nextPageCursor.isEmpty()) {
        res = getBybitWithdrawRecords(
            params.getTransferId(),
            params.getCurrency(),
            BybitWithdrawType.ALL,
            params.getStartTime(),
            params.getEndTime(),
            (params.getLimit() == null) ? MAX_PAGINATION_LIMIT : params.getLimit(), // 50 is the maximum
            res.getNextPageCursor()
        ).getResult();

        fundingRecordList.addAll(adaptBybitWithdrawRecords(res.getRows()));
        nextPageCursor = res.getNextPageCursor();
      }
    }

    return fundingRecordList;
  }

  @Override
  public List<FundingRecord> getSubAccountDepositHistory(FundingRecordParamAll params)
      throws IOException {

    if (params.getSubAccountId() == null) {
      throw new IllegalArgumentException("Sub account id is required");
    }

    List<FundingRecord> fundingRecordList = new ArrayList<>();

    BybitDepositRecordsResponse res = getBybitSubAccountDepositRecords(
        params.getSubAccountId(),
        params.getCurrency(),
        params.getStartTime(),
        params.getEndTime(),
        (params.getLimit() == null) ? MAX_PAGINATION_LIMIT : params.getLimit(), // 50 is the maximum
        null
    ).getResult();

    fundingRecordList.addAll(adaptBybitDepositRecords(res.getRows()));

    if(params.isUsePagination()){
      String nextPageCursor = res.getNextPageCursor();
      while (nextPageCursor != null && !nextPageCursor.isEmpty()) {
        res = getBybitSubAccountDepositRecords(
            params.getSubAccountId(),
            params.getCurrency(),
            params.getStartTime(),
            params.getEndTime(),
            (params.getLimit() == null) ? MAX_PAGINATION_LIMIT : params.getLimit(), // 50 is the maximum
            res.getNextPageCursor()
        ).getResult();

        fundingRecordList.addAll(adaptBybitDepositRecords(res.getRows()));
        nextPageCursor = res.getNextPageCursor();
      }
    }

    return fundingRecordList;
  }

  @Override
  public List<FundingRecord> getDepositHistory(FundingRecordParamAll params) throws IOException {

    BybitDepositRecordsResponse res = getBybitDepositRecords(
        params.getCurrency(),
        params.getStartTime(),
        params.getEndTime(),
        (params.getLimit() == null) ? MAX_PAGINATION_LIMIT : params.getLimit(), // 50 is the maximum
        null
    ).getResult();

    List<FundingRecord> fundingRecordList = new ArrayList<>();

    BybitInternalDepositRecordsResponse internalRes = getBybitInternalDepositRecords(
        params.getCurrency(),
        params.getStartTime(),
        params.getEndTime(),
        (params.getLimit() == null) ? MAX_PAGINATION_LIMIT : params.getLimit(), // 50 is the maximum
        null
    ).getResult();

    fundingRecordList.addAll(adaptBybitDepositRecords(res.getRows()));
    fundingRecordList.addAll(adaptBybitInternalDepositRecords(internalRes.getRows()));

    if(params.isUsePagination()){
      // Make calls to main deposit history
      String nextPageCursor = res.getNextPageCursor();

      while (nextPageCursor != null && !nextPageCursor.isEmpty()) {

        res = getBybitDepositRecords(
            params.getCurrency(),
            params.getStartTime(),
            params.getEndTime(),
            (params.getLimit() == null) ? MAX_PAGINATION_LIMIT : params.getLimit(), // 50 is the maximum
            res.getNextPageCursor()
        ).getResult();

        fundingRecordList.addAll(adaptBybitDepositRecords(res.getRows()));
        nextPageCursor = res.getNextPageCursor();
      }

      // Make calls to internal deposit history
      nextPageCursor = internalRes.getNextPageCursor();

      while (nextPageCursor != null && !nextPageCursor.isEmpty()) {

        internalRes = getBybitInternalDepositRecords(
            params.getCurrency(),
            params.getStartTime(),
            params.getEndTime(),
            (params.getLimit() == null) ? MAX_PAGINATION_LIMIT : params.getLimit(), // 50 is the maximum
            internalRes.getNextPageCursor()
        ).getResult();

        fundingRecordList.addAll(adaptBybitInternalDepositRecords(internalRes.getRows()));
        nextPageCursor = internalRes.getNextPageCursor();
      }
    }

    return fundingRecordList;
  }

  @Override
  public List<FundingRecord> getLedger(FundingRecordParamAll params) throws IOException {
    List<FundingRecord> fundingRecordList = new ArrayList<>();

    BybitTransactionLogResponse res = getBybitLedger(
        accountType,
        params.getAccountCategory() == null ? null : BybitCategory.valueOf(params.getAccountCategory()),
        params.getCurrency(),
        null,
        null,
        params.getStartTime(),
        params.getEndTime(),
        (params.getLimit() == null) ? MAX_PAGINATION_LIMIT : params.getLimit(),
        null
    ).getResult();

    fundingRecordList.addAll(BybitAdapters.adaptBybitLedger(res.getList()));

    if(params.isUsePagination()){
      String nextPageCursor = res.getNextPageCursor();

      while (nextPageCursor != null && !nextPageCursor.isEmpty()) {
        res = getBybitLedger(
            accountType,
            null,
            params.getCurrency(),
            null,
            null,
            params.getStartTime(),
            params.getEndTime(),
            (params.getLimit() == null) ? MAX_PAGINATION_LIMIT : params.getLimit(),
            res.getNextPageCursor()
        ).getResult();

        fundingRecordList.addAll(BybitAdapters.adaptBybitLedger(res.getList()));

        nextPageCursor = res.getNextPageCursor();
      }

      return fundingRecordList;
    } else {
      return BybitAdapters.adaptBybitLedger(res.getList());
    }
  }
}
