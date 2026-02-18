package org.knowm.xchange.kucoin.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.kucoin.config.converter.StringToCurrencyConverter;

@Data
@Builder
@Jacksonized
public class KucoinCurrencyResponseV3 {

  @JsonProperty("currency")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  private Currency currency;

  @JsonProperty("name")
  private String name;

  @JsonProperty("fullName")
  private String fullName;

  @JsonProperty("precision")
  private Integer precision;

  @JsonProperty("confirms")
  private Integer confirms;

  @JsonProperty("contractAddress")
  private String contractAddress;

  @JsonProperty("isMarginEnabled")
  private Boolean isMarginEnabled;

  @JsonProperty("isDebitEnabled")
  private Boolean isDebitEnabled;

  @JsonProperty("chains")
  private List<Chain> chains;

  public boolean isDepositEnabled() {
    return chains != null
        && !chains.isEmpty()
        && chains.stream().anyMatch(Chain::getIsDepositEnabled);
  }

  public boolean isWithdrawEnabled() {
    return chains != null
        && !chains.isEmpty()
        && chains.stream().anyMatch(Chain::getIsWithdrawEnabled);
  }

  @Data
  @Builder
  @Jacksonized
  public static class Chain {

    @JsonProperty("chainName")
    private String chainName;

    @JsonProperty("chainId")
    private String chainId;

    @JsonProperty("withdrawalMinSize")
    private BigDecimal withdrawalMinSize;

    @JsonProperty("depositMinSize")
    private BigDecimal depositMinSize;

    @JsonProperty("withdrawFeeRate")
    private BigDecimal withdrawFeeRate;

    @JsonProperty("withdrawalMinFee")
    private BigDecimal withdrawalMinFee;

    @JsonProperty("isWithdrawEnabled")
    private Boolean isWithdrawEnabled;

    @JsonProperty("isDepositEnabled")
    private Boolean isDepositEnabled;

    @JsonProperty("confirms")
    private Long confirms;

    @JsonProperty("preConfirms")
    private Long preConfirms;

    @JsonProperty("contractAddress")
    private String contractAddress;

    @JsonProperty("withdrawPrecision")
    private Integer withdrawPrecision;

    @JsonProperty("maxWithdraw")
    private BigDecimal maxWithdraw;

    @JsonProperty("maxDeposit")
    private BigDecimal maxDeposit;

    @JsonProperty("needTag")
    private Boolean needTag;
  }
}
