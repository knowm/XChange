package org.knowm.xchange.okex.v5.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** Author: Max Gao (gaamox@tutanota.com) Created: 08-06-2021 */

/** https://www.okex.com/docs-v5/en/#rest-api-account-get-balance * */
@Getter
@NoArgsConstructor
public class OkexWalletBalance {
  @JsonProperty("uTime")
  private String asOfTime;

  @JsonProperty("totalEq")
  private String totalEquity;

  @JsonProperty("isoEq")
  private String isolatedMarginEquity;

  @JsonProperty("adjEq")
  private String adjustedEquity;

  @JsonProperty("ordFroz")
  private String marginFrozen;

  @JsonProperty("imr")
  private String initialMarginRequirement;

  @JsonProperty("mmr")
  private String maintenanceMarginRequirement;

  @JsonProperty("mgnRatio")
  private String marginRatio;

  @JsonProperty("notionalUsd")
  private String notionalUsd;

  @JsonProperty("details")
  private Detail[] details;

  @NoArgsConstructor
  @Getter
  public static class Detail {
    @JsonProperty("ccy")
    private String currency;

    @JsonProperty("eq")
    private String equity;

    @JsonProperty("cashBal")
    private String cashBalance;

    @JsonProperty("uTime")
    private String asOfTime;

    @JsonProperty("isoEq")
    private String isolatedMarginEquity;

    @JsonProperty("availEq")
    private String avilableEquity;

    @JsonProperty("disEq")
    private String discountEquity;

    @JsonProperty("availBal")
    private String availableBalance;

    @JsonProperty("frozenBal")
    private String frozenBalance;

    @JsonProperty("ordFrozen")
    private String marginFrozen;

    @JsonProperty("eqUsd")
    private String usdEqual;

    // TODO: Model the rest of the margin fields
  }
}
