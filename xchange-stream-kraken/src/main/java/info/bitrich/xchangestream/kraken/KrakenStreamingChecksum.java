package info.bitrich.xchangestream.kraken;

import org.knowm.xchange.dto.trade.LimitOrder;

import java.math.BigDecimal;
import java.util.TreeSet;
import java.util.zip.CRC32;

public class KrakenStreamingChecksum {
    static void addBigDecimalToCrcString(StringBuilder stringBuilder, BigDecimal bigDecimal) {
        stringBuilder.append(bigDecimal.movePointRight(bigDecimal.scale()));
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
        crc.update(crcString.getBytes());
        return crc.getValue();
    }

    public static long createCrcChecksum(TreeSet<LimitOrder> asks, TreeSet<LimitOrder> bids) {
        return createCrcLong(createCrcString(asks, bids));
    }
}
