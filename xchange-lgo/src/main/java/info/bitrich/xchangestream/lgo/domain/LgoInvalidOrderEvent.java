package info.bitrich.xchangestream.lgo.domain;

import java.util.Date;

/**
 * Order was invalid
 */
public class LgoInvalidOrderEvent extends LgoOrderEvent {

    /**
     * Reason of invalidity (InvalidQuantity, InvalidPrice, InvalidAmount, InvalidPriceIncrement, InvalidProduct, InsufficientFunds)
     */
    private final String reason;

    public LgoInvalidOrderEvent(Long batchId, String type, String orderId, Date time, String reason) {
        super(batchId, type, orderId, time);
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}
