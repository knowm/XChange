package org.knowm.xchange.bybit.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.account.BybitBalance;
import org.knowm.xchange.bybit.dto.account.BybitBalances;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.service.account.AccountService;

import java.io.IOException;
import java.util.List;

import static org.knowm.xchange.bybit.BybitAdapters.adaptBybitBalances;

public class BybitAccountService extends BybitAccountServiceRaw implements AccountService {

    public BybitAccountService(Exchange exchange) {
        super(exchange);
    }

    @Override
    public AccountInfo getAccountInfo() throws IOException {
        BybitResult<BybitBalances> walletBalances = getWalletBalances();
        BybitBalances walletBalancesResult = walletBalances.getResult();
        List<BybitBalance> balances = walletBalancesResult.getBalances();
        return new AccountInfo(adaptBybitBalances(balances));
    }

}
