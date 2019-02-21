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

    private ProductSubscription(ProductSubscriptionBuilder builder) {
        this.orderBook = builder.orderBook;
        this.trades = builder.trades;
        this.ticker = builder.ticker;
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

    public boolean isEmpty() {
      return ticker.isEmpty() && trades.isEmpty() && orderBook.isEmpty();
    }

    public static ProductSubscriptionBuilder create() {
        return new ProductSubscriptionBuilder();
    }

    public static class ProductSubscriptionBuilder {
        private final List<CurrencyPair> orderBook;
        private final List<CurrencyPair> trades;
        private final List<CurrencyPair> ticker;

        private ProductSubscriptionBuilder() {
            orderBook = new ArrayList<>();
            trades = new ArrayList<>();
            ticker = new ArrayList<>();
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

        public ProductSubscriptionBuilder addAll(CurrencyPair pair) {
            orderBook.add(pair);
            trades.add(pair);
            ticker.add(pair);
            return this;
        }

        public ProductSubscription build() {
            return new ProductSubscription(this);
        }
    }
}