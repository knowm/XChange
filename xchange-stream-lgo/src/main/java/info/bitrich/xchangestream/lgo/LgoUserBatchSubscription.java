package info.bitrich.xchangestream.lgo;

import static java.util.stream.Collectors.toConcurrentMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.lgo.domain.LgoBatchOrderEvent;
import info.bitrich.xchangestream.lgo.domain.LgoGroupedUserUpdate;
import info.bitrich.xchangestream.lgo.dto.LgoUserMessage;
import info.bitrich.xchangestream.lgo.dto.LgoUserSnapshot;
import info.bitrich.xchangestream.lgo.dto.LgoUserUpdate;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;

class LgoUserBatchSubscription {

  private final Observable<LgoGroupedUserUpdate> downstream;
  private final LgoStreamingService streamingService;
  private final CurrencyPair currencyPair;

  static LgoUserBatchSubscription create(
      LgoStreamingService streamingService, CurrencyPair currencyPair) {
    return new LgoUserBatchSubscription(streamingService, currencyPair);
  }

  private LgoUserBatchSubscription(
      LgoStreamingService streamingService, CurrencyPair currencyPair) {
    this.streamingService = streamingService;
    this.currencyPair = currencyPair;
    downstream = createSubscription();
  }

  Observable<LgoGroupedUserUpdate> getPublisher() {
    return downstream;
  }

  private Observable<LgoGroupedUserUpdate> createSubscription() {
    final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();
    return streamingService
        .subscribeChannel(LgoAdapter.channelName("user", currencyPair))
        .map(s -> mapper.readValue(s.toString(), LgoUserMessage.class))
        .scan(
            new LgoGroupedUserUpdate(),
            (acc, s) -> {
              List<LgoBatchOrderEvent> events = new ArrayList<>();
              if (s.getType().equals("update")) {
                LgoUserUpdate userUpdate = (LgoUserUpdate) s;
                List<Order> updates =
                    updateAllOrders(
                        currencyPair, userUpdate.getOrderEvents(), acc.getAllOpenOrders());
                events.addAll(
                    LgoAdapter.adaptOrderEvent(
                        userUpdate.getOrderEvents(), s.getBatchId(), updates));
                return new LgoGroupedUserUpdate(
                    acc.getAllOpenOrders(), updates, events, s.getBatchId(), s.getType());
              } else {
                Collection<LimitOrder> allOrders =
                    handleUserSnapshot(currencyPair, (LgoUserSnapshot) s);
                ConcurrentMap<String, Order> ordersById =
                    allOrders.stream().collect(toConcurrentMap(LimitOrder::getId, this::copyOrder));
                return new LgoGroupedUserUpdate(
                    ordersById, new ArrayList<>(allOrders), events, s.getBatchId(), s.getType());
              }
            })
        .skip(1) // skips the first element, for this is the empty accumulator
        .share();
  }

  private List<Order> updateAllOrders(
      CurrencyPair currencyPair,
      List<LgoBatchOrderEvent> orderEvents,
      Map<String, Order> allOpenOrders) {
    return orderEvents.stream()
        .map(orderEvent -> orderEvent.applyOnOrders(currencyPair, allOpenOrders))
        .map(this::copyOrder)
        .collect(Collectors.toList());
  }

  private Collection<LimitOrder> handleUserSnapshot(CurrencyPair currencyPair, LgoUserSnapshot s) {
    return LgoAdapter.adaptOrdersSnapshot(s.getSnapshotData(), currencyPair);
  }

  private Order copyOrder(Order order) {
    Order copy =
        order instanceof LimitOrder
            ? LimitOrder.Builder.from(order).build()
            : MarketOrder.Builder.from(order).build();
    // because actual released version of xchange-core has buggy Builder.from methods
    copy.setFee(order.getFee());
    copy.setCumulativeAmount(order.getCumulativeAmount());
    // https://github.com/knowm/XChange/pull/3163
    return copy;
  }
}
