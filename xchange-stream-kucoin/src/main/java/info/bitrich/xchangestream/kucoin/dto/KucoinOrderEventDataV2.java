package info.bitrich.xchangestream.kucoin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.kucoin.KucoinAdapters;

@Data
public class KucoinOrderEventDataV2 {

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("orderType")
    private String orderType;

    @JsonProperty("side")
    private String side;

    @JsonProperty("orderId")
    private String orderId;

    @JsonProperty("type")
    private String type;

    @JsonProperty("orderTime")
    private Long orderTime;

    @JsonProperty("size")
    private BigDecimal size;

    @JsonProperty("filledSize")
    private BigDecimal filledSize;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("clientOid")
    private String clientOid;

    @JsonProperty("tradeId")
    private String tradeId;

    @JsonProperty("remainSize")
    private BigDecimal remainSize;

    @JsonProperty("matchPrice")
    private BigDecimal matchPrice;

    @JsonProperty("matchSize")
    private BigDecimal matchSize;

    @JsonProperty("status")
    private String status;

    @JsonProperty("ts")
    private Long timestamp;

    @JsonProperty("canceledSize")
    private BigDecimal canceledSize;

    @JsonProperty("canceledFunds")
    private BigDecimal canceledFunds;

    @JsonProperty("originSize")
    private BigDecimal originSize;

    @JsonProperty("originFunds")
    private BigDecimal originFunds;

    @JsonProperty("liquidity")
    private String liquidity;

    public CurrencyPair getCurrencyPair() {
        return KucoinAdapters.adaptCurrencyPair(symbol);
    }
}
