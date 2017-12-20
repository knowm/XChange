package info.bitrich.xchangestream.binance.dto;

import java.util.List;

import org.knowm.xchange.binance.dto.marketdata.BinanceOrderbook;

import com.fasterxml.jackson.annotation.JsonProperty;

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
