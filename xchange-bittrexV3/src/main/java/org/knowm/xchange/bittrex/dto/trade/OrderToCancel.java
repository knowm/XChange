package org.knowm.xchange.bittrex.dto.trade;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderToCancel {
  private String type;
  private String id;
}
