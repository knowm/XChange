package com.xeiam.xchange.gatecoin.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.gatecoin.GatecoinAdapters;
import com.xeiam.xchange.gatecoin.dto.account.GatecoinDepositAddress;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.gatecoin.dto.account.Results.GatecoinDepositAddressResult;
import com.xeiam.xchange.gatecoin.dto.account.Results.GatecoinWithdrawResult;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

/**
 * @author Sumedha
 */
public class GatecoinAccountService extends GatecoinAccountServiceRaw implements PollingAccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public GatecoinAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return GatecoinAdapters.adaptAccountInfo(getGatecoinBalance().getBalances(), exchange.getExchangeSpecification().getUserName());
  }
   
  @Override
  public String requestDepositAddress(String currency, String... arguments) throws IOException {

    GatecoinDepositAddressResult result = getGatecoinDepositAddress();
    if(result.getResponseStatus().getMessage().equalsIgnoreCase("ok"))
    {
         GatecoinDepositAddress[] addresses = result.getAddresses();
         if(addresses.length > 0)
             return addresses[0].getAddress();
         else
             return null;    
    }
     return null;  
  }
  
  @Override
  public String withdrawFunds(String currency, BigDecimal amount, String address) throws IOException {

    GatecoinWithdrawResult result = withdrawGatecoinFunds(currency, amount, address);
    if(result.getResponseStatus().getMessage().equalsIgnoreCase("ok"))
    {
      return "Ok";
    }
    else
    {
        return result.getResponseStatus().getMessage();    
    }
  }
}
