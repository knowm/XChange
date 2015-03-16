package com.xeiam.xchange.bittrex.v1.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bittrex.v1.dto.account.BittrexBalance;
import com.xeiam.xchange.bittrex.v1.dto.account.BittrexBalancesResponse;
import com.xeiam.xchange.bittrex.v1.dto.account.BittrexDepositAddressResponse;
import com.xeiam.xchange.exceptions.ExchangeException;

public class BittrexAccountServiceRaw extends BittrexBasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BittrexAccountServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public List<BittrexBalance> getBittrexAccountInfo() throws IOException {

    BittrexBalancesResponse response = bittrexAuthenticated.balances(apiKey, signatureCreator, exchange.getNonceFactory());

    if (response.getSuccess()) {
      return response.getResult();
    } else {
      throw new ExchangeException(response.getMessage());
    }
  }

  public String getBittrexDepositAddress(String currency) throws IOException {

    BittrexDepositAddressResponse response = bittrexAuthenticated.getdepositaddress(apiKey, signatureCreator, exchange.getNonceFactory(), currency);
    if (response.getSuccess()) {
      return response.getResult().getAddress();
    } else {
      throw new ExchangeException(response.getMessage());
    }
  }

}