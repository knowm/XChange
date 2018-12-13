package org.knownm.xchange.dvchain.dto.marketdata;

import java.math.BigDecimal;

public class DVChainLevel {
    private final BigDecimal sellPrice;
    private final BigDecimal buyPrice;
    private final BigDecimal maxQuantity;

    public DVChainLevel(BigDecimal sellPrice, BigDecimal buyPrice, BigDecimal maxQuantity) {
       this.buyPrice = buyPrice;
       this.sellPrice = sellPrice;
       this.maxQuantity = maxQuantity;
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public BigDecimal getMaxQuantity() {
        return maxQuantity;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }
}
