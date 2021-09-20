package info.bitrich.xchangestream.coinbasepro.cache;

import info.bitrich.xchangestream.coinbasepro.dto.CoinbaseProOrderBuilder;
import info.bitrich.xchangestream.coinbasepro.dto.CoinbaseProWebSocketTransaction;
import io.reactivex.rxjava3.processors.FlowableProcessor;
import org.knowm.xchange.coinbasepro.CoinbaseProAdapters;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProOrder;
import org.knowm.xchange.coinbasepro.service.CoinbaseProMarketDataService;
import org.knowm.xchange.coinbasepro.service.CoinbaseProTradeService;
import org.knowm.xchange.dto.Order;
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

    private boolean initiated;

    public ProductOpenOrders(String product, CoinbaseProMarketDataService marketDataService, CoinbaseProTradeService tradeService) {
        this.product = product;
        this.tradeService = tradeService;
        openOrders = new ArrayList<>();
        doneOrders = new ArrayList<>();
        initializer = new ProductOpenOrdersInitializer(product, marketDataService, tradeService);
        initiated = false;
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

    public void processWebSocketTransaction(CoinbaseProWebSocketTransaction transaction, FlowableProcessor<CoinbaseProOrder> orderUpdatePublisher) throws IOException {
        if (!initiated) {
            if (transaction.getSequence() <= initializer.getSequence()) {
                initializer.processWebSocketTransaction(transaction);
                return;
            } else {
                openOrders = initializer.initializeOpenOrders();
                initiated = true;
            }
        }
        if ("heartbeat".equals(transaction.getType())) {
            return;
        }

        LOG.info("Transaction received: " + transaction);

        CoinbaseProOrder order = getOpenOrder(transaction.getOrderId());

        if (order == null) {
            if ("activate".equals(transaction.getType()) || "received".equals(transaction.getType())) {
                order = CoinbaseProOrderBuilder.from(transaction);
                LOG.info("Order created: " + order);
                openOrders.add(order);
                orderUpdatePublisher.onNext(order);
            } else {
                LOG.error("Order is not in open order but there is a message.");
            }
            return;
        }

        switch (transaction.getType()) {
            case "open":
            case "match":
            case "done": {
                LOG.info("Order before transaction: " + order);
                order = CoinbaseProOrderBuilder.from(order, transaction).build();
                LOG.info("Order after transaction: " + order);
                final CoinbaseProOrder finalOrder = order;
                openOrders.removeIf(order1 -> order1.getId().equals(finalOrder.getId()));

                if ("done".equals(order.getStatus())) {
                    doneOrders.add(order);
                } else {
                    openOrders.add(order);
                }
                orderUpdatePublisher.onNext(order);
            }
            break;
            case "received": {
                if ("active".equals(order.getStatus())) {
                    final CoinbaseProOrder finalOrder = order;
                    openOrders.removeIf(order1 -> order1.getId().equals(finalOrder.getId()));
                    order = CoinbaseProOrderBuilder.from(transaction);
                    LOG.info("Order created: " + order);
                    openOrders.add(order);
                    orderUpdatePublisher.onNext(order);
                } else {
                    LOG.error("Order already exists in the cache.");
                }
            }
        }
    }

    public boolean isInitiated() {
        return initiated;
    }
}
