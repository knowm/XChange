package info.bitrich.xchangestream.kraken.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.kraken.dto.enums.KrakenEventType;

import java.util.List;

/**
 * @author pchertalev
 */
public class KrakenSubscriptionMessage extends KrakenEvent {

    @JsonProperty
    private final Integer reqid;

    /**
     * Array of currency pairs (pair1,pair2,pair3).
     */
    @JsonProperty("pair")
    private final List<String> pairs;

    @JsonProperty("subscription")
    private final KrakenSubscriptionConfig subscription;

    @JsonCreator
    public KrakenSubscriptionMessage(@JsonProperty("reqid") Integer reqid, @JsonProperty("event") KrakenEventType event,
                                     @JsonProperty("pair") List<String> pairs, @JsonProperty("subscription") KrakenSubscriptionConfig subscription) {
        super(event);
        this.reqid = reqid;
        this.pairs = pairs;
        this.subscription = subscription;
    }

    public List<String> getPairs() {
        return pairs;
    }

    public KrakenSubscriptionConfig getSubscription() {
        return subscription;
    }

    public Integer getReqid() {
        return reqid;
    }
}
