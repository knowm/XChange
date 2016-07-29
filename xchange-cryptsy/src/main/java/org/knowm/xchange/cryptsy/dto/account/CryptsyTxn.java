package org.knowm.xchange.cryptsy.dto.account;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.cryptsy.CryptsyUtils;

/**
 * @author ObsessiveOrange
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptsyTxn {

  private final String currency;
  private final CryptsyTxnType type;
  private final Date timeStamp;
  private final String address;
  private final BigDecimal amount;
  private final BigDecimal fee;
  private final String txnId;

  /**
   * Constructor
   * 
   * @param transactionCount The number of transactions
   * @param openOrders The open orders
   * @param serverTime The server time (Unix time)
   * @param rights The rights
   * @param funds The funds
   * @throws ParseException
   */
  public CryptsyTxn(@JsonProperty("currency") String currency, @JsonProperty("type") CryptsyTxnType type, @JsonProperty("datetime") String timeStamp,
      @JsonProperty("address") String address, @JsonProperty("amount") BigDecimal amount, @JsonProperty("fee") BigDecimal fee,
      @JsonProperty("trxid") String txnId) throws ParseException {

    this.currency = currency;
    this.type = type;
    this.timeStamp = timeStamp == null ? null : CryptsyUtils.convertDateTime(timeStamp);
    this.address = address;
    this.amount = amount;
    this.fee = fee;
    this.txnId = txnId;
  }

  public String getCurrency() {

    return currency;
  }

  public CryptsyTxnType getType() {

    return type;
  }

  public Date getTimestamp() {

    return timeStamp;
  }

  public String getAddress() {

    return address;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  public BigDecimal getFee() {

    return fee;
  }

  public String getTransactionId() {

    return txnId;
  }

  @Override
  public String toString() {

    return "CryptsyTransactionHistory[" + "Currency='" + currency + "', Type='" + type + "',Timestamp='" + timeStamp + "',Address='" + address
        + "',Amount='" + amount + "',Fee='" + fee + "',Transaction ID='" + txnId + "']";
  }

  public static enum CryptsyTxnType {
    Deposit, Withdrawal
  }
}
