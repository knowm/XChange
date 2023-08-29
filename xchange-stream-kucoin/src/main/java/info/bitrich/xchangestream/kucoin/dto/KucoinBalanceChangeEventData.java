package info.bitrich.xchangestream.kucoin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class KucoinBalanceChangeEventData {

    @JsonProperty("total")
    private BigDecimal total;
    @JsonProperty("available")
    private BigDecimal available;
    @JsonProperty("availableChange")
    private BigDecimal availableChange;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("hold")
    private BigDecimal hold;
    @JsonProperty("holdChange")
    private BigDecimal holdChange;
    @JsonProperty("relationEvent")
    private String relationEvent;
    @JsonProperty("relationEventId")
    private String relationEventId;
    @JsonProperty("relationContext")
    private RelationContext relationContext;
    @JsonProperty("time")
    private Long time;

    @Data
    public static class RelationContext {

        @JsonProperty("tradeId")
        private String tradeId;
        @JsonProperty("orderId")
        private String orderId;
        @JsonProperty("symbol")
        private String symbol;
    }
}
