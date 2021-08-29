package nostro.xchange.persistence;

import java.sql.Timestamp;

public class TradeEntity {
    private long id;
    private String orderId;
    private String externalId;
    private Timestamp timestamp;
    private String document;

    private TradeEntity(Builder builder) {
        setId(builder.id);
        setOrderId(builder.orderId);
        setExternalId(builder.externalId);
        setTimestamp(builder.timestamp);
        setDocument(builder.document);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public static final class Builder {
        private long id;
        private String orderId;
        private String externalId;
        private Timestamp timestamp;
        private String document;

        public Builder() {
        }

        public Builder id(long val) {
            id = val;
            return this;
        }

        public Builder orderId(String val) {
            orderId = val;
            return this;
        }

        public Builder externalId(String val) {
            externalId = val;
            return this;
        }

        public Builder timestamp(Timestamp val) {
            timestamp = val;
            return this;
        }

        public Builder document(String val) {
            document = val;
            return this;
        }

        public TradeEntity build() {
            return new TradeEntity(this);
        }
    }
}
