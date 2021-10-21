package org.knowm.xchange.binance.futures.service;

import org.knowm.xchange.binance.*;
import org.knowm.xchange.binance.dto.BinanceException;
import org.knowm.xchange.binance.futures.BinanceFuturesAdapter;
import org.knowm.xchange.binance.futures.BinanceFuturesAuthenticated;
import org.knowm.xchange.binance.futures.dto.account.BinanceFuturesAccountInformation;
import org.knowm.xchange.binance.futures.dto.account.BinanceUserCommissionRate;
import org.knowm.xchange.binance.service.BinanceAccountService;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Fee;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.knowm.xchange.binance.BinanceResilience.REQUEST_WEIGHT_RATE_LIMITER;

public class BinanceFuturesAccountService extends BinanceAccountService {

    public BinanceFuturesAccountService(BinanceExchange exchange, BinanceAuthenticated binance, ResilienceRegistries resilienceRegistries) {
        super(exchange, binance, resilienceRegistries);
    }

    @Override
    public AccountInfo getAccountInfo() throws IOException {
        return BinanceFuturesAdapter.adaptAccountInfo(getFuturesAccountInfo());
    }

    public BinanceFuturesAccountInformation getFuturesAccountInfo() throws IOException {
        try {
            return decorateApiCall(
                    () -> ((BinanceFuturesAuthenticated)binance).futuresAccount(getRecvWindow(), getTimestampFactory(), apiKey, signatureCreator))
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
                    () -> ((BinanceFuturesAuthenticated)binance).userCommissionRate(BinanceAdapters.toSymbol(pair), getRecvWindow(), getTimestampFactory(), apiKey, signatureCreator))
                    .withRetry(retry("userCommissionRate"))
                    .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER), 20)
                    .call();
        } catch (BinanceException e) {
            throw BinanceErrorAdapter.adapt(e);
        }
    }
}
