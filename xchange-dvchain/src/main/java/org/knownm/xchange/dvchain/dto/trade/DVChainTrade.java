package org.knownm.xchange.dvchain.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knownm.xchange.dvchain.dto.account.DVChainUser;

import java.math.BigDecimal;
import java.time.Instant;

public class DVChainTrade {
    private final String id;
    private final Instant createdAt;
    private final BigDecimal price;
    private final BigDecimal quantity;
    private final String side;
    private final String asset;
    private final String status;
    private final DVChainUser user;

    public DVChainTrade(
            @JsonProperty("_id") String id,
            Instant createdAt,
            BigDecimal price,
            BigDecimal quantity,
            String side,
            DVChainUser user,
            String asset,
            String status
    ) {
        this.id = id;
        this.createdAt = createdAt;
        this.price = price;
        this.quantity = quantity;
        this.side = side;
        this.user = user;
        this.asset = asset;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public String getSide() {
        return side;
    }

    public DVChainUser getUser() {
        return user;
    }

    public String getAsset() {
        return asset;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("DVChainTrade {id=");
        builder.append(id);
//        builder.append()
        return builder.toString();
    }
}