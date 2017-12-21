package info.bitrich.xchangestream.binance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.binance.dto.marketdata.BinanceOrderbook;

import java.util.List;

public class DepthBinanceWebSocketTransaction extends ProductBinanceWebSocketTransaction {

    private final BinanceOrderbook orderBook;

    public DepthBinanceWebSocketTransaction(
            @JsonProperty("e") String eventType,
            @JsonProperty("E") String eventTime,
            @JsonProperty("s") String symbol,
            @JsonProperty("u") long lastUpdateId,
            @JsonProperty("b") List<Object[]> _bids,
            @JsonProperty("a") List<Object[]> _asks
    ) {
        super(eventType, eventTime, symbol);
        orderBook = new BinanceOrderbook(lastUpdateId, _bids, _asks);
    }

    public BinanceOrderbook getOrderBook() {
        return orderBook;
    }
}
