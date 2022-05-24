package org.knowm.xchange.blockchain.service;

import org.knowm.xchange.blockchain.BlockchainAuthenticated;
import org.knowm.xchange.blockchain.BlockchainExchange;
import org.knowm.xchange.blockchain.dto.trade.BlockchainOrder;
import org.knowm.xchange.client.ResilienceRegistries;

import java.io.IOException;
import java.util.List;

import static org.knowm.xchange.blockchain.BlockchainConstants.*;

public class BlockchainTradeServiceRaw extends BlockchainBaseService {

    protected BlockchainTradeServiceRaw(BlockchainExchange exchange, BlockchainAuthenticated blockchainApi, ResilienceRegistries resilienceRegistries) {
        super(exchange, blockchainApi, resilienceRegistries);
    }

    protected List<BlockchainOrder> getOrders() throws IOException {
        return decorateApiCall(this.blockchainApi::getOrders)
                .withRetry(retry(GET_ORDERS))
                .withRateLimiter(rateLimiter(ENDPOINT_RATE_LIMIT))
                .call();
    }

    protected List<BlockchainOrder> getTrades() throws IOException {
        return decorateApiCall(this.blockchainApi::getTrades)
                .withRetry(retry(GET_TRADES))
                .withRateLimiter(rateLimiter(ENDPOINT_RATE_LIMIT))
                .call();
    }

    protected BlockchainOrder postOrder(BlockchainOrder blockchainOrder) throws IOException{
        return decorateApiCall(() -> this.blockchainApi.postOrder(blockchainOrder))
                .withRetry(retry(POST_ORDER))
                .withRateLimiter(rateLimiter(ENDPOINT_RATE_LIMIT))
                .call();
    }
}
