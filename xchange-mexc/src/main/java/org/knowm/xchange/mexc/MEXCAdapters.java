package org.knowm.xchange.mexc;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.mexc.dto.account.MEXCBalance;
import org.knowm.xchange.mexc.dto.trade.MEXCOrder;
import org.knowm.xchange.mexc.dto.trade.MEXCOrderRequestPayload;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MEXCAdapters {

  public static Wallet adaptMEXCBalances(Map<String, MEXCBalance> mexcBalances) {
    List<Balance> balances = new ArrayList<>(mexcBalances.size());
    for (Map.Entry<String, MEXCBalance> mexcBalance : mexcBalances.entrySet()) {
      MEXCBalance mexcBalanceValue = mexcBalance.getValue();
      BigDecimal available = new BigDecimal(mexcBalanceValue.getAvailable());
      BigDecimal frozen = new BigDecimal(mexcBalanceValue.getFrozen());
      balances.add(new Balance(new Currency(mexcBalance.getKey()),
              frozen.add(available),
              available
      ));
    }
    return Wallet.Builder.from(balances).build();
  }

  public static String convertToMEXCSymbol(String instrumentName) {
    return instrumentName.replace("/", "_").toUpperCase();
  }

  private static Instrument adaptSymbol(String symbol) {
    String[] symbolTokenized = symbol.split("_");
    return new CurrencyPair(symbolTokenized[0], symbolTokenized[1]);
  }

  public static MEXCOrderRequestPayload adaptOrder(LimitOrder limitOrder) {
    return new MEXCOrderRequestPayload(
            convertToMEXCSymbol(limitOrder.getInstrument().toString()),
            limitOrder.getLimitPrice().toString(),
            limitOrder.getOriginalAmount().toString(),
            limitOrder.getType().toString(),
            "LIMIT_ORDER",
            null
    );
  }


  public static Order adaptOrder(MEXCOrder mexcOrder) {

    BigDecimal dealQuantity = new BigDecimal(mexcOrder.getDealQuantity());
    LimitOrder limitOrder = new LimitOrder(
            Order.OrderType.valueOf(mexcOrder.getType()),
            new BigDecimal(mexcOrder.getQuantity()),
            dealQuantity,
            adaptSymbol(mexcOrder.getSymbol()),
            mexcOrder.getId(),
            new Date(Long.parseLong(mexcOrder.getCreateTime())),
            new BigDecimal(mexcOrder.getPrice())) {
    };
    BigDecimal dealAmount = new BigDecimal(mexcOrder.getDealAmount());
    BigDecimal averagePrice = getAveragePrice(dealQuantity, dealAmount);
    limitOrder.setAveragePrice(averagePrice);
    limitOrder.setOrderStatus(Order.OrderStatus.valueOf(mexcOrder.getState()));
    return limitOrder;

  }

  private static BigDecimal getAveragePrice(BigDecimal dealQuantity, BigDecimal dealAmount) {
    if (dealQuantity.compareTo(BigDecimal.ZERO) == 0) {
      return BigDecimal.ZERO;
    }
    return dealAmount.divide(dealQuantity, RoundingMode.UNNECESSARY);
  }

}
