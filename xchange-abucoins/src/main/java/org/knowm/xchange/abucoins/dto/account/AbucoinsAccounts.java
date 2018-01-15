package org.knowm.xchange.abucoins.dto.account;

import java.util.Arrays;

import org.knowm.xchange.abucoins.service.AbucoinsArrayOrMessageDeserializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * <p>POJO representing the output JSON for the Abucoins
 * <code>GET /accounts</code> endpoint.</p>
 *
 * Example:
 * <code><pre>
 * [
 *     {
 *         "id": "3-BTC",
 *         "currency": "BTC",
 *         "balance": 13.38603805,
 *         "available": 13.38589212,
 *         "available_btc": 13.38589212,
 *         "hold": 0.00014593,
 *         "profile_id": 3
 *     },
 *     {
 *         "id": "3-ETH",
 *         "currency": "ETH",
 *         "balance": 133.48685448,
 *         "available": 133.48685448,
 *         "available_btc": 9.38012126,
 *         "hold": 0,
 *         "profile_id": 3
 *     }
 * ]
 * </pre></code>
 * @author bryant_harris
 */
@JsonDeserialize(using = AbucoinsAccounts.AbucoinsAccountsDeserializer.class)
public class AbucoinsAccounts {
  AbucoinsAccount[] accounts;
        
  public AbucoinsAccounts(AbucoinsAccount[] accounts) {
    this.accounts = accounts;
  }

  public AbucoinsAccount[] getAccounts() {
    return accounts;
  }

  @Override
  public String toString() {
    return "AbucoinsAccounts [accounts=" + Arrays.toString(accounts) + "]";
  }
        
  /**
   * Deserializer handles the success case (array json) as well as the error case
   * (json object with <em>message</em> field).
   * @author bryant_harris
   */
  static class AbucoinsAccountsDeserializer extends AbucoinsArrayOrMessageDeserializer<AbucoinsAccount, AbucoinsAccounts> {
    public AbucoinsAccountsDeserializer() {
        super(AbucoinsAccount.class, AbucoinsAccounts.class);
    }
  }
}
