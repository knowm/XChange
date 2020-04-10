package org.knowm.xchange.binance.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.binance.BinanceErrorAdapter;
import org.knowm.xchange.binance.dto.BinanceException;
import org.knowm.xchange.binance.dto.account.AssetDetail;
import org.knowm.xchange.binance.dto.account.BinanceAccountInformation;
import org.knowm.xchange.binance.dto.account.DepositAddress;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.AddressWithTag;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Fee;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.FundingRecord.Status;
import org.knowm.xchange.dto.account.FundingRecord.Type;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.*;

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

  /** (0:pending,6: credited but cannot withdraw,1:success) */
  private static FundingRecord.Status depositStatus(int status) {
    switch (status) {
      case 0:
      case 6:
        return Status.PROCESSING;
      case 1:
        return Status.COMPLETE;
      default:
        throw new RuntimeException("Unknown binance deposit status: " + status);
    }
  }

  private BinanceAccountInformation getBinanceAccountInformation() throws IOException {
    return super.account(getRecvWindow(), getTimestamp());
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    try {
      BinanceAccountInformation acc = getBinanceAccountInformation();
      List<Balance> balances =
          acc.balances.stream()
              .map(b -> new Balance(b.getCurrency(), b.getTotal(), b.getAvailable()))
              .collect(Collectors.toList());
      return new AccountInfo(new Date(acc.updateTime), Wallet.Builder.from(balances).build());
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
                p.getAddressTag(),
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
    String destinationTag =
        (depositAddress.addressTag == null || depositAddress.addressTag.isEmpty())
            ? null
            : depositAddress.addressTag;
    return new AddressWithTag(depositAddress.address, destinationTag);
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

      Integer limit = null;
      Integer page = null;

      if (params instanceof TradeHistoryParamLimit) {
        limit = ((TradeHistoryParamLimit) params).getLimit();
      }

      if (params instanceof TradeHistoryParamPaging) {
        page = ((TradeHistoryParamPaging) params).getPageNumber();
      }

      boolean withdrawals = true;
      boolean deposits = true;
      boolean otherInflow = true;

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
          otherInflow = f.getType() == Type.OTHER_INFLOW;
        }
      }

      String email = null;
      boolean subAccount = false;

      // Get transfer history from a master account to a sub account
      if (params instanceof BinanceMasterAccountTransferHistoryParams) {
        email = ((BinanceMasterAccountTransferHistoryParams) params).getEmail();
      }

      // Get transfer history from a sub account to a master/sub account
      if (params instanceof BinanceSubAccountTransferHistoryParams) {
        subAccount = true;
      }

      List<FundingRecord> result = new ArrayList<>();
      if (withdrawals) {
        super.withdrawHistory(asset, startTime, endTime, getRecvWindow(), getTimestamp())
            .forEach(
                w -> {
                  result.add(
                      new FundingRecord(
                          w.getAddress(),
                          w.getAddressTag(),
                          new Date(w.getApplyTime()),
                          Currency.getInstance(w.getAsset()),
                          w.getAmount(),
                          w.getId(),
                          w.getTxId(),
                          Type.WITHDRAWAL,
                          withdrawStatus(w.getStatus()),
                          null,
                          w.getTransactionFee(),
                          null));
                });
      }

      if (deposits) {
        super.depositHistory(asset, startTime, endTime, getRecvWindow(), getTimestamp())
            .forEach(
                d -> {
                  result.add(
                      new FundingRecord(
                          d.getAddress(),
                          d.getAddressTag(),
                          new Date(d.getInsertTime()),
                          Currency.getInstance(d.getAsset()),
                          d.getAmount(),
                          null,
                          d.getTxId(),
                          Type.DEPOSIT,
                          depositStatus(d.getStatus()),
                          null,
                          null,
                          null));
                });
      }

      if (otherInflow) {
        super.getAssetDividend(asset, startTime, endTime)
            .forEach(
                a -> {
                  result.add(
                      new FundingRecord(
                          null,
                          null,
                          new Date(a.getDivTime()),
                          Currency.getInstance(a.getAsset()),
                          a.getAmount(),
                          null,
                          String.valueOf(a.getTranId()),
                          Type.OTHER_INFLOW,
                          Status.COMPLETE,
                          null,
                          null,
                          a.getEnInfo()));
                });
      }

      final String finalEmail = email;

      if (email != null) {
        super.getTransferHistory(email, startTime, endTime, page, limit)
            .forEach(
                a -> {
                  result.add(
                      new FundingRecord.Builder()
                          .setAddress(finalEmail)
                          .setDate(new Date(a.getTime()))
                          .setCurrency(Currency.getInstance(a.getAsset()))
                          .setAmount(a.getQty())
                          .setType(Type.INTERNAL_WITHDRAWAL)
                          .setStatus(Status.COMPLETE)
                          .build());
                });
      }

      if (subAccount) {

        Integer type = deposits && withdrawals ? null : deposits ? 1 : 0;
        super.getSubUserHistory(asset, type, startTime, endTime, limit)
            .forEach(
                a -> {
                  result.add(
                      new FundingRecord.Builder()
                          .setAddress(a.getEmail())
                          .setDate(new Date(a.getTime()))
                          .setCurrency(Currency.getInstance(a.getAsset()))
                          .setAmount(a.getQty())
                          .setType(
                              a.getType().equals(1)
                                  ? Type.INTERNAL_DEPOSIT
                                  : Type.INTERNAL_WITHDRAWAL)
                          .setStatus(Status.COMPLETE)
                          .build());
                });
      }

      return result;
    } catch (BinanceException e) {
      throw BinanceErrorAdapter.adapt(e);
    }
  }
}
