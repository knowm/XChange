package org.knowm.xchange.bitget.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class BitgetChainDto {

  @JsonProperty("chain")
  private String chain;

  @JsonProperty("needTag")
  private Boolean needTag;

  @JsonProperty("withdrawable")
  private Boolean isWithdrawEnabled;

  @JsonProperty("rechargeable")
  private Boolean isDepositEnabled;

  @JsonProperty("withdrawFee")
  private BigDecimal withdrawFee;

  @JsonProperty("extraWithdrawFee")
  private BigDecimal extraWithdrawFee;

  @JsonProperty("depositConfirm")
  private Integer depositConfirmBlockCount;

  @JsonProperty("withdrawConfirm")
  private Integer withdrawConfirmBlockCount;

  @JsonProperty("minDepositAmount")
  private BigDecimal minDepositAmount;

  @JsonProperty("minWithdrawAmount")
  private BigDecimal minWithdrawAmount;

  @JsonProperty("browserUrl")
  private String browserUrl;

  @JsonProperty("contractAddress")
  private String contractAddress;

  @JsonProperty("withdrawStep")
  private Integer withdrawStep;

  @JsonProperty("withdrawMinScale")
  private Integer withdrawMinScale;

  @JsonProperty("congestion")
  private Congestion congestion;

  public static enum Congestion {
    @JsonProperty("normal")
    NORMAL,

    @JsonProperty("congested")
    CONGESTED;
  }
}
