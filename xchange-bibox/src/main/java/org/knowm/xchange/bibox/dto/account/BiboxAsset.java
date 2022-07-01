package org.knowm.xchange.bibox.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Getter;
import org.knowm.xchange.currency.Currency;

public class BiboxAsset {

  @JsonProperty("coin_symbol")
  @Getter
  private Currency coin_symbol;

  @JsonProperty("BTCValue")
  @Getter
  private BigDecimal BTCValue;

  @JsonProperty("CNYValue")
  @Getter
  private BigDecimal CNYValue;

  @JsonProperty("USDValue")
  @Getter
  private BigDecimal USDValue;

  @JsonProperty("balance")
  @Getter
  private BigDecimal balance;

  @JsonProperty("freeze")
  @Getter
  private BigDecimal freeze;
}
