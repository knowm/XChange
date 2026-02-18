package org.knowm.xchange.kraken.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class KrakenExtendedBalance {

  @JsonProperty("balance")
  private BigDecimal balance;

  @JsonProperty("credit")
  private BigDecimal credit;

  @JsonProperty("credit_used")
  private BigDecimal creditUsed;

  @JsonProperty("hold_trade")
  private BigDecimal holdTrade;
}
