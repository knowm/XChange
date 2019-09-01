package info.bitrich.xchange.coinmate;

import info.bitrich.xchange.coinmate.dto.CoinmateWebSocketUserTrade;
import info.bitrich.xchange.coinmate.dto.CoinmateWebsocketOpenOrder;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class CoinmateStreamingAdapter {

    public static String getChannelPostfix(CurrencyPair currencyPair) {
        return currencyPair.base.toString().toUpperCase() + "_" + currencyPair.counter.toString().toUpperCase();
    }

    public static UserTrades adaptWebSocketUserTrades(List<CoinmateWebSocketUserTrade> coinmateWebSocketUserTrades, CurrencyPair currencyPair) {
        List<UserTrade> userTrades = new ArrayList<>();
        coinmateWebSocketUserTrades.forEach((coinmateWebSocketUserTrade) -> {
            userTrades.add(
                    new UserTrade(
                            (coinmateWebSocketUserTrade.getUserOrderType().equals("SELL"))
                                    ? Order.OrderType.ASK
                                    : Order.OrderType.BID,
                            BigDecimal.valueOf(coinmateWebSocketUserTrade.getAmount()),
                            currencyPair,
                            BigDecimal.valueOf(coinmateWebSocketUserTrade.getPrice()),
                            Date.from(Instant.ofEpochMilli(coinmateWebSocketUserTrade.getTimestamp())),
                            coinmateWebSocketUserTrade.getTransactionId(),
                            (coinmateWebSocketUserTrade.getUserOrderType().equals("SELL"))
                                    ? coinmateWebSocketUserTrade.getSellOrderId()
                                    : coinmateWebSocketUserTrade.getBuyOrderId(),
                            BigDecimal.valueOf(coinmateWebSocketUserTrade.getFee()),
                            currencyPair.counter)
            );
        });
        return new UserTrades(userTrades, Trades.TradeSortType.SortByTimestamp);
    }

    public static OpenOrders adaptWebsocketOpenOrders(List<CoinmateWebsocketOpenOrder> coinmateWebsocketOpenOrders, CurrencyPair currencyPair) {
        List<LimitOrder> openOrders = new ArrayList<>();
        coinmateWebsocketOpenOrders.forEach((coinmateWebsocketOpenOrder) -> {
            openOrders.add(
                    new LimitOrder(
                            (coinmateWebsocketOpenOrder.getOrderType().contains("SELL"))
                                    ? Order.OrderType.ASK
                                    : Order.OrderType.BID,
                            BigDecimal.valueOf(coinmateWebsocketOpenOrder.getAmount()),
                            BigDecimal.valueOf(coinmateWebsocketOpenOrder.getOriginalOrderSize()),
                            currencyPair, coinmateWebsocketOpenOrder.getId(),
                            Date.from(Instant.ofEpochMilli(coinmateWebsocketOpenOrder.getTimestamp())),
                            BigDecimal.valueOf(coinmateWebsocketOpenOrder.getPrice())
                    )
            );
        });
        return new OpenOrders(openOrders);
    }
}
