package info.bitrich.xchangestream.binance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Used for live subscription/unsubscription
 * Created by Danny Pageau
 */
public final class BinanceWebSocketSubscriptionMessage {

    private static final String METHOD = "method";
    private static final String PARAMS = "params";
    private static final String IDENTIFIER = "id";

    public enum MethodType {
        SUBSCRIBE, UNSUBSCRIBE;
    }

    @JsonProperty(METHOD)
    private MethodType method;

    @JsonProperty(PARAMS)
    private final List<String> params = new ArrayList<>();

    @JsonProperty(IDENTIFIER)
    private Integer identifier;

    public BinanceWebSocketSubscriptionMessage(final MethodType method, final String streamName, final int identifier) {

        super();
        this.method = method;
        this.params.add(streamName);
        this.identifier = identifier;
    }

    public MethodType getMethod() {
        return method;
    }

    public List<String> getParams() {
        return params;
    }

    public Integer getIdentifier() {
        return identifier;
    }
}
