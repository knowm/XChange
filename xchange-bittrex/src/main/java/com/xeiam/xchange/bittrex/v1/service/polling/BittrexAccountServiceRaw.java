package com.xeiam.xchange.bittrex.v1.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bittrex.v1.BittrexAuthenticated;
import com.xeiam.xchange.bittrex.v1.dto.account.BittrexBalance;
import com.xeiam.xchange.bittrex.v1.dto.account.BittrexBalancesResponse;
import com.xeiam.xchange.bittrex.v1.dto.account.BittrexDepositAddressResponse;
import com.xeiam.xchange.exceptions.ExchangeException;

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

    if (response.getSuccess()) {
      return response.getResult();
    }
    else {
      throw new ExchangeException(response.getMessage());
    }
  }

  public String getBittrexDepositAddress(String currency) throws IOException {

    BittrexDepositAddressResponse response = bittrex.getdepositaddress(apiKey, signatureCreator, String.valueOf(nextNonce()), currency);
    if (response.getSuccess()) {
      return response.getResult().getAddress();
    }
    else {
      throw new ExchangeException(response.getMessage());
    }
  }

}