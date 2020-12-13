package info.bitrich.xchangestream.binance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public class BaseBinanceWebSocketTransaction {

  public enum BinanceWebSocketTypes {
    DEPTH_UPDATE("depthUpdate"),
    TICKER_24_HR("24hrTicker"),
    KLINE("kline"),
    AGG_TRADE("aggTrade"),
    TRADE("trade"),
    OUTBOUND_ACCOUNT_INFO("outboundAccountInfo"),
    EXECUTION_REPORT("executionReport");

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
    eventType = BinanceWebSocketTypes.fromTransactionValue(_eventType);
    eventTime = new Date(Long.parseLong(_eventTime));
  }

  public BinanceWebSocketTypes getEventType() {
    return eventType;
  }

  public Date getEventTime() {
    return eventTime;
  }
}
