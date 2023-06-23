package org.knowm.xchange.kucoin.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyResponseV2 {

  @JsonProperty("currency")
  private String currency;

  @JsonProperty("name")
  private String name;

  @JsonProperty("fullName")
  private String fullName;

  @JsonProperty("precision")
  private BigDecimal precision;

  @JsonProperty("chains")
  private List<Chain> chains;

  @JsonProperty("withdrawalMinSize")
  private String withdrawalMinSize;

  @JsonProperty("withdrawalMinFee")
  private String withdrawalMinFee;

  @JsonProperty("isMarginEnabled")
  private Boolean isMarginEnabled;

  @JsonProperty("isDebitEnabled")
  private Boolean isDebitEnabled;

  @Data
  public static class Chain {

    @JsonProperty("chainName")
    private String chainName;

    @JsonProperty("chain")
    private String chain;

    @JsonProperty("withdrawalMinSize")
    private BigDecimal withdrawalMinSize;

    @JsonProperty("withdrawalMinFee")
    private BigDecimal withdrawalMinFee;

    @JsonProperty("isWithdrawEnabled")
    private Boolean isWithdrawEnabled;

    @JsonProperty("isDepositEnabled")
    private Boolean isDepositEnabled;

    @JsonProperty("confirms")
    private Long confirms;

    @JsonProperty("contractAddress")
    private String contractAddress;

  }
}
