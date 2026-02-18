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
import org.knowm.xchange.bitfinex.config.converter.StringToCurrencyPairConverter;
import org.knowm.xchange.bitfinex.config.converter.StringToOrderStatusConverter;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderStatus;

@Data
@Builder
@Jacksonized
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
public class BitfinexOrderDetails {

  /** Order ID */
  private Long id;

  /** Group ID */
  private Long gid;

  /** Client Order ID */
  private Long clientOrderId;

  /** Pair (tBTCUSD, …) */
  @JsonDeserialize(converter = StringToCurrencyPairConverter.class)
  private CurrencyPair currencyPair;

  /** Millisecond timestamp of creation */
  private Instant createdAt;

  /** Millisecond timestamp of update */
  private Instant updatedAt;

  /** Positive means buy, negative means sell. */
  private BigDecimal amount;

  /** Original amount. */
  private BigDecimal amountOrig;

  /** The type of the order */
  private OrderType type;

  /** Previous order type */
  private OrderType previousType;

  /** Epoch timestamp for TIF (Time-In-Force) * */
  private Instant timeInForceTimestamp;

  private Object placeHolder11;

  private Long flags;

  @JsonDeserialize(converter = StringToOrderStatusConverter.class)
  private OrderStatus status;

  private Object placeHolder14;
  private Object placeHolder15;

  /** Price */
  private BigDecimal price;

  /** Average price */
  private BigDecimal priceAvg;

  /** The trailing price */
  private BigDecimal priceTrailing;

  /** Auxiliary Limit price (for STOP LIMIT) */
  private BigDecimal priceAuxLimit;

  private Object placeHolder20;
  private Object placeHolder21;
  private Object placeHolder22;

  @JsonDeserialize(converter = StringToBooleanConverter.class)
  private Boolean shouldTriggerNotification;

  @JsonDeserialize(converter = StringToBooleanConverter.class)
  private Boolean hidden;

  /** If another order caused this order to be placed (OCO) this will be that other order's ID */
  private Long causeOrderId;

  private Object placeHolder26;
  private Object placeHolder27;

  /** Indicates origin of action: BFX, API>BFX * */
  private String routing;

  private Object placeHolder29;
  private Object placeHolder30;

  /**
   * Additional meta information about the order ( $F7 = IS_POST_ONLY (0 if false, 1 if true), $F33
   * = Leverage (int), aff_code: "aff_code_here") *
   */
  private Object meta;

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
