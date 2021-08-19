package org.knowm.xchange.bittrex.dto.marketdata;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.knowm.xchange.currency.Currency;

@Data
@NoArgsConstructor
public class BittrexSymbol {
  private String symbol;
  private Date createdAt;
  private String[] prohibitedIn;
  private BigDecimal minTradeSize;
  private Integer precision;
  private Currency quoteCurrencySymbol;
  private Currency baseCurrencySymbol;
  private String status;
  private String notice;
}
