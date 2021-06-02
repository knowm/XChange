package info.bitrich.xchangestream.kraken;

import com.google.common.cache.*;
import org.knowm.xchange.dto.trade.LimitOrder;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.zip.CRC32;

public class KrakenStreamingChecksum {
    private static final LoadingCache<BigDecimal, String> crcStringCache = CacheBuilder
            .newBuilder()
            .expireAfterAccess(1, TimeUnit.MINUTES)
            .maximumSize(500)
            .build(new CacheLoader<BigDecimal, String>() {
                @Override
                public String load(BigDecimal key) throws Exception {
                    String result = key.toPlainString();
                    result = result.replace(".","");
                    while (result.startsWith("0")){
                        result = result.replaceFirst("0", "");
                    }
                    return result;
                }
            });

    static void addBigDecimalToCrcString(StringBuilder stringBuilder, BigDecimal bigDecimal) {
        stringBuilder.append(crcStringCache.getUnchecked(bigDecimal));
    }

    static void addOrderToCrcString(StringBuilder stringBuilder, LimitOrder order) {
        addBigDecimalToCrcString(stringBuilder, order.getLimitPrice());
        addBigDecimalToCrcString(stringBuilder, order.getOriginalAmount());
    }

    public static String createCrcString(TreeSet<LimitOrder> asks, TreeSet<LimitOrder> bids) {
        StringBuilder stringBuilder = new StringBuilder();
        asks.stream().limit(10).forEach(o -> addOrderToCrcString(stringBuilder, o));
        bids.stream().limit(10).forEach(o -> addOrderToCrcString(stringBuilder, o));
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
