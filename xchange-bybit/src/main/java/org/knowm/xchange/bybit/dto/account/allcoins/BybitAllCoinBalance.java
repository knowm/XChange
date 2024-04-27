package org.knowm.xchange.bybit.dto.account.allcoins;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@Value
public class BybitAllCoinBalance {

  @JsonProperty("coin")
  String coin;

  @JsonProperty("walletBalance")
  BigDecimal walletBalance;

  @JsonProperty("transferBalance")
  BigDecimal transferBalance;

  @JsonProperty("bonus")
  BigDecimal bonus;
}
