package org.knowm.xchange.bitfinex.v2.dto.trade;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.bitfinex.config.converter.StringToBooleanConverter;
import org.knowm.xchange.bitfinex.config.converter.StringToCurrencyConverter;
import org.knowm.xchange.bitfinex.config.converter.StringToCurrencyPairConverter;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;

@Data
@Builder
@Jacksonized
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
public class BitfinexTrade {

  private String id;

  /** Pair (BTCUSD, …) */
  @JsonDeserialize(converter = StringToCurrencyPairConverter.class)
  private CurrencyPair symbol;

  /** Execution timestamp millis */
  private Instant timestamp;

  /** Order id */
  private String orderId;

  /** Positive means buy, negative means sell */
  private BigDecimal execAmount;

  /** Execution price */
  private BigDecimal execPrice;

  /** Order type */
  private OrderType type;

  /** Order price */
  private BigDecimal orderPrice;

  @JsonDeserialize(converter = StringToBooleanConverter.class)
  private Boolean isMaker;

  /** Fee */
  private BigDecimal fee;

  /** Fee currency */
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  private Currency feeCurrency;

  public enum OrderType {
    LIMIT,

    @JsonProperty("EXCHANGE LIMIT")
    EXCHANGE_LIMIT,

    MARKET,

    @JsonProperty("EXCHANGE MARKET")
    EXCHANGE_MARKET,

    STOP,

    @JsonProperty("EXCHANGE STOP")
    EXCHANGE_STOP,

    @JsonProperty("STOP LIMIT")
    STOP_LIMIT,

    @JsonProperty("EXCHANGE STOP LIMIT")
    EXCHANGE_STOP_LIMIT,

    @JsonProperty("TRAILING STOP")
    TRAILING_STOP,

    @JsonProperty("EXCHANGE TRAILING STOP")
    EXCHANGE_TRAILING_STOP,

    FOK,

    @JsonProperty("EXCHANGE FOK")
    EXCHANGE_FOK,

    IOC,

    @JsonProperty("EXCHANGE IOC")
    EXCHANGE_IOC;
  }
}
