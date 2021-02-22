package info.bitrich.xchangestream.dydx;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.core.ProductSubscription;

import info.bitrich.xchangestream.dydx.dto.dydxWebSocketSubscriptionMessage;
import info.bitrich.xchangestream.dydx.dto.dydxWebSocketTransaction;
import info.bitrich.xchangestream.service.netty.JsonNettyStreamingService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Observable;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: Max Gao (gaamox@tutanota.com)
 * Created: 20-02-2021
 * Notes:
 */
public class dydxStreamingService extends JsonNettyStreamingService {

    private static final Logger LOG = LoggerFactory.getLogger(dydxStreamingService.class);
    private static final String SUBSCRIBE = "subscribe";
    private static final String UNSUBSCRIBE = "unsubscribe";
    private static final String ORDERBOOK = "orderbook";

    private final String apiUri;
    private final boolean batchMessages;
    private ProductSubscription productSubscription;

    private final Map<String, Observable<JsonNode>> subscriptions = new ConcurrentHashMap<>();

    public dydxStreamingService(String apiUri, boolean batchMessages) {
        super(apiUri, Integer.MAX_VALUE);
        this.apiUri = apiUri;
        this.batchMessages = batchMessages;
    }

    @Override
    protected String getChannelNameFromMessage(JsonNode message) {
        return ""; //TODO: Parse the message and grab the channel name from it
    }

    public void subscribeMultipleCurrencyPairs(ProductSubscription... products) {
        this.productSubscription = products[0];
    }

    public Observable<dydxWebSocketTransaction> getRawWebsocketTransactions(CurrencyPair currencyPair, String channelName) {
        final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();
        return subscribeChannel(channelName)
                .map(msg -> mapper.readValue(msg.toString(), dydxWebSocketTransaction.class))
                .filter(t -> channelName.equals(t.getChannel()));
    }

    /**
     * The connection to dydx will have one channel per channel type.
     * Each channel may receive messages for multiple currency pairs.
     * @param channelName
     * @param args
     * @return
     */
    @Override
    public Observable<JsonNode> subscribeChannel(String channelName, Object... args) {
        if (!channels.containsKey(channelName) && !subscriptions.containsKey(channelName)) {
            subscriptions.put(channelName, super.subscribeChannelMultipleMessages(channelName, args));
        }

        return subscriptions.get(channelName);
    }

    /**
     * To subscribe to multiple pairs over a single connection, dydx requires the client to send multiple subscription
     * messages.
     * @param channelName
     * @param args
     * @return
     */
    @Override
    public List<String> getSubscribeMessages(String channelName, Object... args) throws IOException {
        List<String> subscriptionMessages = new ArrayList<>();

        switch(channelName) {
            case ORDERBOOK:
                if (productSubscription != null && productSubscription.getOrderBook() != null) {
                    for (CurrencyPair currencyPair: productSubscription.getOrderBook()) {
                        dydxWebSocketSubscriptionMessage subscriptionMessage = new dydxWebSocketSubscriptionMessage(
                                SUBSCRIBE, channelName, currencyPair.toString(), batchMessages
                        );
                                subscriptionMessages.add(
                                objectMapper.writeValueAsString(subscriptionMessage));
                    }
                }
                break;
            default:
                return null;
        }

        return subscriptionMessages;
    }

    /**
     * Don't use this for dydx.
     */
    @Override
    public String getSubscribeMessage(String channelName, Object... args) throws IOException {
        return null;
    }

    /**
     * Don't use this for dydx.
     */
    @Override
    public String getUnsubscribeMessage(String channelName) throws IOException {
        return null;
    }


}
