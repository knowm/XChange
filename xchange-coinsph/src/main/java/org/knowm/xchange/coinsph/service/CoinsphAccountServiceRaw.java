package org.knowm.xchange.coinsph.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.coinsph.CoinsphExchange;
import org.knowm.xchange.coinsph.dto.CoinsphException;
import org.knowm.xchange.coinsph.dto.account.*;
import org.knowm.xchange.currency.Currency;

public class CoinsphAccountServiceRaw extends CoinsphBaseService {

  protected CoinsphAccountServiceRaw(
      CoinsphExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
  }

  public CoinsphAccount getCoinsphAccount() throws IOException, CoinsphException {
    return decorateApiCall(
            () ->
                coinsphAuthenticated.getAccount(
                    apiKey, timestampFactory, signatureCreator, exchange.getRecvWindow()))
        .call();
  }

  public List<CoinsphTradeFee> getCoinsphTradeFees(String symbol)
      throws IOException, CoinsphException {
    return decorateApiCall(
            () ->
                coinsphAuthenticated.getTradeFee(
                    apiKey, timestampFactory, signatureCreator, symbol, exchange.getRecvWindow()))
        .call();
  }

  public List<CoinsphTradeFee> getCoinsphTradeFees() throws IOException, CoinsphException {
    return getCoinsphTradeFees(null); // Call with null symbol to get all
  }

  // User Data Stream methods
  public CoinsphListenKey createCoinsphListenKey() throws IOException, CoinsphException {
    return decorateApiCall(() -> coinsphAuthenticated.createListenKey(apiKey)).call();
  }

  public void keepAliveCoinsphListenKey(String listenKey) throws IOException, CoinsphException {
    decorateApiCall(() -> coinsphAuthenticated.keepAliveListenKey(apiKey, listenKey)).call();
  }

  public void closeCoinsphListenKey(String listenKey) throws IOException, CoinsphException {
    decorateApiCall(() -> coinsphAuthenticated.closeListenKey(apiKey, listenKey)).call();
  }

  /**
   * Request a withdrawal from Coins.ph
   *
   * @param coin Currency code (e.g., "BTC")
   * @param network Network to use for withdrawal (e.g., "BTC", "ETH", etc.)
   * @param address Destination address
   * @param amount Amount to withdraw
   * @param addressTag Address tag/memo for currencies that require it (optional)
   * @return Withdrawal ID
   * @throws IOException
   * @throws CoinsphException
   */
  public CoinsphWithdrawal withdraw(
      String coin, String network, String address, BigDecimal amount, String addressTag)
      throws IOException, CoinsphException {
    return decorateApiCall(
            () ->
                coinsphAuthenticated.withdraw(
                    apiKey,
                    timestampFactory,
                    signatureCreator,
                    coin,
                    network,
                    address,
                    amount,
                    addressTag,
                    exchange.getRecvWindow()))
        .call();
  }

  /**
   * Request a deposit address for a specific currency
   *
   * @param coin Currency code (e.g., "BTC")
   * @param network Network to use (optional, if not specified, default network will be used)
   * @return Deposit address
   * @throws IOException
   * @throws CoinsphException
   */
  public CoinsphDepositAddress requestDepositAddress(String coin, String network)
      throws IOException, CoinsphException {
    return decorateApiCall(
            () ->
                coinsphAuthenticated.getDepositAddress(
                    apiKey,
                    timestampFactory,
                    signatureCreator,
                    coin,
                    network,
                    exchange.getRecvWindow()))
        .call();
  }

  /**
   * Get deposit history
   *
   * @param coin Filter by currency (optional)
   * @param startTime Start time in milliseconds (optional)
   * @param endTime End time in milliseconds (optional)
   * @param limit Maximum number of records to return (optional)
   * @return List of deposit records
   * @throws IOException
   * @throws CoinsphException
   */
  public List<CoinsphDepositRecord> getDepositHistory(
      String coin, Long startTime, Long endTime, Integer limit)
      throws IOException, CoinsphException {
    return decorateApiCall(
            () ->
                coinsphAuthenticated.getDepositHistory(
                    apiKey,
                    timestampFactory,
                    signatureCreator,
                    coin,
                    startTime,
                    endTime,
                    limit,
                    exchange.getRecvWindow()))
        .call();
  }

  /**
   * Get withdrawal history
   *
   * @param coin Filter by currency (optional)
   * @param withdrawOrderId Filter by client order ID (optional)
   * @param startTime Start time in milliseconds (optional)
   * @param endTime End time in milliseconds (optional)
   * @param limit Maximum number of records to return (optional)
   * @return List of withdrawal records
   * @throws IOException
   * @throws CoinsphException
   */
  public List<CoinsphWithdrawalRecord> getWithdrawalHistory(
      String coin, String withdrawOrderId, Long startTime, Long endTime, Integer limit)
      throws IOException, CoinsphException {
    return decorateApiCall(
            () ->
                coinsphAuthenticated.getWithdrawalHistory(
                    apiKey,
                    timestampFactory,
                    signatureCreator,
                    coin,
                    withdrawOrderId,
                    startTime,
                    endTime,
                    limit,
                    exchange.getRecvWindow()))
        .call();
  }

