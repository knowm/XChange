package org.knowm.xchange.coinex.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.coinex.config.converter.StringToCurrencyConverter;
import org.knowm.xchange.currency.Currency;

@Data
@Builder
@Jacksonized
public class CoinexBalanceInfo {

  @JsonProperty("ccy")
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  private Currency currency;

  private BigDecimal available;

  private BigDecimal frozen;
}
