package org.knowm.xchange.ascendex.dto.trade;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.knowm.xchange.ascendex.dto.enums.AscendexOrderType;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AscendexOrderResponse {

  private String ac;

  private String accountId;

  private String action;

  private AscendexPlaceOrderInfo info;

  private String status;

  private String message;

  private String reason;

  private String code;
/*
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
  }*/
  @Data

  public static class AscendexPlaceOrderInfo {
    private  String id;

    private  String orderId;

    private  String orderType;

    private  String symbol;
    @JsonProperty("timestamp") @JsonAlias({"lastExecTime"})
    private  Date timestamp;

   /* public AscendexPlaceOrderInfo(
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
    }*/
  }
}
