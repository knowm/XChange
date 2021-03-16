package info.bitrich.xchangestream.kraken;

import com.fasterxml.jackson.databind.JsonNode;
import info.bitrich.xchangestream.core.StreamingTradeService;
import info.bitrich.xchangestream.kraken.dto.KrakenOpenOrder;
import info.bitrich.xchangestream.kraken.dto.KrakenOwnTrade;
import info.bitrich.xchangestream.kraken.dto.enums.KrakenSubscriptionName;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import java.util.*;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.kraken.KrakenAdapters;
import org.knowm.xchange.kraken.dto.trade.KrakenOrderFlags;
import org.knowm.xchange.kraken.dto.trade.KrakenOrderStatus;
import org.knowm.xchange.kraken.dto.trade.KrakenType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KrakenStreamingTradeService implements StreamingTradeService {
  private static final Logger LOG = LoggerFactory.getLogger(KrakenStreamingTradeService.class);

  private final KrakenStreamingService streamingService;

  private volatile boolean ownTradesFlowableSet, userTradeFlowableSet;
  private Flowable<Order> ownTradesFlowable;
  private Flowable<UserTrade> userTradeFlowable;

  KrakenStreamingTradeService(KrakenStreamingService streamingService) {
    this.streamingService = streamingService;
  }

  private String getChannelName(KrakenSubscriptionName subscriptionName) {
    return subscriptionName.toString();
  }

  private static class KrakenDtoOrderHolder extends HashMap<String, KrakenOpenOrder> {}

  private static class KrakenDtoUserTradeHolder extends HashMap<String, KrakenOwnTrade> {}

  @Override
  public Flowable<Order> getOrderChanges(CurrencyPair currencyPair, Object... args) {
    try {
      if (!ownTradesFlowableSet) {
        synchronized (this) {
          if (!ownTradesFlowableSet) {
            String channelName = getChannelName(KrakenSubscriptionName.openOrders);
            ownTradesFlowable =
                streamingService
                    .subscribeChannel(channelName)
                    .filter(JsonNode::isArray)
                    .filter(Objects::nonNull)
                    .map(jsonNode -> jsonNode.get(0))
                    .map(
                        jsonNode ->
                            StreamingObjectMapperHelper.getObjectMapper()
                                .treeToValue(jsonNode, KrakenDtoOrderHolder[].class))
                    .flatMapIterable(this::adaptKrakenOrders)
                    .publish(1).refCount();

            ownTradesFlowableSet = true;
          }
        }
      }
      return Flowable.create(
          emit ->
              ownTradesFlowable
                  .filter(
                      order ->
                          currencyPair == null
                              || order.getCurrencyPair() == null
                              || order.getCurrencyPair().compareTo(currencyPair) == 0)
                  .subscribe(emit::onNext, emit::onError, emit::onComplete), 
          BackpressureStrategy.LATEST);

    } catch (Exception e) {
      return Flowable.error(e);
    }
  }

  private Iterable<Order> adaptKrakenOrders(KrakenDtoOrderHolder[] dtoList) {
    List<Order> result = new ArrayList<>();

    for (KrakenDtoOrderHolder dtoHolder : dtoList) {
      for (Map.Entry<String, KrakenOpenOrder> dtoOrderEntry : dtoHolder.entrySet()) {
        String orderId = dtoOrderEntry.getKey();
        KrakenOpenOrder dto = dtoOrderEntry.getValue();
        KrakenOpenOrder.KrakenDtoDescr descr = dto.descr;

        CurrencyPair pair = descr == null ? null : new CurrencyPair(descr.pair);
        Order.OrderType side =
            descr == null ? null : KrakenAdapters.adaptOrderType(KrakenType.fromString(descr.type));
        String orderType = (descr == null || descr.ordertype == null) ? null : descr.ordertype;

        Order.Builder builder;
        if ("limit".equals(orderType))
          builder = new LimitOrder.Builder(side, pair).limitPrice(descr.price);
        else if ("stop".equals(orderType))
          builder =
              new StopOrder.Builder(side, pair).limitPrice(descr.price).stopPrice(descr.price2);
        else if ("market".equals(orderType)) builder = new MarketOrder.Builder(side, pair);
        else // this is an order update (not the full order, it may only update one field)
        builder = new MarketOrder.Builder(side, pair);

        result.add(
            builder
                .id(orderId)
                .originalAmount(dto.vol)
                .cumulativeAmount(dto.vol_exec)
                .orderStatus(
                    dto.status == null
                        ? null
                        : KrakenAdapters.adaptOrderStatus(KrakenOrderStatus.fromString(dto.status)))
                .timestamp(dto.opentm == null ? null : new Date((long) (dto.opentm * 1000L)))
                .fee(dto.fee)
                .flags(adaptFlags(dto.oflags))
                .userReference(dto.userref == null ? null : Integer.toString(dto.userref))
                .build());
      }
    }
    return result;
  }

  /**
   * Comma delimited list of order flags (optional). viqc = volume in quote currency (not available
   * for leveraged orders), fcib = prefer fee in base currency, fciq = prefer fee in quote currency,
   * nompp = no market price protection, post = post only order (available when ordertype = limit)
   */
  private Set<Order.IOrderFlags> adaptFlags(String oflags) {
    if (oflags == null) return new HashSet<>(0);

    String[] arr = oflags.split(",");
    Set<Order.IOrderFlags> flags = new HashSet<>(arr.length);

    for (String flag : arr) {
      flags.add(KrakenOrderFlags.fromString(flag));
    }
    return flags;
  }

  @Override
  public Flowable<UserTrade> getUserTrades(CurrencyPair currencyPair, Object... args) {
    try {
      if (!userTradeFlowableSet) {
        synchronized (this) {
          if (!userTradeFlowableSet) {
            String channelName = getChannelName(KrakenSubscriptionName.ownTrades);
            userTradeFlowable =
                streamingService
                    .subscribeChannel(channelName)
                    .filter(JsonNode::isArray)
                    .filter(Objects::nonNull)
                    .map(jsonNode -> jsonNode.get(0))
                    .map(
                        jsonNode ->
                            StreamingObjectMapperHelper.getObjectMapper()
                                .treeToValue(jsonNode, KrakenDtoUserTradeHolder[].class))
                    .flatMapIterable(this::adaptKrakenUserTrade)
                    .publish(1).refCount();
            userTradeFlowableSet = true;
          }
        }
      }
      return Flowable.create(
          emit ->
              userTradeFlowable
                  .filter(
                      order ->
                          currencyPair == null
                              || order.getCurrencyPair() == null
                              || order.getCurrencyPair().compareTo(currencyPair) == 0)
                  .subscribe(emit::onNext, emit::onError, emit::onComplete),
          BackpressureStrategy.LATEST);

    } catch (Exception e) {
      return Flowable.error(e);
    }
  }

  private List<UserTrade> adaptKrakenUserTrade(KrakenDtoUserTradeHolder[] ownTrades) {
    List<UserTrade> result = new ArrayList<>();

    for (KrakenDtoUserTradeHolder holder : ownTrades) {
      for (Map.Entry<String, KrakenOwnTrade> entry : holder.entrySet()) {
        String tradeId = entry.getKey();
        KrakenOwnTrade dto = entry.getValue();

        CurrencyPair currencyPair = new CurrencyPair(dto.pair);
        result.add(
            new UserTrade.Builder()
                .id(tradeId) // The tradeId should be the key of the map, postxid can be null and is
                // not unique as required for a tradeId
                .orderId(dto.ordertxid)
                .currencyPair(currencyPair)
                .timestamp(dto.time == null ? null : new Date((long) (dto.time * 1000L)))
                .type(KrakenAdapters.adaptOrderType(KrakenType.fromString(dto.type)))
                .price(dto.price)
                .feeAmount(dto.fee)
                .feeCurrency(currencyPair.base)
                .originalAmount(dto.vol)
                .build());
      }
    }
    return result;
  }
}
