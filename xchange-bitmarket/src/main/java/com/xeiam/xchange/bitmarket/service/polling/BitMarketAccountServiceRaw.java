package com.xeiam.xchange.bitmarket.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitmarket.BitMarketAuth;
import com.xeiam.xchange.bitmarket.dto.BitMarketBaseResponse;
import com.xeiam.xchange.bitmarket.dto.account.BitMarketAccount;
import com.xeiam.xchange.bitmarket.service.BitMarketDigest;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import java.io.IOException;
import java.util.Date;

/**
 * @author yarkh
 */
public class BitMarketAccountServiceRaw extends BitMarketBasePollingService {

  protected BitMarketAuth bitMarketAuth;
  protected final ParamsDigest signatureCreator;
  protected final String apiKey;

  protected BitMarketAccountServiceRaw(Exchange exchange) {

      super(exchange);
      this.bitMarketAuth = RestProxyFactory.createProxy(BitMarketAuth.class, exchange.getExchangeSpecification().getSslUri());
      this.apiKey = exchange.getExchangeSpecification().getApiKey();
      this.signatureCreator = BitMarketDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

    protected BitMarketBaseResponse<BitMarketAccount> getBitMarketAccountInfo() throws IOException {
      return bitMarketAuth.info(new Date().getTime()/1000, apiKey, signatureCreator);
    }

}
