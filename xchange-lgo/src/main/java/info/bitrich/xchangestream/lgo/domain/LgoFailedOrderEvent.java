package info.bitrich.xchangestream.lgo.domain;

import java.util.Date;

/**
 * Order could not be added to a batch.
 * No batchId for this event.
 */
public class LgoFailedOrderEvent extends LgoOrderEvent {

    /**
     * Reference set by the trader
     */
    private final String reference;

    /**
     * Reason of failure (InvalidQuantity, InvalidPrice, InvalidAmount, InvalidPriceIncrement, InvalidProduct, InsufficientFunds)
     */
    private final String reason;

    public LgoFailedOrderEvent(String type, String orderId, Date time, String reference, String reason) {
        super(null, type, orderId, time);
        this.reference = reference;
        this.reason = reason;
    }

    public String getReference() {
        return reference;
    }

    public String getReason() {
        return reason;
    }
}
