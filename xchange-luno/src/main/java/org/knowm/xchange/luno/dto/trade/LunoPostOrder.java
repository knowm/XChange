package org.knowm.xchange.luno.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LunoPostOrder {

    public final String orderId;
    
    public LunoPostOrder(@JsonProperty(value="order_id", required=true) String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "LunoPostOrder [orderId=" + orderId + "]";
    }
}
