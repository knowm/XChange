package org.knowm.xchange.stream.wrapper;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;

@Getter
@EqualsAndHashCode
@Accessors(fluent = true)
class Subscription {

  private final CurrencyPair currencyPair;
  private final Currency currency;
  private final MarketDataType type;

  Subscription(CurrencyPair currencyPair, MarketDataType type) {
    this.currencyPair = currencyPair;
    this.currency = null;
    this.type = type;
  }

  Subscription(Currency currency, MarketDataType type) {
    this.currency = currency;
    this.currencyPair = null;
    this.type = type;
  }

  final String key() {
    return (currencyPair == null ? currency : currencyPair) + "/" + type;
  }

  @Override
  public final String toString() {
    return key();
  }

}
