package nostro.xchange.persistence;

import java.sql.Timestamp;

public class FundingEntity {
    private long id;
    private String externalId;
    private String type;
    private Timestamp timestamp;
    private String document;

    private FundingEntity(Builder builder) {
        setId(builder.id);
        setExternalId(builder.externalId);
        setType(builder.type);
        setTimestamp(builder.timestamp);
        setDocument(builder.document);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        private String externalId;
        private String type;
        private Timestamp timestamp;
        private String document;

        public Builder() {
        }

        public Builder id(long val) {
            id = val;
            return this;
        }

        public Builder externalId(String val) {
            externalId = val;
            return this;
        }

        public Builder type(String val) {
            type = val;
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

        public FundingEntity build() {
            return new FundingEntity(this);
        }
    }
}

