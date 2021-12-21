package info.bitrich.xchangestream.binance.futures.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserAccountConfigUpdateData {
    public final boolean multiAssetsMode;

    public UserAccountConfigUpdateData(
            @JsonProperty("j") boolean multiAssetsMode) {
        this.multiAssetsMode = multiAssetsMode;
    }
}
