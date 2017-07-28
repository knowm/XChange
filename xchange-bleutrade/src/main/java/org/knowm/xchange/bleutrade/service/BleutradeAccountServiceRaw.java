package org.knowm.xchange.bleutrade.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bleutrade.BleutradeException;
import org.knowm.xchange.bleutrade.dto.account.BleutradeBalance;
import org.knowm.xchange.bleutrade.dto.account.BleutradeBalanceReturn;
import org.knowm.xchange.bleutrade.dto.account.BleutradeBalancesReturn;
import org.knowm.xchange.bleutrade.dto.account.BleutradeDepositAddress;
import org.knowm.xchange.bleutrade.dto.account.BleutradeDepositAddressReturn;
import org.knowm.xchange.bleutrade.dto.account.BleutradeWithdrawReturn;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.exceptions.ExchangeException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class BleutradeAccountServiceRaw extends BleutradeBaseService {
  
  public BleutradeAccountServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public String withdraw(Currency currency, BigDecimal amount, String address) throws IOException {
    BleutradeWithdrawReturn response = bleutrade.withdraw(apiKey, signatureCreator, exchange.getNonceFactory(), currency.getCurrencyCode(), amount, address);

    if(!response.success) {
      throw new ExchangeException("Withdraw funds failed: " + response.toString());
    }

    return response.message;
  }

  public BleutradeDepositAddress getBleutradeDepositAddress(String currency) throws IOException {

    try {
      BleutradeDepositAddressReturn response = bleutrade.getDepositAddress(apiKey, signatureCreator, exchange.getNonceFactory(), currency);

      if (!response.getSuccess()) {
        throw new ExchangeException(response.getMessage());
      }

      return response.getResult();
    } catch (BleutradeException e) {
      throw new ExchangeException(e);
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
      throw new ExchangeException(e);
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
      throw new ExchangeException(e);
    }
  }

}
