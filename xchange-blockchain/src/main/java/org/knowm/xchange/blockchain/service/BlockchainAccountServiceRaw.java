package org.knowm.xchange.blockchain.service;

import org.knowm.xchange.blockchain.BlockchainAdapters;
import org.knowm.xchange.blockchain.BlockchainAuthenticated;
import org.knowm.xchange.blockchain.BlockchainExchange;
import org.knowm.xchange.blockchain.dto.BlockchainException;
import org.knowm.xchange.blockchain.dto.account.*;
import org.knowm.xchange.blockchain.dto.account.BlockchainSymbol;
import org.knowm.xchange.blockchain.params.BlockchainWithdrawalParams;
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

    protected Map<String, List<BlockchainAccountInformation>> getAccountInformation() throws IOException, BlockchainException {
        return decorateApiCall(this.blockchainApi::getAccountInformation)
                .withRetry(retry(GET_ACCOUNT_INFORMATION))
                .withRateLimiter(rateLimiter(ENDPOINT_RATE_LIMIT))
                .call();
    }
    protected BlockchainWithdrawal postWithdrawFunds(BlockchainWithdrawalParams blockchainWithdrawalRequest) throws IOException, BlockchainException {
        return decorateApiCall(() -> this.blockchainApi.postWithdrawFunds(blockchainWithdrawalRequest))
                .withRetry(retry(GET_WITHDRAWAL))
                .withRateLimiter(rateLimiter(ENDPOINT_RATE_LIMIT))
                .call();
    }
    protected BlockchainDeposit getDepositAddress(Currency currency) throws IOException, BlockchainException {
        return decorateApiCall(() -> this.blockchainApi.getDepositAddress(currency.getCurrencyCode()))
                .withRetry(retry(GET_DEPOSIT_ADDRESS))
                .withRateLimiter(rateLimiter(ENDPOINT_RATE_LIMIT))
                .call();
    }

    public BlockchainFees getFees() throws IOException {
        return decorateApiCall(this.blockchainApi::getFees)
                .withRetry(retry(GET_FEES))
                .withRateLimiter(rateLimiter(ENDPOINT_RATE_LIMIT))
                .call();
    }

    public List<BlockchainDeposits> depositHistory(Long startTime, Long endTime) throws IOException {
        return decorateApiCall(() -> this.blockchainApi.depositHistory(startTime, endTime))
                .withRetry(retry(GET_DEPOSIT_HISTORY))
                .withRateLimiter(rateLimiter(ENDPOINT_RATE_LIMIT))
                .call();
    }

    public List<BlockchainWithdrawal> withdrawHistory(Long startTime, Long endTime) throws IOException {
        return decorateApiCall(() -> this.blockchainApi.getWithdrawFunds(startTime, endTime))
                .withRetry(retry(GET_WITHDRAWAL_HISTORY))
                .withRateLimiter(rateLimiter(ENDPOINT_RATE_LIMIT))
                .call();
    }

    public Map<String, BlockchainSymbol> getSymbols() throws IOException {
        return decorateApiCall(this.blockchainApi::getSymbols)
                .withRetry(retry(GET_SYMBOLS))
                .withRateLimiter(rateLimiter(ENDPOINT_RATE_LIMIT))
                .call();
    }

    public List<CurrencyPair> getExchangeSymbols() throws IOException {
        List<CurrencyPair> currencyPairs = new ArrayList<>();
        Map<String, BlockchainSymbol> symbol = this.getSymbols();
        for (Map.Entry<String, BlockchainSymbol> entry : symbol.entrySet()) {
            currencyPairs.add(BlockchainAdapters.toCurrencyPairBySymbol(entry.getValue()));
        }
        return currencyPairs;
    }
}
