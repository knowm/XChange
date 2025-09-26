package info.bitrich.xchangestream.coinsph;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.coinsph.dto.CoinsphWebSocketOutboundAccountPosition; // Added
import info.bitrich.xchangestream.core.StreamingAccountService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.rxjava3.core.Observable;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.service.account.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoinsphStreamingAccountService implements StreamingAccountService {
  private static final Logger LOG = LoggerFactory.getLogger(CoinsphStreamingAccountService.class);

  private final CoinsphStreamingService service;
  private final AccountService accountService;
  private final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

  /** Constructor with just streaming service. */
  public CoinsphStreamingAccountService(CoinsphStreamingService service) {
    this.service = service;
    this.accountService = null;
  }

  /** Constructor with streaming service and account service. */
  public CoinsphStreamingAccountService(
      CoinsphStreamingService service, AccountService accountService) {
    this.service = service;
    this.accountService = accountService;
  }

  @Override
  public Observable<Balance> getBalanceChanges(Currency currency, Object... args) {
    // User data streams (e.g., "outboundAccountPosition" or "balanceUpdate" in Binance)
    // are typically not per-currency for subscription.
    // The listenKey subscribes to all user account updates. Filtering by currency happens
    // client-side.
    // The channel name here might be a generic one like "balanceUpdate" or
    // "outboundAccountPosition".

    // Ensure user data stream is started
    // service.startUserDataStreamIfNeeded(); // Example placeholder

    // String balanceUpdateChannel = "outboundAccountPosition"; // Or "balanceUpdate" - check
    // Coins.ph docs
    // For now, let's assume a DTO CoinsphWebSocketBalanceUpdate and channel "balanceUpdate"
    // This part is highly dependent on the actual WebSocket message format from Coins.ph for
    // balances.
    // If balance updates come via executionReport (e.g. for fees), that's handled by
    // StreamingTradeService.
    // This method is for dedicated balance update messages.
    // The channel name "outboundAccountPosition" is based on Binance and Coins.ph user data stream
    // docs.
    String balanceUpdateChannel = "outboundAccountPosition";

    // Ensure user data stream is started by CoinsphStreamingService when this is subscribed.
    // This is handled by CoinsphStreamingService.subscribeChannel if isUserDataChannel returns true
    // for "outboundAccountPosition".

    return service
        .subscribeChannel(balanceUpdateChannel)
        .map(node -> mapper.treeToValue(node, CoinsphWebSocketOutboundAccountPosition.class))
        .flatMap(
            accountPosition ->
                Observable.fromIterable(CoinsphStreamingAdapters.adaptBalances(accountPosition)))
        .filter(balance -> currency == null || balance.getCurrency().equals(currency));
  }
}
