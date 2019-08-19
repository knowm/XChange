package info.bitrich.xchangestream.lgo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class LgoAckUpdateData {

    private final String orderId;
    private final String reference;
    private final String type;
    private final Date time;
    private final String reason;

    public LgoAckUpdateData(
            @JsonProperty("order_id") String orderId,
            @JsonProperty("reference") String reference,
            @JsonProperty("type") String type,
            @JsonProperty("time") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") Date time,
            @JsonProperty("reason") String reason) {
        this.orderId = orderId;
        this.reference = reference;
        this.type = type;
        this.time = time;
        this.reason = reason;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getReference() {
        return reference;
    }

    public String getType() {
        return type;
    }

    public Date getTime() {
        return time;
    }

    public String getReason() {
        return reason;
    }
}
