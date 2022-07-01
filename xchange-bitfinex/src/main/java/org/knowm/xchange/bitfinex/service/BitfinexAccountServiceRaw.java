package org.knowm.xchange.bitfinex.service;

import static org.knowm.xchange.bitfinex.BitfinexResilience.BITFINEX_RATE_LIMITER;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.bitfinex.BitfinexExchange;
import org.knowm.xchange.bitfinex.dto.BitfinexException;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexAccountFeesResponse;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexBalanceHistoryRequest;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexBalanceHistoryResponse;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexBalancesRequest;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexBalancesResponse;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexDepositAddressRequest;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexDepositAddressResponse;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexDepositWithdrawalHistoryRequest;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexDepositWithdrawalHistoryResponse;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexMarginInfosRequest;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexMarginInfosResponse;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexTradingFeeResponse;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexTradingFeesRequest;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexWithdrawalRequest;
import org.knowm.xchange.bitfinex.v1.dto.account.BitfinexWithdrawalResponse;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexNonceOnlyRequest;
import org.knowm.xchange.bitfinex.v2.dto.EmptyRequest;
import org.knowm.xchange.bitfinex.v2.dto.account.LedgerEntry;
import org.knowm.xchange.bitfinex.v2.dto.account.LedgerRequest;
import org.knowm.xchange.bitfinex.v2.dto.account.Movement;
import org.knowm.xchange.bitfinex.v2.dto.account.TransferBetweenWalletsRequest;
import org.knowm.xchange.bitfinex.v2.dto.account.TransferBetweenWalletsResponse;
import org.knowm.xchange.bitfinex.v2.dto.account.UpdateCollateralDerivativePositionRequest;
import org.knowm.xchange.bitfinex.v2.dto.account.Wallet;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.exceptions.ExchangeException;

