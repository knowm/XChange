package org.knowm.xchange.binance.service;

import static org.knowm.xchange.binance.BinanceAdapters.adaptSymbol;
import static org.knowm.xchange.binance.BinanceAdapters.toSymbol;
import static org.knowm.xchange.binance.BinanceExchange.EXCHANGE_TYPE;

import java.io.IOException;
import java.math.BigDecimal;
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
import org.knowm.xchange.binance.dto.account.BinanceCurrencyInfo;
import org.knowm.xchange.binance.dto.account.BinanceCurrencyInfo.Network;
import org.knowm.xchange.binance.dto.account.BinanceFlexiblePosition;
import org.knowm.xchange.binance.dto.account.BinanceFlexiblePositionResponse;
import org.knowm.xchange.binance.dto.account.BinanceFundingHistoryParams;
import org.knowm.xchange.binance.dto.account.BinanceLockedPosition;
import org.knowm.xchange.binance.dto.account.BinanceLockedPositionResponse;
import org.knowm.xchange.binance.dto.account.BinanceMasterAccountTransferHistoryParams;
import org.knowm.xchange.binance.dto.account.BinanceSimpleAccount;
import org.knowm.xchange.binance.dto.account.BinanceSubAccountTransferHistoryParams;
import org.knowm.xchange.binance.dto.account.BinanceTradeFee;
import org.knowm.xchange.binance.dto.account.DepositAddress;
import org.knowm.xchange.binance.dto.account.WithdrawResponse;
import org.knowm.xchange.binance.dto.account.futures.BinanceFutureAccountInformation;
import org.knowm.xchange.binance.dto.account.futures.BinanceFutureCommissionRate;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.derivative.FuturesContract;
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

  /**
   * Maps fiat order status to FundingRecord.Status. Status values: Processing, Failed, Successful,
   * Finished, Refunding, Refunded, Refund Failed, Order Partial credit Stopped, Expired
   */
  private static FundingRecord.Status fiatOrderStatus(String status) {
    if (status == null) {
      return Status.FAILED;
    }
    switch (status) {
      case "Processing":
      case "Refunding":
      case "Order Partial credit Stopped":
        return Status.PROCESSING;
      case "Successful":
      case "Finished":
        return Status.COMPLETE;
      case "Failed":
      case "Refund Failed":
      case "Expired":
        return Status.FAILED;
      case "Refunded":
        return Status.CANCELLED;
      default:
        Status resolved = Status.resolveStatus(status);
        return resolved != null ? resolved : Status.FAILED;
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

  /** Results based on ExchangeSpecificParametersItem(EXCHANGE_TYPE) */
  @Override
  public Map<Instrument, Fee> getDynamicTradingFeesByInstrument(String... category)
      throws IOException {
    try {
      Map<Instrument, Fee> fees = new HashMap<>();
      if (exchange
          .getExchangeSpecification()
          .getExchangeSpecificParametersItem(EXCHANGE_TYPE)
          .equals(ExchangeType.SPOT)) {
        List<BinanceTradeFee> binanceTradeFees = getTradeFee();
        binanceTradeFees.forEach(
            binanceTradeFee -> {
              Instrument instrument = adaptSymbol(binanceTradeFee.getSymbol(), false);
              if (instrument != null) // some deleted pair still exist in fees result
              fees.put(
                    instrument,
                    new Fee(
                        new BigDecimal(binanceTradeFee.getMakerCommission()),
                        new BigDecimal(binanceTradeFee.getTakerCommission())));
            });
      } else throw new UnsupportedOperationException("Only SPOT exchange type is supported");
      return fees;
    } catch (BinanceException e) {
      throw BinanceErrorAdapter.adapt(e);
    }
  }

  public Fee getCommissionRateByInstrument(Instrument instrument) throws IOException {
    try {
      // only 1 req per every symbol, cost 20 REQUEST_WEIGHT, did not find any other way to get all
      // fees
      if (exchange
          .getExchangeSpecification()
          .getExchangeSpecificParametersItem(EXCHANGE_TYPE)
          .equals(ExchangeType.FUTURES)) {
        BinanceFutureCommissionRate binanceFutureCommissionRate =
            getCommissionRate(toSymbol(instrument));
        return new Fee(
            new BigDecimal(binanceFutureCommissionRate.getMakerCommissionRate()),
            new BigDecimal(binanceFutureCommissionRate.getTakerCommissionRate()));
      } else throw new UnsupportedOperationException("Only FUTURES exchange type is supported");
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
                        FundingRecord.builder()
                            .address(w.getAddress())
                            .addressTag(w.getAddressTag())
                            .date(BinanceAdapters.toDate(w.getApplyTime()))
                            .currency(Currency.getInstance(w.getCoin()))
                            .amount(w.getAmount())
                            .internalId(w.getId())
                            .blockchainTransactionHash(w.getTxId())
                            .type(Type.WITHDRAWAL)
                            .status(withdrawStatus(w.getStatus()))
                            .fee(w.getTransactionFee())
                            .build()));
      }

      if (deposits) {
        super.depositHistory(asset, startTime, endTime)
            .forEach(
                d ->
                    result.add(
                        FundingRecord.builder()
                            .address(d.getAddress())
                            .addressTag(d.getAddressTag())
                            .date(new Date(d.getInsertTime()))
                            .currency(Currency.getInstance(d.getCoin()))
                            .amount(d.getAmount())
                            .blockchainTransactionHash(d.getTxId())
                            .type(Type.DEPOSIT)
                            .status(depositStatus(d.getStatus()))
                            .build()));
      }

      if (otherInflow) {
        super.getAssetDividend(asset, startTime, endTime)
            .forEach(
                a ->
                    result.add(
                        FundingRecord.builder()
                            .date(new Date(a.getDivTime()))
                            .currency(Currency.getInstance(a.getAsset()))
                            .amount(a.getAmount())
                            .blockchainTransactionHash(String.valueOf(a.getTranId()))
                            .type(Type.OTHER_INFLOW)
                            .status(Status.COMPLETE)
                            .description(a.getEnInfo())
                            .build()));
      }

      final String finalEmail = email;

      if (email != null) {
        super.getTransferHistory(email, startTime, endTime, page, limit)
            .forEach(
                a ->
                    result.add(
                        FundingRecord.builder()
                            .address(finalEmail)
                            .date(new Date(a.getTime()))
                            .currency(Currency.getInstance(a.getAsset()))
                            .amount(a.getQty())
                            .type(Type.INTERNAL_WITHDRAWAL)
                            .status(transferHistoryStatus(a.getStatus()))
                            .build()));
      }

      if (subAccount) {

        Integer type = deposits && withdrawals ? null : deposits ? 1 : 0;
        super.getSubUserHistory(asset, type, startTime, endTime, limit)
            .forEach(
                a ->
                    result.add(
                        FundingRecord.builder()
                            .address(a.getEmail())
                            .date(new Date(a.getTime()))
                            .currency(Currency.getInstance(a.getAsset()))
                            .amount(a.getQty())
                            .type(
                                a.getType().equals(1)
                                    ? Type.INTERNAL_DEPOSIT
                                    : Type.INTERNAL_WITHDRAWAL)
                            .status(Status.COMPLETE)
                            .build()));
      }

      // Fetch fiat deposit/withdrawal history
      if (deposits) {
        super.getFiatOrders("0", startTime, endTime, page, limit)
            .forEach(
                f ->
                    result.add(
                        FundingRecord.builder()
                            .internalId(f.getOrderNo())
                            .date(new Date(f.getCreateTime()))
                            .currency(Currency.getInstance(f.getFiatCurrency()))
                            .amount(f.getAmount())
                            .fee(f.getTotalFee())
                            .type(Type.DEPOSIT)
                            .status(fiatOrderStatus(f.getStatus()))
                            .description(f.getMethod())
                            .build()));
      }

      if (withdrawals) {
        super.getFiatOrders("1", startTime, endTime, page, limit)
            .forEach(
                f ->
                    result.add(
                        FundingRecord.builder()
                            .internalId(f.getOrderNo())
                            .date(new Date(f.getCreateTime()))
                            .currency(Currency.getInstance(f.getFiatCurrency()))
                            .amount(f.getAmount())
                            .fee(f.getTotalFee())
                            .type(Type.WITHDRAWAL)
                            .status(fiatOrderStatus(f.getStatus()))
                            .description(f.getMethod())
                            .build()));
      }

      return result;
    } catch (BinanceException e) {
      throw BinanceErrorAdapter.adapt(e);
    }
  }

  @Override
  public boolean setLeverage(Instrument instrument, int leverage) throws IOException {
    if (instrument instanceof FuturesContract) {
      return setLeverageRaw(instrument, leverage).leverage == leverage;
    } else return false;
  }

  public BinanceSimpleAccount getSimpleAccount() throws IOException {
    try {
      return super.getSimpleAccount();
    } catch (BinanceException e) {
      throw BinanceErrorAdapter.adapt(e);
    }
  }

  public List<BinanceFlexiblePosition> getFlexiblePositions(
      String asset, String productId, Long current, Long size) throws IOException {
    try {
      BinanceFlexiblePositionResponse response =
          super.getFlexiblePositionsRaw(asset, productId, current, size);
      return response != null ? response.getData() : List.of();
    } catch (BinanceException e) {
      throw BinanceErrorAdapter.adapt(e);
    }
  }

  public List<BinanceLockedPosition> getLockedPositions(
      String asset, Long positionId, String projectId, Long current, Long size) throws IOException {
    try {
      BinanceLockedPositionResponse response =
          super.getLockedPositionsRaw(asset, positionId, projectId, current, size);
      return response != null ? response.getData() : List.of();
    } catch (BinanceException e) {
      throw BinanceErrorAdapter.adapt(e);
    }
  }
}
