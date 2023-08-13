package org.knowm.xchange.kucoin;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.FundingRecord.Type;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.kucoin.dto.response.AccountBalancesResponse;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.HistoryParamsFundingType;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;

public class KucoinAccountService extends KucoinAccountServiceRaw implements AccountService {

  KucoinAccountService(KucoinExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    List<AccountBalancesResponse> accounts = getKucoinAccounts();
    return new AccountInfo(
        accounts.stream()
            .map(AccountBalancesResponse::getType)
            .distinct()
            .map(
                type ->
                    Wallet.Builder.from(
                            accounts.stream()
                                .filter(a -> a.getType().equals(type))
                                .map(KucoinAdapters::adaptBalance)
                                .collect(toList()))
                        .id(type)
                        .build())
            .collect(toList()));
  }

  public TradeHistoryParams createFundingHistoryParams() {
    return new KucoinTradeHistoryParams();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    String currency = null;
    if (params instanceof TradeHistoryParamCurrency) {
      Currency c = ((TradeHistoryParamCurrency) params).getCurrency();
      currency = c == null ? null : c.getCurrencyCode();
    }
    boolean withdrawals = true, deposits = true;
    if (params instanceof HistoryParamsFundingType) {
      HistoryParamsFundingType p = (HistoryParamsFundingType) params;
      withdrawals = p.getType() == null || p.getType() == Type.WITHDRAWAL;
      deposits = p.getType() == null || p.getType() == Type.DEPOSIT;
    }

    Long startAt = null, endAt = null;
    if (params instanceof TradeHistoryParamsTimeSpan) {
      TradeHistoryParamsTimeSpan p = (TradeHistoryParamsTimeSpan) params;
      startAt = p.getStartTime() == null ? null : p.getStartTime().getTime();
      endAt = p.getEndTime() == null ? null : p.getEndTime().getTime();
    }
    List<FundingRecord> result = new ArrayList<>();
    if (withdrawals) {
      result.addAll(
          getWithdrawalsList(currency, null, startAt, endAt, null, null).getItems().stream()
              .map(KucoinAdapters::adaptFundingRecord)
              .collect(Collectors.toList()));
    }
    if (deposits) {
      result.addAll(
          getDepositList(currency, null, startAt, endAt, null, null).getItems().stream()
              .map(KucoinAdapters::adaptFundingRecord)
              .collect(Collectors.toList()));
    }
    return result;
  }
}
