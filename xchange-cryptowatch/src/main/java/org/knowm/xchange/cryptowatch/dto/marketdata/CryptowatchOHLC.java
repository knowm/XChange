package org.knowm.xchange.cryptowatch.dto.marketdata;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/** @author massi.gerardi */
@Getter
@AllArgsConstructor
@ToString
public class CryptowatchOHLC {

  private final long time;
  private final BigDecimal open;
  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal close;
  private final BigDecimal vwap;
  private final BigDecimal volume;
  private final long count;
}
