package org.knowm.xchange.blockchain.service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.knowm.xchange.blockchain.BlockchainAuthenticated;
import org.knowm.xchange.blockchain.BlockchainErrorAdapter;
import org.knowm.xchange.blockchain.BlockchainExchange;
import org.knowm.xchange.blockchain.dto.BlockchainException;
import org.knowm.xchange.blockchain.dto.trade.BlockchainOrder;
import org.knowm.xchange.blockchain.params.BlockchainOrderParams;
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

    protected BlockchainOrder postOrder(BlockchainOrderParams blockchainOrder) throws IOException{
        return decorateApiCall(() -> this.blockchainApi.postOrder(blockchainOrder))
                .withRetry(retry(POST_ORDER))
                .withRateLimiter(rateLimiter(ENDPOINT_RATE_LIMIT))
                .call();
    }

    protected Boolean cancelOrderById(String orderId) throws IOException, BlockchainException{
        try {
            decorateApiCall(() -> this.blockchainApi.cancelOrder(orderId))
                    .withRetry(retry(CANCEL_ORDER))
                    .withRateLimiter(rateLimiter(ENDPOINT_RATE_LIMIT))
                    .call();
            return true;
        }catch (BlockchainException e){
            throw BlockchainErrorAdapter.adapt(e);
        }

    }

    protected Boolean cancelAllOrdersBySymbol(String symbol) throws IOException, BlockchainException{
        try {
            decorateApiCall(() -> this.blockchainApi.cancelAllOrders(symbol))
                    .withRetry(retry(CANCEL_ALL_ORDERS))
                    .withRateLimiter(rateLimiter(ENDPOINT_RATE_LIMIT))
                    .call();
            return true;
        }catch (BlockchainException e){
            throw BlockchainErrorAdapter.adapt(e);
        }
    }
}
