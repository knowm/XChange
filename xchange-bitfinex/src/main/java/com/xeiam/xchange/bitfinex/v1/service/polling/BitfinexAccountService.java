package com.xeiam.xchange.bitfinex.v1.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitfinex.v1.BitfinexAdapters;
import com.xeiam.xchange.bitfinex.v1.BitfinexUtils;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

public class BitfinexAccountService extends BitfinexAccountServiceRaw implements PollingAccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitfinexAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return new AccountInfo(BitfinexAdapters.adaptWallet(getBitfinexAccountInfo()));
  }

    /**   
   * Withdrawal suppport   
   * @param currency
   * @param amount
   * @param address
   * @return
   * @throws IOException 
   */
  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {
    //determine withdrawal type
     String type = BitfinexUtils.convertToBitfinexWithdrawalType(currency.toString());
    //Bitfinex withdeawal can be from different type of wallets    * 
    // we have to use one of these for now: Exchange - 
    //to be able to withdraw instantly after trading for example
    //The wallet to withdraw from, can be “trading”, “exchange”, or “deposit”.
     String walletSelected= "exchange";
    //We have to convert XEIAM currencies to Bitfinex currencies: can be “bitcoin”, “litecoin” or “darkcoin” or “tether” or “wire”.      
    return withdraw(type, walletSelected, amount, address);    
  }

  @Override
  public String requestDepositAddress(Currency currency, String... arguments) throws IOException {

    throw new NotAvailableFromExchangeException();
  }
}
