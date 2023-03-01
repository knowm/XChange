package org.knowm.xchange.dto.trade;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Collectors;
import org.knowm.xchange.dto.marketdata.Trades;

public class UserTrades extends Trades {

    private static final long serialVersionUID = 1647451200702821967L;

    public UserTrades(
            @JsonProperty("trades") List<UserTrade> trades,
            @JsonProperty("tradeSortType") TradeSortType tradeSortType) {

        super((List) trades, tradeSortType);
    }

    public UserTrades(List<UserTrade> trades, long lastID, TradeSortType tradeSortType) {

        super((List) trades, lastID, tradeSortType);
    }

    public UserTrades(
            List<UserTrade> trades, long lastID, TradeSortType tradeSortType, String nextPageCursor) {
        super((List) trades, lastID, tradeSortType, nextPageCursor);
    }

    public List<UserTrade> getUserTrades() {
        return (List) getTrades();
    }

    public List<UserTrade> getCombinedUserTrades() {
        List<UserTrade> flattenedTrades = getUserTrades().stream()
                .collect(Collectors.groupingBy(UserTrade::getOrderId))
                .entrySet()
                .stream()
                .map(entry -> {
                    String orderId = entry.getKey();
                    List<UserTrade> tradesWithSameOrderId = entry.getValue();
                    BigDecimal totalAmount = BigDecimal.ZERO;
                    BigDecimal totalPrice = BigDecimal.ZERO;

                    for (UserTrade trade : tradesWithSameOrderId) {
                        totalAmount = totalAmount.add(trade.getAmount());
                        totalPrice = totalPrice.add(trade.getPrice().multiply(trade.getAmount()));
                    }

                    BigDecimal averagePrice = totalPrice.divide(totalAmount, RoundingMode.HALF_EVEN);
                    return new UserTrade(totalAmount, averagePrice, orderId);
                })
                .collect(Collectors.toList());
    }
}
