package info.bitrich.xchangestream.kucoin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class KucoinRawTickerEventData {
    @JsonProperty("sequence")
    private Long sequence;
    @JsonProperty("price")
    private BigDecimal price;
    @JsonProperty("size")
    private BigDecimal size;
    @JsonProperty("bestAsk")
    private BigDecimal bestAsk;
    @JsonProperty("bestAskSize")
    private BigDecimal bestAskSize;
    @JsonProperty("bestBid")
    private BigDecimal bestBid;
    @JsonProperty("bestBidSize")
    private BigDecimal bestBidSize;
}
