package org.knowm.xchange.coinbasepro.service;

import static org.knowm.xchange.coinbasepro.CoinbaseProResilience.PRIVATE_REST_ENDPOINT_RATE_LIMITER;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.coinbasepro.CoinbasePro;
import org.knowm.xchange.coinbasepro.CoinbaseProExchange;
import org.knowm.xchange.coinbasepro.dto.CoinbaseProException;
import org.knowm.xchange.coinbasepro.dto.CoinbaseProTransfers;
import org.knowm.xchange.coinbasepro.dto.account.CoinbaseProAccount;
import org.knowm.xchange.coinbasepro.dto.account.CoinbaseProFee;
import org.knowm.xchange.coinbasepro.dto.account.CoinbaseProLedger;
import org.knowm.xchange.coinbasepro.dto.account.CoinbaseProSendMoneyRequest;
import org.knowm.xchange.coinbasepro.dto.account.CoinbaseProWithdrawCryptoResponse;
import org.knowm.xchange.coinbasepro.dto.account.CoinbaseProWithdrawFundsRequest;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProWallet;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProWalletAddress;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProSendMoneyResponse;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.utils.DateUtils;
import org.knowm.xchange.utils.timestamp.UnixTimestampFactory;

public class CoinbaseProAccountServiceRaw extends CoinbaseProBaseService {

  public CoinbaseProAccountServiceRaw(
      CoinbaseProExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
  }

  public CoinbaseProAccount[] getCoinbaseProAccountInfo()
      throws CoinbaseProException, IOException {
    return decorateApiCall(
            () ->
                coinbasePro.getAccounts(
                    apiKey, digest, UnixTimestampFactory.INSTANCE.createValue(), passphrase))
        .withRateLimiter(rateLimiter(PRIVATE_REST_ENDPOINT_RATE_LIMITER))
        .call();
  }

  public CoinbaseProAccount getCoinbaseProAccountById(String accountId)
      throws CoinbaseProException, IOException {
    return decorateApiCall(
        () ->
            coinbasePro.getAccountById(
                apiKey, digest, UnixTimestampFactory.INSTANCE.createValue(), passphrase, accountId))
        .withRateLimiter(rateLimiter(PRIVATE_REST_ENDPOINT_RATE_LIMITER))
        .call();
  }

  /** https://docs.pro.coinbase.com/#fees */
  public CoinbaseProFee getCoinbaseProFees() throws CoinbaseProException, IOException {
    return decorateApiCall(
            () ->
                coinbasePro.getFees(
                    apiKey, digest, UnixTimestampFactory.INSTANCE.createValue(), passphrase))
        .withRateLimiter(rateLimiter(PRIVATE_REST_ENDPOINT_RATE_LIMITER))
        .call();
  }

  public CoinbaseProSendMoneyResponse sendMoney(
      String accountId, String to, BigDecimal amount, Currency currency)
      throws CoinbaseProException, IOException {
    return decorateApiCall(
            () ->
                coinbasePro.sendMoney(
                    new CoinbaseProSendMoneyRequest(to, amount, currency.getCurrencyCode()),
                    apiKey,
                    digest,
                    UnixTimestampFactory.INSTANCE.createValue(),
                    passphrase,
                    accountId))
        .withRateLimiter(rateLimiter(PRIVATE_REST_ENDPOINT_RATE_LIMITER))
        .call();
  }

  /** https://docs.pro.coinbase.com/#crypto */
  public CoinbaseProWithdrawCryptoResponse withdrawCrypto(
      String address,
      BigDecimal amount,
      Currency currency,
      String destinationTag,
      boolean noDestinationTag)
      throws CoinbaseProException, IOException {
    return decorateApiCall(
            () ->
                coinbasePro.withdrawCrypto(
                    apiKey,
                    digest,
                    UnixTimestampFactory.INSTANCE.createValue(),
                    passphrase,
                    new CoinbaseProWithdrawFundsRequest(
                        amount,
                        currency.getCurrencyCode(),
                        address,
                        destinationTag,
                        noDestinationTag)))
        .withRateLimiter(rateLimiter(PRIVATE_REST_ENDPOINT_RATE_LIMITER))
        .call();
  }

