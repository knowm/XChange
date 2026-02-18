package info.bitrich.xchangestream.binance.dto.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.binance.dto.trade.OrderSide;

/** DTO for order amend/cancel/replace endpoint as per issue description. */
@Getter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BinanceWebsocketOrderAmendPayload {
  private final String symbol;
  private final OrderSide side;
  private final BigDecimal quantity;
  private final BigDecimal price;
  private long timestamp;

  // Optional
  private final PriceMatch priceMatch;
  private Long recvWindow;
  private final Long orderId;
  private final String origClientOrderId;

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
}
