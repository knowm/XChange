package org.knowm.xchange.binance.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public final class BinanceLockedPosition {

  @JsonProperty("positionId")
  private final Long positionId;

  @JsonProperty("parentPositionId")
  private final Long parentPositionId;

  @JsonProperty("projectId")
  private final String projectId;

  @JsonProperty("asset")
  private final String asset;

  @JsonProperty("amount")
  private final BigDecimal amount;

  @JsonProperty("purchaseTime")
  private final Long purchaseTime;

  @JsonProperty("duration")
  private final Integer duration;

  @JsonProperty("accrualDays")
  private final Integer accrualDays;

  @JsonProperty("rewardAsset")
  private final String rewardAsset;

  @JsonProperty("APY")
  private final BigDecimal apy;

  @JsonProperty("rewardAmt")
  private final BigDecimal rewardAmt;

  @JsonProperty("extraRewardAsset")
  private final String extraRewardAsset;

  @JsonProperty("extraRewardAPR")
  private final BigDecimal extraRewardAPR;

  @JsonProperty("estExtraRewardAmt")
  private final BigDecimal estExtraRewardAmt;

  @JsonProperty("boostRewardAsset")
  private final String boostRewardAsset;

  @JsonProperty("boostApr")
  private final BigDecimal boostApr;

  @JsonProperty("totalBoostRewardAmt")
  private final BigDecimal totalBoostRewardAmt;

  @JsonProperty("nextPay")
  private final BigDecimal nextPay;

  @JsonProperty("nextPayDate")
  private final Long nextPayDate;

  @JsonProperty("payPeriod")
  private final Integer payPeriod;

  @JsonProperty("redeemAmountEarly")
  private final BigDecimal redeemAmountEarly;

  @JsonProperty("rewardsEndDate")
  private final Long rewardsEndDate;

  @JsonProperty("deliverDate")
  private final Long deliverDate;

  @JsonProperty("redeemPeriod")
  private final Integer redeemPeriod;

  @JsonProperty("redeemingAmt")
  private final BigDecimal redeemingAmt;

  @JsonProperty("redeemTo")
  private final String redeemTo;

  @JsonProperty("partialAmtDeliverDate")
  private final Long partialAmtDeliverDate;

  @JsonProperty("canRedeemEarly")
  private final Boolean canRedeemEarly;

  @JsonProperty("canFastRedemption")
  private final Boolean canFastRedemption;

  @JsonProperty("autoSubscribe")
  private final Boolean autoSubscribe;

  @JsonProperty("type")
  private final String type;

  @JsonProperty("status")
  private final String status;

  @JsonProperty("canReStake")
  private final Boolean canReStake;
}
