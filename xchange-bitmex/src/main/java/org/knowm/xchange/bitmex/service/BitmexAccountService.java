package org.knowm.xchange.bitmex.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitmex.dto.account.BitmexAccount;
import org.knowm.xchange.bitmex.dto.account.BitmexWallet;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

public class BitmexAccountService extends BitmexAccountServiceRaw implements AccountService {

    /**
     * Constructor
     *
     * @param exchange
     */
    public BitmexAccountService(Exchange exchange) {

        super(exchange);
    }

    @Override
    public AccountInfo getAccountInfo() throws IOException {
        BitmexAccount account = super.getBitmexAccountInfo();
        BitmexWallet bitmexWallet = getBitmexWallet();
        String username = account.getUsername();
        BigDecimal amount = bitmexWallet.getAmount();
        BigDecimal amt = amount.divide(BigDecimal.valueOf(100_000_000L));
        Balance balance = new Balance(Currency.BTC, amt);
        Wallet wallet = new Wallet(Currency.BTC.getSymbol(), balance
        );
        AccountInfo accountInfo = new AccountInfo(username, wallet);
        return accountInfo;
    }

    @Override
    public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {
        return withdrawFunds(currency.getCurrencyCode(), amount, address);
    }

    @Override
    public String withdrawFunds(WithdrawFundsParams params) throws IOException {
        throw new NotYetImplementedForExchangeException();
    }

    @Override
    public String requestDepositAddress(Currency currency, String... args) throws IOException {
        return requestDepositAddress(currency.getCurrencyCode());
    }

    @Override
    public TradeHistoryParams createFundingHistoryParams() {
        throw new NotYetImplementedForExchangeException();
    }

    @Override
    public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
        throw new NotYetImplementedForExchangeException();
    }

}
