package org.knowm.xchange.deribit.v1.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeribitOrder {

    @JsonProperty("quantity") public int quantity;
    @JsonProperty("amount") public int amount;
    @JsonProperty("price") public float price;
    @JsonProperty("cm") public int cm;
    @JsonProperty("cm_amount") public int cmAmount;
    @JsonProperty("oid") public String oid;


    public int getQuantity() {
        return quantity;
    }

    public int getAmount() {
        return amount;
    }

    public float getPrice() {
        return price;
    }

    public int getCm() {
        return cm;
    }

    public int getCmAmount() {
        return cmAmount;
    }

    public String getOid() {
        return oid;
    }
}
