package org.knowm.xchange.gateio.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class GateioCurrencyBalance {

  @JsonProperty("currency")
  String currency;

  @JsonProperty("available")
  BigDecimal available;

  @JsonProperty("locked")
  BigDecimal locked;

}
