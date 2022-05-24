package org.knowm.xchange.blockchain.service;

import org.knowm.xchange.blockchain.BlockchainAdapters;
import org.knowm.xchange.blockchain.BlockchainAuthenticated;
import org.knowm.xchange.blockchain.BlockchainExchange;
import org.knowm.xchange.blockchain.dto.BlockchainException;
import org.knowm.xchange.blockchain.dto.account.*;
import org.knowm.xchange.blockchain.dto.account.BlockchainSymbols;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.knowm.xchange.blockchain.BlockchainConstants.*;

public abstract class BlockchainAccountServiceRaw extends BlockchainBaseService{

    protected BlockchainAccountServiceRaw(BlockchainExchange exchange, BlockchainAuthenticated blockchainApi, ResilienceRegistries resilienceRegistries) {
        super(exchange, blockchainApi, resilienceRegistries);
    }

    protected BlockchainWithdrawal postWithdrawFunds(BlockchainWithdrawalRequest blockchainWithdrawalRequest) throws IOException, BlockchainException {
        return decorateApiCall(() -> this.blockchainApi.postWithdrawFunds(blockchainWithdrawalRequest))
                .withRateLimiter(rateLimiter(ENDPOINT_RATE_LIMIT))
                .call();
    }
    protected BlockchainAccount getDepositAddress(Currency currency) throws IOException {
        return decorateApiCall(() -> this.blockchainApi.getDepositAddress(currency.getCurrencyCode()))
                .withRetry(retry(GET_DEPOSIT_ADDRESS))
                .withRateLimiter(rateLimiter(ENDPOINT_RATE_LIMIT))
                .call();
    }

    public BlockchainFees getFees() throws IOException {
        return decorateApiCall(() -> this.blockchainApi.getFees())
                .withRateLimiter(rateLimiter(ENDPOINT_RATE_LIMIT))
                .call();
    }

    public List<BlockchainDeposit> depositHistory(Long startTime, Long endTime) throws IOException {
        return decorateApiCall(() -> this.blockchainApi.depositHistory(startTime, endTime))
                .withRateLimiter(rateLimiter(ENDPOINT_RATE_LIMIT))
                .call();
    }

    public List<BlockchainWithdrawal> withdrawHistory(Long startTime, Long endTime) throws IOException {
        return decorateApiCall(() -> this.blockchainApi.getWithdrawFunds(startTime, endTime))
                .withRateLimiter(rateLimiter(ENDPOINT_RATE_LIMIT))
                .call();
    }

    public Map<String,BlockchainSymbols> getSymbols() throws IOException {
        return decorateApiCall(() -> this.blockchainApi.getSymbols())
                .withRateLimiter(rateLimiter(ENDPOINT_RATE_LIMIT))
                .call();
    }

    public List<CurrencyPair> getExchangeSymbols() throws IOException {
        List<CurrencyPair> currencyPairs = new ArrayList<>();
        Map<String, BlockchainSymbols> symbol = this.getSymbols();
        for (Map.Entry<String, BlockchainSymbols> entry : symbol.entrySet()) {
            currencyPairs.add(BlockchainAdapters.toCurrencyPairBySymbol(entry.getValue()));
        }
        return currencyPairs;
    }
}
