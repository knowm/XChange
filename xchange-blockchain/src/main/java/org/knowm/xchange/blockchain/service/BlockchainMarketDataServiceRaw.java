package org.knowm.xchange.blockchain.service;

import org.knowm.xchange.blockchain.BlockchainAdapters;
import org.knowm.xchange.blockchain.BlockchainAuthenticated;
import org.knowm.xchange.blockchain.BlockchainExchange;
import org.knowm.xchange.blockchain.dto.BlockchainException;
import org.knowm.xchange.blockchain.dto.marketdata.BlockchainOrderBook;
import org.knowm.xchange.blockchain.dto.trade.BlockchainOrder;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.CurrencyPair;

import java.io.IOException;
import java.util.List;

import static org.knowm.xchange.blockchain.BlockchainConstants.*;

public class BlockchainMarketDataServiceRaw extends BlockchainBaseService{

    protected BlockchainMarketDataServiceRaw(BlockchainExchange exchange, BlockchainAuthenticated blockchainApi, ResilienceRegistries resilienceRegistries) {
        super(exchange, blockchainApi, resilienceRegistries);
    }

    protected BlockchainOrderBook getOrderBookL3(CurrencyPair currencyPair) throws IOException, BlockchainException {
        return decorateApiCall(() -> this.blockchainApi.getOrderBookL3(BlockchainAdapters.toSymbol(currencyPair)))
                .withRetry(retry(GET_ORDER_BOOK_L3))
                .withRateLimiter(rateLimiter(ENDPOINT_RATE_LIMIT))
                .call();
    }

    protected List<BlockchainOrder> getExchangeTrades(String symbol, Long startTime, Long endTime, Integer limit) throws IOException, BlockchainException {
        return decorateApiCall(() -> this.blockchainApi.getTrades(symbol, startTime, endTime, limit))
                .withRetry(retry(GET_EXCHANGE_TRADES))
                .withRateLimiter(rateLimiter(ENDPOINT_RATE_LIMIT))
                .call();
    }
}
