package org.knowm.xchange.kucoin.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.FundingRecord.Type;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.kucoin.dto.KucoinAdapters;
import org.knowm.xchange.kucoin.dto.KucoinSimpleResponse;
import org.knowm.xchange.kucoin.dto.account.KucoinCoinBalance;
import org.knowm.xchange.kucoin.dto.account.KucoinCoinBalances;
import org.knowm.xchange.kucoin.dto.account.KucoinWalletRecords;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.HistoryParamsFundingType;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

public class KucoinAccountService extends KucoinAccountServiceRaw implements AccountService {

  public KucoinAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    // Kucoins balances are available only in paged form
    // We load the first page, then loop over the remaining pages
    List<KucoinCoinBalance> balances = new LinkedList<>();
    // 20 is the maximum page size
    KucoinCoinBalances balancesInfo = getKucoinBalances(20, 1).getData();
    balances.addAll(balancesInfo.getBalances());
    for (int page = 2; page <= balancesInfo.getPageNos(); page++) {
      balances.addAll(getKucoinBalances(20, page).getData().getBalances());
    }
    return KucoinAdapters.adaptAccountInfo(balances);
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    return withdrawFunds(new DefaultWithdrawFundsParams(address, currency, amount));
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    if (!(params instanceof DefaultWithdrawFundsParams)) {
      throw new ExchangeException("Need DefaultWithdrawFundsParams to apply for withdrawal!");
    }
    DefaultWithdrawFundsParams defParams = (DefaultWithdrawFundsParams) params;
    return withdrawalApply(defParams.getCurrency(), defParams.getAmount(), defParams.getAddress())
        .getCode();
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {
    return getKucoinDepositAddress(currency).getData().getAddress();
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {

    return new KucoinFundingHistoryParams();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {

    if (!(params instanceof TradeHistoryParamPaging)
        && !(params instanceof TradeHistoryParamCurrency)) {
      throw new ExchangeException(
          "You need to provide paging information and currency to get the trade history.");
    }

    TradeHistoryParamPaging pagingParams = (TradeHistoryParamPaging) params;
    TradeHistoryParamCurrency curParams = (TradeHistoryParamCurrency) params;

    Type type = null;
    if (params instanceof HistoryParamsFundingType) {
      HistoryParamsFundingType fundingType = (HistoryParamsFundingType) params;
      if (fundingType.getType() != null) {
        type = fundingType.getType();
      }
    }
    // Paging params are 0-based, Kucoin account balances pages are 1-based
    KucoinSimpleResponse<KucoinWalletRecords> response =
        walletRecords(
            curParams.getCurrency(),
            type,
            pagingParams.getPageLength(),
            pagingParams.getPageNumber() != null ? pagingParams.getPageNumber() + 1 : null);
    return KucoinAdapters.adaptFundingHistory(response.getData().getRecords());
  }
}
