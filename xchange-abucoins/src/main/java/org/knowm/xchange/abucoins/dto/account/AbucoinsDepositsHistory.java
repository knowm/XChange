package org.knowm.xchange.abucoins.dto.account;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.knowm.xchange.abucoins.service.AbucoinsArrayOrMessageDeserializer;

/**
 * POJO representing the output JSON for the Abucoins <code>GET /deposits/historycrypto</code>
 * endpoint. Example:
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
@JsonDeserialize(using = AbucoinsDepositsHistory.AbucoinsDepositsHistoryDeserializer.class)
public class AbucoinsDepositsHistory {
  AbucoinsDepositHistory[] history;

  public AbucoinsDepositsHistory(AbucoinsDepositHistory[] history) {
    this.history = history;
  }

  public AbucoinsDepositHistory[] getHistory() {
    return history;
  }

  /**
   * Deserializer handles the success case (array json) as well as the error case (json object with
   * <em>message</em> field).
   *
   * @author bryant_harris
   */
  static class AbucoinsDepositsHistoryDeserializer
      extends AbucoinsArrayOrMessageDeserializer<AbucoinsDepositHistory, AbucoinsDepositsHistory> {
    public AbucoinsDepositsHistoryDeserializer() {
      super(AbucoinsDepositHistory.class, AbucoinsDepositsHistory.class);
    }
  }
}
