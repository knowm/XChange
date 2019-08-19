package info.bitrich.xchangestream.lgo.domain;

import java.util.Date;

/**
 * Order entered order book
 */
public class LgoOpenOrderEvent extends LgoOrderEvent {

    public LgoOpenOrderEvent(Long batchId, String type, String orderId, Date time) {
        super(batchId, type, orderId, time);
    }

}
