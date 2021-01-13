package org.knowm.xchange.bitfinex.v2.dto.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

/** see https://docs.bitfinex.com/reference#rest-auth-transfer */
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class TransferBetweenWalletsResponse {
  /** Millisecond Time Stamp of the update */
  private long timestamp;
  /** acc_tf */
  private String type;
  /** unique ID of the message */
  private Long messageId;

  private Object placeHolder0;
  private Transfer transfer;

  /** Work in progress */
  private Integer code;
  /** Status of the notification; it may vary over time (SUCCESS, ERROR, FAILURE, ...) */
  private String status;
  /** Text of the notification */
  private String text;

  public Date getTimestamp() {
    return new Date(timestamp);
  }

  @JsonFormat(shape = JsonFormat.Shape.ARRAY)
  @Value
  @NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
  public static class Transfer {
    /** Millisecond Time Stamp when the transfer was created */
    private long timestamp;
    /** Starting wallet */
    private String walletFrom;
    /** Destination wallet */
    private String walletTo;

    private Object placeHolder0;
    /** Currency */
    private String currency;
    /** Currency converted to */
    private String currencyTo;

    private Object placeHolder1;
    /** Amount of Transfer */
    private BigDecimal amount;

    public Date getTimestamp() {
      return new Date(timestamp);
    }
  }
}
