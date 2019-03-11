package org.knowm.xchange.abucoins.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/**
 * POJO representing the output JSON for the Abucoins <code>GET /deposits/history</code> endpoint.
 * Example:
 *
 * <p>
 *
 * <table>
 * <thead>
 * <th><td>Field</td><td>Description</td></th>
 * </thead>
 * <tbody>
 * <tr><td>deposit_id</td><td>Deposit transaction ID</td></tr>
 * <tr><td>currency</td><td>Deposit currency</td></tr>
 * <tr><td>date</td><td>Date of deposit</td></tr>
 * <tr><td>amount</td><td>Deposit amount</td></tr>
 * <tr><td>fee</td><td>Deposit fee</td></tr>
 * <tr><td>status</td><td>Deposit status</td></tr>
 * <tr><td>url</td><td>blockchain explorer url (null if not available)</td></tr>
 * </tbody>
 * </table>
 *
 * @author bryant_harris
 */
public class AbucoinsDepositHistory extends AbucoinsHistory {
  /** Deposit transaction ID */
  String depositID;

  public AbucoinsDepositHistory(
      @JsonProperty("deposit_id") String depositID,
      @JsonProperty("currency") String currency,
      @JsonProperty("date") String date,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("fee") BigDecimal fee,
      @JsonProperty("status") String status,
      @JsonProperty("url") String url,
      @JsonProperty("message") String message) {
    super(currency, date, amount, fee, status, url, message);
    this.depositID = depositID;
  }

  public String getDepositID() {
    return depositID;
  }

  @Override
  public String toString() {
    return "AbucoinsDepositHistory [depositID="
        + depositID
        + ", currency="
        + currency
        + ", date="
        + date
        + ", amount="
        + amount
        + ", fee="
        + fee
        + ", status="
        + status
        + ", url="
        + url
        + ", message="
        + message
        + "]";
  }
}
