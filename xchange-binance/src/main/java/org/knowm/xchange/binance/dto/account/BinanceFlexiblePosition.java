package org.knowm.xchange.binance.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public final class BinanceFlexiblePosition {

  @JsonProperty("totalAmount")
  private final BigDecimal totalAmount;

  @JsonProperty("tierAnnualPercentageRate")
  private final Map<String, BigDecimal> tierAnnualPercentageRate;

  @JsonProperty("latestAnnualPercentageRate")
  private final BigDecimal latestAnnualPercentageRate;

  @JsonProperty("yesterdayAirdropPercentageRate")
  private final BigDecimal yesterdayAirdropPercentageRate;

  @JsonProperty("asset")
  private final String asset;

  @JsonProperty("airDropAsset")
  private final String airDropAsset;

  @JsonProperty("canRedeem")
  private final Boolean canRedeem;

  @JsonProperty("collateralAmount")
  private final BigDecimal collateralAmount;

  @JsonProperty("productId")
  private final String productId;

  @JsonProperty("yesterdayRealTimeRewards")
  private final BigDecimal yesterdayRealTimeRewards;

  @JsonProperty("cumulativeBonusRewards")
  private final BigDecimal cumulativeBonusRewards;

  @JsonProperty("cumulativeRealTimeRewards")
  private final BigDecimal cumulativeRealTimeRewards;

  @JsonProperty("cumulativeTotalRewards")
  private final BigDecimal cumulativeTotalRewards;

  @JsonProperty("autoSubscribe")
  private final Boolean autoSubscribe;
}
