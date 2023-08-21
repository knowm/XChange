package org.knowm.xchange.coinbasepro.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class CoinbaseProCurrency {

  private final String id;
  private final String name;
  private final BigDecimal minSize;
  private final String status;
  private final String message;
  private final BigDecimal maxPrecision;
  private final List<String> convertibleTo;
  private final CoinbaseProCurrencyDetails details;
  private final String defaultNetwork;
  private final List<CoinbaseCurrencySupportedNetwork> supportedNetworks;

  public CoinbaseProCurrency(
      @JsonProperty("id") String id,
      @JsonProperty("name") String name,
      @JsonProperty("min_size") BigDecimal minSize,
      @JsonProperty("status") String status,
      @JsonProperty("message") String message,
      @JsonProperty("max_precision") BigDecimal maxPrecision,
      @JsonProperty("convertible_to") List<String> convertibleTo,
      @JsonProperty("details") CoinbaseProCurrencyDetails details,
      @JsonProperty("default_network") String defaultNetwork,
      @JsonProperty("supported_networks") List<CoinbaseCurrencySupportedNetwork> supportedNetworks
  ) {
    this.id = id;
    this.name = name;
    this.minSize = minSize;
    this.status = status;
    this.message = message;
    this.maxPrecision = maxPrecision;
    this.convertibleTo = convertibleTo;
    this.details = details;
    this.defaultNetwork = defaultNetwork;
    this.supportedNetworks = supportedNetworks;
  }

  @Getter
  @ToString
  public static class CoinbaseProCurrencyDetails {

    private final CoinbaseProCurrencyDetailsType type;
    private final String symbol;
    private final int networkConfirmations;
    private final int sortOrder;
    private final String cryptoAddressLink;
    private final String cryptoTransactionLink;
    private final List<String> pushPaymentMethods;
    private final List<String> groupTypes;
    private final String displayName;
    private final BigDecimal processingTimeSeconds;
    private final BigDecimal minWithdrawalAmount;
    private final BigDecimal maxWithdrawalAmount;

    public CoinbaseProCurrencyDetails(
        @JsonProperty("type") CoinbaseProCurrencyDetailsType type,
        @JsonProperty("symbol") String symbol,
        @JsonProperty("network_confirmations") int networkConfirmations,
        @JsonProperty("sort_order") int sortOrder,
        @JsonProperty("crypto_address_link") String cryptoAddressLink,
        @JsonProperty("crypto_transaction_link") String cryptoTransactionLink,
        @JsonProperty("push_payment_methods") List<String> pushPaymentMethods,
        @JsonProperty("group_types") List<String> groupTypes,
        @JsonProperty("display_name") String displayName,
        @JsonProperty("processing_time_seconds") BigDecimal processingTimeSeconds,
        @JsonProperty("min_withdrawal_amount") BigDecimal minWithdrawalAmount,
        @JsonProperty("max_withdrawal_amount") BigDecimal maxWithdrawalAmount) {
      this.type = type;
      this.symbol = symbol;
      this.networkConfirmations = networkConfirmations;
      this.sortOrder = sortOrder;
      this.cryptoAddressLink = cryptoAddressLink;
      this.cryptoTransactionLink = cryptoTransactionLink;
      this.pushPaymentMethods = pushPaymentMethods;
      this.groupTypes = groupTypes;
      this.displayName = displayName;
      this.processingTimeSeconds = processingTimeSeconds;
      this.minWithdrawalAmount = minWithdrawalAmount;
      this.maxWithdrawalAmount = maxWithdrawalAmount;
    }

    public enum CoinbaseProCurrencyDetailsType {
      crypto,
      fiat
    }
  }

  @Getter
  @ToString
  public static class CoinbaseCurrencySupportedNetwork {
    private final String id;
    private final String name;
    private final String status;
    private final String contractAddress;
    private final String cryptoAddressLink;
    private final String cryptoTransactionLink;
    private final BigDecimal minWithdrawalAmount;
    private final BigDecimal maxWithdrawalAmount;
    private final Integer networkConfirmations;
    private final BigDecimal processingTimeSeconds;

    public CoinbaseCurrencySupportedNetwork(
        @JsonProperty("id") String id,
        @JsonProperty("name") String name,
        @JsonProperty("status") String status,
        @JsonProperty("contract_address") String contractAddress,
        @JsonProperty("crypto_address_link") String cryptoAddressLink,
        @JsonProperty("crypto_transaction_link") String cryptoTransactionLink,
        @JsonProperty("min_withdrawal_amount") BigDecimal minWithdrawalAmount,
        @JsonProperty("max_withdrawal_amount") BigDecimal maxWithdrawalAmount,
        @JsonProperty("network_confirmations") Integer networkConfirmations,
        @JsonProperty("processing_time_seconds") BigDecimal processingTimeSeconds) {
      this.id = id;
      this.name = name;
      this.status = status;
      this.contractAddress = contractAddress;
      this.cryptoAddressLink = cryptoAddressLink;
      this.cryptoTransactionLink = cryptoTransactionLink;
      this.minWithdrawalAmount = minWithdrawalAmount;
      this.maxWithdrawalAmount = maxWithdrawalAmount;
      this.networkConfirmations = networkConfirmations;
      this.processingTimeSeconds = processingTimeSeconds;
    }
  }
}
