package org.knowm.xchange.blockchain.service;

import org.knowm.xchange.blockchain.BlockchainAdapters;
import org.knowm.xchange.blockchain.BlockchainAuthenticated;
import org.knowm.xchange.blockchain.BlockchainExchange;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

import java.io.IOException;
import java.util.Optional;

public class BlockchainTradeService extends BlockchainTradeServiceRaw implements TradeService {

    public BlockchainTradeService(BlockchainExchange exchange, BlockchainAuthenticated blockchainApi, ResilienceRegistries resilienceRegistries) {
        super(exchange, blockchainApi, resilienceRegistries);
    }

    @Override
    public OpenOrders getOpenOrders() throws IOException {
        return BlockchainAdapters.toOpenOrders(this.getOrders());
    }

    @Override
    public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
        return BlockchainAdapters.toOpenOrders(this.getOrders());
    }

    @Override
    public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
        return Optional.ofNullable(this.postOrder(BlockchainAdapters.toBlockchainOrder(limitOrder)).getExOrdId())
                .map(id -> Long.toString(id))
                .orElse(null);
    }

    @Override
    public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
        return BlockchainAdapters.toUserTrades(this.getTrades());
    }
}
