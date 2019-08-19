package info.bitrich.xchangestream.lgo.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Order left orderbook
 */
public class LgoDoneOrderEvent extends LgoOrderEvent {

    /**
     * Reason of done status (rejected, canceled, canceledBySelfTradePrevention, filled)
     */
    private final String reason;

    /**
     * Amount canceled (base currency) when reason=canceledBySelfTradePrevention
     */
    private final BigDecimal canceled;

    public LgoDoneOrderEvent(Long batchId, String type, String orderId, Date time, String reason, BigDecimal canceled) {
        super(batchId, type, orderId, time);
        this.reason = reason;
        this.canceled = canceled;
    }

    public String getReason() {
        return reason;
    }

    public BigDecimal getCanceled() {
        return canceled;
    }
}
