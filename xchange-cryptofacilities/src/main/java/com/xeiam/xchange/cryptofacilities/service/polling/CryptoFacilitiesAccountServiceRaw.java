package com.xeiam.xchange.cryptofacilities.service.polling;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.cryptofacilities.dto.account.CryptoFacilitiesBalance;

/**
 * @author Jean-Christophe Laruelle
 */

public class CryptoFacilitiesAccountServiceRaw extends CryptoFacilitiesBasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CryptoFacilitiesAccountServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public Map<String, BigDecimal> getCryptoFacilitiesBalance() throws IOException {

    CryptoFacilitiesBalance balanceResult = cryptoFacilities.balance(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory());
    
    return balanceResult.getBalances();
  }

}
