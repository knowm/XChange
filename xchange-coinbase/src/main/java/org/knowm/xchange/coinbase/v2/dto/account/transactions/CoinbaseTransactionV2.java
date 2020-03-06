package org.knowm.xchange.coinbase.v2.dto.account.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;
import lombok.Getter;
import org.knowm.xchange.coinbase.v2.dto.CoinbaseAmount;

@Getter
public class CoinbaseTransactionV2 {
  private final String id;
  private final String idem;
  private final String type;
  private final String status;
  private final CoinbaseAmount amount;
  private final CoinbaseAmount nativeAmount;
  private final String description;
  private final String createdAt;
  private final String updatedAt;
  private final String resource;
  private final String resourcePath;
  private final boolean instantExchange;
  private final CoinbaseTransactionV2Field buy;
  private final CoinbaseTransactionV2Field sell;
  private final CoinbaseTransactionV2Field trade;
  private final CoinbaseTransactionV2FromField from;
  private final CoinbaseTransactionV2ToField to;
  private final CoinbaseTransactionV2NetworkField network;
  private final CoinbaseTransactionV2Field application;
  private final CoinbaseTransactionDetails details;

  public CoinbaseTransactionV2(
      @JsonProperty("id") String id,
      @JsonProperty("idem") String idem,
      @JsonProperty("type") String type,
      @JsonProperty("status") String status,
      @JsonProperty("amount") CoinbaseAmount amount,
      @JsonProperty("native_amount") CoinbaseAmount nativeAmount,
      @JsonProperty("description") String description,
      @JsonProperty("created_at") String createdAt,
      @JsonProperty("updated_at") String updatedAt,
      @JsonProperty("resource") String resource,
      @JsonProperty("resource_path") String resourcePath,
      @JsonProperty("instant_exchange") boolean instantExchange,
      @JsonProperty("buy") CoinbaseTransactionV2Field buy,
      @JsonProperty("sell") CoinbaseTransactionV2Field sell,
      @JsonProperty("trade") CoinbaseTransactionV2Field trade,
      @JsonProperty("from") CoinbaseTransactionV2FromField from,
      @JsonProperty("to") CoinbaseTransactionV2ToField to,
      @JsonProperty("network") CoinbaseTransactionV2NetworkField network,
      @JsonProperty("application") CoinbaseTransactionV2Field application,
      @JsonProperty("details") CoinbaseTransactionDetails details) {
    this.id = id;
    this.idem = idem;
    this.type = type;
    this.status = status;
    this.amount = amount;
    this.nativeAmount = nativeAmount;
    this.description = description;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.resource = resource;
    this.resourcePath = resourcePath;
    this.instantExchange = instantExchange;
    this.buy = buy;
    this.sell = sell;
    this.trade = trade;
    this.from = from;
    this.to = to;
    this.network = network;
    this.application = application;
    this.details = details;
  }

  public ZonedDateTime getCreatedAt() {
    return ZonedDateTime.parse(createdAt);
  }

  public ZonedDateTime getUpdatedAt() {
    return ZonedDateTime.parse(updatedAt);
  }

  @Override
  public String toString() {
    return "{"
        + "\"id\":"
        + '\"'
        + id
        + '\"'
        + ",\"idem\":"
        + '\"'
        + idem
        + '\"'
        + ",\"type\":"
        + '\"'
        + type
        + '\"'
        + ",\"status\":"
        + '\"'
        + status
        + '\"'
        + ",\"amount\":"
        + '\"'
        + amount
        + '\"'
        + ",\"nativeAmount\":"
        + '\"'
        + nativeAmount
        + '\"'
        + ",\"description\":"
        + '\"'
        + description
        + '\"'
        + ",\"createdAt\":"
        + '\"'
        + createdAt
        + '\"'
        + ",\"updatedAt\":"
        + '\"'
        + updatedAt
        + '\"'
        + ",\"resource\":"
        + '\"'
        + resource
        + '\"'
        + ",\"resourcePath\":"
        + '\"'
        + resourcePath
        + '\"'
        + ",\"instantExchange\":"
        + '\"'
        + instantExchange
        + '\"'
        + ",\"buy\":"
        + buy
        + ",\"sell\":"
        + sell
        + ",\"trade\":"
        + trade
        + ",\"from\":"
        + from
        + ",\"to\":"
        + to
        + ",\"details\":"
        + details
        + ",\"network\":"
        + network
        + ",\"application\":"
        + application
        + '}';
  }

  @Getter
  public static class CoinbaseTransactionDetails {
    private final String title;
    private final String subtitle;
    private final String paymentMethodName;

    public CoinbaseTransactionDetails(
        @JsonProperty("title") String title,
        @JsonProperty("subtitle") String subtitle,
        @JsonProperty("payment_method_name") String paymentMethodName) {
      this.title = title;
      this.subtitle = subtitle;
      this.paymentMethodName = paymentMethodName;
    }

    @Override
    public String toString() {
      return "{"
          + "\"title\":"
          + '\"'
          + title
          + '\"'
          + ",\"subtitle\":"
          + '\"'
          + subtitle
          + '\"'
          + ",\"paymentMethodName\":"
          + '\"'
          + paymentMethodName
          + '\"'
          + "}";
    }
  }
}
