package info.bitrich.xchangestream.binance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.dto.marketdata.BinanceKline;
import org.knowm.xchange.binance.dto.marketdata.KlineInterval;

import java.util.Arrays;
import java.util.Map;

import static org.knowm.xchange.utils.StreamUtils.singletonCollector;

public class KlineBinanceWebSocketTransaction extends BaseBinanceWebSocketTransaction {

    protected final BinanceKline binanceKline;

    public KlineBinanceWebSocketTransaction(
            @JsonProperty("e") String eventType,
            @JsonProperty("E") String eventTime,
            @JsonProperty("s") String symbol,
            @JsonProperty("k") Map<String, Object> kline) {
        super(eventType, eventTime);
        KlineInterval klineInterval =  Arrays.stream(KlineInterval.values())
                .filter(i -> i.code().equals(kline.get("i")))
                .collect(singletonCollector());
        this.binanceKline = new BinanceKline(BinanceAdapters.adaptSymbol(symbol), klineInterval, getParameters(kline));
    }

    private static Object[] getParameters(Map<String, Object> kline) {
        Object[] parameters = new Object[11];
        parameters[0] = kline.get("t");
        parameters[1] = kline.get("o");
        parameters[2] = kline.get("h");
        parameters[3] = kline.get("l");
        parameters[4] = kline.get("c");
        parameters[5] = kline.get("v");
        parameters[6] = kline.get("T");
        parameters[7] = kline.get("q");
        parameters[8] = kline.get("n");
        parameters[9] = kline.get("V");
        parameters[10] = kline.get("Q");
        return parameters;
    }

    public BinanceKline getBinanceKline() {
        return binanceKline;
    }
}
