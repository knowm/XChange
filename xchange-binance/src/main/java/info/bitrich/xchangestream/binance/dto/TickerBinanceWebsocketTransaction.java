package info.bitrich.xchangestream.binance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

import org.knowm.xchange.binance.dto.marketdata.BinanceTicker24h;

public class TickerBinanceWebsocketTransaction extends ProductBinanceWebSocketTransaction {

    private final BinanceTicker24h ticker;

    public TickerBinanceWebsocketTransaction(
            @JsonProperty("e") String eventType,
            @JsonProperty("E") String eventTime,
            @JsonProperty("s") String symbol,
            @JsonProperty("p") BigDecimal priceChange,
            @JsonProperty("P") BigDecimal priceChangePercent,
            @JsonProperty("w") BigDecimal weightedAvgPrice,
            @JsonProperty("x") BigDecimal prevClosePrice,
            @JsonProperty("c") BigDecimal lastPrice,
            @JsonProperty("Q") BigDecimal lastQty,
            @JsonProperty("b") BigDecimal bidPrice,
            @JsonProperty("B") BigDecimal bidQty,
            @JsonProperty("a") BigDecimal askPrice,
            @JsonProperty("A") BigDecimal askQty,
            @JsonProperty("o") BigDecimal openPrice,
            @JsonProperty("h") BigDecimal highPrice,
            @JsonProperty("l") BigDecimal lowPrice,
            @JsonProperty("v") BigDecimal volume,
            @JsonProperty("q") BigDecimal quoteVolume,
            @JsonProperty("O") Long openTime,
            @JsonProperty("C") Long closeTime,
            @JsonProperty("F") Long firstId,
            @JsonProperty("L") Long lastId,
            @JsonProperty("n") Long count) {

        super(eventType, eventTime, symbol);

        ticker = new BinanceTicker24h(
                priceChange,
                priceChangePercent,
                weightedAvgPrice,
                prevClosePrice,
                lastPrice,
                lastQty,
                bidPrice,
                bidQty,
                askPrice,
                askQty,
                openPrice,
                highPrice,
                lowPrice,
                volume,
                quoteVolume,
                openTime,
                closeTime,
                firstId,
                lastId,
                count);
        ticker.setCurrencyPair(currencyPair);
    }

    public BinanceTicker24h getTicker() {
        return ticker;
    }

}
