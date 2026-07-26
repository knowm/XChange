package org.knowm.xchange.cryptocom.dto.trade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoComOrder {

  @JsonProperty("account_id")
  private String accountId;

  @JsonProperty("order_id")
  private String orderId;

  @JsonProperty("client_oid")
  private String clientOid;

  @JsonProperty("order_type")
  private String orderType;

  @JsonProperty("time_in_force")
  private String timeInForce;

  @JsonProperty("side")
  private String side;

  @JsonProperty("quantity")
  private String quantity;

  @JsonProperty("limit_price")
  private String limitPrice;

  @JsonProperty("order_value")
  private String orderValue;

  @JsonProperty("avg_price")
  private String avgPrice;

  @JsonProperty("cumulative_quantity")
  private String cumulativeQuantity;

  @JsonProperty("cumulative_value")
  private String cumulativeValue;

  @JsonProperty("cumulative_fee")
  private String cumulativeFee;

  @JsonProperty("status")
  private String status;

  @JsonProperty("order_date")
  private String orderDate;

  @JsonProperty("instrument_name")
  private String instrumentName;

  @JsonProperty("fee_instrument_name")
  private String feeInstrumentName;

  @JsonProperty("create_time")
  private Long createTime;

  @JsonProperty("update_time")
  private Long updateTime;

  @JsonProperty("reason")
  private Integer reason;
}
