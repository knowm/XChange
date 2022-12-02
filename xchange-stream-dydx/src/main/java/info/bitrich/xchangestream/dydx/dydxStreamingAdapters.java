package info.bitrich.xchangestream.dydx;

import static org.knowm.xchange.currency.Currency.DAI;
import static org.knowm.xchange.currency.Currency.PBTC;
import static org.knowm.xchange.currency.Currency.PLINK;
import static org.knowm.xchange.currency.Currency.USD;
import static org.knowm.xchange.currency.Currency.USDC;
import static org.knowm.xchange.currency.Currency.WETH;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;

/** Author: Max Gao (gaamox@tutanota.com) Created: 08-03-2021 */
public class dydxStreamingAdapters {

  /** See https://legacy-docs.dydx.exchange/#amounts */
  public static final Map<Currency, Integer> amounts =
      new HashMap<Currency, Integer>() {
        {
          put(WETH, 18);
          put(DAI, 18);
          put(USDC, 6);
          put(USD, 6);
          put(PBTC, 8);
          put(PLINK, 6);
        }
      };

  public static List<LimitOrder> dydxOrderBookChanges(
      org.knowm.xchange.dto.Order.OrderType orderType,
      CurrencyPair currencyPair,
      String[][] changes,
      SortedMap<BigDecimal, BigDecimal> sideEntries,
      int maxDepth,
      boolean isV1) {

    if (sideEntries == null) {
      return Collections.emptyList();
    }

    if (changes != null) {
      for (String[] level : changes) {
        BigDecimal price;
        BigDecimal volume;
        if (isV1) {
          price =
              new BigDecimal(level[level.length - 2])
                  .movePointRight(
                      amounts.get(currencyPair.base) - amounts.get(currencyPair.counter));
          volume =
              new BigDecimal(level[level.length - 1]).movePointLeft(amounts.get(currencyPair.base));
        } else {
          price = new BigDecimal(level[level.length - 2]);
          volume = new BigDecimal(level[level.length - 1]);
        }
        sideEntries.put(price, volume);
      }
    }

    Stream<Map.Entry<BigDecimal, BigDecimal>> stream =
        sideEntries.entrySet().stream()
            .filter(level -> level.getValue().compareTo(BigDecimal.ZERO) != 0);

    if (maxDepth != 0) {
      stream = stream.limit(maxDepth);
    }

    return stream
        .map(
            level ->
                new LimitOrder(
                    orderType, level.getValue(), currencyPair, "0", null, level.getKey()))
        .collect(Collectors.toList());
  }
}
