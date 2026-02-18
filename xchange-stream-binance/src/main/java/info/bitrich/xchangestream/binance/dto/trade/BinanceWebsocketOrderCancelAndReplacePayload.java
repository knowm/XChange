package info.bitrich.xchangestream.binance.dto.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import org.knowm.xchange.binance.dto.trade.OrderSide;
import org.knowm.xchange.binance.dto.trade.OrderType;
import org.knowm.xchange.binance.dto.trade.TimeInForce;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BinanceWebsocketOrderCancelAndReplacePayload {

  private String symbol;
  private CancelReplaceMode cancelReplaceMode;
  private Long cancelOrderId;
  private String cancelOrigClientOrderId;
  //    private String cancelNewClientOrderId;

  private OrderSide side;
  private OrderType type;
  private TimeInForce timeInForce;
  private BigDecimal price;
  private BigDecimal quantity;
  private String newClientOrderId;
  private Long recvWindow;
  private Long timestamp;

  public enum CancelReplaceMode {
    STOP_ON_FAILURE,
    ALLOW_FAILURE;
  }
}
