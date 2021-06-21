package org.knowm.xchange.bittrex.dto.account;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BittrexComissionRatesWithMarket {
  private String marketSymbol;
  private Double makerRate;
  private Double takerRate;
}
