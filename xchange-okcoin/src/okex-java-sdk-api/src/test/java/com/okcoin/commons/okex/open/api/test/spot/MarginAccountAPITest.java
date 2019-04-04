package com.okcoin.commons.okex.open.api.test.spot;

import com.okcoin.commons.okex.open.api.bean.spot.result.*;
import com.okcoin.commons.okex.open.api.service.spot.MarginAccountAPIService;
import com.okcoin.commons.okex.open.api.service.spot.impl.MarginAccountAPIServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class MarginAccountAPITest extends SpotAPIBaseTests {
    private static final Logger LOG = LoggerFactory.getLogger(MarginAccountAPITest.class);

    private MarginAccountAPIService marginAccountAPIService;

    @Before
    public void before() {
        this.config = this.config();
        this.marginAccountAPIService = new MarginAccountAPIServiceImpl(this.config);
    }

    /**
     * 账号数据
     */
    @Test
    public void getAccounts() {
        final List<Map<String, Object>> result = this.marginAccountAPIService.getAccounts();
        this.toResultString(MarginAccountAPITest.LOG, "result", result);
    }

    /**
     * 单个账号数据
     */
    @Test
    public void getAccountsByProductId() {
        final Map<String, Object> result = this.marginAccountAPIService.getAccountsByProductId("eth-usdt");
        this.toResultString(MarginAccountAPITest.LOG, "result", result);
    }

    /**
     * 账单流水
     */
    @Test
    public void getLedger() {
        final List<UserMarginBillDto> result = this.marginAccountAPIService.getLedger(
                "eth-usdt", "2",
                "1", null, "1");
        this.toResultString(MarginAccountAPITest.LOG, "result", result);
    }

    /**
     * 全部币对杠杆配置信息
     */
    @Test
    public void getAvailability() {
        final List<Map<String, Object>> result = this.marginAccountAPIService.getAvailability();
        this.toResultString(MarginAccountAPITest.LOG, "result", result);
    }

    /**
     * 单个币对杠杆配置信息
     */
    @Test
    public void getAvailabilityByProductId() {
        final List<Map<String, Object>> result = this.marginAccountAPIService.getAvailabilityByProductId("lTC-usdt");
        this.toResultString(MarginAccountAPITest.LOG, "result", result);
    }


    /**
     * 借币记录
     */
    @Test
    public void getBorrowedAccounts() {
        final List<MarginBorrowOrderDto> result = this.marginAccountAPIService.getBorrowedAccounts("0", null, null, "2");
        this.toResultString(MarginAccountAPITest.LOG, "result", result);
    }

    /**
     * 单个账号借币
     */
    @Test
    public void getBorrowedAccountsByProductId() {
        final List<MarginBorrowOrderDto> result = this.marginAccountAPIService.getBorrowedAccountsByProductId("lTC_usdt", null, null, "2", "0");
        this.toResultString(MarginAccountAPITest.LOG, "result", result);
    }
    /**
     * 借币
     */
    @Test
    public void borrow_1() {
        final BorrowRequestDto dto = new BorrowRequestDto();
        dto.setAmount("10");
        dto.setCurrency("usdt");
        dto.setInstrument_id("ltc_usdt");
        final BorrowResult result = this.marginAccountAPIService.borrow_1(dto);
        this.toResultString(MarginAccountAPITest.LOG, "result", result);
    }

    /**
     * 还币
     */
    @Test
    public void repayment_1() {
        for (int i = 0; i < 3; i++) {
            System.out.println("========= i=" + i);
            final RepaymentRequestDto dto = new RepaymentRequestDto();
            dto.setAmount("1");
            dto.setBorrow_id("185778");
            dto.setCurrency("usdt");
            dto.setInstrument_id("ltc_usdt");
            final RepaymentResult result = this.marginAccountAPIService.repayment_1(dto);
            this.toResultString(MarginAccountAPITest.LOG, "result", result);
        }

    }

}
