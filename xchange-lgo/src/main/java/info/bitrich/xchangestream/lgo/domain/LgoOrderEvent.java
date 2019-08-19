package info.bitrich.xchangestream.lgo.domain;

import java.util.Date;

public abstract class LgoOrderEvent {

    /**
     * Identifier of the batch where the event happened.
     * Null in the case of a type=received event. Not null otherwise
     */
    private final Long batchId;

    /**
     * Type of the event (all events).
     */
    private final String type;

    /**
     * Identifier of the order concerned by the event (all events)
     */
    private final String orderId;

    /**
     * Date when the event happend, UTC (all events)
     */
    private final Date time;

    protected LgoOrderEvent(Long batchId, String type, String orderId, Date time) {
        this.batchId = batchId;
        this.type = type;
        this.orderId = orderId;
        this.time = time;
    }

    public Long getBatchId() {
        return batchId;
    }

    public String getType() {
        return type;
    }

    public String getOrderId() {
        return orderId;
    }

    public Date getTime() {
        return time;
    }
}
