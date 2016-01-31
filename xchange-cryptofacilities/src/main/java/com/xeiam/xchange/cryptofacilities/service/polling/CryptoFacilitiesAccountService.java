package com.xeiam.xchange.cryptofacilities.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.cryptofacilities.CryptoFacilitiesAdapters;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

/**
 * @author Jean-Christophe Laruelle
 */

public class CryptoFacilitiesAccountService extends CryptoFacilitiesAccountServiceRaw implements PollingAccountService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CryptoFacilitiesAccountService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

	  return CryptoFacilitiesAdapters.adaptBalance(getCryptoFacilitiesBalance(), exchange.getExchangeSpecification().getUserName());
	  
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {
	  
	  throw new NotAvailableFromExchangeException();
	  
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {
    
	  throw new NotAvailableFromExchangeException();
	  
  }

}
