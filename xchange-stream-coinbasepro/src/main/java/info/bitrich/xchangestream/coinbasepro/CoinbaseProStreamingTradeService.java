package info.bitrich.xchangestream.coinbasepro;

import static org.knowm.xchange.coinbasepro.CoinbaseProAdapters.adaptTradeHistory;

import info.bitrich.xchangestream.coinbasepro.dto.CoinbaseProWebSocketTransaction;
import info.bitrich.xchangestream.core.StreamingTradeService;
import io.reactivex.Observable;
import java.util.Collections;
import org.knowm.xchange.coinbasepro.dto.trade.CoinbaseProFill;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeSecurityException;
import org.knowm.xchange.instrument.Instrument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoinbaseProStreamingTradeService implements StreamingTradeService {

  private static final Logger LOG = LoggerFactory.getLogger(CoinbaseProStreamingTradeService.class);

  private static final String MATCH = "match";

  private final CoinbaseProStreamingService service;

  CoinbaseProStreamingTradeService(CoinbaseProStreamingService service) {
    this.service = service;
  }

  @Override
  public Observable<UserTrade> getUserTrades(CurrencyPair currencyPair, Object... args) {
    checkPairAndAuthentication(currencyPair);

    return service
        .getRawWebSocketTransactions(currencyPair, true)
        .filter(message -> message.getType().equals(MATCH))
        .filter((CoinbaseProWebSocketTransaction s) -> s.getUserId() != null)
        .map(CoinbaseProWebSocketTransaction::toCoinbaseProFill)
        .map((CoinbaseProFill f) -> adaptTradeHistory(Collections.singletonList(f)))
        .map((UserTrades h) -> h.getUserTrades().get(0));
  }

  @Override
  public Observable<UserTrade> getUserTrades(Instrument instrument, Object... args) {
    checkPairAndAuthentication(instrument);

    return service
        .getRawWebSocketTransactions(instrument, true)
        .filter(message -> message.getType().equals(MATCH))
        .filter((CoinbaseProWebSocketTransaction s) -> s.getUserId() != null)
        .map(CoinbaseProWebSocketTransaction::toCoinbaseProFill)
        .map((CoinbaseProFill f) -> adaptTradeHistory(Collections.singletonList(f)))
        .map((UserTrades h) -> h.getUserTrades().get(0));
  }

  private boolean orderChangesWarningLogged;
  /**
   * <strong>Warning:</strong> the order change stream is not yet fully implemented for Coinbase
   * Pro. Orders are not fully populated, containing only the values changed since the last update.
   * Other values will be null.
   */
  @Override
  public Observable<Order> getOrderChanges(CurrencyPair currencyPair, Object... args) {
    checkPairAndAuthentication(currencyPair);

    if (!orderChangesWarningLogged) {
      LOG.warn(
          "The order change stream is not yet fully implemented for Coinbase Pro. "
              + "Orders are not fully populated, containing only the values changed since "
              + "the last update. Other values will be null.");
      orderChangesWarningLogged = true;
    }
    return service
        .getRawWebSocketTransactions(currencyPair, true)
        .filter(s -> s.getUserId() != null)
        .map(CoinbaseProStreamingAdapters::adaptOrder);
  }

  private void checkPairAndAuthentication(Instrument instrument) {
    if (!service.getProduct().getUserTrades().contains(instrument))
      throw new UnsupportedOperationException(
          String.format("The currency pair %s is not subscribed for user trades", instrument));
    if (!service.isAuthenticated()) {
      throw new ExchangeSecurityException("Not authenticated");
    }
  }

  /**
   * Web socket transactions related to the specified currency, in their raw format.
   *
   * @param currencyPair The currency pair.
   * @return The stream.
   */
  public Observable<CoinbaseProWebSocketTransaction> getRawWebSocketTransactions(
      CurrencyPair currencyPair, boolean filterChannelName) {
    return service.getRawWebSocketTransactions(currencyPair, filterChannelName);
  }
}
