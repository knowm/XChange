package org.knowm.xchange.binance.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.binance.dto.account.BinanceAccountInformation;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.FundingRecord.Type;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.HistoryParamsFundingType;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;
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
        super.withdraw(currency.getCurrencyCode(), address, amount, null, null, System.currentTimeMillis());
        return null;
    }

    @Override
    public String withdrawFunds(WithdrawFundsParams params)
            throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        if (!(params instanceof DefaultWithdrawFundsParams)) {
            throw new RuntimeException("DefaultWithdrawFundsParams must be provided.");
        }
        DefaultWithdrawFundsParams p = (DefaultWithdrawFundsParams) params;
        super.withdraw(p.currency.getCurrencyCode(), p.address, p.amount, null, null, System.currentTimeMillis());
        return null;
    }

    @Override
    public String requestDepositAddress(Currency currency, String... args)
            throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        throw new NotAvailableFromExchangeException();
    }

    @Override
    public TradeHistoryParams createFundingHistoryParams() {
        return new BinanceFundingHistoryParams();
    }

    @Override
    public List<FundingRecord> getFundingHistory(TradeHistoryParams params)
            throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        if (params instanceof TradeHistoryParamCurrency) {
            throw new RuntimeException("You must provide the currency in order to get the funding history (TradeHistoryParamCurrency).");
        }
        TradeHistoryParamCurrency cp = (TradeHistoryParamCurrency) params;
        final String asset = cp.getCurrency().getCurrencyCode();
        
        boolean withdrawals = true;
        boolean deposits = true;
        
        Long startTime = null;
        Long endTime = null;
        if (params instanceof TradeHistoryParamsTimeSpan) {
            TradeHistoryParamsTimeSpan tp = (TradeHistoryParamsTimeSpan) params;
            if (tp.getStartTime() != null) {
                startTime = tp.getStartTime().getTime();
            }
            if (tp.getEndTime() != null) {
                endTime = tp.getEndTime().getTime();
            }
        }
        
        if (params instanceof HistoryParamsFundingType) {
            HistoryParamsFundingType f = (HistoryParamsFundingType) params;
            withdrawals = f.getType() != null && f.getType() == Type.WITHDRAWAL;
            deposits = f.getType() != null && f.getType() == Type.DEPOSIT;
        }
        
        List<FundingRecord> result = new ArrayList<>();
        if (withdrawals) {
            super.withdrawHistory(asset, startTime, endTime, null, System.currentTimeMillis()).forEach(w -> {
                result.add(new FundingRecord(w.address, new Date(w.applyTime), Currency.getInstance(w.asset), w.amount, null, null, Type.WITHDRAWAL, Integer.toString(w.status), null, null, null));
            });
        }
        
        if (deposits) {
            super.depositHistory(asset, startTime, endTime, null, System.currentTimeMillis()).forEach(d -> {
                result.add(new FundingRecord(null, new Date(d.insertTime), Currency.getInstance(d.asset), d.amount, null, null, Type.DEPOSIT, Integer.toString(d.status), null, null, null));
            });
        }
        
        return result;
    }

}
