package nostro.xchange.persistence;

import java.sql.Timestamp;

public class SyncTaskEntity {
    private String symbol;
    private Timestamp timestamp;
    private String document;

    public SyncTaskEntity(String symbol, Timestamp timestamp, String document) {
        this.symbol = symbol;
        this.timestamp = timestamp;
        this.document = document;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
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
}
