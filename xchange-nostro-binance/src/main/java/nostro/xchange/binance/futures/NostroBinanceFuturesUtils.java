package nostro.xchange.binance.futures;

import nostro.xchange.binance.NostroBinanceUtils;
import nostro.xchange.persistence.OrderEntity;
import nostro.xchange.persistence.TradeEntity;
import nostro.xchange.utils.NostroUtils;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.futures.BinanceFuturesAdapter;
import org.knowm.xchange.binance.futures.dto.trade.BinanceFuturesOrder;
import org.knowm.xchange.binance.futures.dto.trade.BinanceFuturesTrade;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.utils.jackson.InstrumentDeserializer;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class NostroBinanceFuturesUtils {
    public static OrderEntity toEntity(BinanceFuturesOrder binanceOrder) {
        final Order order = BinanceFuturesAdapter.adaptOrder(binanceOrder);
        return new OrderEntity.Builder()
                .id(binanceOrder.clientOrderId)
                .externalId(Long.toString(binanceOrder.orderId))
                .instrument(order.getInstrument().toString())
                .terminal(NostroBinanceUtils.isTerminal(binanceOrder.status))
                .document(NostroUtils.writeOrderDocument(order))
                .created(new Timestamp(binanceOrder.time))
                .updated(new Timestamp(binanceOrder.updateTime))
                .build();
    }

    public static boolean updateRequired(OrderEntity e, BinanceFuturesOrder o) {
        if (e.isTerminal()) {
            return false;
        }
        if (NostroBinanceUtils.isTerminal(o.status) || e.getUpdated().getTime() < o.updateTime) {
            return true;
        }

        BigDecimal currentAmount = NostroUtils.readOrderDocument(e.getDocument()).getCumulativeAmount();
        return currentAmount.compareTo(o.executedQty) < 0;
    }

    public static TradeEntity toEntity(BinanceFuturesTrade binanceTrade, String orderId, CurrencyPair pair) {
        return new TradeEntity.Builder()
                .orderId(orderId)
                .externalId(Long.toString(binanceTrade.id))
                .timestamp(new Timestamp(binanceTrade.time))
                .document(NostroUtils.writeTradeDocument(adaptTrade(binanceTrade, pair)))
                .build();
    }

    public static UserTrade adaptTrade(BinanceFuturesTrade t, CurrencyPair pair) {
        return new UserTrade.Builder()
                .type(BinanceAdapters.convertType(t.buyer))
                .originalAmount(t.qty)
                .instrument(pair)
                .price(t.price)
                .timestamp(t.getTime())
                .id(Long.toString(t.id))
                .orderId(Long.toString(t.orderId))
                .feeAmount(t.commission)
                .feeCurrency(Currency.getInstance(t.commissionAsset))
                .build();
    }

    public static Instrument adaptInstrument(String s) {
        return InstrumentDeserializer.fromString(s);
    }
}
