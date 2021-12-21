package info.bitrich.xchangestream.binance.futures.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.binance.futures.BinanceFuturesAdapter;
import org.knowm.xchange.instrument.Instrument;

public class AccountConfigUpdateData {
    public final int leverage;
    public final String symbol;

    public AccountConfigUpdateData(
            @JsonProperty("l") int leverage,
            @JsonProperty("s") String symbol) {
        this.leverage = leverage;
        this.symbol = symbol;
    }

    public Instrument getInstrument() {
        if (symbol != null) return BinanceFuturesAdapter.adaptInstrument(symbol);
        return null;
    }
}
