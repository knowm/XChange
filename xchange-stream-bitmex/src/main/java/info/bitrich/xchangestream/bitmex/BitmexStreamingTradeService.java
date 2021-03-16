package info.bitrich.xchangestream.bitmex;

import info.bitrich.xchangestream.bitmex.dto.BitmexOrder;
import io.reactivex.rxjava3.core.Flowable;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;

/** Created by Declan */
public class BitmexStreamingTradeService {

  private final BitmexStreamingService streamingService;

  public BitmexStreamingTradeService(BitmexStreamingService streamingService) {
    this.streamingService = streamingService;
  }

  public Flowable<Order> getOrders(CurrencyPair currencyPair, Object... args) {
    String channelName = "order";
    String instrument = currencyPair.base.toString() + currencyPair.counter.toString();
    return streamingService
        .subscribeBitmexChannel(channelName)
        .flatMapIterable(
            s -> {
              BitmexOrder[] bitmexOrders = s.toBitmexOrders();
              return Arrays.stream(bitmexOrders)
                  .filter(bitmexOrder -> bitmexOrder.getSymbol().equals(instrument))
                  .filter(BitmexOrder::isNotWorkingIndicator)
                  .map(BitmexOrder::toOrder)
                  .collect(Collectors.toList());
            });
  }
}
