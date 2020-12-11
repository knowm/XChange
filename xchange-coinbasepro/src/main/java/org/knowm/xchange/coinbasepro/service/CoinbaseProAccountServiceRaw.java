package org.knowm.xchange.coinbasepro.service;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.coinbasepro.CoinbasePro;
import org.knowm.xchange.coinbasepro.CoinbaseProExchange;
import org.knowm.xchange.coinbasepro.dto.CoinbaseProException;
import org.knowm.xchange.coinbasepro.dto.CoinbaseProTransfers;
import org.knowm.xchange.coinbasepro.dto.account.*;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProAccount;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProAccountAddress;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProSendMoneyResponse;
import org.knowm.xchange.currency.Currency;
import si.mazi.rescu.SynchronizedValueFactory;

import static org.knowm.xchange.coinbasepro.CoinbaseProResilience.PRIVATE_PER_SECOND_RATE_LIMITER;

public class CoinbaseProAccountServiceRaw extends CoinbaseProBaseService {

  private final SynchronizedValueFactory<Long> nonceFactory;

  public CoinbaseProAccountServiceRaw(
      CoinbaseProExchange exchange, ResilienceRegistries resilienceRegistries) {

    super(exchange, resilienceRegistries);
    this.nonceFactory = exchange.getNonceFactory();
  }

  public org.knowm.xchange.coinbasepro.dto.account.CoinbaseProAccount[] getCoinbaseProAccountInfo()
      throws CoinbaseProException, IOException {
    return decorateApiCall(() -> coinbasePro.getAccounts(apiKey, digest, nonceFactory, passphrase))
        .withRetry(retry("getCoinbaseProAccountInfo"))
        .withRateLimiter(rateLimiter(PRIVATE_PER_SECOND_RATE_LIMITER))
        .call();
  }

  public CoinbaseProFee getCoinbaseProFees() throws CoinbaseProException, IOException {
    return decorateApiCall(() -> coinbasePro.getFees(apiKey, digest, nonceFactory, passphrase))
        .withRetry(retry("getCoinbaseProFees"))
        .withRateLimiter(rateLimiter(PRIVATE_PER_SECOND_RATE_LIMITER))
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
                    nonceFactory,
                    passphrase,
                    accountId))
        .withRetry(retry("sendMoney"))
        .withRateLimiter(rateLimiter(PRIVATE_PER_SECOND_RATE_LIMITER))
        .call();
  }

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
                    nonceFactory,
                    passphrase,
                    new CoinbaseProWithdrawFundsRequest(
                        amount,
                        currency.getCurrencyCode(),
                        address,
                        destinationTag,
                        noDestinationTag)))
        .withRetry(retry("withdrawCrypto"))
        .withRateLimiter(rateLimiter(PRIVATE_PER_SECOND_RATE_LIMITER))
        .call();
  }

  public List<Map> ledger(String accountId, Integer startingOrderId) throws IOException {
    return decorateApiCall(
            () ->
                coinbasePro.ledger(
                    apiKey, digest, nonceFactory, passphrase, accountId, startingOrderId))
        .withRetry(retry("ledger"))
        .withRateLimiter(rateLimiter(PRIVATE_PER_SECOND_RATE_LIMITER))
        .call();
  }

  /** @return the report id */
  public String requestNewReport(CoinbasePro.CoinbaseProReportRequest reportRequest)
      throws IOException {
    Map response =
        decorateApiCall(
                () ->
                    coinbasePro.createReport(
                        apiKey, digest, nonceFactory, passphrase, reportRequest))
            .withRetry(retry("requestNewReport"))
            .withRateLimiter(rateLimiter(PRIVATE_PER_SECOND_RATE_LIMITER))
            .call();

    return response.get("id").toString();
  }

  public Map report(String reportId) throws IOException {
    return decorateApiCall(
            () -> coinbasePro.getReport(apiKey, digest, nonceFactory, passphrase, reportId))
        .withRetry(retry("report"))
        .withRateLimiter(rateLimiter(PRIVATE_PER_SECOND_RATE_LIMITER))
        .call();
  }

  public CoinbaseProTransfers transfers(
      String type, String profileId, String before, String after, int limit) throws IOException {
    return decorateApiCall(
            () ->
                coinbasePro.transfers(
                    apiKey,
                    digest,
                    nonceFactory,
                    passphrase,
                    type,
                    profileId,
                    before,
                    after,
                    limit))
        .withRetry(retry("transfers"))
        .withRateLimiter(rateLimiter(PRIVATE_PER_SECOND_RATE_LIMITER))
        .call();
  }

  public CoinbaseProAccount[] getCoinbaseAccounts() throws IOException {
    return decorateApiCall(
            () -> coinbasePro.getCoinbaseProAccounts(apiKey, digest, nonceFactory, passphrase))
        .withRetry(retry("getCoinbaseAccounts"))
        .withRateLimiter(rateLimiter(PRIVATE_PER_SECOND_RATE_LIMITER))
        .call();
  }

  public CoinbaseProAccountAddress getCoinbaseAccountAddress(String accountId) throws IOException {
    return decorateApiCall(
            () ->
                coinbasePro.getCoinbaseProAccountAddress(
                    apiKey, digest, nonceFactory, passphrase, accountId))
        .withRetry(retry("getCoinbaseAccountAddress"))
        .withRateLimiter(rateLimiter(PRIVATE_PER_SECOND_RATE_LIMITER))
        .call();
  }

  public CoinbaseProWebsocketAuthData getWebsocketAuthData()
      throws CoinbaseProException, IOException {
    long timestamp = nonceFactory.createValue();
    JsonNode json =
        decorateApiCall(() -> coinbasePro.getVerifyId(apiKey, digest, timestamp, passphrase))
            .withRetry(retry("getWebsocketAuthData"))
            .withRateLimiter(rateLimiter(PRIVATE_PER_SECOND_RATE_LIMITER))
            .call();
    String userId = json.get("id").asText();
    CoinbaseProDigest coinbaseProDigest = (CoinbaseProDigest) digest;
    CoinbaseProWebsocketAuthData data =
        new CoinbaseProWebsocketAuthData(
            userId, apiKey, passphrase, coinbaseProDigest.getSignature(), timestamp);
    return data;
  }
}
