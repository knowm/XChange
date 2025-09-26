package org.knowm.xchange.okex.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OkexTradeFee {
  @JsonProperty("delivery")
  private String delivery;

  @JsonProperty("exercise")
  private String exercise;

  @JsonProperty("instType")
  private String instType;

  @JsonProperty("level")
  private String level;

  @JsonProperty("maker")
  private String maker;

  @JsonProperty("taker")
  private String taker;

  @JsonProperty("makerU")
  private String makerU;

  @JsonProperty("takerU")
  private String takerU;

  @JsonProperty("makerUSDC")
  private String makerUSDC;

  @JsonProperty("takerUSDC")
  private String takerUSDC;

  @JsonProperty("ts")
  private String timestamp;

  @JsonProperty("ruleType")
  private String ruleType;

  @JsonProperty("fiat")
  private List<FiatList> fiatList;

  @Getter
  public static class FiatList {
    @JsonProperty("ccy")
    private String ccy;

    @JsonProperty("taker")
    private String taker;

    @JsonProperty("maker")
    private String maker;
  }
}
