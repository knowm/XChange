package com.okcoin.commons.okex.open.api.test.account;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.account.param.Transfer;
import com.okcoin.commons.okex.open.api.bean.account.param.Withdraw;
import com.okcoin.commons.okex.open.api.bean.account.result.Currency;
import com.okcoin.commons.okex.open.api.bean.account.result.Ledger;
import com.okcoin.commons.okex.open.api.bean.account.result.Wallet;
import com.okcoin.commons.okex.open.api.bean.account.result.WithdrawFee;
import com.okcoin.commons.okex.open.api.service.account.AccountAPIService;
import com.okcoin.commons.okex.open.api.service.account.impl.AccountAPIServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

public class AccountAPITests extends  AccountAPIBaseTests{

    private static final Logger LOG = LoggerFactory.getLogger(AccountAPITests.class);

    private AccountAPIService accountAPIService;

    @Before
    public void before() {
        this.config = this.config();
        this.accountAPIService = new AccountAPIServiceImpl(this.config);
    }

    @Test
    public void transfer() {
        Transfer transfer = new Transfer();
        transfer.setFrom(1);
        transfer.setTo(6);
        transfer.setCurrency("eos");
        transfer.setAmount(BigDecimal.valueOf(0.0001));
        JSONObject result = this.accountAPIService.transfer(transfer);
        this.toResultString(AccountAPITests.LOG, "result", result);
    }

    @Test
    public void withdraw() {
        Withdraw withdraw = new Withdraw();
        withdraw.setTo_address("xxxxxxxxxxxxxxxxxxxxxxxxxxx");
        withdraw.setFee(new BigDecimal("0.0005"));
        withdraw.setCurrency("btc");
        withdraw.setAmount(BigDecimal.ONE);
        withdraw.setDestination(4);
        withdraw.setTrade_pwd("123456");
        JSONObject result = this.accountAPIService.withdraw(withdraw);
        this.toResultString(AccountAPITests.LOG, "result", result);
    }

    @Test
    public void getCurrencies() {
        List<Currency> result = this.accountAPIService.getCurrencies();
        this.toResultString(AccountAPITests.LOG, "result", result);
    }

    @Test
    public void getLedger() {
        List<Ledger> result = this.accountAPIService.getLedger(2, "btc",null, null, 10);
        this.toResultString(AccountAPITests.LOG, "result", result);
    }

    @Test
    public void getWallet() {
        List<Wallet> result = this.accountAPIService.getWallet();
        this.toResultString(AccountAPITests.LOG, "result", result);
        List<Wallet> result2 = this.accountAPIService.getWallet("btc");
        this.toResultString(AccountAPITests.LOG, "result", result2);
    }

    @Test
    public void getDepositAddress() {
        JSONArray result = this.accountAPIService.getDepositAddress("btc");
        this.toResultString(AccountAPITests.LOG, "result", result);
    }

    @Test
    public void getWithdrawFee() {
        List<WithdrawFee> result = this.accountAPIService.getWithdrawFee("btc");
        this.toResultString(AccountAPITests.LOG, "result", result);
    }

    @Test
    public void getOnHold() {
        JSONArray result = this.accountAPIService.getOnHold("cac");
        this.toResultString(AccountAPITests.LOG, "result", result);
    }

    @Test
    public void lock() {
        JSONObject result = this.accountAPIService.lock("cac", new BigDecimal("10000"));        this.toResultString(AccountAPITests.LOG, "result", result);
        this.toResultString(AccountAPITests.LOG, "result", result);

    }

    @Test
    public void unlock() {
        JSONObject result = this.accountAPIService.unlock("cac", new BigDecimal("10000"));        this.toResultString(AccountAPITests.LOG, "result", result);
        this.toResultString(AccountAPITests.LOG, "result", result);

    }

    @Test
    public void getDepositHistory() {
        JSONArray result = this.accountAPIService.getDepositHistory();
        this.toResultString(AccountAPITests.LOG, "result", result);
        JSONArray result2 = this.accountAPIService.getDepositHistory("btc");
        this.toResultString(AccountAPITests.LOG, "result", result2);
    }
}
