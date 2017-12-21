package info.bitrich.xchangestream.binance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class BaseBinanceWebSocketTransaction {

    public static enum BinanceWebSocketTypes {
        depthUpdate,
        kline,
        aggTrade,
        outboundAccountInfo
    }

    protected final BinanceWebSocketTypes eventType;
    protected final Date eventTime;

    public BaseBinanceWebSocketTransaction(
            @JsonProperty("e") String _eventType,
            @JsonProperty("E") String _eventTime) {
        eventType = BinanceWebSocketTypes.valueOf(_eventType);
        eventTime = new Date(Long.parseLong(_eventTime));
    }

    public BinanceWebSocketTypes getEventType() {
        return eventType;
    }

    public Date getEventTime() {
        return eventTime;
    }
}
