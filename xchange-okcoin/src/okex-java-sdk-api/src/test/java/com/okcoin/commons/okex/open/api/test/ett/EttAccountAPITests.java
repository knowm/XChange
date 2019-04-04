package com.okcoin.commons.okex.open.api.test.ett;

import com.okcoin.commons.okex.open.api.bean.ett.result.CursorPager;
import com.okcoin.commons.okex.open.api.bean.ett.result.EttAccount;
import com.okcoin.commons.okex.open.api.bean.ett.result.EttLedger;
import com.okcoin.commons.okex.open.api.service.ett.EttAccountAPIService;
import com.okcoin.commons.okex.open.api.service.ett.impl.EttAccountAPIServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EttAccountAPITests extends EttAPIBaseTests {

    private static final Logger LOG = LoggerFactory.getLogger(EttAccountAPITests.class);

    private EttAccountAPIService ettAccountAPIService;

    @Before
    public void before() {
        this.config = this.config();
        this.ettAccountAPIService = new EttAccountAPIServiceImpl(this.config);
    }

    @Test
    public void getAccounts() {
        List<EttAccount> result = this.ettAccountAPIService.getAccount();
        this.toResultString(EttAccountAPITests.LOG, "result", result);
    }

    @Test
    public void getAccount() {
        EttAccount result = this.ettAccountAPIService.getAccount("btc");
        this.toResultString(EttAccountAPITests.LOG, "result", result);
    }

    @Test
    public void getLedger() {
        CursorPager<EttLedger> result = this.ettAccountAPIService.getLedger("oka", null, null, 1);
        this.toResultString(EttAccountAPITests.LOG, "result", result);
    }

}
