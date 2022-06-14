package org.knowm.xchange.examples.blockchain.trade;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.examples.blockchain.BlockchainDemoUtils;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import static org.knowm.xchange.examples.blockchain.BlockchainDemoUtils.END_TIME;

public class BlockchainTradeDemo {
    private static final Exchange BLOCKCHAIN_EXCHANGE = BlockchainDemoUtils.createExchange();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public static void main(String[] args) throws IOException {
        System.out.println("===== TRADE SERVICE =====");
        tradeServiceDemo();
    }

    @SneakyThrows
    private static void tradeServiceDemo() throws IOException {
        TradeService tradeService = BLOCKCHAIN_EXCHANGE.getTradeService();

        System.out.println("===== placeLimitOrder =====");
        LimitOrder limitOrder =
                new LimitOrder.Builder(Order.OrderType.BID, CurrencyPair.ADA_USDT)
                        .originalAmount(new BigDecimal("1.0"))
                        .limitPrice(new BigDecimal("0.23"))
                        .build();

        String tradeLimitOrder = tradeService.placeLimitOrder(limitOrder);
        System.out.println(OBJECT_MAPPER.writeValueAsString(tradeLimitOrder));

        System.out.println("===== placeMarketOrder =====");
        MarketOrder marketOrder =
                new MarketOrder.Builder(Order.OrderType.BID, CurrencyPair.ADA_USDT)
                        .originalAmount(new BigDecimal("1.0"))
                        .cumulativeAmount(new BigDecimal("0.22"))
                        .build();

        String tradeMarketOrder = tradeService.placeMarketOrder(marketOrder);
        System.out.println(OBJECT_MAPPER.writeValueAsString(tradeMarketOrder));

        System.out.println("===== placeStopOrder =====");
        StopOrder stopOrder =
                new StopOrder.Builder(Order.OrderType.BID, CurrencyPair.ADA_USDT)
                        .originalAmount(new BigDecimal("1.0"))
                        .stopPrice(new BigDecimal("0.21"))
                        .build();

        String tradeStopOrder = tradeService.placeStopOrder(stopOrder);
        System.out.println(OBJECT_MAPPER.writeValueAsString(tradeStopOrder));

        Thread.sleep(5000);
        System.out.println("===== getOpenOrders =====");

        try {
            final OpenOrdersParamCurrencyPair openOrdersParamsBtcUsd =
                    (OpenOrdersParamCurrencyPair) tradeService.createOpenOrdersParams();
            openOrdersParamsBtcUsd.setCurrencyPair(CurrencyPair.ADA_USDT);
            OpenOrders openOrdersParams = tradeService.getOpenOrders(openOrdersParamsBtcUsd);
            System.out.println(OBJECT_MAPPER.writeValueAsString(openOrdersParams));

            OpenOrders openOrders = tradeService.getOpenOrders();
            System.out.println(OBJECT_MAPPER.writeValueAsString(openOrders));
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("===== cancelOrder =====");
        CancelOrderByCurrencyPair cancelOrderByCurrencyPair = () -> new CurrencyPair("ADA/USDT");
        Boolean cancelAllOrderByCurrency = tradeService.cancelOrder(cancelOrderByCurrencyPair);
        System.out.println("Canceling returned " + cancelAllOrderByCurrency);

        System.out.println("===== getTradeHistory =====");

        TradeHistoryParams params = tradeService.createTradeHistoryParams();
        if (params instanceof TradeHistoryParamsTimeSpan) {
            final TradeHistoryParamsTimeSpan timeSpanParam = (TradeHistoryParamsTimeSpan) params;
            timeSpanParam.setStartTime(
                    new Date(System.currentTimeMillis() - END_TIME));
        }

        if (params instanceof TradeHistoryParamCurrencyPair) {
            ((TradeHistoryParamCurrencyPair) params).setCurrencyPair(CurrencyPair.ADA_USDT);
        }
        UserTrades tradeHistory = tradeService.getTradeHistory(params);
        System.out.println(OBJECT_MAPPER.writeValueAsString(tradeHistory));

        System.out.println("===== getOrder =====");
        Collection<Order> getOrder = tradeService.getOrder(tradeLimitOrder);
        System.out.println(OBJECT_MAPPER.writeValueAsString(getOrder));
    }
}
