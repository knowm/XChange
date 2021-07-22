package org.knowm.xchange.kucoin.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.kucoin.dto.KlineIntervalType;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KucoinKline {

  private final CurrencyPair pair;
  private final KlineIntervalType intervalType;
  private Long time;

  private BigDecimal open;

  private BigDecimal high;

  private BigDecimal low;

  private BigDecimal close;

  private BigDecimal volume;

  private BigDecimal amount;

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

  public CurrencyPair getPair() {
    return pair;
  }

  public KlineIntervalType getIntervalType() {
    return intervalType;
  }

  public Long getTime() {
    return time;
  }

  public BigDecimal getOpen() {
    return open;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public BigDecimal getLow() {
    return low;
  }

  public BigDecimal getClose() {
    return close;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public BigDecimal getAmount() {
    return amount;
  }
}
