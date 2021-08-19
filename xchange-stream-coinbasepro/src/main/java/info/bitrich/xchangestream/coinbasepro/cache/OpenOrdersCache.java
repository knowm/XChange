package info.bitrich.xchangestream.coinbasepro.cache;

import info.bitrich.xchangestream.coinbasepro.dto.CoinbaseProChannelProducts;
import info.bitrich.xchangestream.coinbasepro.dto.CoinbaseProWebSocketTransaction;
import org.knowm.xchange.coinbasepro.CoinbaseProAdapters;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProOrder;
import org.knowm.xchange.coinbasepro.service.CoinbaseProMarketDataService;
import org.knowm.xchange.coinbasepro.service.CoinbaseProTradeService;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class OpenOrdersCache {
    private static final Logger LOG = LoggerFactory.getLogger(OpenOrdersCache.class);
    private Map<String, ProductOpenOrders> products;
    private final CoinbaseProMarketDataService marketDataService;
    private final CoinbaseProTradeService tradeService;
    private boolean inited;

    public OpenOrdersCache(CoinbaseProMarketDataService marketDataService, CoinbaseProTradeService tradeService) {
        this.marketDataService = marketDataService;
        this.tradeService = tradeService;
        products = new HashMap<>();
        inited = false;
    }

    private void init(CoinbaseProChannelProducts[] channels) {
        this.products = new HashMap<>();
        for (CoinbaseProChannelProducts channel : channels) {
            if ("user".equals(channel.getName())) {
                for (String product_id : channel.getProduct_ids()) {
                    this.products.put(product_id, new ProductOpenOrders(product_id, marketDataService, tradeService));
                }
                break;
            }
        }
        inited = true;
    }

    public void processWebSocketTransaction(CoinbaseProWebSocketTransaction transaction) {
        try {
            if ("subscriptions".equals(transaction.getType()) ||
                    "received".equals(transaction.getType()) ||
                    "open".equals(transaction.getType()) ||
                    "done".equals(transaction.getType()) ||
                    "match".equals(transaction.getType()) && transaction.getUserId() != null) {

                if ("subscriptions".equals(transaction.getType())) {
                    init(transaction.getChannels());
                } else {
                    ProductOpenOrders cache = products.get(transaction.getProductId());
                    if (cache != null) {
                        cache.processWebSocketTransaction(transaction);
                    }
                }
            }
        }catch(Exception e) {
            LOG.error("Exception during transaction - " + transaction.toString(), e);
        }
    }

    public boolean isInited() {
        if (!inited) {
            return false;
        }
        for (Map.Entry<String, ProductOpenOrders> p : products.entrySet()) {
            if (!p.getValue().isInited()) {
                return false;
            }
        }
        return true;
    }

    public OpenOrders getOpenOrders() {
        return CoinbaseProAdapters.adaptOpenOrders(
                products.values().stream()
                        .map(ProductOpenOrders::getCoinbaseProOpenOrders)
                        .toArray(CoinbaseProOrder[]::new));
    }

    public Order getOrder(String orderId) {
        return products.values().stream()
                .map(p -> p.getOrder(orderId))
                .filter(Objects::nonNull)
                .findFirst().orElse(null);
    }
}
