package org.knowm.xchange.binance.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.binance.dto.account.BinanceAccountInformation;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.*;
import org.knowm.xchange.dto.account.Balance.Builder;
import org.knowm.xchange.dto.account.FundingRecord.Status;
import org.knowm.xchange.dto.account.FundingRecord.Type;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.HistoryParamsFundingType;
import org.knowm.xchange.service.trade.params.RippleWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

public class BinanceAccountService extends BinanceAccountServiceRaw implements AccountService {

  public BinanceAccountService(Exchange exchange) {
    super(exchange);
  }

  /** (0:Email Sent,1:Cancelled 2:Awaiting Approval 3:Rejected 4:Processing 5:Failure 6Completed) */
  private static Status withdrawStatus(int status) {
    switch (status) {
      case 0:
      case 2:
      case 4:
        return Status.PROCESSING;
      case 1:
        return Status.CANCELLED;
      case 3:
      case 5:
        return Status.FAILED;
      case 6:
        return Status.COMPLETE;
      default:
        throw new RuntimeException("Unknown binance withdraw status: " + status);
    }
  }

  /** (0:pending,1:success) */
  private static Status depositStatus(int status) {
    switch (status) {
      case 0:
        return Status.PROCESSING;
      case 1:
        return Status.COMPLETE;
      default:
        throw new RuntimeException("Unknown binance deposit status: " + status);
    }
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    Long recvWindow =
        (Long) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
    BinanceAccountInformation acc = account(recvWindow, getTimestamp());
    List<Balance> balances =
        acc.balances
            .stream()
            .map(
                b -> {
                  BigDecimal total = b.getTotal();
                  BigDecimal available = b.getAvailable();
                  return new Builder()
                      .setCurrency(b.getCurrency())
                      .setTotal(total)
                      .setAvailable(available)
                      .setFrozen(total.add(available.negate()))
                      .createBalance();
                })
            .collect(Collectors.toList());
    return AccountInfo.build(Wallet.build(balances));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws ExchangeException, NotAvailableFromExchangeException,
          NotYetImplementedForExchangeException, IOException {
    return withdraw(currency.getCurrencyCode(), address, amount);
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params)
      throws ExchangeException, NotAvailableFromExchangeException,
          NotYetImplementedForExchangeException, IOException {
    if (!(params instanceof DefaultWithdrawFundsParams)) {
      throw new RuntimeException("DefaultWithdrawFundsParams must be provided.");
    }
    String id = null;
    if (params instanceof RippleWithdrawFundsParams) {
      RippleWithdrawFundsParams rippleParams = null;
      rippleParams = (RippleWithdrawFundsParams) params;
      id =
          withdraw(
              rippleParams.getCurrency().getCurrencyCode(),
              rippleParams.getAddress(),
              rippleParams.getTag(),
              rippleParams.getAmount());
    } else {
      DefaultWithdrawFundsParams p = (DefaultWithdrawFundsParams) params;
      id = withdraw(p.getCurrency().getCurrencyCode(), p.getAddress(), p.getAmount());
    }
    return id;
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {
    return requestDepositAddress(currency).address;
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return new BinanceFundingHistoryParams();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    String asset = null;
    if (params instanceof TradeHistoryParamCurrency) {
      TradeHistoryParamCurrency cp = (TradeHistoryParamCurrency) params;
      if (cp.getCurrency() != null) {
        asset = cp.getCurrency().getCurrencyCode();
      }
    }
    Long recvWindow =
        (Long) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");

    boolean withdrawals = true;
    boolean deposits = true;

    Long startTime = null;
    Long endTime = null;
    if (params instanceof TradeHistoryParamsTimeSpan) {
      TradeHistoryParamsTimeSpan tp = (TradeHistoryParamsTimeSpan) params;
      if (tp.getStartTime() != null) {
        startTime = tp.getStartTime().getTime();
      }
      if (tp.getEndTime() != null) {
        endTime = tp.getEndTime().getTime();
      }
    }

    if (params instanceof HistoryParamsFundingType) {
      HistoryParamsFundingType f = (HistoryParamsFundingType) params;
      if (f.getType() != null) {
        withdrawals = f.getType() == Type.WITHDRAWAL;
        deposits = f.getType() == Type.DEPOSIT;
      }
    }

    List<FundingRecord> result = new ArrayList<>();
    if (withdrawals) {
      withdrawHistory(asset, startTime, endTime, recvWindow, getTimestamp())
          .forEach(
              w ->
                  result.add(
                      new FundingRecord(
                          w.address,
                          new Date(w.applyTime),
                          Currency.valueOf(w.asset),
                          w.amount,
                          w.id,
                          w.txId,
                          Type.WITHDRAWAL,
                          withdrawStatus(w.status),
                          null,
                          null,
                          null)));
    }

    if (deposits) {
      depositHistory(asset, startTime, endTime, recvWindow, getTimestamp())
          .forEach(
              d ->
                  result.add(
                      new FundingRecord(
                          d.address,
                          new Date(d.insertTime),
                          Currency.valueOf(d.asset),
                          d.amount,
                          null,
                          d.txId,
                          Type.DEPOSIT,
                          depositStatus(d.status),
                          null,
                          null,
                          null)));
    }

    return result;
  }
}
