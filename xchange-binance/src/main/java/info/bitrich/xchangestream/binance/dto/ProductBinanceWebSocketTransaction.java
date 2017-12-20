package info.bitrich.xchangestream.binance.dto;

import org.knowm.xchange.currency.CurrencyPair;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductBinanceWebSocketTransaction extends BaseBinanceWebSocketTransaction {
    
    protected final CurrencyPair currencyPair;
    
    public ProductBinanceWebSocketTransaction(
            @JsonProperty("e") String eventType,
            @JsonProperty("E") String eventTime,
            @JsonProperty("s") String symbol) {
        super(eventType, eventTime);
        currencyPair = new CurrencyPair(symbol.substring(0,3), symbol.substring(3,6));
    }

}
