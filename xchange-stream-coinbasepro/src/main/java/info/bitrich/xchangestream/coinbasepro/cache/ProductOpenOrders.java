package info.bitrich.xchangestream.coinbasepro.cache;

import info.bitrich.xchangestream.coinbasepro.CoinbaseProStreamingExchange;
import info.bitrich.xchangestream.coinbasepro.dto.CoinbaseProOrderBuilder;
import info.bitrich.xchangestream.coinbasepro.dto.CoinbaseProWebSocketTransaction;
import org.knowm.xchange.coinbasepro.CoinbaseProAdapters;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProOrder;
import org.knowm.xchange.coinbasepro.service.CoinbaseProMarketDataService;
import org.knowm.xchange.coinbasepro.service.CoinbaseProTradeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

public class ProductOpenOrders {
    private static final Logger LOG = LoggerFactory.getLogger(ProductOpenOrders.class);
    private final String product;
    private final CoinbaseProTradeService tradeService;
    private List<CoinbaseProOrder> openOrders;
    private final List<CoinbaseProOrder> doneOrders;

    private final ProductOpenOrdersInitializer initializer;

    private boolean inited;

    public ProductOpenOrders(String product, CoinbaseProMarketDataService marketDataService, CoinbaseProTradeService tradeService) {
        this.product = product;
        this.tradeService = tradeService;
        openOrders = new ArrayList<>();
        doneOrders = new ArrayList<>();
        initializer = new ProductOpenOrdersInitializer(product, marketDataService, tradeService);
        inited = false;
    }

    public OpenOrders getOpenOrders() {
        return CoinbaseProAdapters.adaptOpenOrders(openOrders.toArray(new CoinbaseProOrder[0]));
    }

    public List<CoinbaseProOrder> getCoinbaseProOpenOrders() {
        return new ArrayList<>(openOrders);
    }

    private CoinbaseProOrder getOpenOrder(String orderId) {
        return openOrders.stream().filter(o -> o.getId().equals(orderId))
                .findFirst().orElse(null);
    }

    public Order getOrder(String orderId) {
        return Stream.concat(openOrders.stream(), doneOrders.stream())
                .filter(o -> o.getId().equals(orderId))
                .findFirst()
                .map(CoinbaseProAdapters::adaptOrder).orElse(null);
    }

    public void processWebSocketTransaction(CoinbaseProWebSocketTransaction transaction) throws IOException {
        if (!inited) {
            if (transaction.getSequence() <= initializer.getSequence()) {
                initializer.processWebSocketTransaction(transaction);
                return;
            } else {
                openOrders = initializer.initializeOpenOrders();
                inited = true;
            }
        }
        if ("heartbeat".equals(transaction.getType())) {
            return;
        }

        CoinbaseProOrder order = getOpenOrder(transaction.getOrderId());

        if (order == null) {
            if ("received".equals(transaction.getType())) {
                order = CoinbaseProOrderBuilder.from(transaction);
                openOrders.add(order);
            } else {
                LOG.error("Order is not in open order but there is a message.");
            }
            return;
        }

        switch (transaction.getType()) {
            case "open":
            case "match":
            case "done": {
                order = CoinbaseProOrderBuilder.from(order, transaction).build();
                final CoinbaseProOrder finalOrder = order;
                openOrders.removeIf(order1 -> order1.getId().equals(finalOrder.getId()));

                if ("done".equals(order.getStatus())) {
                    doneOrders.add(order);
                } else {
                    openOrders.add(order);
                }
            }
        }
    }

    public boolean isInited() {
        return inited;
    }
}
