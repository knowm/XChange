package org.knowm.xchange.mexc;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.mexc.dto.account.MEXCBalance;
import org.knowm.xchange.mexc.dto.trade.MEXCOrderRequestPayload;

import java.math.BigDecimal;
import java.util.ArrayList;
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
}
