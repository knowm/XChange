package org.knowm.xchange.cryptowatch.dto.marketdata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

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
