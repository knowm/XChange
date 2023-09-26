package org.knowm.xchange.bybit.service;

import com.google.common.collect.Sets;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bybit.BybitAdapters;
import org.knowm.xchange.bybit.dto.account.BybitInternalTransfersResponse.BybitTransferStatus;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitAccountType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.account.Wallet.WalletFeature;
import org.knowm.xchange.dto.account.params.FundingRecordParamAll;
import org.knowm.xchange.service.account.AccountService;

public class BybitAccountService extends BybitAccountServiceRaw implements AccountService {

  private final BybitAccountType accountType;

  public BybitAccountService(Exchange exchange, BybitAccountType accountType) {
    super(exchange);
    this.accountType = accountType;
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    List<Wallet> wallets = new ArrayList<>();

    wallets.add(BybitAdapters.adaptBybitBalances(getAllCoinsBalance(BybitAccountType.FUND, null, null, null).getResult(),
        Sets.newHashSet(WalletFeature.FUNDING)));
    wallets.add(BybitAdapters.adaptBybitBalances(getAllCoinsBalance(BybitAccountType.CONTRACT, null, null, null).getResult(),
        Sets.newHashSet(WalletFeature.FUTURES_TRADING)));

    if(accountType == BybitAccountType.UNIFIED){
      wallets.add(BybitAdapters.adaptBybitBalances(getAllCoinsBalance(BybitAccountType.UNIFIED, null, null, null).getResult(),
          Sets.newHashSet(WalletFeature.MARGIN_TRADING, WalletFeature.TRADING, WalletFeature.FUTURES_TRADING, WalletFeature.OPTIONS_TRADING)));
    } else if(accountType == BybitAccountType.CLASSIC) {
      wallets.add(BybitAdapters.adaptBybitBalances(getAllCoinsBalance(BybitAccountType.SPOT, null, null, null).getResult(),
          Sets.newHashSet(WalletFeature.TRADING, WalletFeature.MARGIN_TRADING)));
      wallets.add(BybitAdapters.adaptBybitBalances(getAllCoinsBalance(BybitAccountType.OPTION, null, null, null).getResult(),
          Sets.newHashSet(WalletFeature.OPTIONS_TRADING)));
    }

    return new AccountInfo(wallets);
  }

  @Override
  public List<FundingRecord> getTransferHistory(FundingRecordParamAll params) throws IOException {
    return AccountService.super.getTransferHistory(params);
  }

  @Override
  public List<FundingRecord> getInternalTransferHistory(FundingRecordParamAll params)
      throws IOException {
    return BybitAdapters.adaptBybitInternalTransfers(getBybitInternalTransfers(
        params.getTransferId(),
        params.getCurrency().toString(),
        BybitAdapters.convertToBybitStatus(params.getStatus()),
        params.getStartTime(),
        params.getEndTime(),
        (params.getLimit() == null) ? 100 : params.getLimit(),
        null).getResult().getInternalTransfers());
  }
}
