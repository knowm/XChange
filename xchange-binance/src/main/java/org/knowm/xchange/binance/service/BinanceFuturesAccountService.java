package org.knowm.xchange.binance.service;

import org.knowm.xchange.binance.BinanceAuthenticated;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.binance.dto.account.AssetDetail;
import org.knowm.xchange.binance.dto.account.AssetDetailResponse;
import org.knowm.xchange.client.ResilienceRegistries;

import java.io.IOException;
import java.util.Map;

public class BinanceFuturesAccountService extends BinanceAccountService {

    public BinanceFuturesAccountService(BinanceExchange exchange, BinanceAuthenticated binance, ResilienceRegistries resilienceRegistries) {
        super(exchange, binance, resilienceRegistries);
    }

    @Override
    public Map<String, AssetDetail> getAssetDetails() throws IOException {
        return null;
    }
}
