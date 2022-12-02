package org.knowm.xchange.coinbasepro.service;

import static org.knowm.xchange.coinbasepro.CoinbaseProResilience.PRIVATE_REST_ENDPOINT_RATE_LIMITER;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.coinbasepro.CoinbasePro;
import org.knowm.xchange.coinbasepro.CoinbaseProExchange;
import org.knowm.xchange.coinbasepro.dto.CoinbaseProException;
import org.knowm.xchange.coinbasepro.dto.CoinbaseProTransfers;
import org.knowm.xchange.coinbasepro.dto.account.CoinbaseProFee;
import org.knowm.xchange.coinbasepro.dto.account.CoinbaseProSendMoneyRequest;
import org.knowm.xchange.coinbasepro.dto.account.CoinbaseProWebsocketAuthData;
import org.knowm.xchange.coinbasepro.dto.account.CoinbaseProWithdrawCryptoResponse;
import org.knowm.xchange.coinbasepro.dto.account.CoinbaseProWithdrawFundsRequest;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProAccount;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProAccountAddress;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProSendMoneyResponse;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.utils.timestamp.UnixTimestampFactory;

public class CoinbaseProAccountServiceRaw extends CoinbaseProBaseService {

  public CoinbaseProAccountServiceRaw(
      CoinbaseProExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
  }

  public org.knowm.xchange.coinbasepro.dto.account.CoinbaseProAccount[] getCoinbaseProAccountInfo()
      throws CoinbaseProException, IOException {
    return decorateApiCall(
            () ->
                coinbasePro.getAccounts(
                    apiKey, digest, UnixTimestampFactory.INSTANCE.createValue(), passphrase))
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
  public List<Map<?, ?>> ledger(String accountId, String startingOrderId) throws IOException {
    return decorateApiCall(
            () ->
                coinbasePro.ledger(
                    apiKey,
                    digest,
                    UnixTimestampFactory.INSTANCE.createValue(),
                    passphrase,
                    accountId,
                    startingOrderId))
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
  public CoinbaseProTransfers transfers(String accountId, String profileId, int limit, String after)
      throws IOException {
    return decorateApiCall(
            () ->
                coinbasePro.transfers(
                    apiKey,
                    digest,
                    UnixTimestampFactory.INSTANCE.createValue(),
                    passphrase,
                    accountId,
                    profileId,
                    limit,
                    after))
        .withRateLimiter(rateLimiter(PRIVATE_REST_ENDPOINT_RATE_LIMITER))
        .call();
  }

  /** https://docs.pro.coinbase.com/#get-current-exchange-limits */
  public CoinbaseProTransfers transfers(
      String type, String profileId, String before, String after, int limit) throws IOException {
    return decorateApiCall(
            () ->
                coinbasePro.transfers(
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
  public CoinbaseProAccount[] getCoinbaseAccounts() throws IOException {
    return decorateApiCall(
            () ->
                coinbasePro.getCoinbaseProAccounts(
                    apiKey, digest, UnixTimestampFactory.INSTANCE.createValue(), passphrase))
        .withRateLimiter(rateLimiter(PRIVATE_REST_ENDPOINT_RATE_LIMITER))
        .call();
  }

  public CoinbaseProAccountAddress getCoinbaseAccountAddress(String accountId) throws IOException {
    return decorateApiCall(
            () ->
                coinbasePro.getCoinbaseProAccountAddress(
                    apiKey,
                    digest,
                    UnixTimestampFactory.INSTANCE.createValue(),
                    passphrase,
                    accountId))
        .withRateLimiter(rateLimiter(PRIVATE_REST_ENDPOINT_RATE_LIMITER))
        .call();
  }

  public CoinbaseProWebsocketAuthData getWebsocketAuthData()
      throws CoinbaseProException, IOException {
    long timestamp = UnixTimestampFactory.INSTANCE.createValue();
    JsonNode json =
        decorateApiCall(() -> coinbasePro.getVerifyId(apiKey, digest, timestamp, passphrase))
            .withRateLimiter(rateLimiter(PRIVATE_REST_ENDPOINT_RATE_LIMITER))
            .call();
    String userId = json.get("id").asText();
    CoinbaseProDigest coinbaseProDigest = (CoinbaseProDigest) digest;
    return new CoinbaseProWebsocketAuthData(
        userId, apiKey, passphrase, coinbaseProDigest.getSignature(), timestamp);
  }
}
