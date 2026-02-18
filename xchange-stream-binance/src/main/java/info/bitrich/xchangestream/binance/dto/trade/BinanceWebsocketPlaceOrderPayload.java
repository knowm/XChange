package info.bitrich.xchangestream.binance.dto.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.knowm.xchange.binance.dto.trade.BinanceNewOrder;
import org.knowm.xchange.binance.dto.trade.OrderSide;
import org.knowm.xchange.binance.dto.trade.OrderType;
import org.knowm.xchange.binance.dto.trade.TimeInForce;

/**
 * DTO representing Binance Futures new order request parameters. Fields map 1:1 to Binance API
 * docs. Optional fields may be null and are omitted from JSON.
 */
@Getter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BinanceWebsocketPlaceOrderPayload {

  // Required
  private final String symbol; // STRING YES
  private final OrderSide side; // ENUM YES
  private final OrderType type; // ENUM YES

  // Optional / conditional
  private final String positionSide; // ENUM NO (BOTH/LONG/SHORT)
  private final TimeInForce timeInForce; // ENUM NO
  private final BigDecimal quantity; // DECIMAL NO
  private final Boolean reduceOnly; // STRING NO -> boolean-like
  private final BigDecimal price; // DECIMAL NO
  private final String newClientOrderId; // STRING NO
  private final BigDecimal stopPrice; // DECIMAL NO
  private final Boolean closePosition; // STRING NO -> boolean-like
  private final BigDecimal activationPrice; // DECIMAL NO
  private final BigDecimal callbackRate; // DECIMAL NO
  private final WorkingType workingType; // ENUM NO
  private final Boolean priceProtect; // STRING NO -> boolean-like
  private final BinanceNewOrder.NewOrderResponseType newOrderRespType; // ENUM NO
  private final PriceMatch priceMatch; // ENUM NO
  private final SelfTradePreventionMode selfTradePreventionMode; // ENUM NO
  private final Long goodTillDate; // LONG NO
  @Setter private Long recvWindow; // LONG NO
  @Setter private Long timestamp; // LONG YES

  public enum WorkingType {
    MARK_PRICE,
    CONTRACT_PRICE
  }

  public enum PriceMatch {
    OPPONENT,
    OPPONENT_5,
    OPPONENT_10,
    OPPONENT_20,
    QUEUE,
    QUEUE_5,
    QUEUE_10,
    QUEUE_20
  }

  public enum SelfTradePreventionMode {
    NONE,
    EXPIRE_TAKER,
    EXPIRE_MAKER,
    EXPIRE_BOTH
  }

  public BinanceWebsocketPlaceOrderPayload(
      @JsonProperty("symbol") String symbol,
      @JsonProperty("side") OrderSide side,
      @JsonProperty("type") OrderType type,
      @JsonProperty("positionSide") String positionSide,
      @JsonProperty("timeInForce") TimeInForce timeInForce,
      @JsonProperty("quantity") BigDecimal quantity,
      @JsonProperty("reduceOnly") Boolean reduceOnly,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("newClientOrderId") String newClientOrderId,
      @JsonProperty("stopPrice") BigDecimal stopPrice,
      @JsonProperty("closePosition") Boolean closePosition,
      @JsonProperty("activationPrice") BigDecimal activationPrice,
      @JsonProperty("callbackRate") BigDecimal callbackRate,
      @JsonProperty("workingType") WorkingType workingType,
      @JsonProperty("priceProtect") Boolean priceProtect,
      @JsonProperty("newOrderRespType") BinanceNewOrder.NewOrderResponseType newOrderRespType,
      @JsonProperty("priceMatch") PriceMatch priceMatch,
      @JsonProperty("selfTradePreventionMode") SelfTradePreventionMode selfTradePreventionMode,
      @JsonProperty("goodTillDate") Long goodTillDate,
      @JsonProperty("recvWindow") Long recvWindow,
      @JsonProperty("timestamp") Long timestamp) {
    this.symbol = symbol;
    this.side = side;
    this.type = type;
    this.positionSide = positionSide;
    this.timeInForce = timeInForce;
    this.quantity = quantity;
    this.reduceOnly = reduceOnly;
    this.price = price;
    this.newClientOrderId = newClientOrderId;
    this.stopPrice = stopPrice;
    this.closePosition = closePosition;
    this.activationPrice = activationPrice;
    this.callbackRate = callbackRate;
    this.workingType = workingType;
    this.priceProtect = priceProtect;
    this.newOrderRespType = newOrderRespType;
    this.priceMatch = priceMatch;
    this.selfTradePreventionMode = selfTradePreventionMode;
    this.goodTillDate = goodTillDate;
    this.recvWindow = recvWindow;
    this.timestamp = timestamp;
  }
}
