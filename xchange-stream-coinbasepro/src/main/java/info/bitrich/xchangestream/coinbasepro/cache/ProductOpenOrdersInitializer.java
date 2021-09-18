package info.bitrich.xchangestream.coinbasepro.cache;

import info.bitrich.xchangestream.coinbasepro.dto.CoinbaseProOrderBuilder;
import info.bitrich.xchangestream.coinbasepro.dto.CoinbaseProWebSocketTransaction;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProProductBook;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProProductBookEntry;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProProductBookEntryLevel3;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProOrder;
import org.knowm.xchange.coinbasepro.service.CoinbaseProMarketDataService;
import org.knowm.xchange.coinbasepro.service.CoinbaseProTradeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProductOpenOrdersInitializer {
    private static final Logger LOG = LoggerFactory.getLogger(ProductOpenOrdersInitializer.class);

    private final CoinbaseProMarketDataService marketDataService;
    private final CoinbaseProTradeService tradeService;
    private final String product;
    private CoinbaseProProductBook orderBook;
    private CoinbaseProOrder[] openOrders;

    private boolean initiated;

    private List<CoinbaseProWebSocketTransaction> transactions;

    public ProductOpenOrdersInitializer(String product, CoinbaseProMarketDataService marketDataService, CoinbaseProTradeService tradeService) {
        this.marketDataService = marketDataService;
        this.tradeService = tradeService;
        this.product = product;
        transactions = new ArrayList<>();
        initiated = false;
    }

    private void init() throws IOException {
        CurrencyPair currencyPair = new CurrencyPair(product.replace('-', '/'));

        openOrders = tradeService.getCoinbaseProProductOpenOrders(product);
        for (int i = 0; i < openOrders.length; i++) {
            if ("active".equals(openOrders[i].getStatus())) {
                openOrders[i] = tradeService.getOrder(openOrders[i].getId());
            }
        }
        orderBook = marketDataService.getCoinbaseProProductOrderBook(currencyPair, 3);
        initiated = true;
    }

    public void processWebSocketTransaction(CoinbaseProWebSocketTransaction transaction) throws IOException {
        if (!initiated) {
            init();
        }
        switch (transaction.getType()) {
            case "received":
            case "open":
            case "match":
            case "done":
            case "activate":
                transactions.add(transaction);
        }
    }

    public List<CoinbaseProOrder> initializeOpenOrders() {
        List<String> orderIds = Stream.concat(
                transactions.stream().map(CoinbaseProWebSocketTransaction::getOrderId),
                Arrays.stream(openOrders).map(CoinbaseProOrder::getId)
        ).distinct().collect(Collectors.toList());

        Map<String, CoinbaseProOrder> openOrdersMap = Arrays.stream(openOrders).collect(Collectors.toMap(CoinbaseProOrder::getId, o -> o));
        Map<String, CoinbaseProProductBookEntryLevel3> asks = filterOrderBookEntries(orderBook.getAsks(), orderIds);
        Map<String, CoinbaseProProductBookEntryLevel3> bids = filterOrderBookEntries(orderBook.getBids(), orderIds);
        Map<String, List<CoinbaseProWebSocketTransaction>> wsOrders = transactions.stream()
                .collect(Collectors.groupingBy(CoinbaseProWebSocketTransaction::getOrderId));

        return orderIds.stream().map(orderId -> syncOrder(wsOrders.get(orderId), openOrdersMap.get(orderId), asks.get(orderId), bids.get(orderId))).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private CoinbaseProOrder syncOrder(List<CoinbaseProWebSocketTransaction> wsOrders, CoinbaseProOrder openOrder, CoinbaseProProductBookEntryLevel3 ask, CoinbaseProProductBookEntryLevel3 bid) {
        if (wsOrders == null) {
            if (ask == null && bid == null) {
                if ("active".equals(openOrder.getStatus())) {
                    // it is a stop order, it should not be present in the order book.
                    return openOrder;
                }
                LOG.error("Order must be done, but messages are missed");
                return null;
            }
            return openOrder;
        } else {
            if (wsOrders.stream().anyMatch(t -> "done".equals(t.getType()))) {
                // order is done.
                return null;
            } else
            if (openOrder == null) {
                // order was created after we poll open orders
                CoinbaseProOrder order = createOrder(wsOrders);
                if (order != null && order.getSize() != null && order.getFilledSize() != null) {
                    if (ask != null) {
                        if (ask.getVolume().compareTo(order.getSize().subtract(order.getFilledSize())) != 0) {
                            LOG.error("Stream has a difference with order book. Order book: " + ask + ", stream: " + Arrays.toString(wsOrders.toArray()));
                        }
                    } else if (bid != null) {
                        if (bid.getVolume().compareTo(order.getSize().subtract(order.getFilledSize())) != 0) {
                            LOG.error("Stream has a difference with order book. Order book: " + bid + ", stream: " + Arrays.toString(wsOrders.toArray()));
                        }
                    }
                }
                return order;
            } else {
                if (ask == null && bid == null) {
                    LOG.error("Order must be done, but done message is missed");
                    return null;
                }
                // Order is in open orders and in the order book. Just need to restore it according to web socket data.
                return restoreOrder(wsOrders, openOrder, ask == null ? bid : ask);
            }
        }
    }

    private int getType(String type) {
        switch(type) {
            case "activate":
                return 0;
            case "received":
                return 1;
            case "match":
                return 2;
            case "open":
                return 3;
            case "done":
                return 4;
        }

        return 5;
    }

    private void sortWebSocketOrders(List<CoinbaseProWebSocketTransaction> wsOrders) {
        wsOrders.sort(Comparator.comparing(CoinbaseProWebSocketTransaction::getSequence).thenComparingInt(t -> this.getType(t.getType())));
    }

    private CoinbaseProOrder createOrder(List<CoinbaseProWebSocketTransaction> wsOrders) {
        sortWebSocketOrders(wsOrders);

        CoinbaseProWebSocketTransaction received = wsOrders.stream().filter(t -> "received".equals(t.getType())).findFirst().orElse(null);

        if (received != null) {
            CoinbaseProOrder order = CoinbaseProOrderBuilder.from(received);

            for (CoinbaseProWebSocketTransaction t : wsOrders) {
                order = CoinbaseProOrderBuilder.from(order, t).build();
            }
            return order;
        } else {
            LOG.error("Received message is missed.");
            CoinbaseProWebSocketTransaction open = wsOrders.stream().filter(t -> "open".equals(t.getType())).findFirst().orElse(null);
            if (open != null) {
                CoinbaseProOrder order = CoinbaseProOrderBuilder.from(open);

                for (CoinbaseProWebSocketTransaction t : wsOrders) {
                    order = CoinbaseProOrderBuilder.from(order, t).build();
                }
                return order;
            }
        }

        LOG.error("Received and open messages are missed.");
        return null;
    }

    private CoinbaseProOrder restoreOrder(List<CoinbaseProWebSocketTransaction> wsOrders, CoinbaseProOrder order, CoinbaseProProductBookEntryLevel3 book) {
        BigDecimal openOrdersRemaining = order.getSize().subtract(order.getFilledSize());
        if (openOrdersRemaining.compareTo(book.getVolume()) == 0) {
            // Order remaining size is the same as in the order book. No need apply web socket data.
            return order;
        }

        BigDecimal totalWebSocketSize = wsOrders.stream()
                .filter(s -> "match".equals(s.getType()))
                .map(CoinbaseProWebSocketTransaction::getSize)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal difference = totalWebSocketSize.subtract(openOrdersRemaining.subtract(book.getVolume()));

        sortWebSocketOrders(wsOrders);

        for (CoinbaseProWebSocketTransaction t : wsOrders) {
            if (difference.signum() > 0) {
                if ("match".equals(t.getType())) {
                    difference = difference.subtract(t.getSize());
                    if (difference.signum() < 0) {
                        LOG.error("restore order is not accurate. Order: " + order.toString() + ", book: " + book + ", messages: " + Arrays.toString(wsOrders.toArray()));
                    }
                }
            } else {
                order = CoinbaseProOrderBuilder.from(order, t).build();
            }
        }

        return order;
    }

    private Map<String, CoinbaseProProductBookEntryLevel3> filterOrderBookEntries(CoinbaseProProductBookEntry[] entries, List<String> orderIds) {
        return Arrays.stream(entries)
                .map(CoinbaseProProductBookEntryLevel3.class::cast)
                .filter(entry -> orderIds.contains(entry.getOrderId()))
                .collect(Collectors.toMap(CoinbaseProProductBookEntryLevel3::getOrderId, o -> o));
    }

    public Long getSequence() {
        return orderBook == null ? Long.MAX_VALUE : orderBook.getSequence();
    }
}
