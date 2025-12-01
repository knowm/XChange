package org.knowm.xchange.binance.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;

@Data
public final class BinanceFiatOrder {

  @JsonProperty("orderNo")
  private String orderNo;

  @JsonProperty("fiatCurrency")
  private String fiatCurrency;

  @JsonProperty("indicatedAmount")
  private BigDecimal indicatedAmount;

  @JsonProperty("amount")
  private BigDecimal amount;

  @JsonProperty("totalFee")
  private BigDecimal totalFee;

  @JsonProperty("method")
  private String method;

  @JsonProperty("status")
  private String status;

  @JsonProperty("createTime")
  private long createTime;

  @JsonProperty("updateTime")
  private long updateTime;
}
