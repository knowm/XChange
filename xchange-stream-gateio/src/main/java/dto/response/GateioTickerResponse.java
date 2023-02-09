package dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;

/** Author: Vladimir Karol Created: 09-02-2023 */
public class GateioTickerResponse {

    private final GateioTickerResponseResult result;
    private final Long time;
    private final Long timeMs;
    private final String channel;
    private final String event;

    public GateioTickerResponse(
            @JsonProperty("time") Long time,
            @JsonProperty("time_ms") Long timeMs,
            @JsonProperty("channel") String channel,
            @JsonProperty("event") String event,
            @JsonProperty("result") GateioTickerResponseResult result
    ) {
        this.result = result;
        this.time = time;
        this.timeMs = timeMs;
        this.channel = channel;
        this.event = event;
    }

    public Ticker toTicker() {
        return new Ticker.Builder()
                .instrument(new CurrencyPair(result.getCurrencyPair().replace("_", "-")))
                .ask(result.getLowestAsk())
                .bid(result.getHighestBid())
                .last(result.getLast())
                .low(result.getLow24hr())
                .high(result.getHigh24hr())
                .volume(result.getQuoteVolume())
                .quoteVolume(result.getBaseVolume())
                .percentageChange(result.getChangePercentage())
                .build();
    }

    public GateioTickerResponseResult getResult() {
        return result;
    }

    public Long getTime() {
        return time;
    }

    public Long getTimeMs() {
        return timeMs;
    }

    public String getChannel() {
        return channel;
    }

    public String getEvent() {
        return event;
    }

    @Override
    public String toString() {
        return "GateioTickerResponse{" +
                "result=" + result +
                ", time=" + time +
                ", timeMs=" + timeMs +
                ", channel='" + channel + '\'' +
                ", event='" + event + '\'' +
                '}';
    }
}
