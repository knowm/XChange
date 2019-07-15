package info.bitrich.xchangestream.kraken.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.kraken.dto.enums.KrakenSubscriptionName;

/**
 * @author makarid, pchertalev
 **/
public class KrakenSubscriptionConfig {

    /**
     * ticker|ohlc|trade|book|spread|*, * for all (ohlc interval value is 1 if all channels subscribed)
     */
    private KrakenSubscriptionName name;

    /**
     * Optional - depth associated with book subscription in number of levels each side, default 10. Valid Options are: 10, 25, 100, 500, 1000
     */
    private Integer depth;

    public KrakenSubscriptionConfig(KrakenSubscriptionName name) {
        this(name, null);
    }

    @JsonCreator
    public KrakenSubscriptionConfig(@JsonProperty("name") KrakenSubscriptionName name, @JsonProperty("depth") Integer depth) {
        this.name = name;
        this.depth = depth;
    }

    public KrakenSubscriptionName getName() {
        return name;
    }

    public void setName(KrakenSubscriptionName name) {
        this.name = name;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }
}
