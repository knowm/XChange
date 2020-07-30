package org.knowm.xchange.bittrex.dto.account;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.knowm.xchange.currency.Currency;

@Data
@NoArgsConstructor
public class BittrexBalance {
  private Currency currencySymbol;
  private BigDecimal total;
  private BigDecimal available;
  private Date updatedAt;
}
