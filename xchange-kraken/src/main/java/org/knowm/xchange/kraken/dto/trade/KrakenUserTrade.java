package org.knowm.xchange.kraken.dto.trade;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.knowm.xchange.dto.trade.UserTrade;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class KrakenUserTrade extends UserTrade {

  private BigDecimal cost;
}
