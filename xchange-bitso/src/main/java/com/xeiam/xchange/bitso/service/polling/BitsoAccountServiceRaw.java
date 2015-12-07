package com.xeiam.xchange.bitso.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitso.BitsoAuthenticated;
import com.xeiam.xchange.bitso.dto.account.BitsoBalance;
import com.xeiam.xchange.bitso.dto.account.BitsoDepositAddress;
import com.xeiam.xchange.bitso.service.BitsoDigest;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.exceptions.ExchangeException;

public class BitsoAccountServiceRaw extends BitsoBasePollingService {

  private final BitsoDigest signatureCreator;
  private final BitsoAuthenticated bitsoAuthenticated;

  protected BitsoAccountServiceRaw(Exchange exchange) {
    super(exchange);

    this.bitsoAuthenticated = RestProxyFactory.createProxy(BitsoAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    this.signatureCreator = BitsoDigest.createInstance(exchange.getExchangeSpecification().getSecretKey(),
        exchange.getExchangeSpecification().getUserName(), exchange.getExchangeSpecification().getApiKey());
  }

  public BitsoBalance getBitsoBalance() throws IOException {

    BitsoBalance bitsoBalance = bitsoAuthenticated.getBalance(exchange.getExchangeSpecification().getApiKey(), signatureCreator,
        exchange.getNonceFactory());
    if (bitsoBalance.getError() != null) {
      throw new ExchangeException("Error getting balance. " + bitsoBalance.getError());
    }
    return bitsoBalance;
  }

  public String withdrawBitsoFunds(BigDecimal amount, final String address) throws IOException {

    final String response = bitsoAuthenticated.withdrawBitcoin(exchange.getExchangeSpecification().getApiKey(), signatureCreator,
        exchange.getNonceFactory(), amount, address);
    if (!"ok".equals(response)) {
      throw new ExchangeException("Withdrawing funds from Bitso failed: " + response);
    }
    return response;

  }

  public BitsoDepositAddress getBitsoBitcoinDepositAddress() throws IOException {

    final BitsoDepositAddress response = bitsoAuthenticated.getBitcoinDepositAddress(exchange.getExchangeSpecification().getApiKey(),
        signatureCreator, exchange.getNonceFactory());
    if (response.getError() != null) {
      throw new ExchangeException("Requesting Bitcoin deposit address failed: " + response.getError());
    }
    return response;
  }

  /**
   * Withdraws funds to Ripple and associates the receiving Ripple address with the Bitso account for deposits. NOTE: The Ripple address associated to
   * your account for deposits will be updated accordingly! Please ensure that any subsequent Ripple funding emanates from this address.
   *
   * @return true if withdrawal was successful.
   */
  public boolean withdrawToRipple(BigDecimal amount, Currency currency, String rippleAddress) throws IOException {

    final String result = bitsoAuthenticated.withdrawToRipple(exchange.getExchangeSpecification().getApiKey(), signatureCreator,
        exchange.getNonceFactory(), amount, currency.getCurrencyCode(), rippleAddress);
    return "ok".equals(result);
  }
}
