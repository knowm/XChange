package org.knowm.xchange.abucoins.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/**
 * POJO representing the input JSON for the Abucoins <code>POST /withdrawals/crypto</code> endpoint.
 * Example: <code><pre>
 * {
 *    "amount": 10.00,
 *    "currency": "BTC",
 *    "method": "bitcoin",
 *    "address": "0x5ad5769cd04681FeD900BCE3DDc877B50E83d469"
 * }
 * </pre></code>
 */
public class AbucoinsCryptoWithdrawalRequest {
  /** The amount to withdraw */
  @JsonProperty("amount")
  BigDecimal amount;

  /** The type of currency */
  @JsonProperty("currency")
  String currency;

  /** Payment method */
  @JsonProperty("method")
  String method;

  /** A crypto address of the recipient */
  @JsonProperty("address")
  String address;

  /** Tag/PaymentId/Memo of the recipient */
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("tag")
  String tag;

  /**
   * @param amount The amount to withdraw
   * @param currency The type of currency
   * @param method Payment method
   * @param address A crypto address of the recipient
   * @param tag Tag/PaymentId/Memo of the recipient
   */
  public AbucoinsCryptoWithdrawalRequest(
      BigDecimal amount, String currency, String method, String address, String tag) {
    this.amount = amount;
    this.currency = currency;
    this.method = method;
    this.address = address;
    this.tag = tag;
  }

  @Override
  public String toString() {
    return "AbucoinsCryptoWithDrawalRequest [amount="
        + amount
        + ", currency="
        + currency
        + ", method="
        + method
        + ", address="
        + address
        + ", tag="
        + tag
        + "]";
  }
}
