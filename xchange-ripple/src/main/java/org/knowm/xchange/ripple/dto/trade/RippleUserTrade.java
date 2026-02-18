package org.knowm.xchange.ripple.dto.trade;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.trade.UserTrade;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class RippleUserTrade extends UserTrade {

  @Builder.Default private String baseCounterparty = "";

  @Builder.Default private String counterCounterparty = "";

  private BigDecimal baseTransferFee;
  private BigDecimal counterTransferFee;

  public Currency getBaseTransferFeeCurrency() {
    return getInstrument().getBase();
  }

  public Currency getCounterTransferFeeCurrency() {
    return getInstrument().getCounter();
  }
}
