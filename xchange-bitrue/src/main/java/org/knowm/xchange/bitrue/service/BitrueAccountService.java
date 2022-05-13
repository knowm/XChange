package org.knowm.xchange.bitrue.service;

import org.knowm.xchange.bitrue.BitrueAdapters;
import org.knowm.xchange.bitrue.BitrueAuthenticated;
import org.knowm.xchange.bitrue.BitrueErrorAdapter;
import org.knowm.xchange.bitrue.BitrueExchange;
import org.knowm.xchange.bitrue.dto.BitrueException;
import org.knowm.xchange.bitrue.dto.account.AssetDetail;
import org.knowm.xchange.bitrue.dto.account.BitrueAccountInformation;
import org.knowm.xchange.bitrue.dto.account.DepositAddress;
import org.knowm.xchange.bitrue.dto.account.WithdrawResponse;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.*;
import org.knowm.xchange.dto.account.FundingRecord.Status;
import org.knowm.xchange.dto.account.FundingRecord.Type;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public class BitrueAccountService extends BitrueAccountServiceRaw implements AccountService {

  public BitrueAccountService(
      BitrueExchange exchange,
      BitrueAuthenticated binance,
      ResilienceRegistries resilienceRegistries) {
    super(exchange, binance, resilienceRegistries);
  }

  private static Status transferHistoryStatus(String historyStatus) {
    Status status;
    switch (historyStatus) {
      case "SUCCESS":
        status = Status.COMPLETE;
        break;
      default:
        status =
            Status.resolveStatus(
                historyStatus); // FIXME not documented yet in Bitrue spot api docs
        if (status == null) {
          status = Status.FAILED;
        }
    }
    return status;
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

  /** (0:pending,6: credited but cannot withdraw,1:success) */
  private static Status depositStatus(int status) {
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
      BitrueAccountInformation acc = account();
      List<Balance> balances =
          acc.balances.stream()
              .map(b -> new Balance(b.getCurrency(), b.getTotal(), b.getAvailable()))
              .collect(Collectors.toList());
      return new AccountInfo(new Date(acc.updateTime), Wallet.Builder.from(balances).build());
    } catch (BitrueException e) {
      throw BitrueErrorAdapter.adapt(e);
    }
  }

  @Override
  public Map<CurrencyPair, Fee> getDynamicTradingFees() throws IOException {
    try {
      BitrueAccountInformation acc = account();
      BigDecimal makerFee =
          acc.makerCommission.divide(new BigDecimal("10000"), 4, RoundingMode.UNNECESSARY);
      BigDecimal takerFee =
          acc.takerCommission.divide(new BigDecimal("10000"), 4, RoundingMode.UNNECESSARY);

      Map<CurrencyPair, Fee> tradingFees = new HashMap<>();
      List<CurrencyPair> pairs = exchange.getExchangeSymbols();

      pairs.forEach(pair -> tradingFees.put(pair, new Fee(makerFee, takerFee)));

      return tradingFees;
    } catch (BitrueException e) {
      throw BitrueErrorAdapter.adapt(e);
    }
  }



  public Map<String, AssetDetail> getAssetDetails() throws IOException {
    try {
      return super.requestAssetDetail();
    } catch (BitrueException e) {
      throw BitrueErrorAdapter.adapt(e);
    }
  }




}
