package org.knowm.xchange.abucoins.dto.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>POJO representing the output JSON for the Abucoins
 * <code>GET /accounts/&lt;account-id&gt;</code> endpoint.</p>
 *
 * Example:
 * <code><pre>
 *     {
 *         "id": "3-BTC",
 *         "currency": "BTC",
 *         "balance": 13.38603805,
 *         "available": 13.38589212,
 *         "available_btc": 13.38589212,
 *         "hold": 0.00014593,
 *         "profile_id": 3
 *     }
 * </pre></code>
 * @author bryant_harris
 */
public class AbucoinsAccount {
  /** account id */
  String id;
  
  /** the currency of the account */
  String currency;
  
  /** the funds in the account */
  BigDecimal balance;
  
  /** founds available to withdraw or trade */
  BigDecimal available;
  
  /** founds available to withdraw or trade for BTC */
  BigDecimal available_btc;
  
  /** funds on hold (not available for use) */
  BigDecimal hold;
  
  /** profile id */
  long profileID;
  
  /** For error cases */
  String message;
        
  public AbucoinsAccount(@JsonProperty("id") String id,
                         @JsonProperty("currency") String currency,
                         @JsonProperty("balance") BigDecimal balance,
                         @JsonProperty("available") BigDecimal available,
                         @JsonProperty("available_btc") BigDecimal available_btc,
                         @JsonProperty("hold") BigDecimal hold,
                         @JsonProperty("profile_id") long profileID,
                         @JsonProperty("message") String message) {
    this.id = id;
    this.currency = currency;
    this.balance = balance;
    this.available = available;
    this.available_btc = available_btc;
    this.hold = hold;
    this.profileID = profileID;
    this.message = message;
  }

  public String getId() {
    return id;
  }

  public String getCurrency() {
    return currency;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public BigDecimal getAvailable() {
    return available;
  }

  public BigDecimal getAvailable_btc() {
    return available_btc;
  }

  public BigDecimal getHold() {
    return hold;
  }

  public long getProfileID() {
    return profileID;
  }
  
  public String getMessage() {
    return message;
  }

  @Override
  public String toString() {
    return "AbucoinsAccount [id=" + id + ", currency=" + currency + ", balance=" + balance + ", available="
        + available + ", available_btc=" + available_btc + ", hold=" + hold + ", profileID=" + profileID + 
        ", message=" + message +"]";
  }
}
