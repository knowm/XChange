package org.knowm.xchange.blockchain.service;

import org.knowm.xchange.blockchain.BlockchainAdapters;
import org.knowm.xchange.blockchain.BlockchainAuthenticated;
import org.knowm.xchange.blockchain.BlockchainErrorAdapter;
import org.knowm.xchange.blockchain.BlockchainExchange;
import org.knowm.xchange.blockchain.dto.BlockchainException;
import org.knowm.xchange.blockchain.dto.trade.BlockchainOrder;
import org.knowm.xchange.blockchain.params.BlockchainTradeHistoryParams;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.OpenPositions;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.*;
import org.knowm.xchange.service.trade.params.orders.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.knowm.xchange.blockchain.BlockchainConstants.*;

public class BlockchainTradeService extends BlockchainTradeServiceRaw implements TradeService {

    public BlockchainTradeService(BlockchainExchange exchange, BlockchainAuthenticated blockchainApi, ResilienceRegistries resilienceRegistries) {
        super(exchange, blockchainApi, resilienceRegistries);
    }

    @Override
    public OpenOrders getOpenOrders() throws IOException {
        return getOpenOrders(createOpenOrdersParams());
    }

    @Override
    public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
        try {
            CurrencyPair currencyPair = null;
            if (params instanceof OpenOrdersParamCurrencyPair) {
                currencyPair = ((OpenOrdersParamCurrencyPair) params).getCurrencyPair();
            }

            if(currencyPair != null) {
                return BlockchainAdapters.toOpenOrders(this.getOrdersBySymbol(BlockchainAdapters.toSymbol(currencyPair)));
            }
            return BlockchainAdapters.toOpenOrders(this.getOrders());
        } catch (BlockchainException e) {
            throw BlockchainErrorAdapter.adapt(e);
        }
    }

    @Override
    public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
        try {
            BlockchainOrder order = this.postOrder(BlockchainAdapters.toBlockchainLimitOrder(limitOrder));
            if (REJECTED.equals(order.getOrdStatus())) throw new ExchangeException(order.getText());
            return Long.toString(order.getExOrdId());
        } catch (BlockchainException e) {
            throw BlockchainErrorAdapter.adapt(e);
        }
    }

    @Override
    public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
        try {
           BlockchainOrder order = this.postOrder(BlockchainAdapters.toBlockchainMarketOrder(marketOrder));
           if (REJECTED.equals(order.getOrdStatus())) throw new ExchangeException(order.getText());
           return Long.toString(order.getExOrdId());
        } catch (BlockchainException e) {
            throw BlockchainErrorAdapter.adapt(e);
        }
    }

    @Override
    public String placeStopOrder(StopOrder stopOrder) throws IOException {
        try {
            BlockchainOrder order = this.postOrder(BlockchainAdapters.toBlockchainStopOrder(stopOrder));
            if (REJECTED.equals(order.getOrdStatus())) throw new ExchangeException(order.getText());
            return Long.toString(order.getExOrdId());
        } catch (BlockchainException e) {
            throw BlockchainErrorAdapter.adapt(e);
        }

    }

    @Override
    public OpenOrdersParams createOpenOrdersParams() {
        return new DefaultOpenOrdersParamCurrencyPair();
    }

    @Override
    public boolean cancelOrder(String orderId) throws IOException {
        return cancelOrderById(orderId);
    }

    @Override
    public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
        if (orderParams instanceof CancelOrderByCurrencyPair) {
            return this.cancelAllOrdersBySymbol(BlockchainAdapters.toSymbol(((CancelOrderByCurrencyPair) orderParams).getCurrencyPair()));
        }

        if (orderParams instanceof CancelOrderByIdParams) {
            return cancelOrder(((CancelOrderByIdParams) orderParams).getOrderId());
        }

        return false;
    }

    @Override
    public Class[] getRequiredCancelOrderParamClasses() {
        return new Class[]{CancelOrderByIdParams.class, CancelOrderByCurrencyPair.class};
    }

    @Override
    public OpenPositions getOpenPositions() {
        throw new NotYetImplementedForExchangeException(NOT_IMPLEMENTED_YET);
    }

    @Override
    public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
        try {
            Long startTime = null;
            Long endTime = null;
            Integer limit = null;

            if (!(params instanceof TradeHistoryParamCurrencyPair)) {
                throw new ExchangeException(CURRENCY_PAIR_EXCEPTION);
            }

            String symbol = BlockchainAdapters.toSymbol(
                    ((TradeHistoryParamCurrencyPair) params).getCurrencyPair());

            if (params instanceof TradeHistoryParamsTimeSpan) {
                if (((TradeHistoryParamsTimeSpan) params).getStartTime() != null) {
                    startTime = ((TradeHistoryParamsTimeSpan) params).getStartTime().getTime();
                }
                if (((TradeHistoryParamsTimeSpan) params).getEndTime() != null) {
                    endTime = ((TradeHistoryParamsTimeSpan) params).getEndTime().getTime();
                }
            }

            if (params instanceof TradeHistoryParamLimit) {
                TradeHistoryParamLimit limitParams = (TradeHistoryParamLimit) params;
                limit = limitParams.getLimit();
            }

            List<BlockchainOrder> tradesOrders = this.getTrades(symbol, startTime, endTime, limit);
            return BlockchainAdapters.toUserTrades(tradesOrders);
        } catch (BlockchainException e) {
            throw BlockchainErrorAdapter.adapt(e);
        }
    }

    @Override
    public BlockchainTradeHistoryParams createTradeHistoryParams() {
        return BlockchainTradeHistoryParams.builder().build();
    }

    @Override
    public Class getRequiredOrderQueryParamClass() {
        return OrderQueryParamCurrencyPair.class;
    }

    @Override
    public Collection<Order> getOrder(String... orderIds) throws IOException {
        List<Order> openOrders = new ArrayList<>();

        for (String orderId : orderIds) {
            if (orderId != null) {
                openOrders.addAll(getOrder(new DefaultQueryOrderParam(orderId)));
            }
        }
        return openOrders;
    }

    @Override
    public Collection<Order> getOrder(OrderQueryParams... params) throws IOException {
        try {
            Collection<Order> orders = new ArrayList<>();
            for (OrderQueryParams param : params) {
                BlockchainOrder order = this.getOrder(param.getOrderId());
                orders.add(BlockchainAdapters.toOpenOrdersById(order));
            }
            return orders;
        } catch (BlockchainException e) {
            throw BlockchainErrorAdapter.adapt(e);
        }
    }


}
