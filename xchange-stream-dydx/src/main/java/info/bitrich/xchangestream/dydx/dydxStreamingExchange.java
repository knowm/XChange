package info.bitrich.xchangestream.dydx;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingAccountService;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingTradeService;
import io.reactivex.Completable;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.dydx.dydxExchange;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;

/**
 * Author: Max Gao (gaamox@tutanota.com)
 * Created: 20-02-2021
 */
public class dydxStreamingExchange extends dydxExchange implements StreamingExchange {
    private static final String API_URI = "wss://api.dydx.exchange/v1/ws";

    private dydxStreamingService streamingService;
    private dydxStreamingMarketDataService streamingMarketDataService;

    public dydxStreamingExchange() {}

    @Override
    public Completable connect(ProductSubscription... args) {
        if (args == null || args.length == 0)
            throw new UnsupportedOperationException("The ProductSubscription must be defined!");
        ExchangeSpecification exchangeSpec = getExchangeSpecification();

        boolean batchMessages =
                Boolean.TRUE.equals(exchangeSpec.getExchangeSpecificParametersItem(Parameters.PARAM_BATCHED));

        this.streamingService = new dydxStreamingService(API_URI, batchMessages);
        this.streamingMarketDataService = new dydxStreamingMarketDataService(streamingService);
        streamingService.subscribeMultipleCurrencyPairs(args);
        return streamingService.connect();
    }

    @Override
    public Completable disconnect() {
        dydxStreamingService service = streamingService;
        streamingService = null;
        streamingMarketDataService = null;
        return service.disconnect();
    }

    @Override
    public dydxStreamingMarketDataService getStreamingMarketDataService() {
        return streamingMarketDataService;
    }

    @Override
    public StreamingAccountService getStreamingAccountService() {
        throw new NotYetImplementedForExchangeException();
    }

    @Override
    public StreamingTradeService getStreamingTradeService() {
        throw new NotYetImplementedForExchangeException();
    }

    @Override
    public boolean isAlive() {
        return streamingService != null && streamingService.isSocketOpen();
    }

    @Override
    public void useCompressedMessages(boolean compressedMessages) {
        streamingService.useCompressedMessages(compressedMessages);
    }
}
