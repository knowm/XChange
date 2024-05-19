package info.bitrich.xchangestream.kraken.dto;

import java.math.BigDecimal;
import org.knowm.xchange.kraken.dto.marketdata.KrakenOHLC;

public class KrakenStreamingOhlc extends KrakenOHLC {

  private final long etime;

  public KrakenStreamingOhlc(long time, long etime, BigDecimal open,
      BigDecimal high, BigDecimal low, BigDecimal close,
      BigDecimal vwap, BigDecimal volume, long count) {
    super(time, open, high, low, close, vwap, volume, count);
    this.etime = etime;
  }

  public long getEtime() {
    return this.etime;
  }

  @Override
  public String toString() {

    return "KrakenStreamingOhlc [time="
        + this.getTime()
        + ", etime="
        + this.etime
        + ", open="
        + this.getOpen()
        + ", high="
        + this.getHigh()
        + ", low="
        + this.getLow()
        + ", close="
        + this.getClose()
        + ", vwap="
        + this.getVwap()
        + ", volume="
        + this.getVwap()
        + ", count="
        + this.getCount()
        + "]";
  }
}