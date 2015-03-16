package com.xeiam.xchange.bitbay.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitbay.Bitbay;
import com.xeiam.xchange.bitbay.BitbayAuthentiacated;
import com.xeiam.xchange.bitbay.dto.account.BitbayAccount;
import com.xeiam.xchange.bitbay.dto.marketdata.BitbayOrderBook;
import com.xeiam.xchange.bitbay.dto.marketdata.BitbayTicker;
import com.xeiam.xchange.bitbay.dto.marketdata.BitbayTrade;
import com.xeiam.xchange.bitbay.service.BitbayDigest;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.account.AccountInfo;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import java.io.IOException;
import java.util.Date;

/**
 * @author yarkh
 */
public class BitbayAccountServiceRaw extends BitbayBasePollingService {

  protected BitbayAuthentiacated bitbay;
  protected final ParamsDigest signatureCreator;
  protected final String apiKey;

  protected BitbayAccountServiceRaw(Exchange exchange) {

      super(exchange);
      this.bitbay = RestProxyFactory.createProxy(BitbayAuthentiacated.class, exchange.getExchangeSpecification().getSslUri());
      this.apiKey = exchange.getExchangeSpecification().getApiKey();
      this.signatureCreator = BitbayDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

    protected BitbayAccount getBitbayAccountInfo(String currency) throws IOException {

        BitbayAccount info = bitbay.info(apiKey, signatureCreator, new Date().getTime()/1000, currency);
        return info;
    }

}
