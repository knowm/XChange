package com.okcoin.commons.okex.open.api.service.spot.impl;

import com.okcoin.commons.okex.open.api.bean.spot.result.*;
import com.okcoin.commons.okex.open.api.client.APIClient;
import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.service.spot.MarginAccountAPIService;

import java.util.List;
import java.util.Map;

/**
 *
 */
public class MarginAccountAPIServiceImpl implements MarginAccountAPIService {

    private final APIClient client;
    private final MarginAccountAPI api;

    public MarginAccountAPIServiceImpl(final APIConfiguration config) {
        this.client = new APIClient(config);
        this.api = this.client.createService(MarginAccountAPI.class);
    }


    @Override
    public List<Map<String, Object>> getAccounts() {
        return this.client.executeSync(this.api.getAccounts());
    }

    @Override
    public Map<String, Object> getAccountsByProductId(final String product) {
        return this.client.executeSync(this.api.getAccountsByProductId(product));
    }

    @Override
    public List<UserMarginBillDto> getLedger(final String product, final String type, final String from, final String to, final String limit) {
        return this.client.executeSync(this.api.getLedger(product, type, from, to, limit));
    }

    @Override
    public List<Map<String, Object>> getAvailability() {
        return this.client.executeSync(this.api.getAvailability());
    }

    @Override
    public List<Map<String, Object>> getAvailabilityByProductId(final String product) {
        return this.client.executeSync(this.api.getAvailabilityByProductId(product));
    }

    @Override
    public List<MarginBorrowOrderDto> getBorrowedAccounts(final String status, final String from, final String to, final String limit) {
        return this.client.executeSync(this.api.getBorrowedAccounts(status, from, to, limit));
    }

    @Override
    public List<MarginBorrowOrderDto> getBorrowedAccountsByProductId(final String product, final String from, final String to, final String limit, final String status) {
        return this.client.executeSync(this.api.getBorrowedAccountsByProductId(product, from, to, limit, status));
    }

    @Override
    public BorrowResult borrow_1(final BorrowRequestDto order) {
        return this.client.executeSync(this.api.borrow_1(order));
    }

    @Override
    public RepaymentResult repayment_1(final RepaymentRequestDto order) {
        return this.client.executeSync(this.api.repayment_1(order));
    }
}
