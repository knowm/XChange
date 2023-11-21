package info.bitrich.xchangestream.binance.dto;

import static org.knowm.xchange.utils.StreamUtils.singletonCollector;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import java.util.Map;
import lombok.Getter;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.dto.marketdata.BinanceKline;
import org.knowm.xchange.binance.dto.marketdata.KlineInterval;

@Getter
public class KlineBinanceWebSocketTransaction extends BaseBinanceWebSocketTransaction {

    private final String symbol;
    private final Map<String, Object> kline;
    private final KlineInterval klineInterval;

    public KlineBinanceWebSocketTransaction(
            @JsonProperty("e") String eventType,
            @JsonProperty("E") String eventTime,
            @JsonProperty("s") String symbol,
            @JsonProperty("k") Map<String, Object> kline) {
        super(eventType, eventTime);
        this.symbol = symbol;
        this.kline = kline;
        this.klineInterval =  Arrays.stream(KlineInterval.values())
                .filter(i -> i.code().equals(kline.get("i")))
                .collect(singletonCollector());
    }

    private static Object[] getParameters(Map<String, Object> kline) {
        Object[] parameters = new Object[12];
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
        parameters[11] = kline.get("x");
        return parameters;
    }

    public BinanceKline toBinanceKline(boolean isFuture) {
        return new BinanceKline(BinanceAdapters.adaptSymbol(symbol, isFuture), klineInterval, getParameters(kline));
    }
}
