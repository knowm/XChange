package info.bitrich.xchangestream.kucoin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.kucoin.KucoinAdapters;

@ToString
public class KucoinOrderEventData {

    @JsonProperty("symbol")
    public String symbol;

    @JsonProperty("orderType")
    public String orderType;

    @JsonProperty("side")
    public String side;

    @JsonProperty("orderId")
    public String orderId;

    @JsonProperty("type")
    public String type;

    @JsonProperty("orderTime")
    public long orderTime;

    @JsonProperty("size")
    public String size;

    @JsonProperty("filledSize")
    public String filledSize;

    @JsonProperty("price")
    public String price;

    @JsonProperty("clientOid")
    public String clientOid;

    @JsonProperty("remainSize")
    public String remainSize;

    @JsonProperty("matchPrice")
    public String matchPrice;

    @JsonProperty("matchSize")
    public String matchSize;

    @JsonProperty("status")
    public String status;

    @JsonProperty("ts")
    public long timestamp;

    public CurrencyPair getCurrencyPair() {
        return KucoinAdapters.adaptCurrencyPair(symbol);
    }
}
