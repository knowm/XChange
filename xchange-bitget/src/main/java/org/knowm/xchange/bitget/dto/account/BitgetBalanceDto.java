package org.knowm.xchange.bitget.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.bitget.config.converter.StringToCurrencyConverter;
import org.knowm.xchange.currency.Currency;

@Data
@Builder
@Jacksonized
public class BitgetBalanceDto {

  @JsonProperty("coin")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  private Currency currency;

  @JsonProperty("available")
  private BigDecimal available;

  @JsonProperty("frozen")
  private BigDecimal frozen;

  @JsonProperty("locked")
  private BigDecimal locked;

  @JsonProperty("limitAvailable")
  private BigDecimal limitAvailable;

  @JsonProperty("uTime")
  private Instant timestamp;
}
