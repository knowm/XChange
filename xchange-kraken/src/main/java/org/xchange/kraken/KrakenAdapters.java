package org.xchange.kraken;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;
import org.xchange.kraken.dto.marketdata.KrakenOrder;

import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;

public class KrakenAdapters {
    public static List<LimitOrder> adaptOrders(List<KrakenOrder> orders, String currency, String tradableIdentifier, String orderType){
        List<LimitOrder> limitOrders = new ArrayList<LimitOrder>(orders.size());
        for(KrakenOrder order:orders){
          limitOrders.add( adaptOrder(order, orderType,currency, tradableIdentifier));
        }
        return limitOrders;
    }

    private static LimitOrder adaptOrder(KrakenOrder order, String orderType,String currency, String tradableIdentifier) {
        OrderType type = orderType.equalsIgnoreCase("asks")?OrderType.ASK:OrderType.BID;
        Date timeStamp = new Date(order.getTimestamp());
        BigMoney price =BigMoney.of(CurrencyUnit.of(currency), order.getPrice());
        BigDecimal volume = order.getVolume();
        
        return new LimitOrder(type, volume, tradableIdentifier, currency, price,timeStamp);
    }
}
