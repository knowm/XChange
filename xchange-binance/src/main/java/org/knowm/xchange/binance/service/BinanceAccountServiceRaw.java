package org.knowm.xchange.binance.service;

import static org.knowm.xchange.binance.BinanceResilience.REQUEST_WEIGHT_RATE_LIMITER;
import static org.knowm.xchange.client.ResilienceRegistries.NON_IDEMPOTENT_CALLS_RETRY_CONFIG_NAME;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.BinanceAuthenticated;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.binance.dto.BinanceException;
import org.knowm.xchange.binance.dto.account.*;
import org.knowm.xchange.binance.dto.account.DepositList.BinanceDeposit;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.Currency;

public class BinanceAccountServiceRaw extends BinanceBaseService {

  public BinanceAccountServiceRaw(
      BinanceExchange exchange,
      BinanceAuthenticated binance,
      ResilienceRegistries resilienceRegistries) {
    super(exchange, binance, resilienceRegistries);
  }

  public BinanceAccountInformation account() throws BinanceException, IOException {
    return decorateApiCall(
            () -> binance.account(getRecvWindow(), getTimestampFactory(), apiKey, signatureCreator))
        .withRetry(retry("account"))
        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER), 5)
        .call();
  }

  // the /wapi endpoint of binance is not stable yet and can be changed in future, there is also a
  // lack of current documentation

  public String withdraw(String asset, String address, BigDecimal amount)
      throws IOException, BinanceException {
    // the name parameter seams to be mandatory
    String name = address.length() <= 10 ? address : address.substring(0, 10);
    return withdraw(asset, address, amount, name);
  }

  public String withdraw(String asset, String address, String addressTag, BigDecimal amount)
      throws IOException, BinanceException {
    // the name parameter seams to be mandatory
    String name = address.length() <= 10 ? address : address.substring(0, 10);
    return withdraw(asset, address, addressTag, amount, name);
  }

  private String withdraw(String asset, String address, BigDecimal amount, String name)
      throws IOException, BinanceException {
    WithdrawRequest result =
        decorateApiCall(
                () ->
                    binance.withdraw(
                        asset,
                        address,
                        null,
                        amount,
                        name,
                        getRecvWindow(),
                        getTimestampFactory(),
                        apiKey,
                        signatureCreator))
            .withRetry(retry("withdraw", NON_IDEMPOTENT_CALLS_RETRY_CONFIG_NAME))
            .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER), 5)
            .call();
    checkWapiResponse(result);
    return result.getData();
  }

  private String withdraw(
      String asset, String address, String addressTag, BigDecimal amount, String name)
      throws IOException, BinanceException {
    WithdrawRequest result =
        decorateApiCall(
                () ->
                    binance.withdraw(
                        asset,
                        address,
                        addressTag,
                        amount,
                        name,
                        getRecvWindow(),
                        getTimestampFactory(),
                        apiKey,
                        signatureCreator))
            .withRetry(retry("withdraw", NON_IDEMPOTENT_CALLS_RETRY_CONFIG_NAME))
            .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER), 5)
            .call();
    checkWapiResponse(result);
    return result.getData();
  }

  public DepositAddress requestDepositAddress(Currency currency) throws IOException {
    return decorateApiCall(
            () ->
                binance.depositAddress(
                    BinanceAdapters.toSymbol(currency),
                    getRecvWindow(),
                    getTimestampFactory(),
                    apiKey,
                    signatureCreator))
        .withRetry(retry("depositAddress"))
        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER))
        .call();
  }

  public AssetDetailResponse requestAssetDetail() throws IOException {
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
    DepositList result =
        decorateApiCall(
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
    return checkWapiResponse(result);
  }

  public List<WithdrawList.BinanceWithdraw> withdrawHistory(
      String asset, Long startTime, Long endTime) throws BinanceException, IOException {
    WithdrawList result =
        decorateApiCall(
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
    return checkWapiResponse(result);
  }

  public AssetDribbletLogResponse.AssetDribbletLogResults getAssetDribbletLog()
      throws BinanceException, IOException {
    return decorateApiCall(
            () ->
                binance.userAssetDribbletLog(
                    getRecvWindow(), getTimestampFactory(), super.apiKey, super.signatureCreator))
        .withRetry(retry("userAssetDribbletLog"))
        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER))
        .call()
        .getData();
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

  public List<TransferHistoryResponse.TransferHistory> getTransferHistory(
      String email, Long startTime, Long endTime, Integer page, Integer limit)
      throws BinanceException, IOException {
    return decorateApiCall(
            () ->
                binance.transferHistory(
                    email,
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
        .call()
        .getData();
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

  private <T> T checkWapiResponse(WapiResponse<T> result) {
    if (!result.success) {
      BinanceException exception;
      try {
        exception = new ObjectMapper().readValue(result.msg, BinanceException.class);
      } catch (Throwable e) {
        exception = new BinanceException(-1, result.msg);
      }
      throw exception;
    }
    return result.getData();
  }
}
