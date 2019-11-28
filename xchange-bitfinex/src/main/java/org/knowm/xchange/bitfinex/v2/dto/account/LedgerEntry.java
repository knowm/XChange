package org.knowm.xchange.bitfinex.v2.dto.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/** https://docs.bitfinex.com/reference#rest-auth-ledgers */
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@Data
public class LedgerEntry {

  /** Ledger identifier */
  private long id;
  /** The symbol of the currency (ex. "BTC") */
  private String currency;

  private Object placeHolder0;
  /** Timestamp in milliseconds */
  private long timestamp;

  private Object placeHolder1;
  /** Amount of funds moved */
  private BigDecimal amount;
  /** New balance */
  private BigDecimal balance;

  private Object placeHolder2;
  /** Description of ledger transaction */
  private String description;

  public Date getTimestamp() {
    return new Date(timestamp);
  }
}
