package org.knowm.xchange.simulated;

import org.knowm.xchange.dto.trade.UserTrade;

import lombok.Data;

@Data
final class Fill {
  private final String apiKey;
  private final UserTrade trade;
}
