package info.bitrich.xchangestream.binance;

import info.bitrich.xchangestream.binance.dto.DepthBinanceWebSocketTransaction;
import org.knowm.xchange.binance.service.BinanceMarketDataService;

public class BinanceFuturesStreamingMarketDataService extends BinanceStreamingMarketDataService {
    public BinanceFuturesStreamingMarketDataService(BinanceStreamingService service, BinanceMarketDataService marketDataService, Runnable onApiCall, String orderBookUpdateFrequencyParameter) {
        super(service, marketDataService, onApiCall, orderBookUpdateFrequencyParameter);
    }

    @Override
    protected boolean checkDepthDataInOrder(DepthBinanceWebSocketTransaction depth, long lastUpdateId) {
        return lastUpdateId == depth.getLastUpdateIdFromPreviousEvent();
    }
}
