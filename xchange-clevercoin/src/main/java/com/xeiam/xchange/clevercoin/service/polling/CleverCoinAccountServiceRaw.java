package com.xeiam.xchange.clevercoin.service.polling;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.clevercoin.CleverCoinAuthenticated;
import com.xeiam.xchange.clevercoin.dto.account.CleverCoinBalance;
import com.xeiam.xchange.clevercoin.dto.account.CleverCoinDepositAddress;
import com.xeiam.xchange.clevercoin.dto.account.CleverCoinRippleDepositAddress;
import com.xeiam.xchange.clevercoin.dto.account.CleverCoinWithdrawal;
import com.xeiam.xchange.clevercoin.dto.account.DepositTransaction;
import com.xeiam.xchange.clevercoin.dto.account.WithdrawalRequest;
import com.xeiam.xchange.clevercoin.service.CleverCoinDigest;
import com.xeiam.xchange.exceptions.ExchangeException;

/**
 * @author Karsten Nilsen & Konstantin Indjov
 */
public class CleverCoinAccountServiceRaw extends CleverCoinBasePollingService {

  private final CleverCoinDigest signatureCreator;
  private final CleverCoinAuthenticated CleverCoinAuthenticated;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected CleverCoinAccountServiceRaw(Exchange exchange) {

    super(exchange);

    this.CleverCoinAuthenticated = RestProxyFactory.createProxy(CleverCoinAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    this.signatureCreator = CleverCoinDigest.createInstance(exchange.getExchangeSpecification().getSecretKey(), exchange.getExchangeSpecification().getApiKey());
  }

  public CleverCoinBalance[] getCleverCoinBalance() throws IOException {

    return CleverCoinAuthenticated.getBalance(exchange.getExchangeSpecification().getApiKey(), signatureCreator,
        exchange.getNonceFactory());
  }

  public CleverCoinWithdrawal withdrawCleverCoinFunds(BigDecimal amount, final String address) throws IOException {
	
	// amount = amount including the 0.0001 BTC network fee
    final CleverCoinWithdrawal response = CleverCoinAuthenticated.withdrawBitcoin(exchange.getExchangeSpecification().getApiKey(), signatureCreator,
        exchange.getNonceFactory(), amount, address);

    if (response.getError() != null) {
      throw new ExchangeException("Withdrawing funds from CleverCoin failed: " + response.getError());
    }
    return response;

  }

  public CleverCoinDepositAddress getCleverCoinBitcoinDepositAddress() throws IOException {

    final CleverCoinDepositAddress response = CleverCoinAuthenticated.getBitcoinDepositAddress(exchange.getExchangeSpecification().getApiKey(),
        signatureCreator, exchange.getNonceFactory());
    if (response.getError() != null) {
      throw new ExchangeException("Requesting Bitcoin deposit address failed: " + response.getError());
    }
    return response;
  }

}
