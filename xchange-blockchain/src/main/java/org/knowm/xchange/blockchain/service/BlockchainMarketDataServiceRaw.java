package org.knowm.xchange.blockchain.service;

import org.knowm.xchange.blockchain.BlockchainAdapters;
import org.knowm.xchange.blockchain.BlockchainAuthenticated;
import org.knowm.xchange.blockchain.BlockchainExchange;
import org.knowm.xchange.blockchain.Blockchain;
import org.knowm.xchange.blockchain.dto.marketdata.BlockchainOrderBook;
import org.knowm.xchange.blockchain.dto.marketdata.BlockchainTicker;
import org.knowm.xchange.blockchain.dto.marketdata.BlockchainTrade;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.CurrencyPair;

import java.io.IOException;
import java.util.List;

import static org.knowm.xchange.blockchain.BlockchainConstants.*;

public class BlockchainMarketDataServiceRaw extends BlockchainBaseService{

    protected BlockchainMarketDataServiceRaw(BlockchainExchange exchange, BlockchainAuthenticated blockchainApi, ResilienceRegistries resilienceRegistries) {
        super(exchange, blockchainApi, resilienceRegistries);
    }

    protected BlockchainTicker getTicker(CurrencyPair currencyPair) throws IOException {
        return decorateApiCall(() -> this.blockchainApi.getTicker(BlockchainAdapters.toSymbol(currencyPair)))
                .withRetry(retry(GET_TICKER))
                .withRateLimiter(rateLimiter(ENDPOINT_RATE_LIMIT))
                .call();
    }

    protected List<BlockchainTicker> getTickers() throws IOException {
        return decorateApiCall(this.blockchainApi::getTickers)
                .withRetry(retry(GET_TICKERS))
                .withRateLimiter(rateLimiter(ENDPOINT_RATE_LIMIT))
                .call();
    }

    protected BlockchainOrderBook getOrderBookL2(CurrencyPair currencyPair) throws IOException {
        return decorateApiCall(() -> this.blockchainApi.getOrderBookL3(BlockchainAdapters.toSymbol(currencyPair)))
                .withRetry(retry(GET_ORDER_BOOK_L2))
                .withRateLimiter(rateLimiter(ENDPOINT_RATE_LIMIT))
                .call();
    }

    protected BlockchainOrderBook getOrderBookL3(CurrencyPair currencyPair) throws IOException {
        return decorateApiCall(() -> this.blockchainApi.getOrderBookL3(BlockchainAdapters.toSymbol(currencyPair)))
                .withRetry(retry(GET_ORDER_BOOK_L3))
                .withRateLimiter(rateLimiter(ENDPOINT_RATE_LIMIT))
                .call();
    }

    protected List<BlockchainTrade> getExchangeTrades(CurrencyPair currencyPair) throws IOException {
        return decorateApiCall(() -> this.blockchainApi.getExchangeTrades(BlockchainAdapters.currencyPairToId(currencyPair)))
                .withRetry(retry(GET_EXCHANGE_TRADES))
                .withRateLimiter(rateLimiter(ENDPOINT_RATE_LIMIT))
                .call();
    }
}
