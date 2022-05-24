package org.knowm.xchange.blockchain.service;

import org.knowm.xchange.blockchain.*;
import org.knowm.xchange.blockchain.dto.BlockchainException;
import org.knowm.xchange.blockchain.dto.account.BlockchainFees;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.AddressWithTag;
import org.knowm.xchange.dto.account.Fee;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class BlockchainAccountService extends BlockchainAccountServiceRaw implements AccountService {

    public BlockchainAccountService(BlockchainExchange exchange, BlockchainAuthenticated blockchainApi, ResilienceRegistries resilienceRegistries) {
        super(exchange, blockchainApi, resilienceRegistries);
    }

    @Override
    public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {
        try {
            return this.postWithdrawFunds(BlockchainAdapters.toWithdrawalRequest(currency, amount, address)).getWithdrawalId();
        } catch (BlockchainException e) {
            throw BlockchainErrorAdapter.adapt(e);
        }
    }

    @Override
    public String withdrawFunds(Currency currency, BigDecimal amount, AddressWithTag address) throws IOException{
        return withdrawFunds(new DefaultWithdrawFundsParams(address, currency, amount));
    }

    @Override
    public String withdrawFunds(WithdrawFundsParams params) throws IOException {
        if (params instanceof DefaultWithdrawFundsParams) {
            DefaultWithdrawFundsParams defaultParams = (DefaultWithdrawFundsParams) params;
            return postWithdrawFunds(BlockchainAdapters.toWithdrawalRequest(
                    defaultParams.getCurrency(),
                    defaultParams.getAmount(),
                    defaultParams.getAddress()
                    )).getWithdrawalId();
        }

        throw new IllegalStateException("Don't know how to withdraw: " + params);
    }

    @Override
    public String requestDepositAddress(Currency currency, String... args) throws IOException {
        return this.getDepositAddress(currency).getAddress();
    }

    @Override
    public AddressWithTag requestDepositAddressData(Currency currency, String... args) throws IOException {
        return BlockchainAdapters.toAddressWithTag(this.getDepositAddress(currency));
    }

    @Override
    public TradeHistoryParams createFundingHistoryParams() {
        return new BlockchainFundingHistoryParams();
    }

    @Override
    public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
        try {
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

            List<FundingRecord> result = new ArrayList<>();
            if (params instanceof HistoryParamsFundingType) {
                if (((HistoryParamsFundingType) params).getType() != null) {
                    switch (((HistoryParamsFundingType) params).getType()) {
                        case WITHDRAWAL:
                            this.withdrawHistory(startTime, endTime)
                                    .forEach(w -> { result.add(BlockchainAdapters.toFundingWithdrawal(w)); });
                            break;
                        case DEPOSIT:
                            this.depositHistory(startTime, endTime)
                                    .forEach(d -> { result.add(BlockchainAdapters.toFundingDeposit(d)); });
                            break;
                        default:
                            throw new IllegalArgumentException(
                                    "Unsupported FundingRecord.Type: "
                                            + ((HistoryParamsFundingType) params).getType());
                    }
                }
            }

            return result;
        } catch (BlockchainException e) {
            throw BlockchainErrorAdapter.adapt(e);
        }
    }

    @Override
    public Map<CurrencyPair, Fee> getDynamicTradingFees() throws IOException {
        try {
            BlockchainFees fees = this.getFees();

            Map<CurrencyPair, Fee> tradingFees = new HashMap<>();
            List<CurrencyPair> pairs = this.getExchangeSymbols();

            pairs.forEach(pair -> tradingFees.put(pair, new Fee(fees.getMakerRate(), fees.getTakerRate())));
            return tradingFees;
        } catch (BlockchainException e) {
            throw BlockchainErrorAdapter.adapt(e);
        }
    }
}