  /**
   * Get combined funding history (deposits, withdrawals, and fiat transactions)
   *
   * @param includeDeposits Whether to include deposit records
   * @param includeWithdrawals Whether to include withdrawal records
   * @param currency Currency filter (optional)
   * @return List of funding records
   * @throws IOException
   * @throws CoinsphException
   */
  public List<CoinsphFundingRecord> getFundingHistory(
      boolean includeDeposits, boolean includeWithdrawals, Currency currency)
      throws IOException, CoinsphException {
    List<CoinsphFundingRecord> fundingRecords = new ArrayList<>();

    // Convert parameters
    String coin = currency != null ? currency.getCurrencyCode() : null;
    Long startTime = null;
    Long endTime = null;
    Integer limit = null;

    // Get deposit history if requested
    if (includeDeposits) {
      List<CoinsphDepositRecord> depositRecords =
          getDepositHistory(coin, startTime, endTime, limit);
      for (CoinsphDepositRecord depositRecord : depositRecords) {
        fundingRecords.add(new CoinsphFundingRecord(depositRecord));
      }
    }

    // Get withdrawal history if requested
    if (includeWithdrawals) {
      List<CoinsphWithdrawalRecord> withdrawalRecords =
          getWithdrawalHistory(coin, null, startTime, endTime, limit);
      for (CoinsphWithdrawalRecord withdrawalRecord : withdrawalRecords) {
        fundingRecords.add(new CoinsphFundingRecord(withdrawalRecord));
      }
    }

    // Always get fiat history (both cash in and cash out transactions)
    String fiatCurrency = coin; // Use the currency filter for fiat currency

    // Get both cash in (1) and cash out (-1) transactions
    try {
      CoinsphFiatResponse<List<CoinsphFiatHistory>> cashInResponse =
          getFiatHistory(CoinsphFiatHistoryRequest.builder().fiatCurrency(fiatCurrency).build());
      if (cashInResponse != null && cashInResponse.getData() != null) {
        for (CoinsphFiatHistory fiatHistory : cashInResponse.getData()) {
          fundingRecords.add(new CoinsphFundingRecord(fiatHistory));
        }
      }
    } catch (Exception e) {
      // Log and continue if fiat cash in history fails
      // This allows the method to still return other funding records
    }

    // Sort by timestamp (newest first)
    fundingRecords.sort((r1, r2) -> r2.getTimestamp().compareTo(r1.getTimestamp()));

    // Apply limit if specified
    if (limit != null && fundingRecords.size() > limit) {
      return fundingRecords.subList(0, limit);
    }

    return fundingRecords;
  }

  // Fiat API methods
  // =================================================================================================

  /**
   * Get supported fiat channels for cash out operations
   *
   * @param currency The currency for which to get supported channels
   * @param transactionType Transaction type (-1 for cash out)
   * @return List of supported fiat channels
   * @throws IOException
   * @throws CoinsphException
   */
  public CoinsphFiatResponse<List<CoinsphFiatChannel>> getSupportedFiatChannels(
      String currency,
      int transactionType,
      String transactionChannel,
      String transactionChannelSubject,
      BigDecimal amount)
      throws IOException, CoinsphException {
    return decorateApiCall(
            () ->
                coinsphAuthenticated.getSupportedFiatChannels(
                    apiKey,
                    timestampFactory,
                    signatureCreator,
                    currency,
                    transactionType,
                    transactionChannel,
                    transactionChannelSubject,
                    amount,
                    exchange.getRecvWindow()))
        .call();
  }

  /**
   * Create a cash out request
   *
   * @param request Cash out request details
   * @return Cash out response
   * @throws IOException
   * @throws CoinsphException
   */
  public CoinsphCashOutResponse cashOut(CoinsphCashOutRequest request)
      throws IOException, CoinsphException {
    return decorateApiCall(
            () ->
                coinsphAuthenticated.cashOut(
                    apiKey, timestampFactory, signatureCreator, request, exchange.getRecvWindow()))
        .call()
        .getData();
  }

  /**
   * Get fiat transaction history
   *
   * @return Fiat transaction history response
   * @throws IOException
   * @throws CoinsphException
   */
  public CoinsphFiatResponse<List<CoinsphFiatHistory>> getFiatHistory(
      CoinsphFiatHistoryRequest fiatHistoryRequest) throws IOException, CoinsphException {
    return decorateApiCall(
            () ->
                coinsphAuthenticated.fiatHistory(
                    apiKey,
                    timestampFactory,
                    signatureCreator,
                    fiatHistoryRequest,
                    exchange.getRecvWindow()))
        .call();
  }

  /**
   * Find the first available channel for a given currency and transaction type
   *
   * @param currency The currency to search for
   * @param transactionType Transaction type (-1 for cash out)
   * @return Optional of the first available channel
   * @throws IOException
   * @throws CoinsphException
   */
  protected Optional<CoinsphFiatChannel> findFirstAvailableChannel(
      String currency,
      int transactionType,
      String transactionChannel,
      String transactionSubject,
      BigDecimal amount)
      throws IOException, CoinsphException {
    List<CoinsphFiatChannel> channels =
        getSupportedFiatChannels(
                currency, transactionType, transactionChannel, transactionSubject, amount)
            .getData();
    return channels.stream()
        .filter(channel -> channel.getStatus() == 1) // Status 1 means available
        .findFirst();
  }
}
