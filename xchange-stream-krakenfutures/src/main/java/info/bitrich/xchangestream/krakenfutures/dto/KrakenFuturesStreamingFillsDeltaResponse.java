package info.bitrich.xchangestream.krakenfutures.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
public class KrakenFuturesStreamingFillsDeltaResponse {

    private final String feed;
    private final String username;
    private final List<KrakenFuturesStreamingFill> fills;

    public KrakenFuturesStreamingFillsDeltaResponse(
            @JsonProperty("feed") String feed,
            @JsonProperty("username") String username,
            @JsonProperty("fills") List<KrakenFuturesStreamingFill> fills) {
        this.feed = feed;
        this.username = username;
        this.fills = fills;
    }

    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class KrakenFuturesStreamingFill {

        private final String instrument;
        private final Date time;
        private final BigDecimal price;
        private final Long seq;
        private final boolean buy;
        private final BigDecimal qty;
        private final String order_id;
        private final String cli_ord_id;
        private final String fill_id;
        private final String fill_type;
        private final BigDecimal fee_paid;
        private final String fee_currency;

        public KrakenFuturesStreamingFill(
                @JsonProperty("instrument") String instrument,
                @JsonProperty("time") Date time,
                @JsonProperty("price") BigDecimal price,
                @JsonProperty("seq") Long seq,
                @JsonProperty("buy") boolean buy,
                @JsonProperty("qty") BigDecimal qty,
                @JsonProperty("order_id") String order_id,
                @JsonProperty("cli_ord_id") String cli_ord_id,
                @JsonProperty("fill_id") String fill_id,
                @JsonProperty("fill_type") String fill_type,
                @JsonProperty("fee_paid") BigDecimal fee_paid,
                @JsonProperty("fee_currency") String fee_currency) {
            this.instrument = instrument;
            this.time = time;
            this.price = price;
            this.seq = seq;
            this.buy = buy;
            this.qty = qty;
            this.order_id = order_id;
            this.cli_ord_id = cli_ord_id;
            this.fill_id = fill_id;
            this.fill_type = fill_type;
            this.fee_paid = fee_paid;
            this.fee_currency = fee_currency;
        }
    }
}
