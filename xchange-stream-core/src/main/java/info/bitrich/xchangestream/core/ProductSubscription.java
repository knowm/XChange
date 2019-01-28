package info.bitrich.xchangestream.core;

import org.knowm.xchange.currency.CurrencyPair;

import java.util.ArrayList;
import java.util.List;

/**
 * Use to specify subscriptions during the connect phase
 * For instancing, use builder @link {@link ProductSubscriptionBuilder}
 */
public class ProductSubscription {
    private final List<CurrencyPair> orderBook;
    private final List<CurrencyPair> trades;
    private final List<CurrencyPair> ticker;
    private final List<CurrencyPair> userTrades;
    private final List<CurrencyPair> orders;
    private final List<CurrencyPair> balances;

    private ProductSubscription(ProductSubscriptionBuilder builder) {
        this.orderBook = builder.orderBook;
        this.trades = builder.trades;
        this.ticker = builder.ticker;
        this.orders = builder.orders;
        this.userTrades = builder.userTrades;
        this.balances = builder.balances;
    }

    public List<CurrencyPair> getOrderBook() {
        return orderBook;
    }

    public List<CurrencyPair> getTrades() {
        return trades;
    }

    public List<CurrencyPair> getTicker() {
        return ticker;
    }

    public List<CurrencyPair> getOrders() {
        return orders;
    }

    public List<CurrencyPair> getUserTrades() {
        return userTrades;
      }

    public List<CurrencyPair> getBalances() {
        return balances;
      }

    public boolean isEmpty() {
      return ticker.isEmpty() && trades.isEmpty() && orderBook.isEmpty() && orders.isEmpty() && userTrades.isEmpty() && balances.isEmpty();
    }

    public static ProductSubscriptionBuilder create() {
        return new ProductSubscriptionBuilder();
    }

    public static class ProductSubscriptionBuilder {
        private final List<CurrencyPair> orderBook;
        private final List<CurrencyPair> trades;
        private final List<CurrencyPair> ticker;
        private final List<CurrencyPair> userTrades;
        private final List<CurrencyPair> orders;
        private final List<CurrencyPair> balances;

        private ProductSubscriptionBuilder() {
            orderBook = new ArrayList<>();
            trades = new ArrayList<>();
            ticker = new ArrayList<>();
            orders = new ArrayList<>();
            userTrades = new ArrayList<>();
            balances = new ArrayList<>();
        }

        public ProductSubscriptionBuilder addOrderbook(CurrencyPair pair) {
            orderBook.add(pair);
            return this;
        }

        public ProductSubscriptionBuilder addTrades(CurrencyPair pair) {
            trades.add(pair);
            return this;
        }

        public ProductSubscriptionBuilder addTicker(CurrencyPair pair) {
            ticker.add(pair);
            return this;
        }

        public ProductSubscriptionBuilder addOrders(CurrencyPair pair) {
            orders.add(pair);
            return this;
        }

        public ProductSubscriptionBuilder addUserTrades(CurrencyPair pair) {
            userTrades.add(pair);
            return this;
        }

        public ProductSubscriptionBuilder addBalances(CurrencyPair pair) {
            balances.add(pair);
            return this;
        }

        public ProductSubscriptionBuilder addAll(CurrencyPair pair) {
            orderBook.add(pair);
            trades.add(pair);
            ticker.add(pair);
            orders.add(pair);
            userTrades.add(pair);
            balances.add(pair);
            return this;
        }

        public ProductSubscription build() {
            return new ProductSubscription(this);
        }
    }
}