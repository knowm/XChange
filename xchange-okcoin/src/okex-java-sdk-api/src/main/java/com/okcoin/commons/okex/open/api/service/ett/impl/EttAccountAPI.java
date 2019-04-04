package com.okcoin.commons.okex.open.api.service.ett.impl;

import com.okcoin.commons.okex.open.api.bean.ett.result.EttAccount;
import com.okcoin.commons.okex.open.api.bean.ett.result.EttLedger;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

/**
 * @author chuping.cui
 * @date 2018/7/5
 */
interface EttAccountAPI {

    @GET("/api/ett/v3/accounts")
    Call<List<EttAccount>> getAccount();

    @GET("/api/ett/v3/accounts/{currency}")
    Call<EttAccount> getAccount(@Path("currency") String currency);

    @GET("/api/ett/v3/accounts/{currency}/ledger")
    Call<List<EttLedger>> getLedger(@Path("currency") String currency, @Query("before") String before, @Query("after") String after, @Query("limit") int limit);

}
