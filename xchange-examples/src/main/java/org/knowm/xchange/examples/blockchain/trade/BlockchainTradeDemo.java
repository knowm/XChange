package org.knowm.xchange.examples.blockchain.trade;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.blockchain.params.BlockchainTradeHistoryParams;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.examples.blockchain.BlockchainDemoUtils;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;

import static org.knowm.xchange.examples.blockchain.BlockchainDemoUtils.*;

public class BlockchainTradeDemo {
    private static final Exchange BLOCKCHAIN_EXCHANGE = BlockchainDemoUtils.createExchange();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private static final CurrencyPair usdtUsd = new CurrencyPair(Currency.USDT, Currency.USD);
    private static final TradeService tradeService = BLOCKCHAIN_EXCHANGE.getTradeService();

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("===== TRADE SERVICE =====");
        tradeServiceDemo();
    }

    private static void tradeServiceDemo() throws InterruptedException, IOException {
        System.out.println("===== placeLimitOrder =====");
        executeOrder(limitOrder());

        System.out.println("===== placeMarketOrder =====");
        executeOrder(marketOrder());

        System.out.println("===== placeStopOrder =====");
        executeOrder(stopOrder());
    }

    public static void executeOrder(String orderId) throws InterruptedException, IOException{
        Thread.sleep(5000);

        System.out.println("===== getOpenOrders by symbol =====");
        final OpenOrdersParamCurrencyPair openOrdersParamsBtcUsd =
                (OpenOrdersParamCurrencyPair) tradeService.createOpenOrdersParams();
        openOrdersParamsBtcUsd.setCurrencyPair(CurrencyPair.BTC_USDT);
        OpenOrders openOrdersParams = tradeService.getOpenOrders(openOrdersParamsBtcUsd);
        System.out.println(OBJECT_MAPPER.writeValueAsString(openOrdersParams));

        System.out.println("===== getOpenOrders =====");
        OpenOrders openOrders = tradeService.getOpenOrders();
        System.out.println(OBJECT_MAPPER.writeValueAsString(openOrders));

        System.out.println("===== getTradeHistory =====");
        BlockchainTradeHistoryParams params = (BlockchainTradeHistoryParams) tradeService.createTradeHistoryParams();
        ((TradeHistoryParamsTimeSpan) params).setStartTime(
                new Date(System.currentTimeMillis() - END_TIME));

        params.setCurrencyPair(usdtUsd);

        UserTrades tradeHistory = tradeService.getTradeHistory(params);
        System.out.println(OBJECT_MAPPER.writeValueAsString(tradeHistory));

        System.out.println("===== getOrder =====");
        Collection<Order> getOrder = tradeService.getOrder(orderId);
        System.out.println(OBJECT_MAPPER.writeValueAsString(getOrder));

        System.out.println("===== cancelOrder by id =====");
        System.out.println("Canceling returned " + tradeService.cancelOrder(orderId));

        System.out.println("===== cancelOrder by symbol =====");
        CancelOrderByCurrencyPair cancelOrderByCurrencyPair = () -> new CurrencyPair(SYMBOL);
        boolean cancelAllOrderByCurrency = tradeService.cancelOrder(cancelOrderByCurrencyPair);
        System.out.println("Canceling returned " + cancelAllOrderByCurrency);
    }

    public static String limitOrder() throws IOException {
        LimitOrder limitOrder =
                new LimitOrder.Builder(Order.OrderType.BID, CurrencyPair.BTC_USDT)
                        .originalAmount(AMOUNT)
                        .limitPrice(STOP_LIMIT)
                        .build();

        tradeService.verifyOrder(limitOrder);
        String tradeLimitOrder = tradeService.placeLimitOrder(limitOrder);
        System.out.println(OBJECT_MAPPER.writeValueAsString(tradeLimitOrder));

        return tradeLimitOrder;
    }

    public static String marketOrder() throws IOException {
        MarketOrder marketOrder =
                new MarketOrder.Builder(Order.OrderType.BID, CurrencyPair.BTC_USDT)
                        .originalAmount(AMOUNT)
                        .build();

        tradeService.verifyOrder(marketOrder);
        String tradeMarketOrder = tradeService.placeMarketOrder(marketOrder);
        System.out.println(OBJECT_MAPPER.writeValueAsString(tradeMarketOrder));

        return tradeMarketOrder;
    }

    public static String stopOrder() throws IOException {
        StopOrder stopOrder =
                new StopOrder.Builder(Order.OrderType.ASK, usdtUsd)
                        .originalAmount(AMOUNT_LIMIT)
                        .stopPrice(STOP_PRICE)
                        .limitPrice(STOP_LIMIT_PRICE)
                        .build();

        String tradeStopOrder = tradeService.placeStopOrder(stopOrder);
        System.out.println(OBJECT_MAPPER.writeValueAsString(tradeStopOrder));

        return tradeStopOrder;
    }
}
