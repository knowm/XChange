package org.knowm.xchange.binance.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.binance.BinanceErrorAdapter;
import org.knowm.xchange.binance.dto.BinanceException;
import org.knowm.xchange.binance.dto.account.AssetDetail;
import org.knowm.xchange.binance.dto.account.BinanceAccountInformation;
import org.knowm.xchange.binance.dto.account.DepositAddress;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.*;
import org.knowm.xchange.dto.account.FundingRecord.Status;
import org.knowm.xchange.dto.account.FundingRecord.Type;
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

  /** (0:pending,1:success) */
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

  private BinanceAccountInformation getBinanceAccountInformation() throws IOException {
    Long recvWindow =
        (Long) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
    return super.account(recvWindow, getTimestamp());
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    try {
      BinanceAccountInformation acc = getBinanceAccountInformation();
      List<Balance> balances =
          acc.balances.stream()
              .map(b -> new Balance(b.getCurrency(), b.getTotal(), b.getAvailable()))
              .collect(Collectors.toList());
      return new AccountInfo(new Date(acc.updateTime), new Wallet(balances));
    } catch (BinanceException e) {
      throw BinanceErrorAdapter.adapt(e);
    }
  }

  @Override
  public Map<CurrencyPair, Fee> getDynamicTradingFees() throws IOException {
    BinanceAccountInformation acc = getBinanceAccountInformation();
    BigDecimal makerFee =
        acc.makerCommission.divide(new BigDecimal("10000"), 4, RoundingMode.UNNECESSARY);
    BigDecimal takerFee =
        acc.takerCommission.divide(new BigDecimal("10000"), 4, RoundingMode.UNNECESSARY);

    Map<CurrencyPair, Fee> tradingFees = new HashMap<>();
    List<CurrencyPair> pairs = exchange.getExchangeSymbols();

    pairs.forEach(pair -> tradingFees.put(pair, new Fee(makerFee, takerFee)));

    return tradingFees;
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    try {
      return super.withdraw(currency.getCurrencyCode(), address, amount);
    } catch (BinanceException e) {
      throw BinanceErrorAdapter.adapt(e);
    }
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, AddressWithTag address)
      throws IOException {
    return withdrawFunds(new DefaultWithdrawFundsParams(address, currency, amount));
  }

  @Override
  public String withdrawFunds(WithdrawFundsParams params) throws IOException {
    try {
      if (!(params instanceof DefaultWithdrawFundsParams)) {
        throw new IllegalArgumentException("DefaultWithdrawFundsParams must be provided.");
      }
      String id = null;
      if (params instanceof RippleWithdrawFundsParams) {
        RippleWithdrawFundsParams rippleParams = null;
        rippleParams = (RippleWithdrawFundsParams) params;
        id =
            super.withdraw(
                rippleParams.getCurrency().getCurrencyCode(),
                rippleParams.getAddress(),
                rippleParams.getTag(),
                rippleParams.getAmount());
      } else {
        DefaultWithdrawFundsParams p = (DefaultWithdrawFundsParams) params;
        id =
            super.withdraw(
                p.getCurrency().getCurrencyCode(),
                p.getAddress(),
                p.getDestinationTag(),
                p.getAmount());
      }
      return id;
    } catch (BinanceException e) {
      throw BinanceErrorAdapter.adapt(e);
    }
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {
    try {
      return super.requestDepositAddress(currency).address;
    } catch (BinanceException e) {
      throw BinanceErrorAdapter.adapt(e);
    }
  }

  @Override
  public AddressWithTag requestDepositAddressData(Currency currency, String... args)
      throws IOException {
    DepositAddress depositAddress = super.requestDepositAddress(currency);
    return new AddressWithTag(depositAddress.address, depositAddress.addressTag);
  }

  public Map<String, AssetDetail> getAssetDetails() throws IOException {
    try {
      return super.requestAssetDetail().getAssetDetail();
    } catch (BinanceException e) {
      throw BinanceErrorAdapter.adapt(e);
    }
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    return new BinanceFundingHistoryParams();
  }

  @Override
  public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
    try {
      String asset = null;
      if (params instanceof TradeHistoryParamCurrency) {
        TradeHistoryParamCurrency cp = (TradeHistoryParamCurrency) params;
        if (cp.getCurrency() != null) {
          asset = cp.getCurrency().getCurrencyCode();
        }
      }
      Long recvWindow =
          (Long)
              exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");

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
        super.withdrawHistory(asset, startTime, endTime, recvWindow, getTimestamp())
            .forEach(
                w -> {
                  result.add(
                      new FundingRecord(
                          w.address,
                          w.destinationTag,
                          new Date(w.applyTime),
                          Currency.getInstance(w.asset),
                          w.amount,
                          w.id,
                          w.txId,
                          Type.WITHDRAWAL,
                          withdrawStatus(w.status),
                          null,
                          null,
                          null));
                });
      }

      if (deposits) {
        super.depositHistory(asset, startTime, endTime, recvWindow, getTimestamp())
            .forEach(
                d -> {
                  result.add(
                      new FundingRecord(
                          d.address,
                          new Date(d.insertTime),
                          Currency.getInstance(d.asset),
                          d.amount,
                          null,
                          d.txId,
                          Type.DEPOSIT,
                          depositStatus(d.status),
                          null,
                          null,
                          null));
                });
      }

      return result;
    } catch (BinanceException e) {
      throw BinanceErrorAdapter.adapt(e);
    }
  }
}
