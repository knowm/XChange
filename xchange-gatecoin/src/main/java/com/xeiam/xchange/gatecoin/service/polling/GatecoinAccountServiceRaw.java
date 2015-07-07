package com.xeiam.xchange.gatecoin.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.gatecoin.GatecoinAuthenticated;
import com.xeiam.xchange.gatecoin.service.GatecoinDigest;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.gatecoin.dto.account.Results.GatecoinBalanceResult;
import com.xeiam.xchange.gatecoin.dto.account.Results.GatecoinDepositAddressResult;
import com.xeiam.xchange.gatecoin.dto.account.Results.GatecoinWithdrawResult;
import java.util.Date;

/**
 * @author sumedha
 */
public class GatecoinAccountServiceRaw extends GatecoinBasePollingService {

  private final GatecoinDigest signatureCreator;
  private final GatecoinAuthenticated gatecoinAuthenticated;
 private final long now ;
  /**
   * Constructor
   *
   * @param exchange
   */
  protected GatecoinAccountServiceRaw(Exchange exchange) {

    super(exchange);
    now = GetUnixDateNow();
    this.gatecoinAuthenticated = RestProxyFactory.createProxy(GatecoinAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    this.signatureCreator = GatecoinDigest.createInstance(exchange.getExchangeSpecification().getSecretKey(),exchange.getExchangeSpecification().getApiKey(),now);
  }

  public GatecoinBalanceResult getGatecoinBalance() throws IOException {

    GatecoinBalanceResult gatecoinBalanceResult = gatecoinAuthenticated.getUserBalance(exchange.getExchangeSpecification().getApiKey(), signatureCreator, String.valueOf(now));
    if (gatecoinBalanceResult.getResponseStatus().getMessage().equalsIgnoreCase("ok"))
    {
        return gatecoinBalanceResult;      
    }
    throw new ExchangeException("Error getting balance. " + gatecoinBalanceResult.getResponseStatus().getMessage());    
  }

  public GatecoinWithdrawResult withdrawGatecoinFunds(String currency, BigDecimal amount, final String address) throws IOException {

    GatecoinWithdrawResult gatecoinWithdrawalResult = gatecoinAuthenticated.withdrawBitcoin(exchange.getExchangeSpecification().getApiKey(),
            signatureCreator,
            String.valueOf(now),
            currency,
            address,
            amount);
    if (gatecoinWithdrawalResult.getResponseStatus().getMessage().equalsIgnoreCase("ok"))
    {
        return gatecoinWithdrawalResult;      
    }
    throw new ExchangeException("Error withdrawaing funds " + gatecoinWithdrawalResult.getResponseStatus().getMessage());    

  }

  public GatecoinDepositAddressResult getGatecoinDepositAddress() throws IOException {

    GatecoinDepositAddressResult gatecoinDepositAddressResult = gatecoinAuthenticated.getDepositAddress(exchange.getExchangeSpecification().getApiKey(), signatureCreator, String.valueOf(now));
    if (gatecoinDepositAddressResult.getResponseStatus().getMessage().equalsIgnoreCase("ok"))
    {
        return gatecoinDepositAddressResult;
     
    }
     throw new ExchangeException("Requesting Deposit address failed: " + gatecoinDepositAddressResult.getResponseStatus().getMessage());
    
  }
  
 private Long GetUnixDateNow()
  {
     return (new Date()).getTime() / 1000;     
   }
}
