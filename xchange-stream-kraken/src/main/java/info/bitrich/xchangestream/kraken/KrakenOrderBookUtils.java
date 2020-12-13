package info.bitrich.xchangestream.kraken;

import info.bitrich.xchangestream.kraken.dto.KrakenOrderBook;
import info.bitrich.xchangestream.kraken.dto.enums.KrakenOrderBookMessageType;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.kraken.dto.marketdata.KrakenPublicOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Kraken order book utils
 *
 * @author pavel chertalev
 */
public class KrakenOrderBookUtils {

  private static final Logger LOG = LoggerFactory.getLogger(KrakenOrderBookUtils.class);

  private static final String ASK_SNAPSHOT = "as";
  private static final String ASK_UPDATE = "a";

  private static final String BID_SNAPSHOT = "bs";
  private static final String BID_UPDATE = "b";

  private static final int EXPECTED_ORDER_BOOK_ARRAY_SIZE = 4;
  private static final BigDecimal BIG_DECIMAL_1000 = new BigDecimal(1000);

  @SuppressWarnings("unchecked")
  public static KrakenOrderBook parse(List jsonParseResult) {
    try {
      Iterator iterator = jsonParseResult.iterator();

      Integer channelID = getTypedValue(iterator, Integer.class, "order book channel type");
      Map<String, List<List<String>>> orderBookItems =
          getTypedValue(iterator, Map.class, "order book items");
      Map<String, List<List<String>>> orderBookItemsMap = new HashMap(orderBookItems);
      int index = 2;
      if (jsonParseResult.size() > EXPECTED_ORDER_BOOK_ARRAY_SIZE) {
        orderBookItems = getTypedValue(iterator, Map.class, "order book items");
        orderBookItemsMap.putAll(orderBookItems);
        index = 3;
      }
      String channelName = (String) jsonParseResult.get(index++);
      String pair = (String) jsonParseResult.get(index);
      KrakenOrderBookMessageType orderBookType =
          orderBookItemsMap.keySet().stream()
                  .anyMatch(key -> StringUtils.equalsAny(key, ASK_SNAPSHOT, BID_SNAPSHOT))
              ? KrakenOrderBookMessageType.SNAPSHOT
              : KrakenOrderBookMessageType.UPDATE;

      List<List<String>> asksValues;
      List<List<String>> bidsValues;
      if (orderBookType == KrakenOrderBookMessageType.SNAPSHOT) {
        asksValues = orderBookItemsMap.get(ASK_SNAPSHOT);
        bidsValues = orderBookItemsMap.get(BID_SNAPSHOT);
      } else {
        asksValues = orderBookItemsMap.get(ASK_UPDATE);
        bidsValues = orderBookItemsMap.get(BID_UPDATE);
      }
      return new KrakenOrderBook(
          channelID,
          channelName,
          pair,
          orderBookType,
          getItemsArray(asksValues),
          getItemsArray(bidsValues));

    } catch (KrakenException e) {
      LOG.error("failed to parse order book tree {}", e.getMessage());
      return null;
    }
  }

  public static KrakenPublicOrder[] getItemsArray(List<List<String>> values) {
    return values == null
        ? new KrakenPublicOrder[0]
        : values.stream()
            .map(KrakenOrderBookUtils::extractKrakenPublicOrder)
            .toArray(KrakenPublicOrder[]::new);
  }

  @SuppressWarnings("unchecked")
  public static <T> T getTypedValue(Iterator iterator, Class<T> clazz, String fieldName)
      throws KrakenException {
    if (!iterator.hasNext()) {
      throw new KrakenException(
          String.format(
              "Expected value of %s type for %s filed but there is no value", clazz, fieldName));
    }
    Object object = iterator.next();
    if (!clazz.isAssignableFrom(object.getClass())) {
      throw new KrakenException(
          String.format(
              "Expected value of %s type for %s filed but there is invalid type %s",
              clazz, fieldName, object.getClass().getName()));
    }
    return (T) object;
  }

  public static KrakenPublicOrder extractKrakenPublicOrder(List<String> list) {
    return new KrakenPublicOrder(
        new BigDecimal(list.get(0)).stripTrailingZeros(),
        new BigDecimal(list.get(1)).stripTrailingZeros(),
        new BigDecimal(list.get(2)).multiply(BIG_DECIMAL_1000).longValue());
  }
}
