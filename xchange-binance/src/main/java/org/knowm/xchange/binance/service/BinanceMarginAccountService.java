package org.knowm.xchange.binance.service;

import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.BinanceErrorAdapter;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.binance.BinanceMarginAuthenticated;
import org.knowm.xchange.binance.dto.BinanceException;
import org.knowm.xchange.binance.dto.account.BinanceMarginAccountInformation;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.dto.account.AccountInfo;

import java.io.IOException;

import static org.knowm.xchange.binance.BinanceResilience.REQUEST_WEIGHT_RATE_LIMITER;

public class BinanceMarginAccountService extends BinanceAccountService {
    public BinanceMarginAccountService(BinanceExchange exchange, BinanceMarginAuthenticated binance, ResilienceRegistries resilienceRegistries) {
        super(exchange, binance, resilienceRegistries);
    }

    @Override
    public AccountInfo getAccountInfo() throws IOException {
        try {
            return BinanceAdapters.adaptAccountInfo(getMarginAccountInfo());
        } catch (BinanceException e) {
            throw BinanceErrorAdapter.adapt(e);
        }
    }

    public BinanceMarginAccountInformation getMarginAccountInfo() throws IOException {
        try {
            return decorateApiCall(
                    () -> ((BinanceMarginAuthenticated)binance).marginAccount(getRecvWindow(), getTimestampFactory(), apiKey, signatureCreator))
                    .withRetry(retry("account"))
                    .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER), 5)
                    .call();
        } catch (BinanceException e) {
            throw BinanceErrorAdapter.adapt(e);
        }
    }
}
