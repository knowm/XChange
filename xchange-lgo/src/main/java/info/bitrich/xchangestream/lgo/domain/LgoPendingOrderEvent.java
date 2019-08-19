package info.bitrich.xchangestream.lgo.domain;

import org.knowm.xchange.dto.Order;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Order was received by LGO execution engine in a batch
 */
public class LgoPendingOrderEvent extends LgoOrderEvent {

    /**
     * Type of Order (L for LimitOrder, M for MarketOrder) when type=pending
     */
    private final String orderType;

    /**
     * Limit price (quote currency) when type=pending and orderType=L
     */
    private final BigDecimal limitPrice;

    /**
     * Side of the order: BID/ASK when type=pending
     */
    private final Order.OrderType side;

    /**
     * Initial amount (base currency) when type=pending
     */
    private final BigDecimal initialAmount;

    public LgoPendingOrderEvent(Long batchId, String type, String orderId, Date time, String orderType, BigDecimal limitPrice, Order.OrderType side, BigDecimal initialAmount) {
        super(batchId, type, orderId, time);
        this.orderType = orderType;
        this.limitPrice = limitPrice;
        this.side = side;
        this.initialAmount = initialAmount;
    }

    public String getOrderType() {
        return orderType;
    }

    public BigDecimal getLimitPrice() {
        return limitPrice;
    }

    public Order.OrderType getSide() {
        return side;
    }

    public BigDecimal getInitialAmount() {
        return initialAmount;
    }
}
