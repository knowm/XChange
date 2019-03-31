package org.knowm.xchange.deribit.v1.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeribitTrade {

    @JsonProperty("tradeId") public int tradeId;
    @JsonProperty("instrument") public String instrument;
    @JsonProperty("tradeSeq") public int tradeSeq;
    @JsonProperty("timeStamp") public long timeStamp;
    @JsonProperty("quantity") public int quantity;
    @JsonProperty("amount") public int amount;
    @JsonProperty("price") public float price;
    @JsonProperty("direction") public String direction;
    @JsonProperty("tickDirection") public int tickDirection;
    @JsonProperty("indexPrice") public float indexPrice;
    @JsonProperty("iv") public float iv;


    public int getTradeId() {
        return tradeId;
    }

    public String getInstrument() {
        return instrument;
    }

    public int getTradeSeq() {
        return tradeSeq;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getAmount() {
        return amount;
    }

    public float getPrice() {
        return price;
    }

    public String getDirection() {
        return direction;
    }

    public int getTickDirection() {
        return tickDirection;
    }

    public float getIndexPrice() {
        return indexPrice;
    }

    public float getIv() {
        return iv;
    }
}
