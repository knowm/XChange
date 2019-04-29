package org.knowm.xchange.deribit.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeribitTrade {

    @JsonProperty("trade_seq") public int tradeSeq;
    @JsonProperty("trade_id") public String tradeId;
    @JsonProperty("timestamp") public long timestamp;
    @JsonProperty("tick_direction") public int tickDirection;
    @JsonProperty("price") public BigDecimal price;
    @JsonProperty("instrument_name") public String instrumentName;
    @JsonProperty("index_price") public BigDecimal indexPrice;
    @JsonProperty("direction") public String direction;
    @JsonProperty("amount") public BigDecimal amount;


    public int getTradeSeq() {
        return tradeSeq;
    }

    public String getTradeId() {
        return tradeId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getTickDirection() {
        return tickDirection;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getInstrumentName() {
        return instrumentName;
    }

    public BigDecimal getIndexPrice() {
        return indexPrice;
    }

    public String getDirection() {
        return direction;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
