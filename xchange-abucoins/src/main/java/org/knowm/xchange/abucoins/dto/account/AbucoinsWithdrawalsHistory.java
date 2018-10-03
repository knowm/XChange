package org.knowm.xchange.abucoins.dto.account;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.knowm.xchange.abucoins.service.AbucoinsArrayOrMessageDeserializer;

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
@JsonDeserialize(using = AbucoinsWithdrawalsHistory.AbucoinsWithdrawalsHistoryDeserializer.class)
public class AbucoinsWithdrawalsHistory {
  AbucoinsWithdrawalHistory[] history;

  public AbucoinsWithdrawalsHistory(AbucoinsWithdrawalHistory[] history) {
    this.history = history;
  }

  public AbucoinsWithdrawalHistory[] getHistory() {
    return history;
  }

  /**
   * Deserializer handles the success case (array json) as well as the error case (json object with
   * <em>message</em> field).
   *
   * @author bryant_harris
   */
  static class AbucoinsWithdrawalsHistoryDeserializer
      extends AbucoinsArrayOrMessageDeserializer<
          AbucoinsWithdrawalHistory, AbucoinsWithdrawalsHistory> {
    public AbucoinsWithdrawalsHistoryDeserializer() {
      super(AbucoinsWithdrawalHistory.class, AbucoinsWithdrawalsHistory.class);
    }
  }
}
