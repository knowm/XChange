package info.bitrich.xchangestream.coinbasepro.dto;

import info.bitrich.xchangestream.core.ProductSubscription;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.knowm.xchange.currency.CurrencyPair;

/** CoinbasePro specific wrapper class, allows for subscription to CoinbasePro specific channels */
public class ProductSubscriptionWrapper {

  private final ProductSubscription productSubscription;

  private final List<CurrencyPair> full;

  private ProductSubscriptionWrapper(
      final ProductSubscription productSubscription, final Set<CurrencyPair> full) {
    this.productSubscription = productSubscription;
    this.full = asList(full);
  }

  private <T> List<T> asList(Iterable<T> collection) {
    List<T> result = new ArrayList<>();
    collection.forEach(result::add);
    return Collections.unmodifiableList(result);
  }

  public ProductSubscription getProductSubscription() {
    return this.productSubscription;
  }

  public List<CurrencyPair> getFull() {
    return full;
  }

  public static ProductSubscriptionWrapperBuilder create() {
    return new ProductSubscriptionWrapperBuilder();
  }

  public static class ProductSubscriptionWrapperBuilder {

    private ProductSubscription productSubscription;

    private final Set<CurrencyPair> full;

    private ProductSubscriptionWrapperBuilder() {
      this.full = new HashSet<>();
      this.productSubscription = ProductSubscription.create().build();
    }

    public ProductSubscriptionWrapperBuilder setProductSubscription(
        final ProductSubscription productSubscription) {
      this.productSubscription = productSubscription;
      return this;
    }

    public ProductSubscriptionWrapperBuilder addFull(CurrencyPair pair) {
      full.add(pair);
      return this;
    }

    public ProductSubscriptionWrapper build() {
      return new ProductSubscriptionWrapper(productSubscription, full);
    }
  }
}
