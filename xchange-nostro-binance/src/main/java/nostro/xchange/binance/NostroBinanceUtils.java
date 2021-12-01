package nostro.xchange.binance;

import info.bitrich.xchangestream.binance.dto.BinanceWebsocketBalance;
import nostro.xchange.persistence.BalanceEntity;
import nostro.xchange.persistence.OrderEntity;
import nostro.xchange.persistence.TradeEntity;
import nostro.xchange.utils.NostroUtils;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.dto.account.BinanceBalance;
import org.knowm.xchange.binance.dto.trade.BinanceOrder;
import org.knowm.xchange.binance.dto.trade.BinanceTrade;
import org.knowm.xchange.binance.dto.trade.OrderStatus;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.OpenPosition;
import org.knowm.xchange.dto.trade.UserTrade;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class NostroBinanceUtils {
    
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
        if (isTerminal(bo.status) || e.getUpdated().getTime() < bo.updateTime) {
            return true;
        }
        BigDecimal currentAmount = NostroUtils.readOrderDocument(e.getDocument()).getCumulativeAmount();
        return currentAmount.compareTo(bo.executedQty) < 0;
    }

    public static BalanceEntity toEntity(Balance b) {
        return new BalanceEntity.Builder()
                .asset(b.getCurrency().getCurrencyCode())
                .timestamp(new Timestamp(b.getTimestamp().getTime()))
                .zero(isZeroBalance(b))
                .document(NostroUtils.writeBalanceDocument(b))
                .build();
    }

    public static BalanceEntity toEntity(BinanceBalance b, long updateTime) {
        Balance balance = adapt(b, updateTime);
        return new BalanceEntity.Builder()
                .asset(b.getCurrency().getCurrencyCode())
                .timestamp(new Timestamp(updateTime))
                .zero(isZeroBalance(balance))
                .document(NostroUtils.writeBalanceDocument(balance))
                .build();
    }

    public static Balance adapt(BinanceBalance b, long time) {
        return new Balance.Builder()
                .currency(b.getCurrency())
                .total(b.getTotal())
                .available(b.getAvailable())
                .frozen(b.getLocked())
                .timestamp(new Date(time))
                .build();
    }

    public static Balance adapt(BinanceWebsocketBalance b, long time) {
        return adapt(new BinanceBalance(b.getCurrency().getCurrencyCode(), b.getAvailable(), b.getLocked()), time);
    }

    public static boolean isZeroBalance(Balance b) {
        return b.getAvailable().compareTo(BigDecimal.ZERO) == 0 
                && b.getFrozen().compareTo(BigDecimal.ZERO) == 0;
    }

    public static boolean isZeroBalance(BinanceBalance b) {
        return b.getAvailable().compareTo(BigDecimal.ZERO) == 0
                && b.getLocked().compareTo(BigDecimal.ZERO) == 0;
    }

    public static boolean isZeroBalance(BinanceWebsocketBalance b) {
        return b.getAvailable().compareTo(BigDecimal.ZERO) == 0
                && b.getLocked().compareTo(BigDecimal.ZERO) == 0;
    }

    public static boolean updateRequired(BalanceEntity e, Balance bb) {
        if (e.getTimestamp().getTime() >= bb.getTimestamp().getTime()) {
            return false;
        }
        Balance balance = NostroUtils.readBalanceDocument(e.getDocument());

        return bb.getAvailable().compareTo(balance.getAvailable()) != 0
                || bb.getFrozen().compareTo(balance.getFrozen()) != 0;
    }

    /* Position utils */
    public static boolean isZeroPosition(OpenPosition p) {
        return p.getPrice().compareTo(BigDecimal.ZERO) == 0
                && p.getSize().compareTo(BigDecimal.ZERO) == 0;
    }

    public static boolean updateRequired(BalanceEntity e, OpenPosition p) {
        if (e.getTimestamp().getTime() >= p.getTimestamp().getTime()) {
            return false;
        }
        OpenPosition position = NostroUtils.readPositionDocument(e.getDocument());
        return p.getSize().compareTo(position.getSize()) != 0
                || p.getPrice().compareTo(position.getPrice()) != 0;

    }

    public static BalanceEntity toEntity(OpenPosition p) {
        return new BalanceEntity.Builder()
                .asset(p.getInstrument().toString())
                .timestamp(new Timestamp(p.getTimestamp().getTime()))
                .zero(isZeroPosition(p))
                .document(NostroUtils.writePositionDocument(p))
                .build();
    }
}
