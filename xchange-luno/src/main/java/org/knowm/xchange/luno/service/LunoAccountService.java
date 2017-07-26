package org.knowm.xchange.luno.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.FundingRecord.Status;
import org.knowm.xchange.dto.account.FundingRecord.Type;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.luno.LunoAPI;
import org.knowm.xchange.luno.LunoUtil;
import org.knowm.xchange.luno.dto.account.LunoBalance;
import org.knowm.xchange.luno.dto.account.LunoFundingAddress;
import org.knowm.xchange.luno.dto.account.LunoWithdrawals.Withdrawal;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

public class LunoAccountService extends LunoBaseService implements AccountService {

    public LunoAccountService(Exchange exchange, LunoAPI luno) {
        super(exchange, luno);
    }

    @Override
    public AccountInfo getAccountInfo() throws IOException {

        LunoBalance lunoBalance = luno.balance();
        List<Wallet> wallets = new ArrayList<>();
        for (org.knowm.xchange.luno.dto.account.LunoBalance.Balance lb : lunoBalance.getBalance()) {
            List<Balance> balances = new ArrayList<>();
            balances.add(new Balance(LunoUtil.fromLunoCurrency(lb.asset), lb.balance, lb.balance.subtract(lb.reserved)));
            wallets.add(new Wallet(lb.accountId, lb.name, balances));
        }
        
        return new AccountInfo(exchange.getExchangeSpecification().getUserName(), wallets);
    }

    @Override
    public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {
        String lunoCurrency = LunoUtil.toLunoCurrency(currency);
        switch (lunoCurrency) {
        case "XBT":
            luno.send(amount, lunoCurrency, address, null, null);
            return null; // unfortunately luno does not provide any withdrawal id in case of XBT
        default:
            Withdrawal requestWithdrawal = luno.requestWithdrawal(LunoUtil.requestType(lunoCurrency), amount, null);
            return requestWithdrawal.id;
        }
    }

    @Override
    public String withdrawFunds(WithdrawFundsParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        if (params instanceof DefaultWithdrawFundsParams) {
            DefaultWithdrawFundsParams defaultParams = (DefaultWithdrawFundsParams) params;
            return withdrawFunds(defaultParams.currency, defaultParams.amount, defaultParams.address);
        }
        throw new IllegalStateException("Don't know how to withdraw: " + params);
    }

    @Override
    public String requestDepositAddress(Currency currency, String... args) throws IOException {
        String lunoCurrency = LunoUtil.toLunoCurrency(currency);
        LunoFundingAddress lfa = luno.createFundingAddress(lunoCurrency);
        return lfa.address;
    }

    @Override
    public TradeHistoryParams createFundingHistoryParams() {
        return null;
    }

    @Override
    public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws ExchangeException,
            NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        // currently no support for deposits!
        
        List<FundingRecord> result = new ArrayList<>();
        for (Withdrawal w : luno.withdrawals().getWithdrawals()) {
            result.add(new FundingRecord(null, w.getCreatedAt(), LunoUtil.fromLunoCurrency(w.currency), w.amount, w.id, null, Type.WITHDRAWAL, convert(w.status), null, w.fee, null));   
        }
        return result;
    }

    private static Status convert(org.knowm.xchange.luno.dto.account.LunoWithdrawals.Status status) {
        switch (status) {
        case  PENDING:
            return Status.PROCESSING;
        case COMPLETED:
            return Status.COMPLETE;
        case CANCELLED:
            return Status.CANCELLED;
        case UNKNOWN:
        default:
            throw new ExchangeException("Unknown status for luno withdrawal: " + status);
        }
    }

    

}
