package com.okcoin.commons.okex.open.api.test.ett;

import com.okcoin.commons.okex.open.api.bean.ett.result.CursorPager;
import com.okcoin.commons.okex.open.api.bean.ett.result.EttCancelOrderResult;
import com.okcoin.commons.okex.open.api.bean.ett.result.EttCreateOrderResult;
import com.okcoin.commons.okex.open.api.bean.ett.result.EttOrder;
import com.okcoin.commons.okex.open.api.service.ett.EttOrderAPIService;
import com.okcoin.commons.okex.open.api.service.ett.impl.EttOrderAPIServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class EttOrderAPITests extends EttAPIBaseTests {

    private static final Logger LOG = LoggerFactory.getLogger(EttOrderAPITests.class);

    private EttOrderAPIService ettOrderAPIService;

    @Before
    public void before() {
        this.config = this.config();
        this.ettOrderAPIService = new EttOrderAPIServiceImpl(this.config);
    }

    @Test
    public void createOrder() {
        EttCreateOrderResult result = this.ettOrderAPIService.createOrder("ok06ett", 1, BigDecimal.valueOf(100), null, null);
        this.toResultString(EttOrderAPITests.LOG, "result", result);
    }

    @Test
    public void cancelOrder() {
        EttCancelOrderResult result = this.ettOrderAPIService.cancelOrder("1805181314012329");
        this.toResultString(EttOrderAPITests.LOG, "result", result);
    }

    @Test
    public void getOrders() {
        CursorPager<EttOrder> result = this.ettOrderAPIService.getOrder("oka", 1, null, null, null, 1);
        this.toResultString(EttOrderAPITests.LOG, "result", result);
    }

    @Test
    public void getOrder() {
        EttOrder result = this.ettOrderAPIService.getOrder("1805181314012329");
        this.toResultString(EttOrderAPITests.LOG, "result", result);
    }

}
