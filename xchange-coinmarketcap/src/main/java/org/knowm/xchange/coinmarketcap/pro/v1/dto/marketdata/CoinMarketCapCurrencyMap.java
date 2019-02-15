package org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.knowm.xchange.utils.jackson.ISO8601DateDeserializer;

import java.util.Date;

public final class CoinMarketCapCurrencyMap {
    private final int id;
    private final String name;
    private final String symbol;
    private final String slug;
    private final boolean isActive;
    private final Date firstHistoricalData;
    private final Date lastHistoricalData;
    private final CoinMarketCapPlatform platform;

    public CoinMarketCapCurrencyMap(
            @JsonProperty("id") int id,
            @JsonProperty("name") String name,
            @JsonProperty("symbol") String symbol,
            @JsonProperty("slug") String slug,
            @JsonProperty("is_active") int isActive,
            @JsonProperty("first_historical_data")
            @JsonDeserialize(using = ISO8601DateDeserializer.class)
                    Date firstHistoricalData,
            @JsonProperty("last_historical_data")
            @JsonDeserialize(using = ISO8601DateDeserializer.class)
                    Date lastHistoricalData,
            @JsonProperty("platform") CoinMarketCapPlatform platform) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.slug = slug;
        this.isActive = (1 == isActive);
        this.firstHistoricalData = firstHistoricalData;
        this.lastHistoricalData = lastHistoricalData;
        this.platform = platform;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getSlug() {
        return slug;
    }

    public boolean isActive() {
        return isActive;
    }

    public Date getFirstHistoricalData() {
        return firstHistoricalData;
    }

    public Date getLastHistoricalData() {
        return lastHistoricalData;
    }

    public CoinMarketCapPlatform getPlatform() {
        return platform;
    }

    @Override
    public String toString() {
        return "CoinMarketCapCurrencyMap{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                ", slug='" + slug + '\'' +
                ", isActive=" + isActive +
                ", firstHistoricalData=" + firstHistoricalData +
                ", lastHistoricalData=" + lastHistoricalData +
                ", platform=" + platform +
                '}';
    }
}
