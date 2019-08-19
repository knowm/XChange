package info.bitrich.xchangestream.lgo.domain;

import java.util.Date;

/**
 * Order was received by LGO platform.
 * No batchId for this event.
 */
public class LgoReceivedOrderEvent extends LgoOrderEvent {

    private final String reference;

    public LgoReceivedOrderEvent(String type, String orderId, Date time, String reference) {
        super(null, type, orderId, time);
        this.reference = reference;
    }

    public String getReference() {
        return reference;
    }
}
