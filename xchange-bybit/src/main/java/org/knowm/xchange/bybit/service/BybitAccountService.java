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

    wallets.add(BybitAdapters.adaptBybitBalances(getAllCoinsBalance(BybitAccountType.FUND, null, null, false).getResult(),
        Sets.newHashSet(WalletFeature.FUNDING)));
    wallets.add(BybitAdapters.adaptBybitBalances(getAllCoinsBalance(BybitAccountType.CONTRACT, null, null, false).getResult(),
        Sets.newHashSet(WalletFeature.FUTURES_TRADING)));

    if(accountType == BybitAccountType.UNIFIED){
      wallets.add(BybitAdapters.adaptBybitBalances(getAllCoinsBalance(BybitAccountType.UNIFIED, null, null, false).getResult(),
          Sets.newHashSet(WalletFeature.MARGIN_TRADING, WalletFeature.TRADING, WalletFeature.FUTURES_TRADING, WalletFeature.OPTIONS_TRADING)));
    } else if(accountType == BybitAccountType.CLASSIC) {
      wallets.add(BybitAdapters.adaptBybitBalances(getAllCoinsBalance(BybitAccountType.SPOT, null, null, false).getResult(),
          Sets.newHashSet(WalletFeature.TRADING, WalletFeature.MARGIN_TRADING)));
      wallets.add(BybitAdapters.adaptBybitBalances(getAllCoinsBalance(BybitAccountType.OPTION, null, null, false).getResult(),
          Sets.newHashSet(WalletFeature.OPTIONS_TRADING)));
    }

    return new AccountInfo(wallets);
  }

  @Override
  public AccountInfo getSubAccountInfo(String subAccountId) throws IOException {
    List<Wallet> wallets = new ArrayList<>();

    wallets.add(BybitAdapters.adaptBybitBalances(getAllCoinsBalance(BybitAccountType.FUND, subAccountId, null, false).getResult(),
        Sets.newHashSet(WalletFeature.FUNDING)));
    wallets.add(BybitAdapters.adaptBybitBalances(getAllCoinsBalance(BybitAccountType.CONTRACT, subAccountId, null, false).getResult(),
        Sets.newHashSet(WalletFeature.FUTURES_TRADING)));

    if(accountType == BybitAccountType.UNIFIED){
      wallets.add(BybitAdapters.adaptBybitBalances(getAllCoinsBalance(BybitAccountType.UNIFIED, subAccountId, null, false).getResult(),
          Sets.newHashSet(WalletFeature.MARGIN_TRADING, WalletFeature.TRADING, WalletFeature.FUTURES_TRADING, WalletFeature.OPTIONS_TRADING)));
    } else if(accountType == BybitAccountType.CLASSIC) {
      wallets.add(BybitAdapters.adaptBybitBalances(getAllCoinsBalance(BybitAccountType.SPOT, subAccountId, null, false).getResult(),
          Sets.newHashSet(WalletFeature.TRADING, WalletFeature.MARGIN_TRADING)));
      wallets.add(BybitAdapters.adaptBybitBalances(getAllCoinsBalance(BybitAccountType.OPTION, subAccountId, null, false).getResult(),
          Sets.newHashSet(WalletFeature.OPTIONS_TRADING)));
    }

    return new AccountInfo(wallets);
  }

  @Override
  public List<FundingRecord> getSubAccountsTransferHistory(FundingRecordParamAll params) throws IOException {

    BybitTransfersResponse res = getBybitUniversalTransfers(
        params.getTransferId(),
        params.getCurrency(),
        BybitAdapters.convertToBybitStatus(params.getStatus()),
        params.getStartTime(),
        params.getEndTime(),
        (params.getLimit() == null) ? MAX_PAGINATION_LIMIT : params.getLimit(), // 50 is the maximum
        null
    ).getResult();

    if(params.isUsePagination()){
      String nextPageCursor = res.getNextPageCursor();
      List<FundingRecord> fundingRecordList = new ArrayList<>();

      while (nextPageCursor != null) {
        fundingRecordList.addAll(BybitAdapters.adaptBybitUniversalTransfers(res.getInternalTransfers()));

        res = getBybitUniversalTransfers(
            params.getTransferId(),
            params.getCurrency(),
            BybitAdapters.convertToBybitStatus(params.getStatus()),
            params.getStartTime(),
            params.getEndTime(),
            (params.getLimit() == null) ? MAX_PAGINATION_LIMIT : params.getLimit(), // 50 is the maximum
            res.getNextPageCursor()
        ).getResult();

        nextPageCursor = res.getNextPageCursor();
      }

      return fundingRecordList;
    } else {
      return BybitAdapters.adaptBybitUniversalTransfers(res.getInternalTransfers());
    }
  }

  @Override
  public List<FundingRecord> getInternalWalletsTransferHistory(FundingRecordParamAll params)
      throws IOException {

    BybitTransfersResponse res = getBybitInternalTransfers(
        params.getTransferId(),
        params.getCurrency(),
        BybitAdapters.convertToBybitStatus(params.getStatus()),
        params.getStartTime(),
        params.getEndTime(),
        (params.getLimit() == null) ? MAX_PAGINATION_LIMIT : params.getLimit(), // 50 is the maximum
        null
    ).getResult();

    if(params.isUsePagination()){
      String nextPageCursor = res.getNextPageCursor();
      List<FundingRecord> fundingRecordList = new ArrayList<>();

      while (nextPageCursor != null) {
        fundingRecordList.addAll(adaptBybitInternalTransfers(res.getInternalTransfers()));

        res = getBybitInternalTransfers(
            params.getTransferId(),
            params.getCurrency(),
            BybitAdapters.convertToBybitStatus(params.getStatus()),
            params.getStartTime(),
            params.getEndTime(),
            (params.getLimit() == null) ? MAX_PAGINATION_LIMIT : params.getLimit(), // 50 is the maximum
            res.getNextPageCursor()
        ).getResult();

        nextPageCursor = res.getNextPageCursor();
      }

      return fundingRecordList;
    } else {
      return adaptBybitInternalTransfers(res.getInternalTransfers());
    }
  }

  @Override
  public List<FundingRecord> getWithdrawHistory(FundingRecordParamAll params) throws IOException {

    BybitWithdrawRecordsResponse res = getBybitWithdrawRecords(
        params.getTransferId(),
        params.getCurrency(),
        BybitWithdrawType.ALL,
        params.getStartTime(),
        params.getEndTime(),
        (params.getLimit() == null) ? MAX_PAGINATION_LIMIT : params.getLimit(), // 50 is the maximum
        null
    ).getResult();

    if(params.isUsePagination()){
      String nextPageCursor = res.getNextPageCursor();
      List<FundingRecord> fundingRecordList = new ArrayList<>();

      while (nextPageCursor != null) {
        fundingRecordList.addAll(adaptBybitWithdrawRecords(res.getRows()));

        res = getBybitWithdrawRecords(
            params.getTransferId(),
            params.getCurrency(),
            BybitWithdrawType.ALL,
            params.getStartTime(),
            params.getEndTime(),
            (params.getLimit() == null) ? MAX_PAGINATION_LIMIT : params.getLimit(), // 50 is the maximum
            res.getNextPageCursor()
        ).getResult();

        nextPageCursor = res.getNextPageCursor();
      }

      return fundingRecordList;
    } else {
      return adaptBybitWithdrawRecords(res.getRows());
    }
  }

  @Override
  public List<FundingRecord> getSubAccountDepositHistory(FundingRecordParamAll params)
      throws IOException {

    if (params.getSubAccountId() == null) {
      throw new IllegalArgumentException("Sub account id is required");
    }

    BybitDepositRecordsResponse res = getBybitSubAccountDepositRecords(
        params.getSubAccountId(),
        params.getCurrency(),
        params.getStartTime(),
        params.getEndTime(),
        (params.getLimit() == null) ? MAX_PAGINATION_LIMIT : params.getLimit(), // 50 is the maximum
        null
    ).getResult();

    if(params.isUsePagination()){
      String nextPageCursor = res.getNextPageCursor();
      List<FundingRecord> fundingRecordList = new ArrayList<>();

      while (nextPageCursor != null) {
        fundingRecordList.addAll(adaptBybitDepositRecords(res.getRows()));

        res = getBybitSubAccountDepositRecords(
            params.getSubAccountId(),
            params.getCurrency(),
            params.getStartTime(),
            params.getEndTime(),
            (params.getLimit() == null) ? MAX_PAGINATION_LIMIT : params.getLimit(), // 50 is the maximum
            res.getNextPageCursor()
        ).getResult();

        nextPageCursor = res.getNextPageCursor();
      }

      return fundingRecordList;
    } else {
      return adaptBybitDepositRecords(res.getRows());
    }
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

    if(params.isUsePagination()){
      // Make calls to main deposit history
      String nextPageCursor = res.getNextPageCursor();

      while (nextPageCursor != null) {
        fundingRecordList.addAll(adaptBybitDepositRecords(res.getRows()));

        res = getBybitDepositRecords(
            params.getCurrency(),
            params.getStartTime(),
            params.getEndTime(),
            (params.getLimit() == null) ? MAX_PAGINATION_LIMIT : params.getLimit(), // 50 is the maximum
            res.getNextPageCursor()
        ).getResult();

        nextPageCursor = res.getNextPageCursor();
      }

      // Make calls to internal deposit history
      nextPageCursor = internalRes.getNextPageCursor();

      while (nextPageCursor != null) {
        fundingRecordList.addAll(adaptBybitInternalDepositRecords(internalRes.getRows()));

        internalRes = getBybitInternalDepositRecords(
            params.getCurrency(),
            params.getStartTime(),
            params.getEndTime(),
            (params.getLimit() == null) ? MAX_PAGINATION_LIMIT : params.getLimit(), // 50 is the maximum
            internalRes.getNextPageCursor()
        ).getResult();

        nextPageCursor = internalRes.getNextPageCursor();
      }
    } else {
      fundingRecordList.addAll(adaptBybitDepositRecords(res.getRows()));
      fundingRecordList.addAll(adaptBybitInternalDepositRecords(internalRes.getRows()));
    }

    return fundingRecordList;
  }

  @Override
  public List<FundingRecord> getLedger(FundingRecordParamAll params) throws IOException {
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

    if(params.isUsePagination()){
      String nextPageCursor = res.getNextPageCursor();
      List<FundingRecord> fundingRecordList = new ArrayList<>();

      while (nextPageCursor != null) {
        fundingRecordList.addAll(BybitAdapters.adaptBybitLedger(res.getList()));

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

        nextPageCursor = res.getNextPageCursor();
      }

      return fundingRecordList;
    } else {
      return BybitAdapters.adaptBybitLedger(res.getList());
    }
  }
}
