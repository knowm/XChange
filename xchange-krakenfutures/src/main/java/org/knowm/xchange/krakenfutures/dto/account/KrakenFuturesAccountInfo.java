package org.knowm.xchange.krakenfutures.dto.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Map;

/** @author Panchen */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@ToString
public class KrakenFuturesAccountInfo {

  private final String type;

  private final Map<String, KrakenFuturesCurrency> currencies;

  private final BigDecimal balanceValue;

  private final BigDecimal portfolioValue;

  private final BigDecimal collateralValue;

  public KrakenFuturesAccountInfo(
          @JsonProperty("type") String type,
          @JsonProperty("currencies") Map<String, KrakenFuturesCurrency> currencies,
          @JsonProperty("balanceValue") BigDecimal balanceValue,
          @JsonProperty("portfolioValue") BigDecimal portfolioValue,
          @JsonProperty("collateralValue") BigDecimal collateralValue) {
    this.type = type;
    this.currencies = currencies;
    this.balanceValue = balanceValue;
    this.portfolioValue = portfolioValue;
    this.collateralValue = collateralValue;
  }

  @Getter
  @ToString
  @JsonInclude(JsonInclude.Include.NON_NULL)
  public static class KrakenFuturesCurrency{
    private final BigDecimal quantity;
    private final BigDecimal value;
    private final BigDecimal collateralValue;
    private final BigDecimal available;

    public KrakenFuturesCurrency(
            @JsonProperty("quantity") BigDecimal quantity,
            @JsonProperty("value") BigDecimal value,
            @JsonProperty("collateralValue") BigDecimal collateralValue,
            @JsonProperty("available") BigDecimal available) {
      this.quantity = quantity;
      this.value = value;
      this.collateralValue = collateralValue;
      this.available = available;
    }
  }
}
