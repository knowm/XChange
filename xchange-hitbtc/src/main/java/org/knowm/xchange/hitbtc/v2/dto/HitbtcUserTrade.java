package org.knowm.xchange.hitbtc.v2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.knowm.xchange.dto.trade.UserTrade;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class HitbtcUserTrade extends UserTrade {

  private String clientOrderId;
}
