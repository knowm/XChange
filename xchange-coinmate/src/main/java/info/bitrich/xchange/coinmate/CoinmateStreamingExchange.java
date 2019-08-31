package info.bitrich.xchange.coinmate;

import com.pusher.client.util.HttpAuthorizer;
import com.pusher.client.util.UrlEncodedConnectionFactory;
import info.bitrich.xchange.coinmate.dto.auth.PusherAuthParamsObject;
import info.bitrich.xchangestream.core.*;
import info.bitrich.xchangestream.service.pusher.PusherStreamingService;
import io.reactivex.Completable;
import org.knowm.xchange.coinmate.CoinmateExchange;

public class CoinmateStreamingExchange extends CoinmateExchange implements StreamingExchange {
    private static final String API_KEY = "af76597b6b928970fbb0";
    private PusherStreamingService streamingService;

    private CoinmateStreamingMarketDataService streamingMarketDataService;
    private CoinmateStreamingAccountService streamingAccountService;
    private CoinmateStreamingTradeService streamingTradeService;

    public CoinmateStreamingExchange() {

    }

    private void createExchange() {
        if (exchangeSpecification.getApiKey() != null) {
            PusherAuthParamsObject params = new PusherAuthParamsObject(
                    exchangeSpecification.getSecretKey(),
                    exchangeSpecification.getApiKey(),
                    exchangeSpecification.getUserName(),
                    getNonceFactory().createValue()
            );
            UrlEncodedConnectionFactory urlEncodedConnectionFactory = new UrlEncodedConnectionFactory(params.getParams());
            HttpAuthorizer authorizer = new HttpAuthorizer("https://www.coinmate.io/api/pusherAuth", urlEncodedConnectionFactory);
            streamingService = new PusherStreamingService(API_KEY, "mt1", authorizer);
        } else {
            streamingService = new PusherStreamingService(API_KEY);
        }

    }

    @Override
    protected void initServices() {
        super.initServices();
        createExchange();
        streamingMarketDataService = new CoinmateStreamingMarketDataService(streamingService);
        streamingAccountService = new CoinmateStreamingAccountService(streamingService, exchangeSpecification.getUserName());
        streamingTradeService = new CoinmateStreamingTradeService(streamingService, exchangeSpecification.getUserName());
    }

    @Override
    public Completable connect(ProductSubscription... args) {
        return streamingService.connect();
    }

    @Override
    public Completable disconnect() {
        return streamingService.disconnect();
    }

    @Override
    public StreamingMarketDataService getStreamingMarketDataService() {
        return streamingMarketDataService;
    }

    @Override
    public StreamingTradeService getStreamingTradeService() {
        return streamingTradeService;
    }

    @Override
    public StreamingAccountService getStreamingAccountService() {
        return streamingAccountService;
    }

    @Override
    public boolean isAlive() {
        return streamingService.isSocketOpen();
    }

    @Override
    public void useCompressedMessages(boolean compressedMessages) { streamingService.useCompressedMessages(compressedMessages); }
}
