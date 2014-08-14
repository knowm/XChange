package com.xeiam.xchange.bitfinex.v1.service.polling;

import java.io.IOException;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitfinex.v1.BitfinexAuthenticated;
import com.xeiam.xchange.bitfinex.v1.dto.account.BitfinexBalancesRequest;
import com.xeiam.xchange.bitfinex.v1.dto.account.BitfinexBalancesResponse;
import com.xeiam.xchange.bitfinex.v1.dto.account.BitfinexMarginInfosRequest;
import com.xeiam.xchange.bitfinex.v1.dto.account.BitfinexMarginInfosResponse;

public class BitfinexAccountServiceRaw extends BitfinexBasePollingService<BitfinexAuthenticated> {

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BitfinexAccountServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(BitfinexAuthenticated.class, exchangeSpecification);
  }

  public BitfinexBalancesResponse[] getBitfinexAccountInfo() throws IOException {

    BitfinexBalancesResponse[] balances = bitfinex.balances(apiKey, payloadCreator, signatureCreator, new BitfinexBalancesRequest(String.valueOf(nextNonce())));

    return balances;
  }
  
  public BitfinexMarginInfosResponse[] getBitfinexMarginInfos() throws IOException {
    
    BitfinexMarginInfosResponse[] marginInfos = bitfinex.marginInfos(apiKey, payloadCreator, signatureCreator, new BitfinexMarginInfosRequest(String.valueOf(nextNonce())));
    
    return marginInfos;
  }

}
