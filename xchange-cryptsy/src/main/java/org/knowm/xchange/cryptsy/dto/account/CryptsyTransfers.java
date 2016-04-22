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
public class CryptsyTransfers {

  private final String currency;
  private final Date req_timeStamp;
  private final boolean processed;
  private final Date proc_timeStamp;
  private final String from;
  private final String to;
  private final BigDecimal quantity;
  private final CryptsyTrfDirection direction;

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
  public CryptsyTransfers(@JsonProperty("currency") String currency, @JsonProperty("request_timestamp") String req_timeStamp,
      @JsonProperty("processed") int processed, @JsonProperty("processed_timestamp") String proc_timeStamp, @JsonProperty("from") String from,
      @JsonProperty("to") String to, @JsonProperty("quantity") BigDecimal quantity, @JsonProperty("direction") CryptsyTrfDirection direction)
      throws ParseException {

    this.currency = currency;
    this.req_timeStamp = req_timeStamp == null ? null : CryptsyUtils.convertDateTime(req_timeStamp);
    this.processed = (processed == 1 ? true : false);
    this.proc_timeStamp = proc_timeStamp == null ? null : CryptsyUtils.convertDateTime(proc_timeStamp);
    this.from = from;
    this.to = to;
    this.quantity = quantity;
    this.direction = direction;
  }

  public String getCurrency() {

    return currency;
  }

  public Date getRequestTimestamp() {

    return req_timeStamp;
  }

  public Boolean getProcessedBoolean() {

    return processed;
  }

  public Date getProcessedTimestamp() {

    return proc_timeStamp;
  }

  public String getOriginator() {

    return from;
  }

  public String getRecipient() {

    return to;
  }

  public BigDecimal getQuantity() {

    return quantity;
  }

  public CryptsyTrfDirection getTransferDirection() {

    return direction;
  }

  @Override
  public String toString() {

    return "CryptsyTransactionHistory[" + "Currency='" + currency + "', Request Timestamp='" + req_timeStamp + "',Processed='" + processed
        + "',Processed Timestamp='" + proc_timeStamp + "',Originator='" + from + "',Recipient='" + to + "',Quantity='" + quantity
        + "',Transfer Direction='" + direction + "']";
  }

  public static enum CryptsyTrfDirection {
    in, out
  }
}
