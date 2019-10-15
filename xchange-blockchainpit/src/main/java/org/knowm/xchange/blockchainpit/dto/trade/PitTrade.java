package org.knowm.xchange.blockchainpit.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.blockchainpit.dto.PitResponse;

import java.math.BigDecimal;

// {
//   "seqnum":1,
//   "event":"updated",
//   "channel":"trades",
//   "symbol":"BTC-USD",
//   "timestamp":"2019-08-13T11:30:06.100140Z",
//   "side":"sell",
//   "qty":8.5E-5,
//   "price":11252.4,
//   "trade_id":"12884909920"
// }
public class PitTrade extends PitResponse {
    private final String symbol;
    private final String timestamp;
    private final String side;
    private final BigDecimal qty;
    private final BigDecimal price;
    private final String trade_id;

    public PitTrade(
        @JsonProperty("seqnum") Long seqnum,
        @JsonProperty("event") String event,
        @JsonProperty("channel") String channel,
        @JsonProperty("symbol") String symbol,
        @JsonProperty("timestamp") String timestamp,
        @JsonProperty("side") String side,
        @JsonProperty("qty") BigDecimal qty,
        @JsonProperty("price") BigDecimal price,
        @JsonProperty("trade_id") String trade_id
    ) {
        super(seqnum, event, channel);
        this.symbol = symbol;
        this.timestamp = timestamp;
        this.side = side;
        this.qty = qty;
        this.price = price;
        this.trade_id = trade_id;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getSide() {
        return side;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getTradeId() {
        return trade_id;
    }
}
