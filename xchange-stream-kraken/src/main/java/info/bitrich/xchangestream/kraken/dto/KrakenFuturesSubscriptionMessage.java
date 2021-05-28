package info.bitrich.xchangestream.kraken.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.kraken.dto.enums.KrakenEventType;
import info.bitrich.xchangestream.kraken.dto.enums.KrakenSubscriptionName;

import java.util.List;

/**
 * @author pchertalev
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KrakenFuturesSubscriptionMessage extends KrakenEvent {

    /** Optional, client originated ID reflected in response message. */
//  @JsonProperty private final Integer reqid;

    /**
     * Optional - Array of currency pairs. Format of each pair is "A/B", where A and B are ISO 4217-A3
     * for standardized assets and popular unique symbol if not standardized.
     */
    @JsonProperty(value = "product_ids", required = false)
    private final List<String> product_ids;

    @JsonProperty
    private final KrakenSubscriptionName feed;


    @JsonCreator
    public KrakenFuturesSubscriptionMessage(
            @JsonProperty("event") KrakenEventType event,
            @JsonProperty("feed") KrakenSubscriptionName feed,
            @JsonProperty("product_ids") List<String> product_ids) {
        super(event);
        this.feed = feed;
        this.product_ids = product_ids;
    }

    @JsonProperty(value = "product_ids", required = false)
    public List<String> getProduct_ids() {
        return product_ids;
    }

    public KrakenSubscriptionName getFeed() {
        return feed;
    }
}
