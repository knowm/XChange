package info.bitrich.xchangestream.kraken;

import com.fasterxml.jackson.databind.JsonNode;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.kraken.dto.enums.KrakenSubscriptionName;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Observable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.kraken.KrakenAdapters;
import org.knowm.xchange.kraken.dto.marketdata.KrakenPublicOrder;
import org.knowm.xchange.kraken.dto.marketdata.KrakenPublicTrade;
import org.knowm.xchange.kraken.dto.marketdata.KrakenTicker;
import org.knowm.xchange.kraken.dto.trade.KrakenOrderType;
import org.knowm.xchange.kraken.dto.trade.KrakenType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** @author makarid, pchertalev */
public class KrakenStreamingMarketDataService implements StreamingMarketDataService {

  private static final Logger LOG = LoggerFactory.getLogger(KrakenStreamingMarketDataService.class);

  private static final int ORDER_BOOK_SIZE_DEFAULT = 25;
  private static final int[] KRAKEN_VALID_ORDER_BOOK_SIZES = {10, 25, 100, 500, 1000};
  private static final int MIN_DATA_ARRAY_SIZE = 4;

  public static final String KRAKEN_CHANNEL_DELIMITER = "-";

  private final KrakenStreamingService service;
  private final Map<String, KrakenOrderBookStorage> orderBooks = new ConcurrentHashMap<>();

  public KrakenStreamingMarketDataService(KrakenStreamingService service) {
    this.service = service;
  }

  @Override
  public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
    String channelName = getChannelName(KrakenSubscriptionName.book, currencyPair);
    int depth = parseOrderBookSize(args);
    return subscribe(channelName, MIN_DATA_ARRAY_SIZE, depth)
        .map(KrakenOrderBookUtils::parse)
        .map(
            ob -> {
              KrakenOrderBookStorage orderBook =
                  ob.toKrakenOrderBook(orderBooks.get(channelName), depth);
              orderBooks.put(channelName, orderBook);
              return KrakenAdapters.adaptOrderBook(orderBook.toKrakenDepth(), currencyPair);
            });
  }

  @Override
  @SuppressWarnings("unchecked")
  public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
    String channelName = getChannelName(KrakenSubscriptionName.ticker, currencyPair);
    return subscribe(channelName, MIN_DATA_ARRAY_SIZE, null)
        .map(
            jsonParseResult -> {
              Map<String, List<List>> tickerItems;
              if (Map.class.isAssignableFrom(jsonParseResult.get(1).getClass())) {
                tickerItems = (Map<String, List<List>>) jsonParseResult.get(1);
              } else {
                tickerItems = new HashMap<>();
              }
              KrakenTicker krakenTicker =
                  new KrakenTicker(
                      new KrakenPublicOrder(
                          bd(tickerItems.get("a"), 0), bd(tickerItems.get("a"), 2), 0),
                      new KrakenPublicOrder(
                          bd(tickerItems.get("b"), 0), bd(tickerItems.get("b"), 2), 0),
                      new KrakenPublicOrder(
                          bd(tickerItems.get("c"), 0), bd(tickerItems.get("b"), 2), 0),
                      new BigDecimal[] {bd(tickerItems.get("v"), 0), bd(tickerItems.get("v"), 1)},
                      new BigDecimal[] {bd(tickerItems.get("p"), 0), bd(tickerItems.get("p"), 1)},
                      new BigDecimal[] {bd(tickerItems.get("t"), 0), bd(tickerItems.get("t"), 1)},
                      new BigDecimal[] {bd(tickerItems.get("l"), 0), bd(tickerItems.get("l"), 1)},
                      new BigDecimal[] {bd(tickerItems.get("h"), 0), bd(tickerItems.get("h"), 1)},
                      bd(tickerItems.get("o"), 0));
              return KrakenAdapters.adaptTicker(krakenTicker, currencyPair);
            });
  }

  @Override
  @SuppressWarnings("unchecked")
  public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
    String channelName = getChannelName(KrakenSubscriptionName.trade, currencyPair);
    return subscribe(channelName, MIN_DATA_ARRAY_SIZE, null)
        .filter(list -> List.class.isAssignableFrom(list.get(1).getClass()))
        .map(list -> (List<List>) list.get(1))
        .flatMap(
            list ->
                Observable.fromIterable(
                    list.stream()
                        .map(
                            tradeList -> {
                              String type = getValue(tradeList, 3, String.class);
                              String orderType = getValue(tradeList, 4, String.class);
                              return KrakenAdapters.adaptTrade(
                                  new KrakenPublicTrade(
                                      bd(tradeList, 0),
                                      bd(tradeList, 1),
                                      getValue(tradeList, 2, Double.class),
                                      type == null ? null : KrakenType.fromString(type),
                                      orderType == null
                                          ? null
                                          : KrakenOrderType.fromString(orderType),
                                      getValue(tradeList, 5, String.class)),
                                  currencyPair);
                            })
                        .collect(Collectors.toList())));
  }

  public Observable<List> subscribe(String channelName, int maxItems, Integer depth) {
    return service
        .subscribeChannel(channelName, depth)
        .filter(JsonNode::isArray)
        .filter(Objects::nonNull)
        .map(
            jsonNode ->
                StreamingObjectMapperHelper.getObjectMapper().treeToValue(jsonNode, List.class))
        .filter(
            list -> {
              if (list.size() < maxItems) {
                LOG.error(
                    "Invalid message in channel {}. It contains {} array items but expected at least {}",
                    channelName,
                    list.size(),
                    maxItems);
                return false;
              }
              return true;
            });
  }

  private String getChannelName(
      KrakenSubscriptionName subscriptionName, CurrencyPair currencyPair) {
    String pair = currencyPair.base.toString() + "/" + currencyPair.counter.toString();
    return subscriptionName + KRAKEN_CHANNEL_DELIMITER + pair;
  }

  private BigDecimal bd(List list, int index) {
    return getValue(list, index, BigDecimal.class);
  }

  @SuppressWarnings("unchecked")
  private <T> T getValue(List list, int index, Class<T> clazz) {
    if (list == null || list.size() < index + 1) {
      return null;
    }
    return (T) ConvertUtils.convert(list.get(index), clazz);
  }

  private int parseOrderBookSize(Object[] args) {
    if (args != null && args.length > 0) {
      Object obSizeParam = args[0];
      LOG.debug("Specified Kraken order book size: {}", obSizeParam);
      if (Number.class.isAssignableFrom(obSizeParam.getClass())) {
        int obSize = ((Number) obSizeParam).intValue();
        if (ArrayUtils.contains(KRAKEN_VALID_ORDER_BOOK_SIZES, obSize)) {
          return obSize;
        }
        LOG.error(
            "Invalid order book size {}. Valid values: {}. Default order book size has been used: {}",
            obSize,
            ArrayUtils.toString(KRAKEN_VALID_ORDER_BOOK_SIZES),
            ORDER_BOOK_SIZE_DEFAULT);
        return ORDER_BOOK_SIZE_DEFAULT;
      }
      LOG.error(
          "Order book size param type {} is invalid. Expected: {}. Default order book size has been used {}",
          obSizeParam.getClass().getName(),
          Number.class.getName(),
          ORDER_BOOK_SIZE_DEFAULT);
      return ORDER_BOOK_SIZE_DEFAULT;
    }

    LOG.debug(
        "Order book size param has not been specified. Default order book size has been used: {}",
        ORDER_BOOK_SIZE_DEFAULT);
    return ORDER_BOOK_SIZE_DEFAULT;
  }
}
