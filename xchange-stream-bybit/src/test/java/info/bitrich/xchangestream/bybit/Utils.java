package info.bitrich.xchangestream.bybit;

import static java.math.RoundingMode.UP;

import java.math.BigDecimal;
import org.knowm.xchange.dto.marketdata.Ticker;

public class Utils {
  public static BigDecimal getMinAmount(
      BigDecimal usdtMin, BigDecimal amount, Ticker ticker, int volumeScale) {
    // minimal trade size - 5 USDT
    if (amount.multiply(ticker.getLast()).compareTo(usdtMin) <= 0) {
      amount = usdtMin.divide(ticker.getLast(), volumeScale, UP);
    }
    return amount;
  }
}
