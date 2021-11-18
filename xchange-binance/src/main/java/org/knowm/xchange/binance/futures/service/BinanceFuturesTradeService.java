package org.knowm.xchange.binance.futures.service;

import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.BinanceAuthenticated;
import org.knowm.xchange.binance.BinanceErrorAdapter;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.binance.dto.BinanceException;
import org.knowm.xchange.binance.dto.trade.*;
import org.knowm.xchange.binance.futures.BinanceFuturesAdapter;
import org.knowm.xchange.binance.futures.BinanceFuturesAuthenticated;
import org.knowm.xchange.binance.futures.dto.trade.BinanceFuturesOrder;
import org.knowm.xchange.binance.futures.dto.trade.BinanceFuturesTrade;
import org.knowm.xchange.binance.service.BinanceTradeService;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.*;
import org.knowm.xchange.service.trade.params.orders.*;
import org.knowm.xchange.utils.Assert;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.knowm.xchange.binance.BinanceResilience.*;
import static org.knowm.xchange.client.ResilienceRegistries.NON_IDEMPOTENT_CALLS_RETRY_CONFIG_NAME;

public class BinanceFuturesTradeService extends BinanceTradeService {
    public BinanceFuturesTradeService(BinanceExchange exchange, BinanceAuthenticated binance, ResilienceRegistries resilienceRegistries) {
        super(exchange, binance, resilienceRegistries);
    }

    @Override
    public OpenOrders getOpenOrders() throws IOException {
        return super.getOpenOrders();       // DONE
    }

