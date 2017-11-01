package org.knowm.xchange.binance.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.binance.dto.account.BinanceAccountInformation;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

public class BinanceAccountService extends BinanceAccountServiceRaw implements AccountService {

    public BinanceAccountService(Exchange exchange) {
        super(exchange);
    }

    @Override
    public AccountInfo getAccountInfo() throws IOException {
        BinanceAccountInformation acc = super.account(null, System.currentTimeMillis());
        List<Balance> balances = acc.balances.stream()
                .map(b -> new Balance(Currency.getInstance(b.asset), b.free.add(b.locked), b.free))
                .collect(Collectors.toList());
        return new AccountInfo(new Wallet(balances));
    }

    @Override
    public String withdrawFunds(Currency currency, BigDecimal amount, String address)
            throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        throw new NotAvailableFromExchangeException();
    }

    @Override
    public String withdrawFunds(WithdrawFundsParams params)
            throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        throw new NotAvailableFromExchangeException();
    }

    @Override
    public String requestDepositAddress(Currency currency, String... args)
            throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        throw new NotAvailableFromExchangeException();
    }

    @Override
    public TradeHistoryParams createFundingHistoryParams() {
        return null;
    }

    @Override
    public List<FundingRecord> getFundingHistory(TradeHistoryParams params)
            throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        throw new NotAvailableFromExchangeException();
    }

}
