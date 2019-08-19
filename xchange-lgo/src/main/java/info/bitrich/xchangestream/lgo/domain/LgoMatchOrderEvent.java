package info.bitrich.xchangestream.lgo.domain;

import org.knowm.xchange.dto.Order;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Order matched against another order
 */
public class LgoMatchOrderEvent extends LgoOrderEvent {
    /**
     * Trade identifier
     */
    private final String tradeId;

    /**
     * Trade price (quote currency)
     */
    private final BigDecimal tradePrice;

    /**
     * Trade quantity (base currency)
     */
    private final BigDecimal filledQuantity;

    /**
     * Remaining amount (base currency)
     */
    private final BigDecimal remainingQuantity;

    /**
     * Fees (quote currency)
     */
    private final BigDecimal fees;

    /**
     * Trade liquidity (T for taker, M for maker)
     */
    private final String liquidity;

    /**
     * Trade type (BID/ASK): taker order type
     */
    private final Order.OrderType orderType;

    public LgoMatchOrderEvent(Long batchId, String type, String orderId, Date time, String tradeId, BigDecimal tradePrice, BigDecimal filledQuantity, BigDecimal remainingQuantity, BigDecimal fees, String liquidity, Order.OrderType orderType) {
        super(batchId, type, orderId, time);
        this.tradeId = tradeId;
        this.tradePrice = tradePrice;
        this.filledQuantity = filledQuantity;
        this.remainingQuantity = remainingQuantity;
        this.fees = fees;
        this.liquidity = liquidity;
        this.orderType = orderType;
    }

    public String getTradeId() {
        return tradeId;
    }

    public BigDecimal getTradePrice() {
        return tradePrice;
    }

    public BigDecimal getFilledQuantity() {
        return filledQuantity;
    }

    public BigDecimal getRemainingQuantity() {
        return remainingQuantity;
    }

    public BigDecimal getFees() {
        return fees;
    }

    public String getLiquidity() {
        return liquidity;
    }

    public Order.OrderType getOrderType() {
        return orderType;
    }
}
