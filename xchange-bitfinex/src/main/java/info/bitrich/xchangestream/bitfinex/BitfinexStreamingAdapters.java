package info.bitrich.xchangestream.bitfinex;

import com.fasterxml.jackson.databind.JsonNode;

import info.bitrich.xchangestream.bitfinex.dto.BitfinexWebSocketAuthBalance;
import info.bitrich.xchangestream.bitfinex.dto.BitfinexWebSocketAuthOrder;
import info.bitrich.xchangestream.bitfinex.dto.BitfinexWebSocketAuthPreTrade;
import info.bitrich.xchangestream.bitfinex.dto.BitfinexWebSocketAuthTrade;
import info.bitrich.xchangestream.core.OrderStatusChange;
import info.bitrich.xchangestream.core.OrderStatusChangeType;

import io.reactivex.annotations.Nullable;

import io.reactivex.annotations.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Date;
import java.util.stream.Stream;

import static java.util.stream.StreamSupport.stream;

class BitfinexStreamingAdapters {

    private static final Logger LOG = LoggerFactory.getLogger(BitfinexStreamingAdapters.class);

    @Nullable
    static BitfinexWebSocketAuthPreTrade adaptPreTrade(JsonNode preTrade) {
        if (preTrade.size() < 12) {
            LOG.error("addPreTrade unexpected record size={}, record={}", preTrade.size(), preTrade.toString());
            return null;
        }
        long id = preTrade.get(0).longValue();
        String pair = preTrade.get(1).textValue();
        long mtsCreate = preTrade.get(2).longValue();
        long orderId = preTrade.get(3).longValue();
        BigDecimal execAmount = preTrade.get(4).decimalValue();
        BigDecimal execPrice = preTrade.get(5).decimalValue();
        String orderType = preTrade.get(6).textValue();
        BigDecimal orderPrice = preTrade.get(7).decimalValue();
        int maker = preTrade.get(8).intValue();
        BitfinexWebSocketAuthPreTrade preTradeObject = new BitfinexWebSocketAuthPreTrade(id, pair, mtsCreate, orderId,
                execAmount, execPrice, orderType, orderPrice, maker);
        LOG.debug("New pre trade: {}", preTradeObject);
        return preTradeObject;
    }

    @Nullable
    static BitfinexWebSocketAuthTrade adaptTrade(JsonNode trade) {
        if (trade.size() < 11) {
            LOG.error("addTrade unexpected record size={}, record={}", trade.size(), trade.toString());
            return null;
        }
        long id = trade.get(0).longValue();
        String pair = trade.get(1).textValue();
        long mtsCreate = trade.get(2).longValue();
        long orderId = trade.get(3).longValue();
        BigDecimal execAmount = trade.get(4).decimalValue();
        BigDecimal execPrice = trade.get(5).decimalValue();
        String orderType = trade.get(6).textValue();
        BigDecimal orderPrice = trade.get(7).decimalValue();
        int maker = trade.get(8).intValue();
        BigDecimal fee = trade.get(9).decimalValue();
        String currency = trade.get(10).textValue();
        BitfinexWebSocketAuthTrade tradeObject = new BitfinexWebSocketAuthTrade(
                id, pair, mtsCreate, orderId, execAmount, execPrice, orderType, orderPrice, maker, fee, currency
        );
        LOG.debug("New trade: {}", tradeObject);
        return tradeObject;
    }

    static Stream<BitfinexWebSocketAuthOrder> adaptOrders(JsonNode orders) {
        Iterable<JsonNode> iterator = () -> orders.iterator();
        return stream(iterator.spliterator(), false)
                .filter(o -> o.size() >= 32)
                .map(BitfinexStreamingAdapters::createOrderObject)
                .peek(o -> LOG.debug("New order: {}", o));
    }

