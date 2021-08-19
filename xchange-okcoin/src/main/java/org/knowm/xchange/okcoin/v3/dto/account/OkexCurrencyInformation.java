package org.knowm.xchange.okcoin.v3.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.knowm.xchange.okcoin.v3.dto.deserialize.StringNumeralBooleanDeserializer;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OkexCurrencyInformation {
  private String currency;
  private String name;

  @JsonDeserialize(using = StringNumeralBooleanDeserializer.class)
  @JsonProperty("can_deposit")
  private boolean depositable;

  @JsonDeserialize(using = StringNumeralBooleanDeserializer.class)
  @JsonProperty("can_withdraw")
  private boolean withdrawable;

  private String minWithdrawal;
}
