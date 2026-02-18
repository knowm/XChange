package info.bitrich.xchangestream.binance.dto.trade;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BinanceWebsocketOrderCancelPayload {

  private Long orderId;
  private String symbol;
  private String origClientOrderId;
  private String newClientOrderId;
  private Long recvWindow;
  private Long timestamp;
}
