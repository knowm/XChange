package com.okcoin.commons.okex.open.api.service.swap.impl;

import com.okcoin.commons.okex.open.api.client.APIClient;
import com.okcoin.commons.okex.open.api.config.APIConfiguration;
import com.okcoin.commons.okex.open.api.service.swap.SwapMarketAPIService;

public class SwapMarketAPIServiceImpl implements SwapMarketAPIService {
    private APIClient client;
    private SwapMarketAPI api;

    public SwapMarketAPIServiceImpl() {
    }

    public SwapMarketAPIServiceImpl(APIConfiguration config) {
        this.client = new APIClient(config);
        this.api = client.createService(SwapMarketAPI.class);
    }

    /**
     * 获取可用合约的列表。
     *
     * @return
     */
    @Override
    public String getContractsApi() {
        return client.executeSync(api.getContractsApi());
    }

    /**
     * 获取合约的深度列表。
     *
     * @param instrumentId
     * @param size
     * @return
     */
    @Override
    public String getDepthApi(String instrumentId, String size) {
        return client.executeSync(api.getDepthApi(instrumentId, size));
    }

    /**
     * 获取平台全部合约的最新成交价、买一价、卖一价和24交易量。
     *
     * @return
     */
    @Override
    public String getTickersApi() {
        return client.executeSync(api.getTickersApi());
    }

    /**
     * 获取合约的最新成交价、买一价、卖一价和24交易量。
     *
     * @param instrumentId
     * @return
     */
    @Override
    public String getTickerApi(String instrumentId) {
        return client.executeSync(api.getTickerApi(instrumentId));
    }

    /**
     * 获取合约的成交记录。
     *
     * @param instrumentId
     * @param from
     * @param to
     * @param limit
     * @return
     */
    @Override
    public String getTradesApi(String instrumentId, String from, String to, String limit) {
        return client.executeSync(api.getTradesApi(instrumentId, from, to, limit));
    }

    /**
     * 获取合约的K线数据。
     *
     * @param instrumentId
     * @param start
     * @param end
     * @param granularity
     * @return
     */
    @Override
    public String getCandlesApi(String instrumentId, String start, String end, String granularity) {
        return client.executeSync(api.getCandlesApi(instrumentId, start, end, granularity));
    }

    /**
     * 获取币种指数。
     *
     * @param instrumentId
     * @return
     */
    @Override
    public String getIndexApi(String instrumentId) {
        return client.executeSync(api.getIndexApi(instrumentId));
    }

    /**
     * 获取法币汇率。
     *
     * @return
     */
    @Override
    public String getRateApi() {
        return client.executeSync(api.getRateApi());
    }

    /**
     * 获取合约整个平台的总持仓量。
     *
     * @param instrumentId
     * @return
     */
    @Override
    public String getOpenInterestApi(String instrumentId) {
        return client.executeSync(api.getOpenInterestApi(instrumentId));
    }

    /**
     * 获取合约当前开仓的最高买价和最低卖价。
     *
     * @param instrumentId
     * @return
     */
    @Override
    public String getPriceLimitApi(String instrumentId) {
        return client.executeSync(api.getPriceLimitApi(instrumentId));
    }

    /**
     * 获取合约爆仓单。
     *
     * @param instrumentId
     * @param status
     * @param from
     * @param to
     * @param limit
     * @return
     */
    @Override
    public String getLiquidationApi(String instrumentId, String status, String from, String to, String limit) {
        return client.executeSync(api.getLiquidationApi(instrumentId, status, from, to, limit));
    }

    /**
     * 获取合约下一次的结算时间。
     *
     * @param instrumentId
     * @return
     */
    @Override
    public String getFundingTimeApi(String instrumentId) {
        return client.executeSync(api.getFundingTimeApi(instrumentId));
    }

    /**
     * 获取合约历史资金费率
     *
     * @param instrumentId
     * @param from
     * @param to
     * @param limit
     * @return
     */
    @Override
    public String getHistoricalFundingRateApi(String instrumentId, String from, String to, String limit) {
        return client.executeSync(api.getHistoricalFundingRateApi(instrumentId, from, to, limit));
    }

    /**
     * 获取合约标记价格
     *
     * @param instrumentId
     * @return
     */
    @Override
    public String getMarkPriceApi(String instrumentId) {
        return client.executeSync(api.getMarkPriceApi(instrumentId));
    }
}
