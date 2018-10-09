package info.bitrich.xchangestream.gdax.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.core.ProductSubscription;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.gdax.dto.account.GDAXWebsocketAuthData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * GDAX subscription message.
 */
public class GDAXWebSocketSubscriptionMessage {

    public static final String TYPE = "type";
    public static final String CHANNELS = "channels";
    public static final String PRODUCT_IDS = "product_ids";
    public static final String NAME = "name";

    // if authenticating
    public static final String SIGNATURE = "signature";
    public static final String KEY = "key";
    public static final String PASSPHRASE = "passphrase";
    public static final String TIMESTAMP = "timestamp";

    class GDAXProductSubsctiption {
        @JsonProperty(NAME)
        private String name;

        @JsonProperty(PRODUCT_IDS)
        private String[] productIds;

        public GDAXProductSubsctiption(String name, String[] productIds, GDAXWebsocketAuthData authData) {
            this.name = name;
            this.productIds = productIds;
        }

        public String getName() {
            return name;
        }

        public String[] getProductIds() {
            return productIds;
        }
    }

    @JsonProperty(TYPE)
    private String type;

    @JsonProperty(CHANNELS)
    private GDAXProductSubsctiption[] channels;
    
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty(SIGNATURE)
    String signature;
    
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty(KEY)
    String key;
    
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty(PASSPHRASE)
    String passphrase;
    
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty(TIMESTAMP)
    String timestamp;

    public GDAXWebSocketSubscriptionMessage(String type, ProductSubscription product, GDAXWebsocketAuthData authData) {
        this.type = type;
        generateSubscriptionMessage(product, authData);
    }

    public GDAXWebSocketSubscriptionMessage(String type, String[] channelNames, GDAXWebsocketAuthData authData) {
        this.type = type;
        generateSubscriptionMessage(channelNames, authData);
    }

    private String[] generateProductIds(CurrencyPair[] pairs) {
        List<String> productIds = new ArrayList<>(pairs.length);
        for (CurrencyPair pair : pairs) {
            productIds.add(pair.base.toString() + "-" + pair.counter.toString());
        }

        return productIds.toArray(new String[productIds.size()]);
    }

    private GDAXProductSubsctiption generateGDAXProduct(String name, CurrencyPair[] pairs, GDAXWebsocketAuthData authData) {
        String[] productsIds;
        productsIds = generateProductIds(pairs);
        return new GDAXProductSubsctiption(name, productsIds, authData);
    }

    private void generateSubscriptionMessage(String[] channelNames, GDAXWebsocketAuthData authData) {
        List<GDAXProductSubsctiption> channels = new ArrayList<>(3);
        for (String name : channelNames) {
            channels.add(new GDAXProductSubsctiption(name, null, authData));
        }

        this.channels = channels.toArray(new GDAXProductSubsctiption[channels.size()]);
    }

    private void generateSubscriptionMessage(ProductSubscription productSubscription, GDAXWebsocketAuthData authData) {
        List<GDAXProductSubsctiption> channels = new ArrayList<>(3);
        Map<String, List<CurrencyPair>> pairs = new HashMap<>(3);

        pairs.put("level2", productSubscription.getOrderBook());
        pairs.put("ticker", productSubscription.getTicker());
        pairs.put("matches", productSubscription.getTrades());
        if ( authData != null )
            pairs.put("user", productSubscription.getTrades());

        for (Map.Entry<String, List<CurrencyPair>> product : pairs.entrySet()) {
            List<CurrencyPair> currencyPairs = product.getValue();
            if (currencyPairs == null || currencyPairs.size() == 0) {
                continue;
            }
            GDAXProductSubsctiption gdaxProduct = generateGDAXProduct(product.getKey(), product.getValue().toArray(new CurrencyPair[product.getValue().size()]), authData);
            channels.add(gdaxProduct);
        }

        this.channels = channels.toArray(new GDAXProductSubsctiption[channels.size()]);
        
        if ( authData != null ) {
	        this.key = authData.getKey();
	        this.passphrase = authData.getPassphrase();
	        this.signature = authData.getSignature();
	        this.timestamp = String.valueOf(authData.getTimestamp());
        }
    }

    public String getType() {
        return type;
    }

    public GDAXProductSubsctiption[] getChannels() {
        return channels;
    }
}