    @Nullable
    static BitfinexWebSocketAuthOrder adaptOrder(JsonNode order) {
        BitfinexWebSocketAuthOrder orderObject = createOrderObject(order);
        if (orderObject == null) {
            return null;
        }
        LOG.debug("Updated order: {}", orderObject);
        return orderObject;
    }

    @Nullable
    static BitfinexWebSocketAuthBalance adaptBalance(JsonNode balance) {
        BitfinexWebSocketAuthBalance balanceObject = createBalanceObject(balance);
        if (balanceObject == null) {
            return null;
        }
        LOG.debug("Balance: {}", balanceObject);
        return balanceObject;
    }

    static Stream<BitfinexWebSocketAuthBalance> adaptBalances(JsonNode balances) {
        Iterable<JsonNode> iterator = () -> balances.iterator();
        return stream(iterator.spliterator(), false)
                .filter(o -> o.size() >= 5)
                .map(BitfinexStreamingAdapters::createBalanceObject)
                .peek(o -> LOG.debug("Balance: {}", o));
    }

    @Nullable
    static private BitfinexWebSocketAuthBalance createBalanceObject(JsonNode balance) {
        if (balance.size() < 5) {
            LOG.error("createBalanceObject unexpected record size={}, record={}", balance.size(), balance.toString());
            return null;
        }

        String walletType = balance.get(0).textValue();
        String currency = balance.get(1).textValue();
        BigDecimal balanceValue = balance.get(2).decimalValue();
        BigDecimal unsettledInterest = balance.get(3).decimalValue();
        BigDecimal balanceAvailable = balance.get(4).asText().equals("null") ? null : balance.get(4).decimalValue();

        return new BitfinexWebSocketAuthBalance(walletType, currency, balanceValue, unsettledInterest, balanceAvailable);
    }

    @Nullable
    static private BitfinexWebSocketAuthOrder createOrderObject(JsonNode order) {
        if (order.size() < 32) {
            LOG.error("createOrderObject unexpected record size={}, record={}", order.size(), order.toString());
            return null;
        }

        long id = order.get(0).longValue();
        long groupId = order.get(1).longValue();
        long cid = order.get(2).longValue();
        String symbol = order.get(3).textValue();
        long mtsCreate = order.get(4).longValue();
        long mtsUpdate = order.get(5).longValue();
        BigDecimal amount = order.get(6).decimalValue();
        BigDecimal amountOrig = order.get(7).decimalValue();
        String type = order.get(8).textValue();
        String typePrev = order.get(9).textValue();
        int flags = order.get(12).intValue();
        String orderStatus = order.get(13).textValue();
        BigDecimal price = order.get(16).decimalValue();
        BigDecimal priceAvg = order.get(17).decimalValue();
        BigDecimal priceTrailing = order.get(18).decimalValue();
        BigDecimal priceAuxLimit = order.get(19).decimalValue();
        long placedId = order.get(25).longValue();

        return new BitfinexWebSocketAuthOrder(
                id, groupId, cid, symbol, mtsCreate, mtsUpdate, amount, amountOrig,
                type, typePrev, orderStatus, price, priceAvg, priceTrailing,
                priceAuxLimit, placedId, flags
        );
    }

    @Nullable
    static OrderStatusChange adaptOrderStatus(BitfinexWebSocketAuthOrder authOrder) {
        OrderStatusChangeType status = adaptOrderStatusType(authOrder.getOrderStatus());
        if (status == null)
            return OrderStatusChange.create().build();
        return OrderStatusChange.create()
            .type(status)
            .timestamp(new Date())
            .orderId(Long.toString(authOrder.getId()))
            .build();
    }

    @Nullable
    private static OrderStatusChangeType adaptOrderStatusType(String orderStatus) {
        switch(orderStatus) {
            case "ACTIVE": return OrderStatusChangeType.OPENED;
            case "EXECUTED": return OrderStatusChangeType.CLOSED;
            case "CANCELED": return OrderStatusChangeType.CLOSED;
            default: return null;
        }
    }
}