    @Override
    public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
        try {
            CurrencyPair pair = null;
            if (params instanceof OpenOrdersParamInstrument) {
                Instrument instrument = ((OpenOrdersParamInstrument) params).getInstrument();
                if (instrument instanceof FuturesContract) {
                    pair = ((FuturesContract)instrument).getCurrencyPair();
                }
            } else if (params instanceof OpenOrdersParamCurrencyPair) {
                pair = ((OpenOrdersParamCurrencyPair) params).getCurrencyPair();
            }
            List<BinanceFuturesOrder> binanceOpenOrders = futuresOpenOrders(pair);

            List<LimitOrder> limitOrders = new ArrayList<>();
            List<Order> otherOrders = new ArrayList<>();
            binanceOpenOrders.forEach(
                    binanceOrder -> {
                        Order order = BinanceFuturesAdapter.adaptOrder(binanceOrder);
                        if (order instanceof LimitOrder) {
                            limitOrders.add((LimitOrder) order);
                        } else {
                            otherOrders.add(order);
                        }
                    });
            return new OpenOrders(limitOrders, otherOrders);
        } catch (BinanceException e) {
            throw BinanceErrorAdapter.adapt(e);
        }
    }

    @Override
    public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
        if (marketOrder.getInstrument() instanceof FuturesContract) {
            CurrencyPair pair = ((FuturesContract) marketOrder.getInstrument()).getCurrencyPair();
            return super.placeMarketOrder(BinanceFuturesAdapter.replaceInstrument(marketOrder, pair));     // DONE
        }
        throw new NotAvailableFromExchangeException("not supported instrument " + marketOrder.getInstrument());
    }

    @Override
    public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
        if (limitOrder.getInstrument() instanceof FuturesContract) {
            CurrencyPair pair = ((FuturesContract) limitOrder.getInstrument()).getCurrencyPair();
            return super.placeLimitOrder(BinanceFuturesAdapter.replaceInstrument(limitOrder, pair));     // DONE
        }
        throw new NotAvailableFromExchangeException("not supported instrument " + limitOrder.getInstrument());
    }

    @Override
    public String placeStopOrder(StopOrder stopOrder) throws IOException {
        if (stopOrder.getInstrument() instanceof FuturesContract) {
            CurrencyPair pair = ((FuturesContract) stopOrder.getInstrument()).getCurrencyPair();
            return super.placeStopOrder(BinanceFuturesAdapter.replaceInstrument(stopOrder, pair));     // DONE
        }
        throw new NotAvailableFromExchangeException("not supported instrument " + stopOrder.getInstrument());
    }

    @Override
    public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
        return super.cancelOrder(orderParams);// DONE
    }

    @Override
    public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
        try {
            Assert.isTrue(
                    params instanceof TradeHistoryParamCurrencyPair,
                    "You need to provide the currency pair to get the user trades.");
            TradeHistoryParamCurrencyPair pairParams = (TradeHistoryParamCurrencyPair) params;
            CurrencyPair pair = pairParams.getCurrencyPair();
            if (pair == null) {
                throw new ExchangeException(
                        "You need to provide the currency pair to get the user trades.");
            }

            Integer limit = null;
            if (params instanceof TradeHistoryParamLimit) {
                TradeHistoryParamLimit limitParams = (TradeHistoryParamLimit) params;
                limit = limitParams.getLimit();
            }
            Long fromId = null;
            if (params instanceof TradeHistoryParamsIdSpan) {
                TradeHistoryParamsIdSpan idParams = (TradeHistoryParamsIdSpan) params;

                try {
                    fromId = BinanceAdapters.id(idParams.getStartId());
                } catch (Throwable ignored) {
                }
            }

            Long startTime = null;
            Long endTime = null;
            if (params instanceof TradeHistoryParamsTimeSpan) {
                if (((TradeHistoryParamsTimeSpan) params).getStartTime() != null) {
                    startTime = ((TradeHistoryParamsTimeSpan) params).getStartTime().getTime();
                }
                if (((TradeHistoryParamsTimeSpan) params).getEndTime() != null) {
                    endTime = ((TradeHistoryParamsTimeSpan) params).getEndTime().getTime();
                }
            }
            if ((fromId != null) && (startTime != null || endTime != null))
                throw new ExchangeException(
                        "You should either specify the id from which you get the user trades from or start and end times. If you specify both, Binance will only honour the fromId parameter.");
            List<BinanceFuturesTrade> binanceTrades = myFuturesTrades(pair, limit, startTime, endTime, fromId);
            List<UserTrade> trades =
                    binanceTrades.stream()
                            .map(
                                    t ->
                                            new UserTrade.Builder()
                                                    .type(BinanceAdapters.convertType(t.buyer))
                                                    .originalAmount(t.qty)
                                                    .instrument(new FuturesContract(pair, null))
                                                    .price(t.price)
                                                    .timestamp(t.getTime())
                                                    .id(Long.toString(t.id))
                                                    .orderId(Long.toString(t.orderId))
                                                    .feeAmount(t.commission)
                                                    .feeCurrency(Currency.getInstance(t.commissionAsset))
                                                    .build())
                            .collect(Collectors.toList());
            long lastId = binanceTrades.stream().map(t -> t.id).max(Long::compareTo).orElse(0L);
            return new UserTrades(trades, lastId, Trades.TradeSortType.SortByTimestamp);
        } catch (BinanceException e) {
            throw BinanceErrorAdapter.adapt(e);
        }
    }

    @Override
    public TradeHistoryParams createTradeHistoryParams() {
        return super.createTradeHistoryParams();        // DONE
    }

    @Override
    public OpenOrdersParams createOpenOrdersParams() {
        return super.createOpenOrdersParams();      // DONE
    }

    @Override
    public void verifyOrder(LimitOrder limitOrder) {
        super.verifyOrder(limitOrder);      // DONE
    }

    @Override
    public void verifyOrder(MarketOrder marketOrder) {
        super.verifyOrder(marketOrder);     // DONE
    }

    @Override
    public Collection<Order> getOrder(OrderQueryParams... params) throws IOException {
        try {
            Collection<Order> orders = new ArrayList<>();
            for (OrderQueryParams param : params) {
                if (!(param instanceof OrderQueryParamCurrencyPair)) {
                    throw new ExchangeException(
                            "Parameters must be an instance of OrderQueryParamCurrencyPair");
                }

                CurrencyPair currencyPair = ((OrderQueryParamCurrencyPair) param).getCurrencyPair();
                if (currencyPair == null) {
                    throw new ExchangeException(
                            "You need to provide the currency pair to query an order.");
                }

                String orderId = param.getOrderId();
                String userReference = null;

                // Try using user-reference only when order-id is null
                if (orderId == null && param instanceof OrderQueryParamUserReference) {
                    userReference = ((OrderQueryParamUserReference) param).getUserReference();
                }

                if (orderId == null && userReference == null) {
                    throw new ExchangeException(
                            "You need to provide either id or user-reference to query an order.");
                }

                orders.add(
                        BinanceFuturesAdapter.adaptOrder(
                                futuresOrderStatus(
                                        currencyPair,
                                        BinanceAdapters.id(orderId),
                                        userReference)));
            }
            return orders;
        } catch (BinanceException e) {
            throw BinanceErrorAdapter.adapt(e);
        }
    }

    @Override
    protected String placeOrder(
            OrderType type, Order order, BigDecimal limitPrice, BigDecimal stopPrice, TimeInForce tif)
            throws IOException {
        try {
            BinanceNewOrder newOrder =
                    decorateApiCall(
                            () ->
                                    ((BinanceFuturesAuthenticated) binance).newFuturesOrder(
                                            BinanceAdapters.toSymbol(order.getCurrencyPair()),
                                            BinanceAdapters.convert(order.getType()),
                                            null,
                                            type,
                                            tif,
                                            order.getOriginalAmount(),
                                            null,
                                            limitPrice,
                                            getClientOrderId(order),
                                            stopPrice,
                                            null,
                                            null,
                                            null,
                                            null,
                                            null,
                                            null,
                                            getRecvWindow(),
                                            getTimestampFactory(),
                                            apiKey,
                                            signatureCreator))
                            .withRetry(retry("newOrder", NON_IDEMPOTENT_CALLS_RETRY_CONFIG_NAME))
                            .withRateLimiter(rateLimiter(ORDERS_PER_SECOND_RATE_LIMITER))
                            .withRateLimiter(rateLimiter(ORDERS_PER_DAY_RATE_LIMITER))
                            .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER))
                            .call();
            return Long.toString(newOrder.orderId);
        } catch (BinanceException e) {
            throw BinanceErrorAdapter.adapt(e);
        }
    }

    public BinanceFuturesOrder futuresOrderStatus(CurrencyPair pair, long orderId, String origClientOrderId)
            throws IOException, BinanceException {
        return decorateApiCall(
                () ->
                        ((BinanceFuturesAuthenticated)binance).futuresOrderStatus(
                                BinanceAdapters.toSymbol(pair),
                                orderId,
                                origClientOrderId,
                                getRecvWindow(),
                                getTimestampFactory(),
                                super.apiKey,
                                super.signatureCreator))
                .withRetry(retry("futuresOrderStatus"))
                .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER))
                .call();
    }

    public List<BinanceFuturesOrder> futuresOpenOrders(CurrencyPair pair) throws BinanceException, IOException {
        return decorateApiCall(
                () ->
                        ((BinanceFuturesAuthenticated)binance).futuresOpenOrders(
                                Optional.ofNullable(pair).map(BinanceAdapters::toSymbol).orElse(null),
                                getRecvWindow(),
                                getTimestampFactory(),
                                apiKey,
                                signatureCreator))
                .withRetry(retry("futuresOpenOrders"))
                .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER), openOrdersPermits(pair))
                .call();
    }

    public List<BinanceFuturesTrade> myFuturesTrades(
            CurrencyPair pair, Integer limit, Long startTime, Long endTime, Long fromId)
            throws BinanceException, IOException {
        return decorateApiCall(
                () ->
                        ((BinanceFuturesAuthenticated)binance).myFuturesTrades(
                                BinanceAdapters.toSymbol(pair),
                                limit,
                                startTime,
                                endTime,
                                fromId,
                                getRecvWindow(),
                                getTimestampFactory(),
                                apiKey,
                                signatureCreator))
                .withRetry(retry("myFuturesTrades"))
                .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER), 5)
                .call();
    }
}
