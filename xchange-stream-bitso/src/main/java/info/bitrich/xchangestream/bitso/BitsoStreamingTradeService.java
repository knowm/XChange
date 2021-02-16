package info.bitrich.xchangestream.bitso;

import info.bitrich.xchangestream.bitso.dto.BitsoWebSocketTransaction;
import info.bitrich.xchangestream.core.StreamingTradeService;
import io.reactivex.Observable;
import java.util.List;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.exceptions.ExchangeSecurityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BitsoStreamingTradeService implements StreamingTradeService {

  private static final Logger LOG = LoggerFactory.getLogger(BitsoStreamingTradeService.class);

  private static final String MATCH = "match";

  private final BitsoStreamingService service;

  BitsoStreamingTradeService(BitsoStreamingService service) {
    this.service = service;
  }

  private boolean containsPair(List<CurrencyPair> pairs, CurrencyPair pair) {
    for (CurrencyPair item : pairs) {
      if (item.compareTo(pair) == 0) {
        return true;
      }
    }

    return false;
  }

  @Override
  public Observable<UserTrade> getUserTrades(CurrencyPair currencyPair, Object... args) {
//    if (!containsPair(service.getProduct().getUserTrades(), currencyPair))
//      throw new UnsupportedOperationException(
//          String.format("The currency pair %s is not subscribed for user trades", currencyPair));
//    if (!service.isAuthenticated()) {
//      throw new ExchangeSecurityException("Not authenticated");
//    }
//    return service
//        .getRawWebSocketTransactions(currencyPair, true)
//        .filter(message -> message.getType().equals(MATCH))
//        .filter((CoinbaseProWebSocketTransaction s) -> s.getUserId() != null)
//        .map((CoinbaseProWebSocketTransaction s) -> s.toCoinbaseProFill())
//        .map((CoinbaseProFill f) -> adaptTradeHistory(new CoinbaseProFill[] {f}))
//        .map((UserTrades h) -> h.getUserTrades().get(0));

    return null;
  }

  private boolean orderChangesWarningLogged;
  /**
   * <strong>Warning:</strong> the order change stream is not yet fully implemented for Coinbase
   * Pro. Orders are not fully populated, containing only the values changed since the last update.
   * Other values will be null.
   */
  @Override
  public Observable<Order> getOrderChanges(CurrencyPair currencyPair, Object... args) {
    if (!containsPair(service.getProduct().getOrders(), currencyPair))
      throw new UnsupportedOperationException(
          String.format("The currency pair %s is not subscribed for orders", currencyPair));
    if (!service.isAuthenticated()) {
      throw new ExchangeSecurityException("Not authenticated");
    }
    if (!orderChangesWarningLogged) {
      LOG.warn(
          "The order change stream is not yet fully implemented for Coinbase Pro. "
              + "Orders are not fully populated, containing only the values changed since "
              + "the last update. Other values will be null.");
      orderChangesWarningLogged = true;
    }
    return service
        .getRawWebSocketTransactions(currencyPair, true)
        .filter(s -> s.getBook() != null)
        .map(BitsoStreamingAdapters::adaptOrder);
  }

  /**
   * Web socket transactions related to the specified currency, in their raw format.
   *
   * @param currencyPair The currency pair.
   * @return The stream.
   */
  public Observable<BitsoWebSocketTransaction> getRawWebSocketTransactions(
      CurrencyPair currencyPair, boolean filterChannelName) {
    return service.getRawWebSocketTransactions(currencyPair, filterChannelName);
  }
}
