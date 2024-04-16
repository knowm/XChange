package dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import org.knowm.xchange.bybit.dto.trade.BybitSide;

@Getter
public class BybitTrade {

  //https://bybit-exchange.github.io/docs/v5/websocket/public/trad
  //The timestamp (ms) that the order is filled
  private final Date timestamp;
  //Symbol name
  private final String instId;
  //Side of taker. Buy,Sell
  private final BybitSide side;
  private final BigDecimal tradeSize;
  private final BigDecimal tradePrice;
  //L	string	Direction of price change. Unique field for future
  private final String direction;
  private final String tradeId;
  //boolean	Whether it is a block trade order or not
  private final boolean bT;


  public BybitTrade(@JsonProperty("T") Date timestamp, @JsonProperty("s") String instId,
      @JsonProperty("S") String side, @JsonProperty("v") BigDecimal tradeSize,
      @JsonProperty("p") BigDecimal tradePrice, @JsonProperty("L") String direction,
      @JsonProperty("i") String tradeId, @JsonProperty("BT") boolean bT) {
    this.timestamp = timestamp;
    this.instId = instId;
    this.side = BybitSide.valueOf(side.toUpperCase());
    this.tradeSize = tradeSize;
    this.tradePrice = tradePrice;
    this.direction = direction;
    this.tradeId = tradeId;
    this.bT = bT;
  }
}

