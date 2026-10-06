package info.bitrich.xchangestream.kraken;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.zip.CRC32;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.dto.trade.LimitOrder;

public class KrakenStreamingChecksum {
  private static final int CHECKSUM_ORDERBOOK_DEPTH = 10;

  private static final int CRC_STRING_CACHE_SIZE = 500;

  /** Small LRU cache of the CRC string form of recently seen prices and volumes. */
  private static final Map<BigDecimal, String> crcStringCache =
      new LinkedHashMap<>(CRC_STRING_CACHE_SIZE, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<BigDecimal, String> eldest) {
          return size() > CRC_STRING_CACHE_SIZE;
        }
      };

  private static String toCrcString(BigDecimal value) {
    String result = value.toPlainString();
    result = result.replace(".", "");
    return StringUtils.stripStart(result, "0");
  }

  static void addBigDecimalToCrcString(StringBuilder stringBuilder, BigDecimal bigDecimal) {
    String crcString;
    synchronized (crcStringCache) {
      crcString = crcStringCache.computeIfAbsent(bigDecimal, KrakenStreamingChecksum::toCrcString);
    }
    stringBuilder.append(crcString);
  }

  static void addOrderToCrcString(StringBuilder stringBuilder, LimitOrder order) {
    addBigDecimalToCrcString(stringBuilder, order.getLimitPrice());
    addBigDecimalToCrcString(stringBuilder, order.getOriginalAmount());
  }

  public static String createCrcString(TreeSet<LimitOrder> asks, TreeSet<LimitOrder> bids) {
    StringBuilder stringBuilder = new StringBuilder();
    asks.stream()
        .limit(CHECKSUM_ORDERBOOK_DEPTH)
        .forEach(o -> addOrderToCrcString(stringBuilder, o));
    bids.stream()
        .limit(CHECKSUM_ORDERBOOK_DEPTH)
        .forEach(o -> addOrderToCrcString(stringBuilder, o));
    return stringBuilder.toString();
  }

  public static long createCrcLong(String crcString) {
    CRC32 crc = new CRC32();
    crc.update(crcString.getBytes(StandardCharsets.UTF_8));
    return crc.getValue();
  }

  public static long createCrcChecksum(TreeSet<LimitOrder> asks, TreeSet<LimitOrder> bids) {
    return createCrcLong(createCrcString(asks, bids));
  }
}
