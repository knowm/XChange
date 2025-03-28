package org.knowm.xchange.binance.service;

import static org.knowm.xchange.binance.BinanceResilience.REQUEST_WEIGHT_RATE_LIMITER;
import static org.knowm.xchange.client.ResilienceRegistries.NON_IDEMPOTENT_CALLS_RETRY_CONFIG_NAME;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.binance.dto.BinanceException;
import org.knowm.xchange.binance.dto.account.AssetDetail;
import org.knowm.xchange.binance.dto.account.AssetDividendResponse;
import org.knowm.xchange.binance.dto.account.BinanceAccountInformation;
import org.knowm.xchange.binance.dto.account.BinanceCurrencyInfo;
import org.knowm.xchange.binance.dto.account.BinanceDeposit;
import org.knowm.xchange.binance.dto.account.BinanceWithdraw;
import org.knowm.xchange.binance.dto.account.DepositAddress;
import org.knowm.xchange.binance.dto.account.TransferHistory;
import org.knowm.xchange.binance.dto.account.TransferSubUserHistory;
import org.knowm.xchange.binance.dto.account.WithdrawResponse;
import org.knowm.xchange.binance.dto.account.futures.BinanceFutureAccountInformation;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.Currency;

public class BinanceAccountServiceRaw extends BinanceBaseService {
  private List<BinanceCurrencyInfo> currencyInfos;
  private final Lock currencyInfoLock = new ReentrantLock();

  public BinanceAccountServiceRaw(
      BinanceExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
  }

