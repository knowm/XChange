package info.bitrich.xchangestream.deribit.dto.response;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.deribit.dto.response.DeribitTickerNotification.TickerData;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@SuperBuilder(toBuilder = true)
@Jacksonized
public class DeribitTickerNotification extends DeribitWsNotification<TickerData> {

  @Data
  @Builder
  @Jacksonized
  public static class TickerData {
    @JsonProperty("ask_iv")
    BigDecimal askIv;

    @JsonProperty("best_ask_amount")
    BigDecimal bestAskSize;

    @JsonProperty("best_ask_price")
    BigDecimal bestAskPrice;

    @JsonProperty("best_bid_amount")
    BigDecimal bestBidSize;

    @JsonProperty("best_bid_price")
    BigDecimal bestBidPrice;

    @JsonProperty("bid_iv")
    BigDecimal bidIv;

    @JsonProperty("current_funding")
    BigDecimal currentFunding;

    @JsonProperty("delivery_price")
    BigDecimal deliveryPrice;

    @JsonProperty("estimated_delivery_price")
    BigDecimal estimatedDeliveryPrice;

    @JsonProperty("funding_8h")
    BigDecimal funding8H;

    @JsonProperty("greeks")
    Greeks greeks;

    @JsonProperty("index_price")
    BigDecimal indexPrice;

    @JsonProperty("instrument_name")
    String instrumentName;

    @JsonProperty("interest_rate")
    BigDecimal interestRate;

    @JsonProperty("interest_value")
    BigDecimal interestValue;

    @JsonProperty("last_price")
    BigDecimal lastPrice;

    @JsonProperty("mark_iv")
    BigDecimal markIv;

    @JsonProperty("mark_price")
    BigDecimal markPrice;

    @JsonProperty("max_price")
    BigDecimal maxPrice;

    @JsonProperty("min_price")
    BigDecimal minPrice;

    @JsonProperty("open_interest")
    BigDecimal openInterest;

    @JsonProperty("settlement_price")
    BigDecimal settlementPrice;

    @JsonProperty("state")
    State state;

    @JsonProperty("stats")
    Stats stats;

    @JsonProperty("timestamp")
    Instant timestamp;

    @JsonProperty("underlying_index")
    BigDecimal underlyingIndex;

    @JsonProperty("underlying_price")
    BigDecimal underlyingPrice;

    @Data
    @Builder
    @Jacksonized
    public static class Greeks {
      @JsonProperty("delta")
      BigDecimal delta;

      @JsonProperty("gamma")
      BigDecimal gamma;

      @JsonProperty("rho")
      BigDecimal rho;

      @JsonProperty("theta")
      BigDecimal theta;

      @JsonProperty("vega")
      BigDecimal vega;
    }

    public enum State {
      @JsonProperty("open")
      OPEN,

      @JsonProperty("closed")
      CLOSED,

      @JsonEnumDefaultValue
      UNKNOWN
    }

    @Data
    @Builder
    @Jacksonized
    public static class Stats {
      @JsonProperty("high")
      BigDecimal high;

      @JsonProperty("low")
      BigDecimal low;

      @JsonProperty("price_change")
      BigDecimal priceChange;

      @JsonProperty("volume")
      BigDecimal volume;

      @JsonProperty("volume_notional")
      BigDecimal volumeNotional;

      @JsonProperty("volume_usd")
      BigDecimal volumeUsd;
    }
  }
}
