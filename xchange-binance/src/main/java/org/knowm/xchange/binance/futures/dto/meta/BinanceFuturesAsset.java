package org.knowm.xchange.binance.futures.dto.meta;

import org.knowm.xchange.binance.dto.meta.exchangeinfo.RateLimit;

public class BinanceFuturesAsset {
    private String asset;
    private String autoAssetExchange;
    private boolean marginAvailable;

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getAutoAssetExchange() {
        return autoAssetExchange;
    }

    public void setAutoAssetExchange(String autoAssetExchange) {
        this.autoAssetExchange = autoAssetExchange;
    }

    public boolean isMarginAvailable() {
        return marginAvailable;
    }

    public void setMarginAvailable(boolean marginAvailable) {
        this.marginAvailable = marginAvailable;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BinanceFuturesAsset{");
        sb.append("asset='").append(asset).append('\'');
        sb.append(", autoAssetExchange='").append(autoAssetExchange).append('\'');
        sb.append(", marginAvailable=").append(marginAvailable);
        sb.append('}');
        return sb.toString();
    }
}
