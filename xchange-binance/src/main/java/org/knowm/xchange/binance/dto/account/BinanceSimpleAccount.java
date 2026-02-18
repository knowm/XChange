package org.knowm.xchange.binance.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public final class BinanceSimpleAccount {

  @JsonProperty("totalAmountInBTC")
  private final BigDecimal totalAmountInBTC;

  @JsonProperty("totalAmountInUSDT")
  private final BigDecimal totalAmountInUSDT;

  @JsonProperty("totalFlexibleAmountInBTC")
  private final BigDecimal totalFlexibleAmountInBTC;

  @JsonProperty("totalFlexibleAmountInUSDT")
  private final BigDecimal totalFlexibleAmountInUSDT;

  @JsonProperty("totalLockedInBTC")
  private final BigDecimal totalLockedInBTC;

  @JsonProperty("totalLockedInUSDT")
  private final BigDecimal totalLockedInUSDT;
}
