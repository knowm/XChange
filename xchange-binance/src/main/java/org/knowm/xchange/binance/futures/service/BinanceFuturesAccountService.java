package org.knowm.xchange.binance.futures.service;

import org.knowm.xchange.binance.*;
import org.knowm.xchange.binance.dto.BinanceException;
import org.knowm.xchange.binance.futures.BinanceFuturesAdapter;
import org.knowm.xchange.binance.futures.BinanceFuturesAuthenticated;
import org.knowm.xchange.binance.futures.dto.account.BinanceFuturesAccountInformation;
import org.knowm.xchange.binance.futures.dto.account.BinanceUserCommissionRate;
import org.knowm.xchange.binance.service.BinanceAccountService;
import org.knowm.xchange.binance.service.account.params.BinanceAccountMarginParams;
import org.knowm.xchange.binance.service.account.params.BinanceAccountPositionMarginParams;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Fee;
import org.knowm.xchange.service.account.params.AccountLeverageParams;
import org.knowm.xchange.service.account.params.AccountLeverageParamsCurrencyPair;
import org.knowm.xchange.service.account.params.AccountMarginParams;
import org.knowm.xchange.service.account.params.AccountPositionMarginParams;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.knowm.xchange.binance.BinanceResilience.REQUEST_WEIGHT_RATE_LIMITER;

public class BinanceFuturesAccountService extends BinanceAccountService {
    protected final BinanceFuturesAuthenticated binanceFutures;

    public BinanceFuturesAccountService(BinanceExchange exchange, BinanceAuthenticated binance, ResilienceRegistries resilienceRegistries) {
        super(exchange, binance, resilienceRegistries);
        binanceFutures = (BinanceFuturesAuthenticated) binance;
    }

    @Override
    public AccountInfo getAccountInfo() throws IOException {
        return BinanceFuturesAdapter.adaptAccountInfo(getFuturesAccountInfo());
    }

    public BinanceFuturesAccountInformation getFuturesAccountInfo() throws IOException {
        try {
            return decorateApiCall(
                    () -> binanceFutures.futuresAccount(getRecvWindow(), getTimestampFactory(), apiKey, signatureCreator))
                    .withRetry(retry("account"))
                    .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER), 5)
                    .call();
        } catch (BinanceException e) {
            throw BinanceErrorAdapter.adapt(e);
        }
    }

    @Override
    public Map<CurrencyPair, Fee> getDynamicTradingFees() {
        Map<CurrencyPair, Fee> tradingFees = new HashMap<>();
        List<CurrencyPair> pairs = exchange.getExchangeSymbols();
        pairs.forEach(
                pair -> {
                    BinanceUserCommissionRate commission = null;
                    try {
                        commission = getTradingCommission(pair);
                        tradingFees.put(pair, new Fee(commission.makerCommissionRate, commission.takerCommissionRate));
                    } catch (IOException e) {
                        LOG.trace("Exception fetching trade commission for {}", pair, e);
                    }
                });

        return tradingFees;
    }

    public BinanceUserCommissionRate getTradingCommission(CurrencyPair pair) throws IOException {
        try {
            return decorateApiCall(
                    () -> binanceFutures.userCommissionRate(BinanceAdapters.toSymbol(pair), getRecvWindow(), getTimestampFactory(), apiKey, signatureCreator))
                    .withRetry(retry("userCommissionRate"))
                    .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER), 20)
                    .call();
        } catch (BinanceException e) {
            throw BinanceErrorAdapter.adapt(e);
        }
    }

    @Override
    public void setLeverage(AccountLeverageParams params) throws IOException {
        if (!(params instanceof AccountLeverageParamsCurrencyPair))
            throw new IllegalArgumentException("object '" + params + "' is not an instance of '" + AccountLeverageParamsCurrencyPair.class.getName() + "' class");

        final AccountLeverageParamsCurrencyPair leverageParams = (AccountLeverageParamsCurrencyPair) params;

        try {
            decorateApiCall(
                    () -> binanceFutures.changeInitialLeverage(
                            BinanceAdapters.toSymbol(leverageParams.getPair()),
                            params.getLeverage(),
                            getRecvWindow(),
                            getTimestampFactory(),
                            apiKey,
                            signatureCreator))
                    .withRetry(retry("setLeverage"))
                    .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER))
                    .call();
        } catch (BinanceException e) {
            throw BinanceErrorAdapter.adapt(e);
        }
    }

    @Override
    public void setMarginType(AccountMarginParams params) throws IOException {
        if (!(params instanceof BinanceAccountMarginParams))
            throw new IllegalArgumentException("object '" + params + "' is not an instance of '" + BinanceAccountMarginParams.class.getName() + "' class");

        final BinanceAccountMarginParams marginParams = (BinanceAccountMarginParams) params;

        try {
            decorateApiCall(
                    () -> binanceFutures.changeMarginType(
                            BinanceAdapters.toSymbol(marginParams.getPair()),
                            marginParams.getMarginType(),
                            getRecvWindow(),
                            getTimestampFactory(),
                            apiKey,
                            signatureCreator))
                    .withRetry(retry("setMargin"))
                    .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER))
                    .call();
        } catch (BinanceException e) {
            throw BinanceErrorAdapter.adapt(e);
        }
    }

    @Override
    public void setIsolatedPositionMargin(AccountPositionMarginParams params) throws IOException {
        if (!(params instanceof BinanceAccountMarginParams))
            throw new IllegalArgumentException("object '" + params + "' is not an instance of '" + BinanceAccountPositionMarginParams.class.getName() + "' class");

        final BinanceAccountPositionMarginParams marginParams = (BinanceAccountPositionMarginParams) params;

        try {
            decorateApiCall(
                    () -> binanceFutures.modifyIsolatedPositionMargin(
                            BinanceAdapters.toSymbol(marginParams.getPair()),
                            marginParams.getSide(),
                            marginParams.getAmount(),
                            marginParams.getType().getValue(),
                            getRecvWindow(),
                            getTimestampFactory(),
                            apiKey,
                            signatureCreator))
                    .withRetry(retry("setMargin"))
                    .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER))
                    .call();
        } catch (BinanceException e) {
            throw BinanceErrorAdapter.adapt(e);
        }
    }
}
