package nostro.xchange.persistence;

import java.sql.Timestamp;

public class BalanceEntity {
    private String asset;
    private Timestamp timestamp;
    private boolean zero;
    private String document;

    private BalanceEntity(Builder builder) {
        setAsset(builder.asset);
        setTimestamp(builder.timestamp);
        setZero(builder.zero);
        setDocument(builder.document);
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isZero() {
        return zero;
    }

    public void setZero(boolean zero) {
        this.zero = zero;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }
    
    public static final class Builder {
        private String asset;
        private Timestamp timestamp;
        private boolean zero;
        private String document;

        public Builder() {
        }

        public Builder(BalanceEntity copy) {
            this.asset = copy.getAsset();
            this.timestamp = copy.getTimestamp();
            this.zero = copy.isZero();
            this.document = copy.getDocument();
        }

        public Builder asset(String val) {
            asset = val;
            return this;
        }

        public Builder timestamp(Timestamp val) {
            timestamp = val;
            return this;
        }

        public Builder zero(boolean val) {
            zero = val;
            return this;
        }

        public Builder document(String val) {
            document = val;
            return this;
        }

        public BalanceEntity build() {
            return new BalanceEntity(this);
        }
    }
}
