package com.xeiam.xchange.bleutrade.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bleutrade.BleutradeException;
import com.xeiam.xchange.bleutrade.dto.account.BleutradeBalance;
import com.xeiam.xchange.bleutrade.dto.account.BleutradeBalanceReturn;
import com.xeiam.xchange.bleutrade.dto.account.BleutradeBalancesReturn;
import com.xeiam.xchange.bleutrade.dto.account.BleutradeDepositAddress;
import com.xeiam.xchange.bleutrade.dto.account.BleutradeDepositAddressReturn;
import com.xeiam.xchange.exceptions.ExchangeException;

public class BleutradeAccountServiceRaw extends BleutradeBasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BleutradeAccountServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public BleutradeDepositAddress getBleutradeDepositAddress(String currency) throws IOException {

    try {
      BleutradeDepositAddressReturn response = bleutrade.getDepositAddress(apiKey, signatureCreator, exchange.getNonceFactory(), currency);

      if (!response.getSuccess()) {
        throw new ExchangeException(response.getMessage());
      }

      return response.getResult();
    } catch (BleutradeException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public BleutradeBalance getBleutradeBalance(String currency) throws IOException {

    try {
      BleutradeBalanceReturn response = bleutrade.getBalance(apiKey, signatureCreator, exchange.getNonceFactory(), currency);

      if (!response.getSuccess()) {
        throw new ExchangeException(response.getMessage());
      }

      return response.getResult();
    } catch (BleutradeException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public List<BleutradeBalance> getBleutradeBalances() throws IOException {

    try {
      BleutradeBalancesReturn response = bleutrade.getBalances(apiKey, signatureCreator, exchange.getNonceFactory());

      if (!response.getSuccess()) {
        throw new ExchangeException(response.getMessage());
      }

      return response.getResult();
    } catch (BleutradeException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

}
