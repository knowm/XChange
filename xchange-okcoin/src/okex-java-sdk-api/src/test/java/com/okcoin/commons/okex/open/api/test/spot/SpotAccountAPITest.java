package com.okcoin.commons.okex.open.api.test.spot;


import com.okcoin.commons.okex.open.api.bean.spot.result.Account;
import com.okcoin.commons.okex.open.api.bean.spot.result.Ledger;
import com.okcoin.commons.okex.open.api.bean.spot.result.ServerTimeDto;
import com.okcoin.commons.okex.open.api.service.spot.SpotAccountAPIService;
import com.okcoin.commons.okex.open.api.service.spot.impl.SpotAccountAPIServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class SpotAccountAPITest extends SpotAPIBaseTests {

    private static final Logger LOG = LoggerFactory.getLogger(SpotAccountAPITest.class);

    private SpotAccountAPIService spotAccountAPIService;

    @Before
    public void before() {
        this.config = this.config();
        this.spotAccountAPIService = new SpotAccountAPIServiceImpl(this.config);
    }

    @Test
    public void time() {
        final ServerTimeDto serverTimeDto = this.spotAccountAPIService.time();
        this.toResultString(SpotAccountAPITest.LOG, "time", serverTimeDto);
        System.out.println(serverTimeDto.getEpoch());
        System.out.println(serverTimeDto.getIso());
    }

    @Test
    public void getMiningData() {
        final Map<String, Object> miningdata = this.spotAccountAPIService.getMiningData();
        this.toResultString(SpotAccountAPITest.LOG, "miningdata", miningdata);
    }

    /**
     * 账户信息
     */
    @Test
    public void getAccounts() {
        final List<Account> accounts = this.spotAccountAPIService.getAccounts();
        this.toResultString(SpotAccountAPITest.LOG, "accounts", accounts);
    }

    /**
     * 单一账户信息
     */
    @Test
    public void getAccountByCurrency() {
        final Account account = this.spotAccountAPIService.getAccountByCurrency("btc");
        this.toResultString(SpotAccountAPITest.LOG, "account", account);
    }

    /**
     * 账单流水
     */
    @Test
    public void getLedgersByCurrency() {
        final List<Ledger> ledgers = this.spotAccountAPIService.getLedgersByCurrency("usdt", null, null, "2");
        this.toResultString(SpotAccountAPITest.LOG, "ledges", ledgers);
    }


}
