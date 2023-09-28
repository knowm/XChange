package org.knowm.xchange.bybit.service;

import static org.knowm.xchange.bybit.BybitAdapters.adaptBybitInternalTransfers;

import com.google.common.collect.Sets;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bybit.BybitAdapters;
import org.knowm.xchange.bybit.dto.account.BybitTransactionLogResponse;
import org.knowm.xchange.bybit.dto.account.BybitTransfersResponse;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitAccountType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.account.Wallet.WalletFeature;
import org.knowm.xchange.dto.account.params.FundingRecordParamAll;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;

public class BybitAccountService extends BybitAccountServiceRaw implements AccountService {

  private final BybitAccountType accountType;

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
  public List<FundingRecord> getSubAccountsTransferHistory(FundingRecordParamAll params) throws IOException {

    BybitTransfersResponse res = getBybitUniversalTransfers(
        params.getTransferId(),
        params.getCurrency(),
        BybitAdapters.convertToBybitStatus(params.getStatus()),
        params.getStartTime(),
        params.getEndTime(),
        (params.getLimit() == null) ? 50 : params.getLimit(), // 50 is the maximum
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
            (params.getLimit() == null) ? 50 : params.getLimit(), // 50 is the maximum
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
        (params.getLimit() == null) ? 50 : params.getLimit(), // 50 is the maximum
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
            (params.getLimit() == null) ? 50 : params.getLimit(), // 50 is the maximum
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
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    return AccountService.super.getFundingHistory(params);
  }

  @Override
  public List<FundingRecord> getLedger(FundingRecordParamAll params) throws IOException {
    BybitTransactionLogResponse res = getBybitLedger(
        accountType,
        null,
        params.getCurrency(),
        null,
        BybitAdapters.convertToBybitTransactionLogType(params.getType()),
        params.getStartTime(),
        params.getEndTime(),
        (params.getLimit() == null) ? 50 : params.getLimit(),
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
            BybitAdapters.convertToBybitTransactionLogType(params.getType()),
            params.getStartTime(),
            params.getEndTime(),
            (params.getLimit() == null) ? 50 : params.getLimit(),
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
