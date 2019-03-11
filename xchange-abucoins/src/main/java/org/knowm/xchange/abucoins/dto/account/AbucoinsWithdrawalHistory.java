package org.knowm.xchange.abucoins.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/**
 * POJO representing the output JSON for the Abucoins <code>GET /withdrawals/history</code>
 * endpoint. Example:
 *
 * <p>
 *
 * <table>
 * <thead>
 * <th><td>Field</td><td>Description</td></th>
 * </thead>
 * <tbody>
 * <tr><td>withdraw_id</td><td>Withdraw transaction ID</td></tr>
 * <tr><td>currency</td><td>Withdraw currency</td></tr>
 * <tr><td>date</td><td>Date of withdraw</td></tr>
 * <tr><td>amount</td><td>Withdraw amount</td></tr>
 * <tr><td>fee</td><td>Withdraw fee</td></tr>
 * <tr><td>status</td><td>Withdraw status</td></tr>
 * <tr><td>url</td><td>blockchain explorer url (null if not available)</td></tr>
 * </tbody>
 * </table>
 *
 * @author bryant_harris
 */
public class AbucoinsWithdrawalHistory extends AbucoinsHistory {
  /** Deposit transaction ID */
  String withdrawID;

  public AbucoinsWithdrawalHistory(
      @JsonProperty("withdraw_id") String withdrawID,
      @JsonProperty("currency") String currency,
      @JsonProperty("date") String date,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("fee") BigDecimal fee,
      @JsonProperty("status") String status,
      @JsonProperty("url") String url,
      @JsonProperty("message") String message) {
    super(currency, date, amount, fee, status, url, message);
    this.withdrawID = withdrawID;
  }

  public String getWithdrawID() {
    return withdrawID;
  }

  @Override
  public String toString() {
    return "AbucoinsDepositHistory [withdrawID="
        + withdrawID
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
