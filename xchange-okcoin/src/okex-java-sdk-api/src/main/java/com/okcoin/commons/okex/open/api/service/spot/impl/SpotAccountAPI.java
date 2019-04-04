package com.okcoin.commons.okex.open.api.service.spot.impl;

import com.okcoin.commons.okex.open.api.bean.spot.result.Account;
import com.okcoin.commons.okex.open.api.bean.spot.result.Ledger;
import com.okcoin.commons.okex.open.api.bean.spot.result.ServerTimeDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;
import java.util.Map;

public interface SpotAccountAPI {

    /**
     * 获取系统时间
     *
     * @return
     */
    @GET("api/spot/v3/time")
    Call<ServerTimeDto> time();

    /**
     * 挖矿相关数据
     *
     * @return
     */
    @GET("api/spot/v3/miningdata")
    Call<Map<String, Object>> getMiningdata();

    /**
     * 币币资产
     *
     * @return
     */
    @GET("api/spot/v3/accounts")
    Call<List<Account>> getAccounts();

    /**
     * 币币指定币资产
     *
     * @param currency
     * @return
     */
    @GET("api/spot/v3/accounts/{currency}")
    Call<Account> getAccountByCurrency(@Path("currency") String currency);

    /**
     * 币币账单列表
     *
     * @param currency
     * @param from
     * @param to
     * @param limit
     * @return
     */
    @GET("api/spot/v3/accounts/{currency}/ledger")
    Call<List<Ledger>> getLedgersByCurrency(@Path("currency") String currency,
                                            @Query("from") String from,
                                            @Query("to") String to,
                                            @Query("limit") String limit);

}
