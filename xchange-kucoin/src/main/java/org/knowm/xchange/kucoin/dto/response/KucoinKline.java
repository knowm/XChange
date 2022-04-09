package org.knowm.xchange.kucoin.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import lombok.Data;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.kucoin.dto.KlineIntervalType;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KucoinKline {

  private final CurrencyPair pair;

  private final KlineIntervalType intervalType;

  private final Long time;

  private final BigDecimal open;

  private final BigDecimal high;

  private final BigDecimal low;

  private final BigDecimal close;

  private final BigDecimal volume;

  private final BigDecimal amount;

  public KucoinKline(CurrencyPair pair, KlineIntervalType intervalType, Object[] obj) {
    this.pair = pair;
    this.intervalType = intervalType;
    this.time = Long.valueOf(obj[0].toString());
    this.open = new BigDecimal(obj[1].toString());
    this.close = new BigDecimal(obj[2].toString());
    this.high = new BigDecimal(obj[3].toString());
    this.low = new BigDecimal(obj[4].toString());
    this.volume = new BigDecimal(obj[5].toString());
    this.amount = new BigDecimal(obj[6].toString());
  }
}
