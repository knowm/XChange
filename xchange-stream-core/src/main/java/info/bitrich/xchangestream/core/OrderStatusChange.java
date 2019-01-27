package info.bitrich.xchangestream.core;

import java.util.Date;

/**
 * Highly generalised representation of a status change for orders which can be
 * provided in an authenticated stream by exchanges. Note that this does not
 * cover all the granular options any particular exchange supports; it is
 * intended to be a lowest common denominator.
 *
 * <p>The information returned here can be extended in future, provided that
 * it can be shown that there is widespread support for that data in
 * supported exchanges.</p>
 *
 * @author Graham Crockford
 */
public final class OrderStatusChange {

    private final OrderStatusChangeType type;
    private final String orderId;
    private final Date timestamp;

    public static Builder create() {
        return new Builder();
    }

    private OrderStatusChange(Builder builder) {
        this.type = builder.type;
        this.orderId = builder.orderId;
        this.timestamp = builder.timestamp;
    }

    /**
     * @return The id of the order which changed.
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * @return The event type affecting the order.
     */
    public OrderStatusChangeType getType() {
        return type;
    }

    /**
     * @return The time of the status change.
     */
    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "OrderStatusChange [type=" + type + ", orderId=" + orderId + ", timestamp=" + timestamp + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
        result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OrderStatusChange other = (OrderStatusChange) obj;
        if (orderId == null) {
            if (other.orderId != null)
                return false;
        } else if (!orderId.equals(other.orderId))
            return false;
        if (timestamp == null) {
            if (other.timestamp != null)
                return false;
        } else if (!timestamp.equals(other.timestamp))
            return false;
        if (type != other.type)
            return false;
        return true;
    }

    public static final class Builder {

        private OrderStatusChangeType type;
        private String orderId;
        private Date timestamp;

        private Builder() {
        }

        public Builder type(OrderStatusChangeType type) {
            this.type = type;
            return this;
        }

        public Builder orderId(String orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder timestamp(Date timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public OrderStatusChange build() {
            return new OrderStatusChange(this);
        }
    }
}