  /** https://docs.pro.coinbase.com/#get-an-account */
  public CoinbaseProLedger getLedger(String accountId, Date startDate, Date endDate, String beforeId, String afterId, Integer limit, String profileId) throws CoinbaseProException, IOException {
    return decorateApiCall(
            () ->
                coinbasePro.ledger(
                    apiKey,
                    digest,
                    UnixTimestampFactory.INSTANCE.createValue(),
                    passphrase,
                    accountId,
                    (startDate == null) ? null : DateUtils.toISODateString(startDate),
                    (endDate == null) ? null : DateUtils.toISODateString(endDate),
                    beforeId,
                    afterId,
                    limit,
                    profileId))
        .withRateLimiter(rateLimiter(PRIVATE_REST_ENDPOINT_RATE_LIMITER))
        .call();
  }

  /** https://docs.pro.coinbase.com/#create-a-new-report */
  public String requestNewReport(CoinbasePro.CoinbaseProReportRequest reportRequest)
      throws IOException {
    return decorateApiCall(
            () ->
                coinbasePro
                    .createReport(
                        apiKey,
                        digest,
                        UnixTimestampFactory.INSTANCE.createValue(),
                        passphrase,
                        reportRequest)
                    .get("id")
                    .toString())
        .withRateLimiter(rateLimiter(PRIVATE_REST_ENDPOINT_RATE_LIMITER))
        .call();
  }

  /** https://docs.pro.coinbase.com/#get-report-status */
  public Map<?, ?> report(String reportId) throws IOException {
    return decorateApiCall(
            () ->
                coinbasePro.getReport(
                    apiKey,
                    digest,
                    UnixTimestampFactory.INSTANCE.createValue(),
                    passphrase,
                    reportId))
        .withRateLimiter(rateLimiter(PRIVATE_REST_ENDPOINT_RATE_LIMITER))
        .call();
  }

  /** https://docs.pro.coinbase.com/#get-current-exchange-limits */
  public CoinbaseProTransfers getTransfersByAccountId(String accountId, String before, String after, int limit, String type)
      throws CoinbaseProException, IOException {
    return decorateApiCall(
            () ->
                coinbasePro.getTransfersByAccountId(
                    apiKey,
                    digest,
                    UnixTimestampFactory.INSTANCE.createValue(),
                    passphrase,
                    accountId,
                    before,
                    after,
                    limit,
                    type))
        .withRateLimiter(rateLimiter(PRIVATE_REST_ENDPOINT_RATE_LIMITER))
        .call();
  }

  /** https://docs.pro.coinbase.com/#get-current-exchange-limits */
  public CoinbaseProTransfers getTransfers(String type, String profileId, String before, String after, int limit) throws CoinbaseProException, IOException {

      return decorateApiCall(
          () ->
              coinbasePro.getTransfers(
                  apiKey,
                  digest,
                  UnixTimestampFactory.INSTANCE.createValue(),
                  passphrase,
                  type,
                  profileId,
                  before,
                  after,
                  limit))
          .withRateLimiter(rateLimiter(PRIVATE_REST_ENDPOINT_RATE_LIMITER))
          .call();
  }

  /** https://docs.pro.coinbase.com/#coinbase-accounts */
  public CoinbaseProWallet[] getCoinbaseAccounts() throws IOException {
    return decorateApiCall(
            () ->
                coinbasePro.getCoinbaseProWallets(
                    apiKey, digest, UnixTimestampFactory.INSTANCE.createValue(), passphrase))
        .withRateLimiter(rateLimiter(PRIVATE_REST_ENDPOINT_RATE_LIMITER))
        .call();
  }

  public CoinbaseProWalletAddress getCoinbaseAccountAddress(String accountId) throws IOException {
    return decorateApiCall(
            () ->
                coinbasePro.getCoinbaseProWalletAddress(
                    apiKey,
                    digest,
                    UnixTimestampFactory.INSTANCE.createValue(),
                    passphrase,
                    accountId))
        .withRateLimiter(rateLimiter(PRIVATE_REST_ENDPOINT_RATE_LIMITER))
        .call();
  }
}
