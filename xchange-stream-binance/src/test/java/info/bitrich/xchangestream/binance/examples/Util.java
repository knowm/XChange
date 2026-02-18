package info.bitrich.xchangestream.binance.examples;

import static java.math.RoundingMode.UP;

import java.math.BigDecimal;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;

public class Util {
  static String printOrderBookShortInfo(OrderBook orderBook) {
    return String.format(
        "orderBook subscribe: askDepth=%s ask=%s askSize=%s bidDepth=%s. bid=%s, bidSize=%s",
        orderBook.getAsks().size(),
        orderBook.getAsks().get(0).getLimitPrice(),
        orderBook.getAsks().get(0).getRemainingAmount(),
        orderBook.getBids().size(),
        orderBook.getBids().get(0).getLimitPrice(),
        orderBook.getBids().get(0).getRemainingAmount());
  }

  static BigDecimal getMinAmount(
      BigDecimal usdtMin, BigDecimal amount, Ticker ticker, int volumeScale) {
    // minimal trade size - 5 USDT
    if (amount.multiply(ticker.getLast()).compareTo(usdtMin) <= 0) {
      amount = new BigDecimal("6").divide(ticker.getLast(), volumeScale, UP);
    }
    return amount;
  }
}
