package nostro.xchange.binance;

import nostro.xchange.persistence.OrderEntity;
import nostro.xchange.persistence.TradeEntity;
import nostro.xchange.utils.NostroUtils;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.dto.trade.BinanceOrder;
import org.knowm.xchange.binance.dto.trade.BinanceTrade;
import org.knowm.xchange.binance.dto.trade.OrderStatus;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.UserTrade;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class BinanceNostroUtils {
    
    public static boolean isTerminal(OrderStatus orderStatus) {
        return orderStatus == OrderStatus.CANCELED ||
                orderStatus == OrderStatus.FILLED ||
                orderStatus == OrderStatus.EXPIRED;
    }
    
    public static OrderEntity toEntity(BinanceOrder binanceOrder) {
        Order order = BinanceAdapters.adaptOrder(binanceOrder);
        return new OrderEntity.Builder()
                .id(binanceOrder.clientOrderId)
                .externalId(Long.toString(binanceOrder.orderId))
                .instrument(order.getInstrument().toString())
                .terminal(isTerminal(binanceOrder.status))
                .document(NostroUtils.writeOrderDocument(order))
                .created(new Timestamp(binanceOrder.time))
                .updated(new Timestamp(binanceOrder.updateTime))
                .build();
    }
    
    public static TradeEntity toEntity(BinanceTrade binanceTrade, String orderId, CurrencyPair pair) {
        return new TradeEntity.Builder()
                .orderId(orderId)
                .externalId(Long.toString(binanceTrade.id))
                .timestamp(new Timestamp(binanceTrade.time))
                .document(NostroUtils.writeTradeDocument(adapt(binanceTrade, pair)))
                .build();
    }

    public static UserTrade adapt(BinanceTrade t, CurrencyPair pair) {
        return new UserTrade.Builder()
                .type(BinanceAdapters.convertType(t.isBuyer))
                .originalAmount(t.qty)
                .currencyPair(pair)
                .price(t.price)
                .timestamp(t.getTime())
                .id(Long.toString(t.id))
                .orderId(Long.toString(t.orderId))
                .feeAmount(t.commission)
                .feeCurrency(Currency.getInstance(t.commissionAsset))
                .build();
    }
    
    public static boolean updateRequired(OrderEntity e, BinanceOrder bo) {
        if (e.isTerminal()) {
            return false;
        }
        if (isTerminal(bo.status) || e.getUpdated().before(bo.getUpdateTime())) {
            return true;
        }
        BigDecimal currentAmount = NostroUtils.readOrderDocument(e.getDocument()).getCumulativeAmount();
        return currentAmount.compareTo(bo.executedQty) < 0;
    }
}
