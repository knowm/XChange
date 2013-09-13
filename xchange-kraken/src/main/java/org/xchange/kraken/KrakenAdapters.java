package org.xchange.kraken;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;
import org.xchange.kraken.dto.marketdata.KrakenTicker;

import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Ticker.TickerBuilder;
import com.xeiam.xchange.dto.trade.LimitOrder;

public class KrakenAdapters {

  public static List<LimitOrder> adaptOrders(List<BigDecimal[]> orders, String currency, String tradableIdentifier, String orderType) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>(orders.size());
    for (BigDecimal[] order : orders) {
      limitOrders.add(adaptOrder(order, orderType, currency, tradableIdentifier));
    }
    return limitOrders;
  }

  private static LimitOrder adaptOrder(BigDecimal[] order, String orderType, String currency, String tradableIdentifier) {

    OrderType type = orderType.equalsIgnoreCase("asks") ? OrderType.ASK : OrderType.BID;
    Date timeStamp = new Date(order[2].longValue());
    BigMoney price = BigMoney.of(CurrencyUnit.of(currency), order[0]);
    BigDecimal volume = order[1];

    return new LimitOrder(type, volume, tradableIdentifier, currency, price, timeStamp);
  }

  public static Ticker adaptTicker(KrakenTicker krakenTicker, String currency, String tradableIdentifier) {

    TickerBuilder builder = new TickerBuilder();
    builder.withAsk(BigMoney.of(CurrencyUnit.of(currency), krakenTicker.getAsk()[0]));
    builder.withBid(BigMoney.of(CurrencyUnit.of(currency), krakenTicker.getBid()[0]));
    builder.withLast(BigMoney.of(CurrencyUnit.of(currency), krakenTicker.getClose()[0]));
    builder.withHigh(BigMoney.of(CurrencyUnit.of(currency), krakenTicker.getHigh()[0]));
    builder.withLow(BigMoney.of(CurrencyUnit.of(currency), krakenTicker.getLow()[0]));
    builder.withVolume(krakenTicker.getVolume()[1]);
    builder.withTradableIdentifier(tradableIdentifier);
    return builder.build();
  }
}
