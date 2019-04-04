package com.okcoin.commons.okex.open.api.test.swap;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.swap.result.*;
import com.okcoin.commons.okex.open.api.service.swap.SwapMarketAPIService;
import com.okcoin.commons.okex.open.api.service.swap.impl.SwapMarketAPIServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class SwapMarketTest extends SwapBaseTest {
    private SwapMarketAPIService swapMarketAPIService;

    @Before
    public void before() {
        config = config();
        swapMarketAPIService = new SwapMarketAPIServiceImpl(config);
    }


    @Test
    public void getContractsApi() {
        String contractsApi = swapMarketAPIService.getContractsApi();
        if (contractsApi.startsWith("{")) {
            System.out.println(contractsApi);
        } else {
            List<ApiContractVO> list = JSONArray.parseArray(contractsApi, ApiContractVO.class);
            System.out.println(contractsApi);
            list.forEach(contract -> System.out.println(contract.getInstrument_id()));
        }
    }

    @Test
    public void getDepthApi() {
        String depthApi = swapMarketAPIService.getDepthApi(instrument_id, "1");
        DepthVO depthVO = JSONObject.parseObject(depthApi, DepthVO.class);
        System.out.println(depthVO.getAsks());
    }


    @Test
    public void getTickersApi() {
        String tickersApi = swapMarketAPIService.getTickersApi();
        if (tickersApi.startsWith("{")) {
            System.out.println(tickersApi);
        } else {
            List<ApiTickerVO> list = JSONArray.parseArray(tickersApi, ApiTickerVO.class);
            list.forEach(vo -> System.out.println(vo.getInstrument_id()));
            System.out.println(tickersApi);
        }
    }


    @Test
    public void getTickerApi() {
        String tickerApi = swapMarketAPIService.getTickerApi(instrument_id);
        ApiTickerVO apiTickerVO = JSONObject.parseObject(tickerApi, ApiTickerVO.class);
        System.out.println(tickerApi);
        System.out.println(apiTickerVO.getInstrument_id());
    }


    @Test
    public void getTradesApi() {
        String tradesApi = swapMarketAPIService.getTradesApi(instrument_id, null, null, null);
        if (tradesApi.startsWith("{")) {
            System.out.println(tradesApi);
        } else {
            List<ApiDealVO> apiDealVOS = JSONArray.parseArray(tradesApi, ApiDealVO.class);
            apiDealVOS.forEach(vo -> System.out.println(vo.getTimestamp()));
            System.out.println(tradesApi);
        }
    }


    @Test
    public void getCandlesApi() {
        String candlesApi = swapMarketAPIService.getCandlesApi(instrument_id, null, null, "60");
        candlesApi = candlesApi.replaceAll("\\[", "\\{");
        if (candlesApi.lastIndexOf("\\]") != candlesApi.length()) {
            candlesApi = candlesApi.replace("\\]", "\\}");
        }
        if (candlesApi.startsWith("{")) {
            System.out.println(candlesApi);
        } else {
            List<ApiKlineVO> apiKlineVOS = JSONArray.parseArray(candlesApi, ApiKlineVO.class);
            apiKlineVOS.forEach(vo -> System.out.println(vo.getTimestamp()));
            System.out.println(candlesApi);
        }

    }


    @Test
    public void getIndexApi() {
        String indexApi = swapMarketAPIService.getIndexApi(instrument_id);
        ApiIndexVO apiIndexVO = JSONObject.parseObject(indexApi, ApiIndexVO.class);
        System.out.println(indexApi);
        System.out.println(apiIndexVO);
    }


    @Test
    public void getRateApi() {
        String rateApi = swapMarketAPIService.getRateApi();
        ApiRateVO apiRateVO = JSONObject.parseObject(rateApi, ApiRateVO.class);
        System.out.println(apiRateVO.getInstrument_id());
        System.out.println(rateApi);
    }


    @Test
    public void getOpenInterestApi() {
        String openInterestApi = swapMarketAPIService.getOpenInterestApi(instrument_id);
        ApiOpenInterestVO apiOpenInterestVO = JSONObject.parseObject(openInterestApi, ApiOpenInterestVO.class);
        System.out.println(apiOpenInterestVO.getTimestamp());
        System.out.println(openInterestApi);
    }


    @Test
    public void getPriceLimitApi() {
        String priceLimitApi = swapMarketAPIService.getPriceLimitApi(instrument_id);
        ApiPriceLimitVO apiPriceLimitVO = JSONObject.parseObject(priceLimitApi, ApiPriceLimitVO.class);
        System.out.println(apiPriceLimitVO);
        System.out.println(priceLimitApi);
    }


    @Test
    public void getLiquidationApi() {
        String liquidationApi = swapMarketAPIService.getLiquidationApi(instrument_id, "1", "1", "", "10");
        if (liquidationApi.startsWith("{")) {
            System.out.println(liquidationApi);
        } else {
            List<ApiLiquidationVO> apiLiquidationVOS = JSONArray.parseArray(liquidationApi, ApiLiquidationVO.class);
            apiLiquidationVOS.forEach(vo -> System.out.println(vo.getInstrument_id()));
        }
    }


    @Test
    public void getFundingTimeApi() {
        String fundingTimeApi = swapMarketAPIService.getFundingTimeApi(instrument_id);
        ApiFundingTimeVO apiFundingTimeVO = JSONObject.parseObject(fundingTimeApi, ApiFundingTimeVO.class);
        System.out.println(apiFundingTimeVO.getInstrument_id());
    }


    @Test
    public void getHistoricalFundingRateApi() {
        String historicalFundingRateApi = swapMarketAPIService.getHistoricalFundingRateApi(instrument_id, "1", "", "10");
        if (historicalFundingRateApi.startsWith("{")) {
            System.out.println(historicalFundingRateApi);
        } else {
            List<ApiFundingRateVO> apiFundingRateVOS = JSONArray.parseArray(historicalFundingRateApi, ApiFundingRateVO.class);
            apiFundingRateVOS.forEach(vo -> System.out.println(vo.getFunding_rate()));
        }
    }


    @Test
    public void getMarkPriceApi() {
        String markPriceApi = swapMarketAPIService.getMarkPriceApi(instrument_id);
        ApiMarkPriceVO apiMarkPriceVO = JSONObject.parseObject(markPriceApi, ApiMarkPriceVO.class);
        System.out.println(apiMarkPriceVO.getInstrument_id());
    }

}
