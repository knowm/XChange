package org.knowm.xchange.binance.service;

import static org.knowm.xchange.binance.BinanceExchange.EXCHANGE_TYPE;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.BinanceErrorAdapter;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.binance.dto.BinanceException;
import org.knowm.xchange.binance.dto.ExchangeType;
import org.knowm.xchange.binance.dto.account.AssetDetail;
import org.knowm.xchange.binance.dto.account.BinanceAccountInformation;
import org.knowm.xchange.binance.dto.account.BinanceCurrencyInfo;
import org.knowm.xchange.binance.dto.account.BinanceCurrencyInfo.Network;
import org.knowm.xchange.binance.dto.account.BinanceFundingHistoryParams;
import org.knowm.xchange.binance.dto.account.BinanceMasterAccountTransferHistoryParams;
import org.knowm.xchange.binance.dto.account.BinanceSubAccountTransferHistoryParams;
import org.knowm.xchange.binance.dto.account.DepositAddress;
import org.knowm.xchange.binance.dto.account.WithdrawResponse;
import org.knowm.xchange.binance.dto.account.futures.BinanceFutureAccountInformation;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.AddressWithTag;
import org.knowm.xchange.dto.account.Fee;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.FundingRecord.Status;
import org.knowm.xchange.dto.account.FundingRecord.Type;
import org.knowm.xchange.dto.account.OpenPosition;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.account.params.RequestDepositAddressParams;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.HistoryParamsFundingType;
import org.knowm.xchange.service.trade.params.NetworkWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.RippleWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

public class BinanceAccountService extends BinanceAccountServiceRaw implements AccountService {

  public BinanceAccountService(
      BinanceExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
  }

  private static FundingRecord.Status transferHistoryStatus(String historyStatus) {
    Status status;
    switch (historyStatus) {
      case "SUCCESS":
        status = Status.COMPLETE;
        break;
      default:
        status =
            Status.resolveStatus(
                historyStatus); // FIXME not documented yet in Binance spot api docs
        if (status == null) {
          status = Status.FAILED;
        }
    }
    return status;
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

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    try {
      List<Wallet> wallets = new ArrayList<>();
      List<OpenPosition> openPositions = new ArrayList<>();
      switch ((ExchangeType)
          exchange.getExchangeSpecification().getExchangeSpecificParametersItem(EXCHANGE_TYPE)) {
        case SPOT:
          {
            wallets.add(BinanceAdapters.adaptBinanceSpotWallet(account()));
            break;
          }
        case FUTURES:
          {
            BinanceFutureAccountInformation futureAccountInformation = futuresAccount();
            wallets.add(BinanceAdapters.adaptBinanceFutureWallet(futureAccountInformation));
            openPositions.addAll(
                BinanceAdapters.adaptOpenPositions(futureAccountInformation.getPositions()));
            break;
          }
      }
      return new AccountInfo(
          exchange.getExchangeSpecification().getUserName(),
          null,
          wallets,
          openPositions,
          Date.from(Instant.now()));
    } catch (BinanceException e) {
      throw BinanceErrorAdapter.adapt(e);
    }
  }

  @Override
  public Map<Instrument, Fee> getDynamicTradingFeesByInstrument() throws IOException {
    try {
      BinanceAccountInformation acc = account();
      BigDecimal makerFee =
          acc.makerCommission.divide(new BigDecimal("10000"), 4, RoundingMode.UNNECESSARY);
      BigDecimal takerFee =
          acc.takerCommission.divide(new BigDecimal("10000"), 4, RoundingMode.UNNECESSARY);

      Map<Instrument, Fee> tradingFees = new HashMap<>();
      List<Instrument> pairs = exchange.getExchangeInstruments();

      pairs.forEach(pair -> tradingFees.put(pair, new Fee(makerFee, takerFee)));

      return tradingFees;
    } catch (BinanceException e) {
      throw BinanceErrorAdapter.adapt(e);
    }
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    try {
      return super.withdraw(currency.getCurrencyCode(), address, amount).getId();
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
      WithdrawResponse withdraw;
      if (params instanceof RippleWithdrawFundsParams) {
        RippleWithdrawFundsParams rippleParams;
        rippleParams = (RippleWithdrawFundsParams) params;
        withdraw =
            super.withdraw(
                rippleParams.getCurrency().getCurrencyCode(),
                rippleParams.getAddress(),
                rippleParams.getTag(),
                rippleParams.getAmount(),
                Currency.XRP.getCurrencyCode());
      } else if (params instanceof NetworkWithdrawFundsParams) {
        NetworkWithdrawFundsParams p = (NetworkWithdrawFundsParams) params;
        withdraw =
            super.withdraw(
                p.getCurrency().getCurrencyCode(),
                p.getAddress(),
                p.getAddressTag(),
                p.getAmount(),
                p.getNetwork());
      } else {
        DefaultWithdrawFundsParams p = (DefaultWithdrawFundsParams) params;
        withdraw =
            super.withdraw(
                p.getCurrency().getCurrencyCode(),
                p.getAddress(),
                p.getAddressTag(),
                p.getAmount(),
                null);
      }
      return withdraw.getId();
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
    return prepareAddressWithTag(super.requestDepositAddress(currency));
  }

  @Override
  public AddressWithTag requestDepositAddressData(
      RequestDepositAddressParams requestDepositAddressParams) throws IOException {
    if (StringUtils.isEmpty(requestDepositAddressParams.getNetwork())) {
      return requestDepositAddressData(
          requestDepositAddressParams.getCurrency(),
          requestDepositAddressParams.getExtraArguments());
    }

    BinanceCurrencyInfo binanceCurrencyInfo =
        super.getCurrencyInfo(requestDepositAddressParams.getCurrency())
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        "Currency not supported: " + requestDepositAddressParams.getCurrency()));

