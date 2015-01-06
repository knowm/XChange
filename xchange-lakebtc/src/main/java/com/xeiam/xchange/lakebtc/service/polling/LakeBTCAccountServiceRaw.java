package com.xeiam.xchange.lakebtc.service.polling;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.lakebtc.LakeBTCAuthenticated;
import com.xeiam.xchange.lakebtc.LakeBTCUtil;
import com.xeiam.xchange.lakebtc.dto.account.LakeBTCAccountInfoResponse;
import com.xeiam.xchange.lakebtc.dto.account.LakeBTCAccountRequest;
import si.mazi.rescu.SynchronizedValueFactory;

import java.io.IOException;

/**
 * User: cristian.lucaci
 * Date: 10/3/2014
 * Time: 5:02 PM
 */
public class LakeBTCAccountServiceRaw extends LakeBTCBasePollingService<LakeBTCAuthenticated> {


    /**
     * Constructor
     *
     * @param exchangeSpecification The {@link ExchangeSpecification}
     */
    public LakeBTCAccountServiceRaw(ExchangeSpecification exchangeSpecification, SynchronizedValueFactory<Long> tonceFactory) {
      super(LakeBTCAuthenticated.class, exchangeSpecification, tonceFactory);
    }

    public LakeBTCAccountInfoResponse getLakeBTCAccountInfo() throws IOException {
        return checkResult(btcLakeBTC.getAccountInfo(signatureCreator, LakeBTCUtil.getNonce(), new LakeBTCAccountRequest()));
    }


}
