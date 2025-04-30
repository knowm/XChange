package org.knowm.xchange.binance.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.binance.config.converter.StringToCurrencyConverter;
import org.knowm.xchange.currency.Currency;

@Data
@Builder
@Jacksonized
public class BinanceCurrencyInfo {

  @JsonProperty("coin")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  private Currency currency;

  @JsonProperty("depositAllEnable")
  private Boolean depositEnabled;

  @JsonProperty("withdrawAllEnable")
  private Boolean withdrawEnabled;

  @JsonProperty("name")
  private String name;

  @JsonProperty("free")
  private BigDecimal free;

  @JsonProperty("locked")
  private BigDecimal locked;

  @JsonProperty("freeze")
  private BigDecimal freeze;

  @JsonProperty("withdrawing")
  private BigDecimal withdrawing;

  @JsonProperty("ipoing")
  private BigDecimal ipoing;

  @JsonProperty("ipoable")
  private BigDecimal ipoable;

  @JsonProperty("storage")
  private BigDecimal storage;

  @JsonProperty("isLegalMoney")
  private Boolean isLegalMoney;

  @JsonProperty("trading")
  private Boolean trading;

  @JsonProperty("networkList")
  private List<Network> networks;

  @Data
  @Builder
  @Jacksonized
  public static class Network {

    @JsonProperty("network")
    private String id;

    @JsonProperty("coin")
    @JsonDeserialize(converter = StringToCurrencyConverter.class)
    private Currency currency;

    @JsonProperty("withdrawIntegerMultiple")
    private BigDecimal withdrawIntegerMultiple;

    @JsonProperty("isDefault")
    private Boolean isDefault;

    @JsonProperty("depositEnable")
    private Boolean depositEnabled;

    @JsonProperty("withdrawEnable")
    private Boolean withdrawEnabled;

    @JsonProperty("depositDesc")
    private String depositDesc;

    @JsonProperty("withdrawDesc")
    private String withdrawDesc;

    @JsonProperty("specialTips")
    private String specialTips;

    @JsonProperty("name")
    private String name;

    @JsonProperty("resetAddressStatus")
    private Boolean resetAddressStatus;

    @JsonProperty("addressRegex")
    private String addressRegex;

    @JsonProperty("memoRegex")
    private String memoRegex;

    @JsonProperty("withdrawFee")
    private BigDecimal withdrawFee;

    @JsonProperty("withdrawMin")
    private BigDecimal withdrawMin;

    @JsonProperty("withdrawMax")
    private BigDecimal withdrawMax;

    @JsonProperty("minConfirm")
    private Integer minConfirm;

    @JsonProperty("unLockConfirm")
    private Integer unLockConfirm;

    @JsonProperty("sameAddress")
    private Boolean sameAddress;

    @JsonProperty("estimatedArrivalTime")
    private Integer estimatedArrivalTime;

    @JsonProperty("busy")
    private Boolean busy;

    @JsonProperty("contractAddressUrl")
    private String contractAddressUrl;

    @JsonProperty("contractAddress")
    private String contractAddress;
  }
}
