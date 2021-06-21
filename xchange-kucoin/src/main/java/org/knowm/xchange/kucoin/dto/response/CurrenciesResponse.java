package org.knowm.xchange.kucoin.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrenciesResponse {

  private String currency;

  private String name;

  private String fullName;

  private BigDecimal precision;

  private String withdrawalMinSize;

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
