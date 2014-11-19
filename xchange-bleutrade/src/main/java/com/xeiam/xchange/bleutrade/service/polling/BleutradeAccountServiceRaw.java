package com.xeiam.xchange.bleutrade.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bleutrade.BleutradeAuthenticated;
import com.xeiam.xchange.bleutrade.BleutradeException;
import com.xeiam.xchange.bleutrade.dto.account.BleutradeBalance;
import com.xeiam.xchange.bleutrade.dto.account.BleutradeBalanceReturn;
import com.xeiam.xchange.bleutrade.dto.account.BleutradeBalancesReturn;
import com.xeiam.xchange.bleutrade.dto.account.BleutradeDepositAddress;
import com.xeiam.xchange.bleutrade.dto.account.BleutradeDepositAddressReturn;

public class BleutradeAccountServiceRaw extends BleutradeBasePollingService<BleutradeAuthenticated> {

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BleutradeAccountServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(BleutradeAuthenticated.class, exchangeSpecification);
  }

  public BleutradeDepositAddress getBleutradeDepositAddress(String currency) throws IOException {

    try {
      BleutradeDepositAddressReturn response = bleutrade.getDepositAddress(apiKey, signatureCreator, String.valueOf(nextNonce()), currency);

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
      BleutradeBalanceReturn response = bleutrade.getBalance(apiKey, signatureCreator, String.valueOf(nextNonce()), currency);

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
      BleutradeBalancesReturn response = bleutrade.getBalances(apiKey, signatureCreator, String.valueOf(nextNonce()));

      if (!response.getSuccess()) {
        throw new ExchangeException(response.getMessage());
      }

      return response.getResult();
    } catch (BleutradeException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

}
