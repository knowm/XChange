package org.knowm.xchange.ascendex.dto.trade;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public class AscendexOrderResponse {

  private final String ac;

  private final String accountId;

  private final String action;

  private final AscendexPlaceOrderInfo info;

  private final String status;

  private final String message;

  private final String reason;

  private final String code;

  public AscendexOrderResponse(
      @JsonProperty("ac") String ac,
      @JsonProperty("accountId") String accountId,
      @JsonProperty("action") String action,
      @JsonProperty("info") AscendexPlaceOrderInfo info,
      @JsonProperty("status") String status,
      @JsonProperty("message") String message,
      @JsonProperty("reason") String reason,
      @JsonProperty("code") String code) {
    this.ac = ac;
    this.accountId = accountId;
    this.action = action;
    this.info = info;
    this.status = status;
    this.message = message;
    this.reason = reason;
    this.code = code;
  }

  public String getAc() {
    return ac;
  }

  public String getAccountId() {
    return accountId;
  }

  public String getAction() {
    return action;
  }

  public AscendexPlaceOrderInfo getInfo() {
    return info;
  }

  public String getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }

  public String getReason() {
    return reason;
  }

  public String getCode() {
    return code;
  }

  @Override
  public String toString() {
    return "AscendexPlaceOrderResponse{"
        + "ac='"
        + ac
        + '\''
        + ", accountId='"
        + accountId
        + '\''
        + ", action='"
        + action
        + '\''
        + ", info="
        + info
        + ", status='"
        + status
        + '\''
        + ", message='"
        + message
        + '\''
        + ", reason='"
        + reason
        + '\''
        + ", code='"
        + code
        + '\''
        + '}';
  }

  public static class AscendexPlaceOrderInfo {
    private final String id;

    private final String orderId;

    private final String orderType;

    private final String symbol;

    private final Date timestamp;

    public AscendexPlaceOrderInfo(
        @JsonProperty("id") String id,
        @JsonProperty("orderId") String orderId,
        @JsonProperty("orderType") String orderType,
        @JsonProperty("symbol") String symbol,
        @JsonProperty("timestamp") @JsonAlias({"lastExecTime"}) Long timestamp) {
      this.id = id;
      this.orderId = orderId;
      this.orderType = orderType;
      this.symbol = symbol;
      this.timestamp = timestamp == null ? null : new Date(timestamp);
    }

    public String getId() {
      return id;
    }

    public String getOrderId() {
      return orderId;
    }

    public String getOrderType() {
      return orderType;
    }

    public String getSymbol() {
      return symbol;
    }

    public Date getTimestamp() {
      return timestamp;
    }

    @Override
    public String toString() {
      return "{"
          + "id='"
          + id
          + '\''
          + ", orderId='"
          + orderId
          + '\''
          + ", orderType='"
          + orderType
          + '\''
          + ", symbol='"
          + symbol
          + '\''
          + ", timestamp="
          + timestamp
          + '}';
    }
  }
}
