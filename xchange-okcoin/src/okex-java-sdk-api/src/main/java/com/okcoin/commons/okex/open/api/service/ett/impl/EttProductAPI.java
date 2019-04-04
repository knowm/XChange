package com.okcoin.commons.okex.open.api.service.ett.impl;

import com.okcoin.commons.okex.open.api.bean.ett.result.EttConstituentsResult;
import com.okcoin.commons.okex.open.api.bean.ett.result.EttSettlementDefinePrice;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

/**
 * @author chuping.cui
 * @date 2018/7/5
 */
interface EttProductAPI {

    @GET("/api/ett/v3/constituents/{ett}")
    Call<EttConstituentsResult> getConstituents(@Path("ett") String ett);

    @GET("/api/ett/v3/define-price/{ett}")
    Call<List<EttSettlementDefinePrice>> getDefinePrice(@Path("ett") String ett);

}
