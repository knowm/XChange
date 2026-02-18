package info.bitrich.xchangestream.binance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public class BaseBinanceWebSocketTransaction {
  public enum BinanceWebSocketTypes {
    DEPTH_UPDATE("depthUpdate"),
    TICKER_24_HR("24hrTicker"),
    BOOK_TICKER("bookTicker"),
    KLINE("kline"),
    AGG_TRADE("aggTrade"),
    TRADE("trade"), // outdated ?
    // portfolio margin
    // https://developers.binance.com/docs/derivatives/portfolio-margin/user-data-streams/Event-Margin-Account-Update
    OUTBOUND_ACCOUNT_POSITION("outboundAccountPosition"),
    // portfolio margin
    // https://developers.binance.com/docs/derivatives/portfolio-margin/user-data-streams/Event-Margin-Order-Update
    EXECUTION_REPORT("executionReport"),
    // futures (classic only)
    // https://developers.binance.com/docs/derivatives/usds-margined-futures/user-data-streams/Event-Order-Update
    ORDER_TRADE_UPDATE("ORDER_TRADE_UPDATE"),
    // futures (classic only)
    // https://developers.binance.com/docs/derivatives/usds-margined-futures/user-data-streams/Event-Trade-Lite
    TRADE_LITE("TRADE_LITE"),
    // futures (classic and portfolio margin)
    // https://developers.binance.com/docs/derivatives/usds-margined-futures/user-data-streams/Event-Balance-and-Position-Update
    ACCOUNT_UPDATE("ACCOUNT_UPDATE");

    /**
     * Get a type from the `type` string of a `ProductBinanceWebSocketTransaction`.
     *
     * @param value The string representation.
     * @return THe enum value.
     */
    public static BinanceWebSocketTypes fromTransactionValue(String value) {
      for (BinanceWebSocketTypes type : BinanceWebSocketTypes.values()) {
        if (type.serializedValue.equals(value)) {
          return type;
        }
      }
      return null;
    }

    private String serializedValue;

    BinanceWebSocketTypes(String serializedValue) {
      this.serializedValue = serializedValue;
    }

    public String getSerializedValue() {
      return serializedValue;
    }
  }

  protected final BinanceWebSocketTypes eventType;
  protected final Date eventTime;

  public BaseBinanceWebSocketTransaction(
      @JsonProperty("e") String _eventType, @JsonProperty("E") String _eventTime) {
    this(
        BinanceWebSocketTypes.fromTransactionValue(_eventType),
        new Date(Long.parseLong(_eventTime)));
  }

  protected BaseBinanceWebSocketTransaction(BinanceWebSocketTypes eventType, Date eventTime) {
    this.eventType = eventType;
    this.eventTime = eventTime;
  }

  public BinanceWebSocketTypes getEventType() {
    return eventType;
  }

  public Date getEventTime() {
    return eventTime;
  }
}
