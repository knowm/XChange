package info.bitrich.xchangestream.kucoin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class KucoinRawMatchEventData {
  @JsonProperty("sequence")
  private Long sequence;
  @JsonProperty("type")
  private String type;
  @JsonProperty("symbol")
  private String symbol;
  @JsonProperty("side")
  private String side;
  @JsonProperty("price")
  private BigDecimal price;
  @JsonProperty("size")
  private BigDecimal size;
  @JsonProperty("tradeId")
  private String tradeId;
  @JsonProperty("takerOrderId")
  private String takerOrderId;
  @JsonProperty("makerOrderId")
  private String makerOrderId;
  @JsonProperty("time")
  private Long time;
}
