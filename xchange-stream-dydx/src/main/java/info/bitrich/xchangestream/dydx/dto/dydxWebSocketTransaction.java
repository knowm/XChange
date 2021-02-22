package info.bitrich.xchangestream.dydx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author: Max Gao (gaamox@tutanota.com)
 * Created: 20-02-2021
 */
@NoArgsConstructor
@Getter
@Setter
public class dydxWebSocketTransaction {
    @JsonProperty("type")
    private String type;
    @JsonProperty("connection_id")
    private String connectionId;
    @JsonProperty("message_id")
    private String messageId;
    @JsonProperty("channel")
    private String channel;
    @JsonProperty("id")
    private String id;
    @JsonProperty("contents")
    private Contents contents;

    @Getter
    @Setter
    public static class Contents {
        @JsonProperty("updates")
        private Update updates[];
        @JsonProperty("bids")
        private Order bids[];
        @JsonProperty("asks")
        private Order asks[];
    }

    @Getter
    @Setter
    public static class Update {
        @JsonProperty("type")
        private String type;
        @JsonProperty("id")
        private String id;
        @JsonProperty("amount")
        private String amount;
        @JsonProperty("price")
        private String price;
        @JsonProperty("side")
        private String side;
    }

    @Getter
    @Setter
    public static class Order {
        @JsonProperty("id")
        private String id;
        @JsonProperty("uuid")
        private String uuid;
        @JsonProperty("amount")
        private String amount;
        @JsonProperty("price")
        private String price;
    }
}