  public BinanceAccountInformation account() throws BinanceException, IOException {
    return decorateApiCall(
            () -> binance.account(getRecvWindow(), getTimestampFactory(), apiKey, signatureCreator))
        .withRetry(retry("account"))
        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER), 5)
        .call();
  }

  public List<BinanceCurrencyInfo> currencyInfos() throws BinanceException, IOException {
    return decorateApiCall(
            () ->
                binance.getCurrencyInfos(
                    getRecvWindow(), getTimestampFactory(), apiKey, signatureCreator))
        .withRetry(retry("currencyInfo"))
        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER), 5)
        .call();
  }

  public BinanceFutureAccountInformation futuresAccount() throws BinanceException, IOException {
    return decorateApiCall(
            () ->
                binanceFutures.futuresAccount(
                    getRecvWindow(), getTimestampFactory(), apiKey, signatureCreator))
        .withRetry(retry("futures-account"))
        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER), 5)
        .call();
  }

  public WithdrawResponse withdraw(String coin, String address, BigDecimal amount)
      throws IOException, BinanceException {
    // the name parameter seams to be mandatory
    String name = address.length() <= 10 ? address : address.substring(0, 10);
    return withdraw(coin, address, null, amount, name, null);
  }

  public WithdrawResponse withdraw(
      String coin, String address, String addressTag, BigDecimal amount, String network)
      throws IOException, BinanceException {
    // the name parameter seams to be mandatory
    String name = address.length() <= 10 ? address : address.substring(0, 10);
    return withdraw(coin, address, addressTag, amount, name, network);
  }

  private WithdrawResponse withdraw(
      String coin,
      String address,
      String addressTag,
      BigDecimal amount,
      String name,
      String network)
      throws IOException, BinanceException {
    return decorateApiCall(
            () ->
                binance.withdraw(
                    coin,
                    address,
                    addressTag,
                    amount,
                    name,
                    network,
                    getRecvWindow(),
                    getTimestampFactory(),
                    apiKey,
                    signatureCreator))
        .withRetry(retry("withdraw", NON_IDEMPOTENT_CALLS_RETRY_CONFIG_NAME))
        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER), 5)
        .call();
  }

  public DepositAddress requestDepositAddress(Currency currency) throws IOException {
    return requestDepositAddressWithNetwork(currency, null);
  }

  public DepositAddress requestDepositAddressWithNetwork(Currency currency, String network)
      throws IOException {
    return decorateApiCall(
            () ->
                binance.depositAddress(
                    BinanceAdapters.toSymbol(currency),
                    network,
                    getRecvWindow(),
                    getTimestampFactory(),
                    apiKey,
                    signatureCreator))
        .withRetry(retry("depositAddress"))
        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER))
        .call();
  }

  public Map<String, AssetDetail> requestAssetDetail() throws IOException {
    return decorateApiCall(
            () ->
                binance.assetDetail(
                    getRecvWindow(), getTimestampFactory(), apiKey, signatureCreator))
        .withRetry(retry("assetDetail"))
        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER))
        .call();
  }

  public List<BinanceDeposit> depositHistory(String asset, Long startTime, Long endTime)
      throws BinanceException, IOException {
    return decorateApiCall(
            () ->
                binance.depositHistory(
                    asset,
                    startTime,
                    endTime,
                    getRecvWindow(),
                    getTimestampFactory(),
                    apiKey,
                    signatureCreator))
        .withRetry(retry("depositHistory"))
        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER))
        .call();
  }

  public List<BinanceWithdraw> withdrawHistory(String asset, Long startTime, Long endTime)
      throws BinanceException, IOException {
    return decorateApiCall(
            () ->
                binance.withdrawHistory(
                    asset,
                    startTime,
                    endTime,
                    getRecvWindow(),
                    getTimestampFactory(),
                    apiKey,
                    signatureCreator))
        .withRetry(retry("withdrawHistory"))
        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER))
        .call();
  }

  public List<AssetDividendResponse.AssetDividend> getAssetDividend(Long startTime, Long endTime)
      throws BinanceException, IOException {
    return getAssetDividend("", startTime, endTime);
  }

  public List<AssetDividendResponse.AssetDividend> getAssetDividend(
      String asset, Long startTime, Long endTime) throws BinanceException, IOException {
    return decorateApiCall(
            () ->
                binance.assetDividend(
                    asset,
                    startTime,
                    endTime,
                    getRecvWindow(),
                    getTimestampFactory(),
                    super.apiKey,
                    super.signatureCreator))
        .withRetry(retry("assetDividend"))
        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER))
        .call()
        .getData();
  }

  public List<TransferHistory> getTransferHistory(
      String fromEmail, Long startTime, Long endTime, Integer page, Integer limit)
      throws BinanceException, IOException {
    return decorateApiCall(
            () ->
                binance.transferHistory(
                    fromEmail,
                    startTime,
                    endTime,
                    page,
                    limit,
                    getRecvWindow(),
                    getTimestampFactory(),
                    super.apiKey,
                    super.signatureCreator))
        .withRetry(retry("transferHistory"))
        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER))
        .call();
  }

  public List<TransferSubUserHistory> getSubUserHistory(
      String asset, Integer type, Long startTime, Long endTime, Integer limit)
      throws BinanceException, IOException {
    return decorateApiCall(
            () ->
                binance.transferSubUserHistory(
                    asset,
                    type,
                    startTime,
                    endTime,
                    limit,
                    getRecvWindow(),
                    getTimestampFactory(),
                    super.apiKey,
                    super.signatureCreator))
        .withRetry(retry("transferSubUserHistory"))
        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER))
        .call();
  }

  protected List<BinanceCurrencyInfo> getCurrencyInfoCached() throws IOException {
    currencyInfoLock.lock();
    try {
      if (currencyInfos == null) {
        currencyInfos = currencyInfos();
      }
      currencyInfos = currencyInfos();
    } finally {
      currencyInfoLock.unlock();
    }

    return currencyInfos;
  }

  protected Optional<BinanceCurrencyInfo> getCurrencyInfo(Currency currency) throws IOException {
    return getCurrencyInfoCached().stream()
        .filter(info -> currency.equals(info.getCurrency()))
        .findFirst();
  }
}
