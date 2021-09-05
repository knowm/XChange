package nostro.xchange.binance.sync;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.utils.ObjectMapperHelper;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;

public class SyncTaskDocument {
    private final long orderId;
    private final long tradeId;

    public SyncTaskDocument(
            @JsonProperty("orderId") long orderId,
            @JsonProperty("tradeId") long tradeId) {
        this.orderId = orderId;
        this.tradeId = tradeId;
    }

    public long getOrderId() {
        return orderId;
    }

    public long getTradeId() {
        return tradeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SyncTaskDocument that = (SyncTaskDocument) o;
        return orderId == that.orderId &&
                tradeId == that.tradeId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, tradeId);
    }

    public static SyncTaskDocument read(String s) {
        try {
            return ObjectMapperHelper.readValue(s, SyncTaskDocument.class);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static String write(SyncTaskDocument d) {
        return ObjectMapperHelper.toCompactJSON(d);
    }
}