package info.bitrich.xchangestream.bitfinex.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import org.knowm.xchange.bitfinex.v1.dto.marketdata.BitfinexTicker;

/** Created by Lukas Zaoralek on 8.11.17. */
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
public class BitfinexWebSocketTickerTransaction {

  public String channelId;
  public String[] tickerArr;

  public BitfinexWebSocketTickerTransaction() {}

  public BitfinexWebSocketTickerTransaction(String channelId, String[] tickerArr) {
    this.channelId = channelId;
    this.tickerArr = tickerArr;
  }

  public String getChannelId() {
    return channelId;
  }

  public BitfinexTicker toBitfinexTicker() {
    BigDecimal bid = new BigDecimal(tickerArr[0]);
    BigDecimal ask = new BigDecimal(tickerArr[2]);
    BigDecimal mid = ask.subtract(bid);
    BigDecimal low = new BigDecimal(tickerArr[9]);
    BigDecimal high = new BigDecimal(tickerArr[8]);
    BigDecimal last = new BigDecimal(tickerArr[6]);
    // Xchange-bitfinex adapter expects the timestamp to be seconds since Epoch.
    double timestamp = System.currentTimeMillis() / 1000;
    BigDecimal volume = new BigDecimal(tickerArr[7]);

    return new BitfinexTicker(mid, bid, ask, low, high, last, timestamp, volume);
  }
}
