package com.okcoin.commons.okex.open.api.test.swap;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okcoin.commons.okex.open.api.bean.swap.param.LevelRateParam;
import com.okcoin.commons.okex.open.api.bean.swap.result.ApiAccountsVO;
import com.okcoin.commons.okex.open.api.bean.swap.result.ApiDealDetailVO;
import com.okcoin.commons.okex.open.api.bean.swap.result.ApiPositionsVO;
import com.okcoin.commons.okex.open.api.bean.swap.result.ApiUserRiskVO;
import com.okcoin.commons.okex.open.api.service.swap.SwapUserAPIServive;
import com.okcoin.commons.okex.open.api.service.swap.impl.SwapUserAPIServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class SwapUserTest extends SwapBaseTest {
    private SwapUserAPIServive swapUserAPIServive;

    @Before
    public void before() {
        config = config();
        swapUserAPIServive = new SwapUserAPIServiceImpl(config);
    }

    @Test
    public void getPosition() {
        String jsonObject = swapUserAPIServive.getPosition("LTC-USD-SWAP");
        ApiPositionsVO apiPositionsVO = JSONObject.parseObject(jsonObject, ApiPositionsVO.class);
        System.out.println("success");
        apiPositionsVO.getHolding().forEach(vp -> System.out.println(apiPositionsVO.getMargin_mode()));
    }

    @Test
    public void getAccounts() {
        String jsonObject = swapUserAPIServive.getAccounts();
        ApiAccountsVO apiAccountsVO = JSONObject.parseObject(jsonObject, ApiAccountsVO.class);
        System.out.println("success");
        apiAccountsVO.getInfo().forEach(vo -> System.out.println(vo.getInstrument_id()));
    }

    @Test
    public void selectAccount() {
        String jsonObject = swapUserAPIServive.selectAccount(instrument_id);
        System.out.println("success");
        System.out.println(jsonObject);

    }

    @Test
    public void selectContractSettings() {
        String jsonObject = swapUserAPIServive.selectContractSettings(instrument_id);
        ApiUserRiskVO apiUserRiskVO = JSONObject.parseObject(jsonObject, ApiUserRiskVO.class);
        System.out.println("success");
        System.out.println(apiUserRiskVO.getInstrument_id());
    }

    @Test
    public void updateLevelRate() {
        LevelRateParam levelRateParam = new LevelRateParam();
        levelRateParam.setLevelRate(new BigDecimal(1));
        levelRateParam.setSide(1);
        String jsonObject = swapUserAPIServive.updateLevelRate(instrument_id, levelRateParam);
        ApiUserRiskVO apiUserRiskVO = JSONObject.parseObject(jsonObject, ApiUserRiskVO.class);
        System.out.println("success");
        System.out.println(apiUserRiskVO.getInstrument_id());
    }

    @Test
    public void selectOrders() {
        String jsonObject = swapUserAPIServive.selectOrders(instrument_id, "1", "", "", "20");
        System.out.println("success");
        System.out.println(jsonObject);
    }

    @Test
    public void selectOrder() {
        String jsonObject = swapUserAPIServive.selectOrder(instrument_id, "123123");
        System.out.println("success");
        System.out.println(jsonObject);
    }

    @Test
    public void selectDealDetail(){
        String jsonArray = swapUserAPIServive.selectDealDetail(instrument_id,"123123","","","");
        if(jsonArray.startsWith("{")){
            System.out.println(jsonArray);
        }
        else {
            List<ApiDealDetailVO> apiDealDetailVOS = JSONArray.parseArray(jsonArray, ApiDealDetailVO.class);
            apiDealDetailVOS.forEach(vo -> System.out.println(vo.getInstrument_id()));
        }
    }
    @Test
    public void getLedger() {
        String jsonArray = swapUserAPIServive.getLedger(instrument_id, "1", "", "30");
        System.out.println("success");
        System.out.println(jsonArray);
    }

    @Test
    public void getHolds() {
        String jsonObject = swapUserAPIServive.getHolds(instrument_id);
        System.out.println("success");
        System.out.println(jsonObject);
    }

}
