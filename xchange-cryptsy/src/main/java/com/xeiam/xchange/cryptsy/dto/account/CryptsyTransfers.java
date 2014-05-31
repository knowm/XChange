/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.cryptsy.dto.account;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.cryptsy.CryptsyUtils;

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
  public CryptsyTransfers(@JsonProperty("currency") String currency, @JsonProperty("request_timeStamp") String req_timeStamp, @JsonProperty("processed") int processed,
      @JsonProperty("processed_timeStamp") String proc_timeStamp, @JsonProperty("from") String from, @JsonProperty("to") String to, @JsonProperty("quantity") BigDecimal quantity,
      @JsonProperty("direction") CryptsyTrfDirection direction) throws ParseException {

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

    return "CryptsyTransactionHistory[" + "Currency='" + currency + "', Request Timestamp='" + req_timeStamp + "',Processed='" + processed + "',Processed Timestamp='" + proc_timeStamp
        + "',Originator='" + from + "',Recipient='" + to + "',Quantity='" + quantity + "',Transfer Direction='" + direction + "']";
  }

  public static enum CryptsyTrfDirection {
    in, out
  }
}
