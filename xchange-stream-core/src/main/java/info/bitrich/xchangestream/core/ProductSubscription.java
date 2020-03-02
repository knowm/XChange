package info.bitrich.xchangestream.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;

/**
 * Use to specify subscriptions during the connect phase For instancing, use builder @link {@link
 * ProductSubscriptionBuilder}
 */
public class ProductSubscription {
  private final List<CurrencyPair> orderBook;
  private final List<CurrencyPair> trades;
  private final List<CurrencyPair> ticker;
  private final List<CurrencyPair> userTrades;
  private final List<CurrencyPair> orders;
  private final List<Currency> balances;

  private ProductSubscription(ProductSubscriptionBuilder builder) {
    this.orderBook = asList(builder.orderBook);
    this.trades = asList(builder.trades);
    this.ticker = asList(builder.ticker);
    this.orders = asList(builder.orders);
    this.userTrades = asList(builder.userTrades);
    this.balances = asList(builder.balances);
  }

  private <T> List<T> asList(Iterable<T> collection) {
    List<T> result = new ArrayList<>();
    collection.forEach(result::add);
    return Collections.unmodifiableList(result);
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

  public List<Currency> getBalances() {
    return balances;
  }

  public boolean isEmpty() {
    return !hasAuthenticated() && !hasUnauthenticated();
  }

  public boolean hasAuthenticated() {
    return !orders.isEmpty() || !userTrades.isEmpty() || !balances.isEmpty();
  }

  public boolean hasUnauthenticated() {
    return !ticker.isEmpty() || !trades.isEmpty() || !orderBook.isEmpty();
  }

  public static ProductSubscriptionBuilder create() {
    return new ProductSubscriptionBuilder();
  }

  public static class ProductSubscriptionBuilder {
    private final Set<CurrencyPair> orderBook;
    private final Set<CurrencyPair> trades;
    private final Set<CurrencyPair> ticker;
    private final Set<CurrencyPair> userTrades;
    private final Set<CurrencyPair> orders;
    private final Set<Currency> balances;

    private ProductSubscriptionBuilder() {
      orderBook = new HashSet<>();
      trades = new HashSet<>();
      ticker = new HashSet<>();
      orders = new HashSet<>();
      userTrades = new HashSet<>();
      balances = new HashSet<>();
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

    public ProductSubscriptionBuilder addBalances(Currency pair) {
      balances.add(pair);
      return this;
    }

    public ProductSubscriptionBuilder addAll(CurrencyPair pair) {
      orderBook.add(pair);
      trades.add(pair);
      ticker.add(pair);
      orders.add(pair);
      userTrades.add(pair);
      balances.add(pair.base);
      balances.add(pair.counter);
      return this;
    }

    public ProductSubscription build() {
      return new ProductSubscription(this);
    }
  }
}
