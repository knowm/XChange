package org.knowm.xchange.binance.service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.dto.trade.BinanceNewOrder;
import org.knowm.xchange.binance.dto.trade.BinanceOrder;
import org.knowm.xchange.binance.dto.trade.BinanceTrade;
import org.knowm.xchange.binance.dto.trade.OrderType;
import org.knowm.xchange.binance.dto.trade.TimeInForce;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsIdSpan;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

public class BinanceTradeService extends BinanceTradeServiceRaw implements TradeService {

    public BinanceTradeService(Exchange exchange) {
        super(exchange);
    }

    @Override
    public OpenOrders getOpenOrders() {
        throw new ExchangeException("Youo need to provide the currency pair to get the list of open orders.");
    }

    @Override
    public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
        if (!(params instanceof OpenOrdersParamCurrencyPair)) {
            throw new ExchangeException("Youo need to provide the currency pair to get the list of open orders.");
        }
        OpenOrdersParamCurrencyPair pairParams = (OpenOrdersParamCurrencyPair) params;
        CurrencyPair pair = pairParams.getCurrencyPair();
        List<BinanceOrder> binanceOpenOrders = super.openOrders(BinanceAdapters.toSymbol(pair), null, System.currentTimeMillis());
        List<LimitOrder> openOrders = binanceOpenOrders.stream().map(o -> new LimitOrder(BinanceAdapters.convert(o.side), o.origQty
                , o.origQty.subtract(o.executedQty), pair, Long.toString(o.orderId), o.getTime(), o.price))
                .collect(Collectors.toList());
        return new OpenOrders(openOrders);
    }

    @Override
    public String placeMarketOrder(MarketOrder mo) throws IOException {
        BinanceNewOrder newOrder = super.newOrder(BinanceAdapters.toSymbol(mo.getCurrencyPair()), BinanceAdapters.convert(mo.getType())
                , OrderType.MARKET, TimeInForce.GTC, mo.getOriginalAmount(), null, null, null, null, null, System.currentTimeMillis());
        return Long.toString(newOrder.orderId);
    }

    @Override
    public String placeLimitOrder(LimitOrder lo) throws IOException {
        BinanceNewOrder newOrder = super.newOrder(BinanceAdapters.toSymbol(lo.getCurrencyPair()), BinanceAdapters.convert(lo.getType())
                , OrderType.LIMIT, TimeInForce.GTC, lo.getOriginalAmount(), lo.getLimitPrice(), null, null, null, null, System.currentTimeMillis());
        return Long.toString(newOrder.orderId);
    }

    @Override
    public boolean cancelOrder(String orderId) {
        throw new ExchangeException("You need to provide the currency pair to cancel an order.");
    }

    @Override
    public boolean cancelOrder(CancelOrderParams params) throws IOException {
        if (!(params instanceof BinanceCancelOrderParams)) {
            throw new ExchangeException("You need to provide the currency pair and the order id to cancel an order.");
        }
        BinanceCancelOrderParams p = (BinanceCancelOrderParams) params;
        super.cancelOrder(BinanceAdapters.toSymbol(p.pair), BinanceAdapters.id(p.orderId), null, null, null, System.currentTimeMillis());
        return true;
    }

    @Override
    public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
        if (!(params instanceof TradeHistoryParamCurrencyPair)) {
            throw new ExchangeException("You need to provide the currency pair to get the user trades.");
        }
        TradeHistoryParamCurrencyPair pairParams = (TradeHistoryParamCurrencyPair) params;
        CurrencyPair pair = pairParams.getCurrencyPair();
        if (pair == null) {
            throw new ExchangeException("You need to provide the currency pair to get the user trades.");
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
            } catch (Throwable ignored) {}
        }
        
        List<BinanceTrade> binanceTrades = super.myTrades(BinanceAdapters.toSymbol(pair), limit, fromId, null, System.currentTimeMillis());
        List<UserTrade> trades = binanceTrades.stream()
                .map(t -> new UserTrade(BinanceAdapters.convertType(t.isBuyer), t.qty, pair, t.price, t.getTime()
                        , Long.toString(t.id),  Long.toString(t.orderId), t.commission, Currency.getInstance(t.commissionAsset)))
                .collect(Collectors.toList());
        return new UserTrades(trades , TradeSortType.SortByTimestamp);
    }

    @Override
    public TradeHistoryParams createTradeHistoryParams() {
        return new BinanceTradeHistoryParams();
    }

    @Override
    public OpenOrdersParams createOpenOrdersParams() {
        return new DefaultOpenOrdersParamCurrencyPair();
    }

    @Override
    public Collection<Order> getOrder(String... orderIds) {
        throw new NotAvailableFromExchangeException();
    }

}
