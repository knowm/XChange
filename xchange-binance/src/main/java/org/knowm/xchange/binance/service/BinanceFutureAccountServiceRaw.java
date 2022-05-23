package org.knowm.xchange.binance.service;

import org.knowm.xchange.binance.BinanceFutureAuthenticated;
import org.knowm.xchange.binance.BinanceFutureExchange;
import org.knowm.xchange.binance.dto.BinanceException;
import org.knowm.xchange.binance.dto.account.BinanceAccountInformation;
import org.knowm.xchange.client.ResilienceRegistries;

import java.io.IOException;

import static org.knowm.xchange.binance.BinanceResilience.REQUEST_WEIGHT_RATE_LIMITER;

public class BinanceFutureAccountServiceRaw extends BinanceFutureBaseService {
    public BinanceFutureAccountServiceRaw(
            BinanceFutureExchange exchange,
            BinanceFutureAuthenticated binance,
            ResilienceRegistries resilienceRegistries) {
        super(exchange, binance, resilienceRegistries);
    }

    public BinanceAccountInformation account() throws BinanceException, IOException {
        return decorateApiCall(
                () -> binance.account(getRecvWindow(), getTimestampFactory(), apiKey, signatureCreator))
                .withRetry(retry("account"))
                .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER), 5)
                .call();
    }

}
