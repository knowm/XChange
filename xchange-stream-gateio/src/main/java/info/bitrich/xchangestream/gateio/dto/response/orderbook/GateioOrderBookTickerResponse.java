package info.bitrich.xchangestream.gateio.dto.response.orderbook;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Getter;

@Getter
public class GateioOrderBookTickerResponse {

  @JsonProperty("t")
  private Long timestamp;

  @JsonProperty("u")
  private String updateId;

  @JsonProperty("b")
  private BigDecimal bidPrice;

  @JsonProperty("B")
  private BigDecimal bidSize;

  @JsonProperty("a")
  private BigDecimal askPrice;

  @JsonProperty("A")
  private BigDecimal askSize;
}
