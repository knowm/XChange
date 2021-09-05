package nostro.xchange.persistence;

import java.sql.Timestamp;

public class OrderEntity {
    private String id;
    private String externalId;
    private String instrument;
    private boolean terminal;
    private Timestamp created;
    private Timestamp updated;
    private String document;

    private OrderEntity(Builder builder) {
        setId(builder.id);
        setExternalId(builder.externalId);
        setTerminal(builder.terminal);
        setInstrument(builder.instrument);
        setCreated(builder.created);
        setUpdated(builder.updated);
        setDocument(builder.document);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public boolean isTerminal() {
        return terminal;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public void setTerminal(boolean terminal) {
        this.terminal = terminal;
    }


    public static final class Builder {
        private String id;
        private String externalId;
        private String instrument;
        private boolean terminal;
        private Timestamp created;
        private Timestamp updated;
        private String document;

        public Builder() {
        }

        public Builder id(String val) {
            id = val;
            return this;
        }

        public Builder externalId(String val) {
            externalId = val;
            return this;
        }

        public Builder instrument(String val) {
            instrument = val;
            return this;
        }

        public Builder terminal(boolean val) {
            terminal = val;
            return this;
        }

        public Builder created(Timestamp val) {
            created = val;
            return this;
        }

        public Builder updated(Timestamp val) {
            updated = val;
            return this;
        }

        public Builder document(String val) {
            document = val;
            return this;
        }

        public OrderEntity build() {
            return new OrderEntity(this);
        }
    }
}
