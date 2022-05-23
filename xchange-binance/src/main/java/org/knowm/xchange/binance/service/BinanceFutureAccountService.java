package org.knowm.xchange.binance.service;

import org.knowm.xchange.binance.*;
import org.knowm.xchange.binance.dto.BinanceException;
import org.knowm.xchange.binance.dto.account.AssetDetail;
import org.knowm.xchange.binance.dto.account.BinanceAccountInformation;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.service.account.AccountService;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BinanceFutureAccountService extends BinanceFutureAccountServiceRaw implements AccountService {
    public BinanceFutureAccountService(
            BinanceFutureExchange exchange,
            BinanceFutureAuthenticated binance,
            ResilienceRegistries resilienceRegistries) {
        super(exchange, binance, resilienceRegistries);
    }

    @Override
    public AccountInfo getAccountInfo() throws IOException {
        try {
            BinanceAccountInformation acc = account();
            List<Balance> balances =
                    acc.balances.stream()
                            .map(b -> new Balance(b.getCurrency(), b.getTotal(), b.getAvailable()))
                            .collect(Collectors.toList());
            return new AccountInfo(new Date(acc.updateTime), Wallet.Builder.from(balances).build());
        } catch (BinanceException e) {
            throw BinanceErrorAdapter.adapt(e);
        }
    }
}
