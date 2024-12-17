package info.bitrich.xchangestream.bitmex;

import info.bitrich.xchangestream.bitmex.dto.BitmexOrder;
import info.bitrich.xchangestream.core.StreamingTradeService;
import io.reactivex.rxjava3.core.Observable;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;

public class BitmexStreamingTradeService implements StreamingTradeService {

  private final BitmexStreamingService streamingService;

  public BitmexStreamingTradeService(BitmexStreamingService streamingService) {
    this.streamingService = streamingService;
  }

  @Override
  public Observable<Order> getOrderChanges(CurrencyPair currencyPair, Object... args) {
    String channelName = "order";
    return streamingService
        .subscribeBitmexChannel(channelName)
        .flatMapIterable(
            s -> {
              BitmexOrder[] bitmexOrders = s.toBitmexOrders();
              return Arrays.stream(bitmexOrders)
                  .filter(bitmexOrder -> bitmexOrder.getInstrument().equals(currencyPair))
                  .map(BitmexStreamingAdapters::toOrder)
                  .collect(Collectors.toList());
            });
  }
}
