package org.knowm.xchange.blockchain.service;

import org.knowm.xchange.blockchain.BlockchainAdapters;
import org.knowm.xchange.blockchain.BlockchainAuthenticated;
import org.knowm.xchange.blockchain.BlockchainErrorAdapter;
import org.knowm.xchange.blockchain.BlockchainExchange;
import org.knowm.xchange.blockchain.dto.BlockchainException;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelAllOrders;
import org.knowm.xchange.service.trade.params.CancelOrderByCurrencyPair;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

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
            return BlockchainAdapters.toOpenOrders(this.getOrders());
        } catch (BlockchainException e) {
            throw BlockchainErrorAdapter.adapt(e);
        }
    }

    @Override
    public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
        Long id = this.postOrder(BlockchainAdapters.toBlockchainOrder(LIMIT, limitOrder,
                null,
                null)).getExOrdId();
        if (id != null) return Long.toString(id);
        return null;
    }

    @Override
    public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
        Long id = this.postOrder(BlockchainAdapters.toBlockchainOrder(MARKET,
                null, marketOrder,
                null)).getExOrdId();
        if (id != null) return Long.toString(id);
        return null;
    }

    @Override
    public String placeStopOrder(StopOrder stopOrder) throws IOException {
        Long id = this.postOrder(BlockchainAdapters.toBlockchainOrder(STOP_ORDER,
                null,
                null, stopOrder)).getExOrdId();
        if (id != null) return Long.toString(id);
        return null;
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
            this.cancelAllOrdersBySymbol(
                    ((CancelOrderByCurrencyPair) orderParams).getCurrencyPair().toString());
            return true;
        } else {
            if (orderParams instanceof CancelOrderByIdParams) {
                return cancelOrder(((CancelOrderByIdParams) orderParams).getOrderId());
            } else {
                return false;
            }
        }
    }

    @Override
    public Collection<String> cancelAllOrders(CancelAllOrders orderParams) throws IOException {
        if (orderParams instanceof CancelOrderByCurrencyPair) {
            cancelOrder(orderParams);
            Collection<String> cancelIds = Arrays.asList(((CancelOrderByCurrencyPair) orderParams).getCurrencyPair().toString());
            return cancelIds;
        }else {
            return null;
        }
    }


}
