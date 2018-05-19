package org.knowm.xchange.bl3p.service;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bl3p.Bl3pExchange;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.service.account.AccountService;

import java.io.IOException;
import java.util.List;

public class Bl3pAccountServiceIntegration {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(Bl3pExchange.class);
    AccountService accountService = exchange.getAccountService();

    @Test
    public void getAccountInfo() throws IOException {
        AccountInfo accountInfo = accountService.getAccountInfo();

        System.out.println(accountInfo);
    }

    @Test
    public void withdrawFunds() {
    }

    @Test
    public void requestDepositAddress() throws IOException {
        /**
         * Don't strain those poor wallets
         *
        String newDepositAddress = accountService.requestDepositAddress(Currency.BTC);
        System.out.println(newDepositAddress);
         */
    }

    @Test
    public void getFundingHistory() throws IOException {
        try {
            List<FundingRecord> fundingRecords = accountService.getFundingHistory(accountService.createFundingHistoryParams());

            for (FundingRecord f : fundingRecords) {
                System.out.println(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}