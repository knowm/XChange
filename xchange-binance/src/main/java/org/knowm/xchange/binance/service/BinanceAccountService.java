package org.knowm.xchange.binance.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.dto.BinanceException;
import org.knowm.xchange.binance.dto.account.BinanceAccountInformation;
import org.knowm.xchange.binance.dto.account.DepositAddress;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.FundingRecord.Status;
import org.knowm.xchange.dto.account.FundingRecord.Type;
import org.knowm.xchange.dto.account.Wallet;
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

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    Long recvWindow = (Long) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
    BinanceAccountInformation acc = super.account(recvWindow, System.currentTimeMillis());
    List<Balance> balances = acc.balances.stream()
        .map(b -> new Balance(Currency.getInstance(b.asset), b.free.add(b.locked), b.free))
        .collect(Collectors.toList());
    return new AccountInfo(new Wallet(balances));
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    return withdraw0(currency.getCurrencyCode(), address, amount);
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
      if (!(params instanceof DefaultWithdrawFundsParams)) {
      throw new RuntimeException("DefaultWithdrawFundsParams must be provided.");
    }
    String id = null;
    if (params instanceof RippleWithdrawFundsParams) {
      RippleWithdrawFundsParams rippleParams = null;
      rippleParams = (RippleWithdrawFundsParams)params;
      id = withdraw0(rippleParams.currency.getCurrencyCode(), rippleParams.address, rippleParams.tag, rippleParams.amount);
    } else {
      DefaultWithdrawFundsParams p = (DefaultWithdrawFundsParams) params;
      id = withdraw0(p.currency.getCurrencyCode(), p.address, p.amount);
    }
    return id;
  }

  private String withdraw0(String asset, String address, BigDecimal amount) throws IOException, BinanceException {
    // the name parameter seams to be mandatory
    String name = address.length() <= 10 ? address : address.substring(0, 10);
    return super.withdraw(asset, address, amount, name, null, System.currentTimeMillis());
  }
  private String withdraw0(String asset, String address, String addressTag, BigDecimal amount) throws IOException, BinanceException {
    // the name parameter seams to be mandatory
    String name = address.length() <= 10 ? address : address.substring(0, 10);
    Long recvWindow = (Long) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
    return super.withdraw(asset, address, addressTag, amount, name, recvWindow, System.currentTimeMillis());
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {
	  Long recvWindow = (Long) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
	  DepositAddress depositAddress = binance.depositAddress(BinanceAdapters.toSymbol(currency), recvWindow, System.currentTimeMillis(), apiKey, super.signatureCreator);
	  return depositAddress.address;
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return new BinanceFundingHistoryParams();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params)
      throws IOException {
    String asset = null;
    if (params instanceof TradeHistoryParamCurrency) {
      TradeHistoryParamCurrency cp = (TradeHistoryParamCurrency) params;
      if (cp.getCurrency() != null) {
        asset = cp.getCurrency().getCurrencyCode();
      }
    }
    Long recvWindow = (Long) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");

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
      super.withdrawHistory(asset, startTime, endTime, recvWindow, System.currentTimeMillis()).forEach(w -> {
        result.add(new FundingRecord(w.address, new Date(w.applyTime), Currency.getInstance(w.asset), w.amount, w.id, w.txId, Type.WITHDRAWAL, withdrawStatus(w.status), null, null, null));
      });
    }

    if (deposits) {
      super.depositHistory(asset, startTime, endTime, recvWindow, System.currentTimeMillis()).forEach(d -> {
        result.add(new FundingRecord(d.address, new Date(d.insertTime), Currency.getInstance(d.asset), d.amount, null, d.txId, Type.DEPOSIT, depositStatus(d.status), null, null, null));
      });
    }

    return result;
  }

  /**
   * (0:Email Sent,1:Cancelled 2:Awaiting Approval 3:Rejected 4:Processing 5:Failure 6Completed)
   */
  private static FundingRecord.Status withdrawStatus(int status) {
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

  /**
   * (0:pending,1:success)
   */
  private static FundingRecord.Status depositStatus(int status) {
    switch (status) {
      case 0:
        return Status.PROCESSING;
      case 1:
        return Status.COMPLETE;
      default:
        throw new RuntimeException("Unknown binance deposit status: " + status);
    }
  }

}
