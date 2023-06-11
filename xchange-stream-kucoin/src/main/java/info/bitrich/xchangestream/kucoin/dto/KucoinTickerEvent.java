package info.bitrich.xchangestream.kucoin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.ToString;
import org.knowm.xchange.dto.marketdata.Ticker;

@ToString
public class KucoinTickerEvent {
    @JsonProperty("data") @JsonDeserialize(converter = KucoinTickerConverter.class)
    public Ticker ticker;

    public Ticker getTicker() {
        return ticker;
    }
}
