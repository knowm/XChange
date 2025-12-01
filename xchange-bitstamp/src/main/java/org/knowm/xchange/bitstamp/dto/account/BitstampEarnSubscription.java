package org.knowm.xchange.bitstamp.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.bitstamp.config.converter.StringToCurrencyConverter;
import org.knowm.xchange.currency.Currency;

@Getter
@ToString
@Builder
@Jacksonized
public class BitstampEarnSubscription {

  @JsonProperty("currency")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  private final Currency currency;

  @JsonProperty("type")
  private final BitstampEarnType type;

  @JsonProperty("term")
  private final BitstampEarnTerm term;

  @JsonProperty("estimated_annual_yield")
  private final BigDecimal estimatedAnnualYield;

  @JsonProperty("distribution_period")
  private final String distributionPeriod;

  @JsonProperty("activation_period")
  private final String activationPeriod;

  @JsonProperty("minimum_subscription_amount")
  private final BigDecimal minimumSubscriptionAmount;

  @JsonProperty("amount")
  private final BigDecimal amount;

  @JsonProperty("available_amount")
  private final BigDecimal availableAmount;

  @JsonProperty("amount_earned")
  private final BigDecimal amountEarned;
}
