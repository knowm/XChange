package com.okcoin.commons.okex.open.api.service.spot;

import com.okcoin.commons.okex.open.api.bean.spot.result.*;

import java.math.BigDecimal;
import java.util.List;

public interface SpotProductAPIService {

    /**
     * 单个币对行情
     *
     * @param product
     * @return
     */
    Ticker getTickerByProductId(String product);

    /**
     * 行情列表
     *
     * @return
     */
    List<Ticker> getTickers();

    /**
     * @param product
     * @param size
     * @param depth
     * @return
     */
    Book bookProductsByProductId(String product, String size, BigDecimal depth);

    /**
     * 币对列表
     *
     * @return
     */
    List<Product> getProducts();

    /**
     * 交易列表
     *
     * @param product
     * @param from
     * @param to
     * @param limit
     * @return
     */
    List<Trade> getTrades(String product, String from, String to, String limit);

    /**
     * @param product
     * @param granularity
     * @param start
     * @param end
     * @return
     */
    List<KlineDto> getCandles(String product, String granularity, String start, String end);

    List<String[]> getCandles_1(String product, String granularity, String start, String end);

}
