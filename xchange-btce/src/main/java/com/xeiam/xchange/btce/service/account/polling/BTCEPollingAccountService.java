package com.xeiam.xchange.btce.service.account.polling;

import java.math.BigDecimal;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btce.BTCEAdapters;
import com.xeiam.xchange.btce.BTCEAuthenticated;
import com.xeiam.xchange.btce.dto.marketdata.BTCEAccountInfoResult;
import com.xeiam.xchange.btce.service.BTCEHmacPostBodyDigest;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.proxy.ParamsDigest;
import com.xeiam.xchange.proxy.RestProxyFactory;
import com.xeiam.xchange.service.BasePollingExchangeService;
import com.xeiam.xchange.service.account.polling.PollingAccountService;
import com.xeiam.xchange.utils.HttpTemplate;

/**
* @author Matija Mazi <br/>
*/
public class BTCEPollingAccountService implements PollingAccountService {

  private final String apiKey;
  private final BTCEAuthenticated btce;
  private final ParamsDigest signatureCreator;

  public BTCEPollingAccountService(ExchangeSpecification spec) {

    this.btce = RestProxyFactory.createProxy(BTCEAuthenticated.class, spec.getUri(), new HttpTemplate(), BasePollingExchangeService.createMapper());
    this.signatureCreator = BTCEHmacPostBodyDigest.createInstance(spec.getSecretKey());
    this.apiKey = spec.getApiKey();
  }

  @Override
  public AccountInfo getAccountInfo() {

    BTCEAccountInfoResult info = btce.getInfo(apiKey, signatureCreator, Math.abs((int) System.currentTimeMillis()), null, null, null, null, "ASC", null, null);
    return BTCEAdapters.adaptAccountInfo(info.getValue());
  }

  @Override
  public String withdrawFunds(BigDecimal amount, String address) {
    // todo!
    return null;
  }

  @Override
  public String requestBitcoinDepositAddress(String description, String notificationUrl) {
    // todo!
    return null;
  }
}
