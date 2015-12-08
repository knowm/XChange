package com.xeiam.xchange.cryptofacilities.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesContracts;
import com.xeiam.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesCumulativeBidAsk;
import com.xeiam.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesIndex;
import com.xeiam.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesTicker;
import com.xeiam.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesVolatility;
import com.xeiam.xchange.currency.CurrencyPair;

/**
 * @author Jean-Christophe Laruelle
 */

public class CryptoFacilitiesMarketDataServiceRaw extends CryptoFacilitiesBasePollingService {

	/**
   * Constructor
   *
   * @param exchange
   */
  public CryptoFacilitiesMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public CryptoFacilitiesTicker getCryptoFacilitiesTicker(CurrencyPair currencyPair) throws IOException {

	CryptoFacilitiesTicker ticker = cryptoFacilities.getTicker(currencyPair.base.toString(), currencyPair.counter.toString());

    return ticker;
  }

  public CryptoFacilitiesContracts getCryptoFacilitiesContracts() throws IOException {

    CryptoFacilitiesContracts contracts = cryptoFacilities.getContracts();

    return contracts;
  }

  public CryptoFacilitiesCumulativeBidAsk getCryptoFacilitiesCumulativeBidAsk(CurrencyPair currencyPair)
  {
	  CryptoFacilitiesCumulativeBidAsk cfcbidask = cryptoFacilities.getCumulativeBidAsk(currencyPair.base.toString(), currencyPair.counter.toString());
	  
	  return cfcbidask;
  }

  public CryptoFacilitiesIndex getCryptoFacilitiesIndex()
  {
	  CryptoFacilitiesIndex cfIndex = cryptoFacilities.getIndex();
	  
	  return cfIndex;
  }

  public CryptoFacilitiesVolatility getCryptoFacilitiesVolatility()
  {
	  CryptoFacilitiesVolatility cfVol = cryptoFacilities.getVolatility();
	  
	  return cfVol;
  }

}
