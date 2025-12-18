package org.knowm.xchange.kucoin.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;

/** DTO for Kucoin Earn Holding */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KucoinEarnHolding {

  @JsonProperty("orderId")
  private String orderId;

  @JsonProperty("productId")
  private String productId;

  @JsonProperty("productCategory")
  private String productCategory;

  @JsonProperty("productType")
  private String productType;

  @JsonProperty("currency")
  private String currency;

  @JsonProperty("incomeCurrency")
  private String incomeCurrency;

  @JsonProperty("returnRate")
  private BigDecimal returnRate;

  @JsonProperty("holdAmount")
  private BigDecimal holdAmount;

  @JsonProperty("redeemedAmount")
  private BigDecimal redeemedAmount;

  @JsonProperty("redeemingAmount")
  private BigDecimal redeemingAmount;

  @JsonProperty("lockStartTime")
  private Long lockStartTime;

  @JsonProperty("lockEndTime")
  private Long lockEndTime;

  @JsonProperty("purchaseTime")
  private Long purchaseTime;

  @JsonProperty("redeemPeriod")
  private Integer redeemPeriod;

  @JsonProperty("status")
  private String status;

  @JsonProperty("earlyRedeemSupported")
  private Integer earlyRedeemSupported;
}
