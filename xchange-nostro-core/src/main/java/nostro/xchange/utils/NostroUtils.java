package nostro.xchange.utils;

import nostro.xchange.persistence.OrderEntity;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.utils.ObjectMapperHelper;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NostroUtils {
    
    public static String randomUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String writeOrderDocument(Order o) {
        return ObjectMapperHelper.toCompactJSON(o);
    }

    public static Order readOrderDocument(String s) {
        try {
            return ObjectMapperHelper.readValue(s, Order.class);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static String writeTradeDocument(UserTrade t) {
        return ObjectMapperHelper.toCompactJSON(t);
    }

    public static UserTrade readTradeDocument(String s) {
        try {
            return ObjectMapperHelper.readValue(s, UserTrade.class);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static String writeBalanceDocument(Balance b) {
        return ObjectMapperHelper.toCompactJSON(b);
    }

    public static Balance readBalanceDocument(String s) {
        try {
            return ObjectMapperHelper.readValue(s, Balance.class);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static String writeAccountDocument(AccountDocument a) {
        return ObjectMapperHelper.toCompactJSON(a);
    }

    public static AccountDocument readAccountDocument(String s) {
        try {
            return s != null ? ObjectMapperHelper.readValue(s, AccountDocument.class) : new AccountDocument();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static List<Order> readOrderList(List<OrderEntity> entities) {
        List<Order> orders = new ArrayList<>();
        for(OrderEntity e : entities) {
            orders.add(NostroUtils.readOrderDocument(e.getDocument()));
        }
        return orders;
    }

    public static OpenOrders adaptOpenOrders(List<Order> orders) {
        List<LimitOrder> limit = new ArrayList<>();
        List<Order> others = new ArrayList<>();
        for (Order o : orders) {
            if (o instanceof LimitOrder) {
                limit.add((LimitOrder) o);
            } else {
                others.add(o);
            }
        }
        return new OpenOrders(limit, others);
    }
}
