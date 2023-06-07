package org.knowm.xchange.kucoin.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrenciesResponse {

  @JsonProperty("currency")
  private String currency;

  @JsonProperty("name")
  private String name;

  @JsonProperty("fullName")
  private String fullName;

  @JsonProperty("precision")
  private BigDecimal precision;

  @JsonProperty("confirms")
  private Integer confirms;

  @JsonProperty("contractAddress")
  private String contractAddress;

  @JsonProperty("withdrawalMinSize")
  private String withdrawalMinSize;

  @JsonProperty("withdrawalMinFee")
  private String withdrawalMinFee;

  @JsonProperty("isWithdrawEnabled")
  private boolean isWithdrawEnabled;

  @JsonProperty("isDepositEnabled")
  private boolean isDepositEnabled;

  @JsonProperty("isMarginEnabled")
  private boolean isMarginEnabled;

  @JsonProperty("isDebitEnabled")
  private boolean isDebitEnabled;
}
