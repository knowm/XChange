package org.knowm.xchange.idex;

import static org.knowm.xchange.idex.IdexExchange.Companion.safeParse;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.idex.dto.*;
import org.knowm.xchange.idex.service.ReturnCompleteBalancesApi;
import org.knowm.xchange.idex.service.ReturnDepositsWithdrawalsApi;
import org.knowm.xchange.idex.service.WithdrawApi;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;
import si.mazi.rescu.RestProxyFactory;

public class IdexAccountService implements AccountService {
  private final String apiKey;
  private final IdexExchange idexExchange;

  public IdexAccountService(IdexExchange idexExchange) {
    Intrinsics.checkParameterIsNotNull(idexExchange, "idexExchange");
    this.idexExchange = idexExchange;
    apiKey = this.idexExchange.getExchangeSpecification().getApiKey();
  }

  public final String getApiKey() {
    return apiKey;
  }

  public AccountInfo getAccountInfo() {
    AccountInfo ret = null;
    try {
      String apiKey = idexExchange.getExchangeSpecification().getApiKey();
      String s = apiKey.substring(0, 6) + "…";
      ReturnCompleteBalancesApi proxy =
          RestProxyFactory.createProxy(
              ReturnCompleteBalancesApi.class, idexExchange.getExchangeSpecification().getSslUri());
      ReturnCompleteBalancesResponse returnBalancesPost;
      ret = null;
      returnBalancesPost = proxy.completeBalances(new CompleteBalancesReq().address(apiKey));

      ret =
          new AccountInfo(
              new Wallet(
                  s,
                  returnBalancesPost
                      .entrySet()
                      .stream()
                      .map(
                          entry -> {
                            Balance balance =
                                new Balance(
                                    new Currency(entry.getKey()),
                                    null,
                                    entry.getValue().getAvailable(),
                                    entry.getValue().getOnOrders());
                            return balance;
                          })
                      .collect(Collectors.toList())));

    } catch (Exception e) {

    }
    return ret;
  }

  public String requestDepositAddress(Currency currency, String... args) {
    return idexExchange.getExchangeSpecification().getApiKey();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) {
    List<FundingRecord> ret = null;
    if (!(params instanceof IdexTradeHistoryParams)) {
      throw new Error(
          "getFundingHistory requires " + IdexTradeHistoryParams.class.getCanonicalName());
    } else {
      try {
        ret =
            mutableList(
                RestProxyFactory.createProxy(
                        ReturnDepositsWithdrawalsApi.class,
                        idexExchange.getDefaultExchangeSpecification().getSslUri())
                    .fundingHistory((DepositsWithdrawalsReq) params));
      } catch (Exception e) {

      }
    }
    return ret;
  }

  private final List<FundingRecord> mutableList(
      ReturnDepositsWithdrawalsResponse returnDepositsWithdrawalsPost) {
    return (List<FundingRecord>)
        Arrays.asList(
                returnDepositsWithdrawalsPost
                    .getWithdrawals()
                    .stream()
                    .map(
                        fundingLedger ->
                            new FundingRecord(
                                apiKey,
                                new Date(Long.parseLong(fundingLedger.getTimestamp()) * 1000),
                                new Currency(fundingLedger.getCurrency()),
                                safeParse(fundingLedger.getAmount()),
                                fundingLedger.getTransactionHash(),
                                fundingLedger.getDepositNumber(),
                                FundingRecord.Type.WITHDRAWAL,
                                FundingRecord.Status.resolveStatus(fundingLedger.getStatus()),
                                BigDecimal.ZERO,
                                BigDecimal.ZERO,
                                ""))
                    .collect(Collectors.toList()),
                returnDepositsWithdrawalsPost
                    .getDeposits()
                    .stream()
                    .map(
                        fundingLedger1 ->
                            new FundingRecord(
                                apiKey,
                                new Date(Long.parseLong(fundingLedger1.getTimestamp()) * 1000),
                                new Currency(fundingLedger1.getCurrency()),
                                safeParse(fundingLedger1.getAmount()),
                                fundingLedger1.getTransactionHash(),
                                fundingLedger1.getDepositNumber(),
                                FundingRecord.Type.WITHDRAWAL,
                                FundingRecord.Status.resolveStatus(fundingLedger1.getStatus()),
                                BigDecimal.ZERO,
                                BigDecimal.ZERO,
                                ""))
                    .collect(Collectors.toList()))
            .stream()
            .flatMap(List::stream)
            .sorted(Comparator.comparing(FundingRecord::getDate));
  }

  public TradeHistoryParams createFundingHistoryParams() {
    return new IdexTradeHistoryParams();
  }

  public String withdrawFunds(WithdrawFundsParams w) {
    String ret = "error";
    if (w instanceof IdexWithdraw) {
      try {
        WithdrawResponse withdraw =
            RestProxyFactory.createProxy(
                    WithdrawApi.class, idexExchange.getDefaultExchangeSpecification().getSslUri())
                .withdraw((WithdrawReq) w);
        ret = withdraw.toString();
      } catch (Exception e) {

      }

    } else {
      throw new Error(
          "withdraw method requires "
              + org.knowm.xchange.idex.IdexWithdraw.class.getCanonicalName());
    }
    return ret;
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) {
    throw new org.knowm.xchange.exceptions.NotAvailableFromExchangeException();
  }
}