public class BitfinexAccountServiceRaw extends BitfinexBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitfinexAccountServiceRaw(
      BitfinexExchange exchange, ResilienceRegistries resilienceRegistries) {

    super(exchange, resilienceRegistries);
  }

  public BitfinexTradingFeeResponse[] getBitfinexDynamicTradingFees() throws IOException {
    try {
      return decorateApiCall(
              () ->
                  bitfinex.tradingFees(
                      apiKey,
                      payloadCreator,
                      signatureCreator,
                      new BitfinexTradingFeesRequest(
                          String.valueOf(exchange.getNonceFactory().createValue()))))
          .withRetry(retry("account-tradingFees"))
          .withRateLimiter(rateLimiter(BITFINEX_RATE_LIMITER))
          .call();
    } catch (BitfinexException e) {
      throw new ExchangeException(e);
    }
  }

  public BitfinexBalancesResponse[] getBitfinexAccountInfo() throws IOException {
    return decorateApiCall(
            () ->
                bitfinex.balances(
                    apiKey,
                    payloadCreator,
                    signatureCreator,
                    new BitfinexBalancesRequest(
                        String.valueOf(exchange.getNonceFactory().createValue()))))
        .withRetry(retry("account-accountInfo"))
        .withRateLimiter(rateLimiter(BITFINEX_RATE_LIMITER))
        .call();
  }

  public BitfinexMarginInfosResponse[] getBitfinexMarginInfos() throws IOException {
    return decorateApiCall(
            () ->
                bitfinex.marginInfos(
                    apiKey,
                    payloadCreator,
                    signatureCreator,
                    new BitfinexMarginInfosRequest(
                        String.valueOf(exchange.getNonceFactory().createValue()))))
        .withRetry(retry("account-marginInfo"))
        .withRateLimiter(rateLimiter(BITFINEX_RATE_LIMITER))
        .call();
  }

  public BitfinexDepositWithdrawalHistoryResponse[] getDepositWithdrawalHistory(
      String currency, String method, Date since, Date until, Integer limit) throws IOException {
    BitfinexDepositWithdrawalHistoryRequest request =
        new BitfinexDepositWithdrawalHistoryRequest(
            String.valueOf(exchange.getNonceFactory().createValue()),
            currency,
            method,
            since,
            until,
            limit);
    return decorateApiCall(
            () ->
                bitfinex.depositWithdrawalHistory(
                    apiKey, payloadCreator, signatureCreator, request))
        .withRetry(retry("account-withdrawalHistory"))
        .withRateLimiter(rateLimiter(BITFINEX_RATE_LIMITER))
        .call();
  }

  public String withdraw(
      String withdrawType, String walletSelected, BigDecimal amount, String address)
      throws IOException {
    return withdraw(withdrawType, walletSelected, amount, address, null);
  }

  public String withdraw(
      String withdrawType,
      String walletSelected,
      BigDecimal amount,
      String address,
      String tagOrPaymentId)
      throws IOException {
    return withdraw(withdrawType, walletSelected, amount, address, tagOrPaymentId, null);
  }

  public String withdraw(
      String withdrawType,
      String walletSelected,
      BigDecimal amount,
      String address,
      String tagOrPaymentId,
      String currency)
      throws IOException {

    BitfinexWithdrawalRequest req =
        new BitfinexWithdrawalRequest(
            String.valueOf(exchange.getNonceFactory().createValue()),
            withdrawType,
            walletSelected,
            amount,
            address,
            tagOrPaymentId);
    req.setCurrency(currency);
    BitfinexWithdrawalResponse[] withdrawResponse =
        decorateApiCall(() -> bitfinex.withdraw(apiKey, payloadCreator, signatureCreator, req))
            .withRateLimiter(rateLimiter(BITFINEX_RATE_LIMITER))
            .call();
    if ("error".equalsIgnoreCase(withdrawResponse[0].getStatus())) {
      throw new ExchangeException(withdrawResponse[0].getMessage());
    }
    return withdrawResponse[0].getWithdrawalId();
  }

  public BitfinexDepositAddressResponse requestDepositAddressRaw(String currency)
      throws IOException {
    String type = "unknown";
    if ("BTC".equalsIgnoreCase(currency)) {
      type = "bitcoin";
    } else if ("LTC".equalsIgnoreCase(currency)) {
      type = "litecoin";
    } else if ("ETH".equalsIgnoreCase(currency)) {
      type = "ethereum";
    } else if ("ETC".equalsIgnoreCase(currency)) {
      type = "ethereumc";
    } else if ("CLO".equalsIgnoreCase(currency)) {
      type = "clo";
    } else if ("IOT".equalsIgnoreCase(currency)) {
      type = "iota";
    } else if ("BCH".equalsIgnoreCase(currency)) {
      type = "bab";
    } else if ("BTG".equalsIgnoreCase(currency)) {
      type = "bgold";
    } else if ("DASH".equalsIgnoreCase(currency)) {
      type = "dash";
    } else if ("EOS".equalsIgnoreCase(currency)) {
      type = "eos";
    } else if ("XMR".equalsIgnoreCase(currency)) {
      type = "monero";
    } else if ("NEO".equalsIgnoreCase(currency)) {
      type = "neo";
    } else if ("XRP".equalsIgnoreCase(currency)) {
      type = "ripple";
    } else if ("XLM".equalsIgnoreCase(currency)) {
      type = "xlm";
    } else if ("TRX".equalsIgnoreCase(currency)) {
      type = "trx";
    } else if ("ZEC".equalsIgnoreCase(currency)) {
      type = "zcash";
    }
    final String finalType = type;
    return decorateApiCall(
            () ->
                bitfinex.requestDeposit(
                    apiKey,
                    payloadCreator,
                    signatureCreator,
                    new BitfinexDepositAddressRequest(
                        String.valueOf(exchange.getNonceFactory().createValue()),
                        finalType,
                        "exchange",
                        0)))
        .withRateLimiter(rateLimiter(BITFINEX_RATE_LIMITER))
        .call();
  }

  public BitfinexAccountFeesResponse getAccountFees() throws IOException {
    return decorateApiCall(
            () ->
                bitfinex.accountFees(
                    apiKey,
                    payloadCreator,
                    signatureCreator,
                    new BitfinexNonceOnlyRequest(
                        "/v1/account_fees",
                        String.valueOf(exchange.getNonceFactory().createValue()))))
        .withRetry(retry("account-accountFees"))
        .withRateLimiter(rateLimiter(BITFINEX_RATE_LIMITER))
        .call();
  }

  public BitfinexBalanceHistoryResponse[] getBitfinexBalanceHistory(
      String currency, String wallet, Long since, Long until, int limit) throws IOException {
    return decorateApiCall(
            () ->
                bitfinex.balanceHistory(
                    apiKey,
                    payloadCreator,
                    signatureCreator,
                    new BitfinexBalanceHistoryRequest(
                        String.valueOf(exchange.getNonceFactory().createValue()),
                        currency,
                        since,
                        until,
                        limit,
                        wallet)))
        .withRetry(retry("account-balanceHistory"))
        .withRateLimiter(rateLimiter(BITFINEX_RATE_LIMITER))
        .call();
  }

  public List<LedgerEntry> getLedgerEntries(
      String currency, Long startTimeMillis, Long endTimeMillis, Long limit, Long category)
      throws IOException {
    if (StringUtils.isBlank(currency)) {
      return decorateApiCall(
              () ->
                  bitfinexV2.getLedgerEntries(
                      exchange.getNonceFactory(),
                      apiKey,
                      signatureV2,
                      startTimeMillis,
                      endTimeMillis,
                      limit,
                      new LedgerRequest(category)))
          .withRetry(retry("account-ledgerEntries"))
          .withRateLimiter(rateLimiter(BITFINEX_RATE_LIMITER))
          .call();
    }
    return decorateApiCall(
            () ->
                bitfinexV2.getLedgerEntries(
                    exchange.getNonceFactory(),
                    apiKey,
                    signatureV2,
                    currency,
                    startTimeMillis,
                    endTimeMillis,
                    limit,
                    new LedgerRequest(category)))
        .withRetry(retry("account-ledgerEntries"))
        .withRateLimiter(rateLimiter(BITFINEX_RATE_LIMITER))
        .call();
  }

  public List<Movement> getMovementHistory(
      String currency, Long startTimeMillis, Long endTimeMillis, Integer limit) throws IOException {
    if (StringUtils.isBlank(currency)) {
      return decorateApiCall(
              () ->
                  bitfinexV2.getMovementsHistory(
                      exchange.getNonceFactory(),
                      apiKey,
                      signatureV2,
                      startTimeMillis,
                      endTimeMillis,
                      limit,
                      EmptyRequest.INSTANCE))
          .withRetry(retry("account-movementHistory"))
          .withRateLimiter(rateLimiter(BITFINEX_RATE_LIMITER))
          .call();
    }

    return decorateApiCall(
            () ->
                bitfinexV2.getMovementsHistory(
                    exchange.getNonceFactory(),
                    apiKey,
                    signatureV2,
                    currency,
                    startTimeMillis,
                    endTimeMillis,
                    limit,
                    EmptyRequest.INSTANCE))
        .withRetry(retry("account-movementHistory"))
        .withRateLimiter(rateLimiter(BITFINEX_RATE_LIMITER))
        .call();
  }

  public List<Wallet> getWallets() throws IOException {
    return decorateApiCall(
            () ->
                bitfinexV2.getWallets(
                    exchange.getNonceFactory(), apiKey, signatureV2, EmptyRequest.INSTANCE))
        .withRetry(retry("account-wallets"))
        .withRateLimiter(rateLimiter(BITFINEX_RATE_LIMITER))
        .call();
  }

  public TransferBetweenWalletsResponse transferBetweenWallets(TransferBetweenWalletsRequest req)
      throws IOException {
    return decorateApiCall(
            () ->
                bitfinexV2.transferBetweenWallets(
                    exchange.getNonceFactory(), apiKey, signatureV2, req))
        .withRateLimiter(rateLimiter(BITFINEX_RATE_LIMITER))
        .call();
  }

  public void updateCollateralDerivativePosition(UpdateCollateralDerivativePositionRequest req)
      throws IOException {
    decorateApiCall(
            () ->
                bitfinexV2.updateCollateralDerivativePosition(
                    exchange.getNonceFactory(), apiKey, signatureV2, req))
        .withRateLimiter(rateLimiter(BITFINEX_RATE_LIMITER))
        .call();
  }
}
