package info.bitrich.xchangestream.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.instrument.Instrument;

/**
 * Use to specify subscriptions during the connect phase For instancing, use builder @link {@link
 * ProductSubscriptionBuilder}
 */
public class ProductSubscription {
  private final List<Instrument> orderBook;
  private final List<Instrument> trades;
  private final List<Instrument> ticker;
  private final List<Instrument> userTrades;
  private final List<Instrument> orders;
  private final List<Instrument> fundingRates;
  private final List<Currency> balances;

  private ProductSubscription(ProductSubscriptionBuilder builder) {
    this.orderBook = asList(builder.orderBook);
    this.trades = asList(builder.trades);
    this.ticker = asList(builder.ticker);
    this.orders = asList(builder.orders);
    this.fundingRates = asList(builder.fundingRates);
    this.userTrades = asList(builder.userTrades);
    this.balances = asList(builder.balances);
  }

  private <T> List<T> asList(Iterable<T> collection) {
    List<T> result = new ArrayList<>();
    collection.forEach(result::add);
    return Collections.unmodifiableList(result);
  }

  public List<Instrument> getOrderBook() {
    return orderBook;
  }

  public List<Instrument> getTrades() {
    return trades;
  }

  public List<Instrument> getTicker() {
    return ticker;
  }

  public List<Instrument> getOrders() {
    return orders;
  }

  public List<Instrument> getFundingRates() {
    return fundingRates;
  }

  public List<Instrument> getUserTrades() {
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
    return !ticker.isEmpty() || !trades.isEmpty() || !orderBook.isEmpty() || !fundingRates.isEmpty();
  }

  public static ProductSubscriptionBuilder create() {
    return new ProductSubscriptionBuilder();
  }

  public static class ProductSubscriptionBuilder {
    private final Set<Instrument> orderBook;
    private final Set<Instrument> trades;
    private final Set<Instrument> ticker;
    private final Set<Instrument> userTrades;
    private final Set<Instrument> orders;
    private final Set<Instrument> fundingRates;
    private final Set<Currency> balances;

    private ProductSubscriptionBuilder() {
      orderBook = new HashSet<>();
      trades = new HashSet<>();
      ticker = new HashSet<>();
      orders = new HashSet<>();
      fundingRates = new HashSet<>();
      userTrades = new HashSet<>();
      balances = new HashSet<>();
    }

    public ProductSubscriptionBuilder addOrderbook(Instrument pair) {
      orderBook.add(pair);
      return this;
    }

    public ProductSubscriptionBuilder addTrades(Instrument pair) {
      trades.add(pair);
      return this;
    }

    public ProductSubscriptionBuilder addTicker(Instrument pair) {
      ticker.add(pair);
      return this;
    }

    public ProductSubscriptionBuilder addOrders(Instrument pair) {
      orders.add(pair);
      return this;
    }

    public ProductSubscriptionBuilder addFundingRates(Instrument pair) {
      fundingRates.add(pair);
      return this;
    }
    public ProductSubscriptionBuilder addUserTrades(Instrument pair) {
      userTrades.add(pair);
      return this;
    }

    public ProductSubscriptionBuilder addBalances(Currency pair) {
      balances.add(pair);
      return this;
    }

    public ProductSubscriptionBuilder addAll(Instrument pair) {
      orderBook.add(pair);
      trades.add(pair);
      ticker.add(pair);
      orders.add(pair);
      fundingRates.add(pair);
      userTrades.add(pair);
      balances.add(pair.getBase());
      balances.add(pair.getCounter());
      return this;
    }

    public ProductSubscription build() {
      return new ProductSubscription(this);
    }
  }
}
