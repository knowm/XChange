package org.knowm.xchange.coinbasepro.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

public class CoinbaseProCurrencyDetails {

  private final String symbol;
  private final int networkConfirmations;
  private final List<String> pushPaymentMethods;
  private final List<String> groupTypes;
  private final String cryptoAddressLink;
  private final String type;
  private final int sortOrder;
  private final String cryptoTransactionLink;
  private final BigDecimal minWithdrawalAmount;

  public CoinbaseProCurrencyDetails(
      @JsonProperty("symbol") String symbol,
      @JsonProperty("network_confirmations") int networkConfirmations,
      @JsonProperty("push_payment_methods") List<String> pushPaymentMethods,
      @JsonProperty("group_types") List<String> groupTypes,
      @JsonProperty("crypto_address_link") String cryptoAddressLink,
      @JsonProperty("type") String type,
      @JsonProperty("sort_order") int sortOrder,
      @JsonProperty("crypto_transaction_link") String cryptoTransactionLink,
      @JsonProperty("min_withdrawal_amount") BigDecimal minWithdrawalAmount) {
    this.symbol = symbol;
    this.networkConfirmations = networkConfirmations;
    this.pushPaymentMethods = pushPaymentMethods;
    this.groupTypes = groupTypes;
    this.cryptoAddressLink = cryptoAddressLink;
    this.type = type;
    this.sortOrder = sortOrder;
    this.cryptoTransactionLink = cryptoTransactionLink;
    this.minWithdrawalAmount = minWithdrawalAmount;
  }

  public String getSymbol() {
    return symbol;
  }

  public int getNetworkConfirmations() {
    return networkConfirmations;
  }

  public List<String> getPushPaymentMethods() {
    return pushPaymentMethods;
  }

  public List<String> getGroupTypes() {
    return groupTypes;
  }

  public String getCryptoAddressLink() {
    return cryptoAddressLink;
  }

  public String getType() {
    return type;
  }

  public int getSortOrder() {
    return sortOrder;
  }

  public String getCryptoTransactionLink() {
    return cryptoTransactionLink;
  }

  public BigDecimal getMinWithdrawalAmount() {
    return minWithdrawalAmount;
  }

  @Override
  public String toString() {
    return "CoinbaseProCurrencyDetails{"
        + "symbol = '"
        + symbol
        + '\''
        + ",network_confirmations = '"
        + networkConfirmations
        + '\''
        + ",push_payment_methods = '"
        + pushPaymentMethods
        + '\''
        + ",group_types = '"
        + groupTypes
        + '\''
        + ",crypto_address_link = '"
        + cryptoAddressLink
        + '\''
        + ",type = '"
        + type
        + '\''
        + ",sort_order = '"
        + sortOrder
        + '\''
        + ",crypto_transaction_link = '"
        + cryptoTransactionLink
        + '\''
        + ",min_withdrawal_amount = '"
        + minWithdrawalAmount
        + '\''
        + "}";
  }
}
