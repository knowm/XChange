package info.bitrich.xchangestream.bitfinex.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import org.knowm.xchange.bitfinex.v1.dto.marketdata.BitfinexTrade;

/** Created by Lukas Zaoralek on 7.11.17. */
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
public class BitfinexWebSocketTrade {
  public long tradeId;
  public long timestamp;
  public BigDecimal amount;
  public BigDecimal price;

  public BitfinexWebSocketTrade() {}

  public BitfinexWebSocketTrade(long tradeId, long timestamp, BigDecimal amount, BigDecimal price) {
    this.tradeId = tradeId;
    this.timestamp = timestamp;
    this.amount = amount;
    this.price = price;
  }

  public long getTradeId() {
    return tradeId;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BitfinexTrade toBitfinexTrade() {
    String type;
    if (amount.compareTo(BigDecimal.ZERO) < 0) {
      type = "sell";
    } else {
      type = "buy";
    }
    return new BitfinexTrade(price, amount.abs(), timestamp / 1000, "bitfinex", tradeId, type);
  }
}
