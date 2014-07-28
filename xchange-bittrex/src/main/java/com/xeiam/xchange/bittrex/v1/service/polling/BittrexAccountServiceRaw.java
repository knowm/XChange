package com.xeiam.xchange.bittrex.v1.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bittrex.v1.BittrexAuthenticated;
import com.xeiam.xchange.bittrex.v1.dto.account.BittrexBalance;
import com.xeiam.xchange.bittrex.v1.dto.account.BittrexBalancesResponse;

public class BittrexAccountServiceRaw extends BittrexBasePollingService<BittrexAuthenticated> {

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BittrexAccountServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(BittrexAuthenticated.class, exchangeSpecification);
  }

  public List<BittrexBalance> getBittrexAccountInfo() throws IOException {

    BittrexBalancesResponse response = bittrex.balances(apiKey, signatureCreator, String.valueOf(nextNonce()));

    return response.getResult();
  }

}