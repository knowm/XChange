package info.bitrich.xchangestream.bitmex;

import info.bitrich.xchangestream.bitmex.dto.BitmexOrder;
import info.bitrich.xchangestream.bitmex.dto.BitmexPosition;
import info.bitrich.xchangestream.bitmex.dto.BitmexPrivateExecution;
import info.bitrich.xchangestream.bitmex.dto.BitmexPrivateExecution.ExecutionType;
import info.bitrich.xchangestream.core.StreamingTradeService;
import io.reactivex.rxjava3.core.Observable;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.instrument.Instrument;

public class BitmexStreamingTradeService implements StreamingTradeService {

  private final BitmexStreamingService streamingService;

  public BitmexStreamingTradeService(BitmexStreamingService streamingService) {
    this.streamingService = streamingService;
  }

  @Override
  public Observable<UserTrade> getUserTrades(Instrument instrument, Object... args) {
    String channelName = "execution";
    return streamingService
        .subscribeBitmexChannel(channelName)
        .flatMapIterable(
            s -> {
              BitmexPrivateExecution[] bitmexPrivateExecutions = s.toBitmexPrivateExecutions();
              return Arrays.stream(bitmexPrivateExecutions)
                  .filter(bitmexPrivateExecution -> bitmexPrivateExecution.getInstrument().equals(instrument))
                  .filter(bitmexPrivateExecution -> bitmexPrivateExecution.getExecType() == ExecutionType.TRADE)
                  .map(BitmexStreamingAdapters::toUserTrade)
                  .collect(Collectors.toList());
            });
  }

  @Override
  public Observable<UserTrade> getUserTrades(CurrencyPair currencyPair, Object... args) {
    return getUserTrades((Instrument) currencyPair, args);
  }

  public Observable<BitmexPosition> getPositions() {
    String channelName = "position";
    return streamingService
        .subscribeBitmexChannel(channelName)
        .flatMapIterable(
            s -> {
              BitmexPosition[] bitmexPositions = s.toBitmexPositions();
              return Arrays.asList(bitmexPositions);
            });
  }

  @Override
  public Observable<Order> getOrderChanges(Instrument instrument, Object... args) {
    String channelName = "order";
    return streamingService
        .subscribeBitmexChannel(channelName)
        .flatMapIterable(
            s -> {
              BitmexOrder[] bitmexOrders = s.toBitmexOrders();
              return Arrays.stream(bitmexOrders)
                  .filter(bitmexOrder -> bitmexOrder.getInstrument().equals(instrument))
                  .map(BitmexStreamingAdapters::toOrder)
                  .collect(Collectors.toList());
            });
  }

  @Override
  public Observable<Order> getOrderChanges(CurrencyPair currencyPair, Object... args) {
    return getOrderChanges((Instrument) currencyPair, args);
  }
}