    Network binanceNetwork =
        binanceCurrencyInfo.getNetworks().stream()
            .filter(
                network ->
                    requestDepositAddressParams.getNetwork().equals(network.getId())
                        || network
                            .getId()
                            .equals(requestDepositAddressParams.getCurrency().getCurrencyCode()))
            .findFirst()
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        "Network not supported: " + requestDepositAddressParams.getNetwork()));

    DepositAddress depositAddress =
        super.requestDepositAddressWithNetwork(
            requestDepositAddressParams.getCurrency(), binanceNetwork.getId());

    return prepareAddressWithTag(depositAddress);
  }

  private static AddressWithTag prepareAddressWithTag(DepositAddress depositAddress) {
    String destinationTag =
        (depositAddress.addressTag == null || depositAddress.addressTag.isEmpty())
            ? null
            : depositAddress.addressTag;
    return new AddressWithTag(depositAddress.address, destinationTag);
  }

  @Override
  public String requestDepositAddress(RequestDepositAddressParams requestDepositAddressParams)
      throws IOException {
    return requestDepositAddressData(requestDepositAddressParams).getAddress();
  }

  public Map<String, AssetDetail> getAssetDetails() throws IOException {
    try {
      return super.requestAssetDetail();
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
        super.withdrawHistory(asset, startTime, endTime)
            .forEach(
                w ->
                    result.add(
                        new FundingRecord(
                            w.getAddress(),
                            w.getAddressTag(),
                            BinanceAdapters.toDate(w.getApplyTime()),
                            Currency.getInstance(w.getCoin()),
                            w.getAmount(),
                            w.getId(),
                            w.getTxId(),
                            Type.WITHDRAWAL,
                            withdrawStatus(w.getStatus()),
                            null,
                            w.getTransactionFee(),
                            null)));
      }

      if (deposits) {
        super.depositHistory(asset, startTime, endTime)
            .forEach(
                d ->
                    result.add(
                        new FundingRecord(
                            d.getAddress(),
                            d.getAddressTag(),
                            new Date(d.getInsertTime()),
                            Currency.getInstance(d.getCoin()),
                            d.getAmount(),
                            null,
                            d.getTxId(),
                            Type.DEPOSIT,
                            depositStatus(d.getStatus()),
                            null,
                            null,
                            null)));
      }

      if (otherInflow) {
        super.getAssetDividend(asset, startTime, endTime)
            .forEach(
                a ->
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
                            a.getEnInfo())));
      }

      final String finalEmail = email;

      if (email != null) {
        super.getTransferHistory(email, startTime, endTime, page, limit)
            .forEach(
                a ->
                    result.add(
                        new FundingRecord.Builder()
                            .setAddress(finalEmail)
                            .setDate(new Date(a.getTime()))
                            .setCurrency(Currency.getInstance(a.getAsset()))
                            .setAmount(a.getQty())
                            .setType(Type.INTERNAL_WITHDRAWAL)
                            .setStatus(transferHistoryStatus(a.getStatus()))
                            .build()));
      }

      if (subAccount) {

        Integer type = deposits && withdrawals ? null : deposits ? 1 : 0;
        super.getSubUserHistory(asset, type, startTime, endTime, limit)
            .forEach(
                a ->
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
                            .build()));
      }

      return result;
    } catch (BinanceException e) {
      throw BinanceErrorAdapter.adapt(e);
    }
  }
}
