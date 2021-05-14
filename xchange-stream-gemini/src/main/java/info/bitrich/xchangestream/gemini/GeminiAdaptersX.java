package info.bitrich.xchangestream.gemini;

import info.bitrich.xchangestream.gemini.dto.GeminiLimitOrder;
import info.bitrich.xchangestream.gemini.dto.GeminiOrderbook;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;

public class GeminiAdaptersX {

    public static OrderBook toOrderbook(
            GeminiOrderbook geminiOrderbook, int maxLevels, Date timestamp) {
        List<LimitOrder> askOrders =
                geminiOrderbook.getAsks().stream()
                        .limit(maxLevels)
                        .map(
                                (GeminiLimitOrder geminiLimitOrder) ->
                                        toLimitOrder(
                                                geminiOrderbook.getCurrencyPair(), geminiLimitOrder, Order.OrderType.ASK))
                        .collect(Collectors.toList());

        List<LimitOrder> bidOrders =
                geminiOrderbook.getBids().stream()
                        .limit(maxLevels)
                        .map(
                                (GeminiLimitOrder geminiLimitOrder) ->
                                        toLimitOrder(
                                                geminiOrderbook.getCurrencyPair(), geminiLimitOrder, Order.OrderType.BID))
                        .collect(Collectors.toList());

        return new OrderBook(timestamp, askOrders, bidOrders);
    }

    private static LimitOrder toLimitOrder(
            CurrencyPair currencyPair, GeminiLimitOrder geminiLimitOrder, Order.OrderType side) {
        return new LimitOrder.Builder(side, currencyPair)
                .limitPrice(geminiLimitOrder.getPrice())
                .originalAmount(geminiLimitOrder.getAmount())
                .timestamp(convertBigDecimalTimestampToDate(geminiLimitOrder.getTimestamp()))
                .build();
    }

    static Date convertBigDecimalTimestampToDate(BigDecimal timestampInSeconds) {
        return new Date((long) Math.floor(timestampInSeconds.doubleValue() * 1000));
    }
}