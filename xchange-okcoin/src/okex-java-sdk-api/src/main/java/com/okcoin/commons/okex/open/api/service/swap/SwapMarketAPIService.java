package com.okcoin.commons.okex.open.api.service.swap;


public interface SwapMarketAPIService {
    /**
     * 获取可用合约的列表。
     *
     * @return
     */
    String getContractsApi();

    /**
     * 获取合约的深度列表。
     *
     * @param instrumentId
     * @param size
     * @return
     */
    String getDepthApi(String instrumentId, String size);

    /**
     * 获取平台全部合约的最新成交价、买一价、卖一价和24交易量。
     *
     * @return
     */
    String getTickersApi();

    /**
     * 获取合约的最新成交价、买一价、卖一价和24交易量。
     *
     * @param instrumentId
     * @return
     */
    String getTickerApi(String instrumentId);

    /**
     * 获取合约的成交记录。
     * @param instrumentId
     * @param from
     * @param to
     * @param limit
     * @return
     */
    String getTradesApi(String instrumentId, String from, String to, String limit);

    /**
     * 获取合约的K线数据。
     * @param instrumentId
     * @param start
     * @param end
     * @param granularity
     * @return
     */
    String getCandlesApi(String instrumentId, String start, String end, String granularity);

    /**
     * 获取币种指数。
     * @param instrumentId
     * @return
     */
    String getIndexApi(String instrumentId);

    /**
     * 获取法币汇率。
     * @return
     */
    String getRateApi();

    /**
     * 获取合约整个平台的总持仓量。
     * @param instrumentId
     * @return
     */
    String getOpenInterestApi(String instrumentId);

    /**
     * 获取合约当前开仓的最高买价和最低卖价。
     * @param instrumentId
     * @return
     */
    String getPriceLimitApi(String instrumentId);

    /**
     * 获取合约爆仓单。
     * @param instrumentId
     * @param status
     * @param from
     * @param to
     * @param limit
     * @return
     */
    String getLiquidationApi(String instrumentId, String status, String from, String to, String limit);

    /**
     * 获取合约下一次的结算时间。
     * @param instrumentId
     * @return
     */
    String getFundingTimeApi(String instrumentId);

    /**
     * 获取合约历史资金费率
     * @param instrumentId
     * @param from
     * @param to
     * @param limit
     * @return
     */
    String getHistoricalFundingRateApi(String instrumentId, String from, String to, String limit);

    /**
     * 获取合约标记价格
     * @return
     */
    String getMarkPriceApi(String instrumentId);
}
