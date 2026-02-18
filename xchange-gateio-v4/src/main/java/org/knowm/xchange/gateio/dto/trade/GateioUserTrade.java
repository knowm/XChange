package org.knowm.xchange.gateio.dto.trade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.knowm.xchange.dto.trade.UserTrade;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class GateioUserTrade extends UserTrade {

  /** Trade role * */
  private Role role;
}
