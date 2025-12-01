package org.knowm.xchange.bitstamp.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Value;

@Value
public class BitstampEarnSubscribeRequest {

  @JsonProperty("currency")
  String currency;

  @JsonProperty("earn_type")
  BitstampEarnType earnType;

  @JsonProperty("earn_term")
  BitstampEarnTerm earnTerm;

  @JsonProperty("amount")
  BigDecimal amount;
}
