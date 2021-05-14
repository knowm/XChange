package info.bitrich.xchangestream.bitso.dto;

public class BitsoWebsocketAuthData {

    private final String userId;
    private final String key;
    private final String passphrase;
    private final String signature;
    private final long timestamp;

    public BitsoWebsocketAuthData(String userId, String key, String passphrase, String signature, long timestamp) {
        this.userId = userId;
        this.key = key;
        this.passphrase = passphrase;
        this.signature = signature;
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getKey() {
        return this.key;
    }

    public String getPassphrase() {
        return this.passphrase;
    }

    public String getSignature() {
        return this.signature;
    }

    public long getTimestamp() {
        return this.timestamp;
    }
}
