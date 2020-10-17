package org.knowm.xchange.bittrex.dto.trade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BittrexNewOrder {
  private String marketSymbol;
  private String direction;
  private String type;
  private String quantity;
  private String ceiling;
  private String limit;
  private String timeInForce;
  private String clientOrderId;
  private String useAwards;
}
